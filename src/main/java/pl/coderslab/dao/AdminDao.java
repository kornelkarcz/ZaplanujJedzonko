package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    // ZAPYTANIA SQL

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins (id, first_name, last_name, email, password, superadmin, enable) VALUES (null, ?,?,?,?,?,?)";

    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins WHERE id = ?";

    private static final String FIND_ALL_ADMIN_QUERY = "SELECT * FROM admins";

    private static final String READ_ADMIN_QUERY = "SELECT * FROM admins WHERE id = ?";

    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ? , last_name = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE id = ?";





    // Get admin by id

    public static Admins read(Integer adminId) {

        Admins admin = new Admins();

        try (Connection connection = DbUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY);

        ) {

            statement.setInt(1, adminId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    admin.setId(resultSet.getInt("id"));

                    admin.setFirstName(resultSet.getString("first_name"));

                    admin.setLastName(resultSet.getString("last_name"));

                    admin.setEmail(resultSet.getString("email"));

               //     admin.setPasswordNoEnryption(resultSet.getString("password"));

                    admin.setSuperadmin(resultSet.getInt("superadmin"));

                    admin.setEnable(resultSet.getBoolean("enable"));

                }

            }

        } catch (Exception e) {

            System.out.println("Problemy z połączeniem z bazą !!!");

            e.printStackTrace();

        }

        return admin;



    }





    //Return all admin

    public static List<Admins> findAll() {

        List<Admins> adminList = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMIN_QUERY);

             ResultSet resultSet = statement.executeQuery()) {



            while (resultSet.next()) {

                Admins adminToAdd = new Admins();

                adminToAdd.setId(resultSet.getInt("id"));

                adminToAdd.setFirstName(resultSet.getString("first_name"));

                adminToAdd.setLastName(resultSet.getString("last_name"));

                adminToAdd.setEmail(resultSet.getString("email"));

         //       adminToAdd.setPasswordNoEnryption(resultSet.getString("password"));

                adminToAdd.setSuperadmin(resultSet.getInt("superadmin"));

                adminToAdd.setEnable(resultSet.getBoolean("enable"));

                adminList.add(adminToAdd);

            }



        } catch (SQLException e) {

            e.printStackTrace();

        }

        return adminList;



    }



    //Create admin

    public static Admins create(Admins admin) {

        try (Connection connection = DbUtil.getConnection();

             PreparedStatement insertStm = connection.prepareStatement(CREATE_ADMIN_QUERY,

                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            insertStm.setString(1, admin.getFirstName());

            insertStm.setString(2, admin.getLastName());

            insertStm.setString(3, admin.getEmail());

            insertStm.setString(4, admin.getPassword());

            insertStm.setInt(5, admin.getSuperadmin());

            insertStm.setBoolean(6, admin.isEnable());

            int result = insertStm.executeUpdate();



            if (result != 1) {

                throw new RuntimeException("Execute update returned " + result);

            }



            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {

                if (generatedKeys.first()) {

                    admin.setId(generatedKeys.getInt(1));

                    return admin;

                } else {

                    throw new RuntimeException("Generated key was not found");

                }



            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }



    //Remove admin

    public static void delete(Integer adminId) {

        try (Connection connection = DbUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY);) {

            statement.setInt(1, adminId);

            statement.executeUpdate();



            boolean deleted = statement.execute();

            if (!deleted) {

                throw new NotFoundException("Product not found");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    //Update admin

    public static void update(Admins admin) {

        try (Connection connection = DbUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY);) {

            statement.setInt(7, admin.getId());

            statement.setString(1, admin.getFirstName());

            statement.setString(2, admin.getLastName());

            statement.setString(3, admin.getEmail());

            statement.setString(4, admin.getPassword());

            statement.setInt(5, admin.getSuperadmin());

            statement.setBoolean(6, admin.isEnable());



            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }



    }



    public static Admins authorization(String emailCandidate, String passwordCandidate) {

        List<Admins> adminsList = AdminDao.findAll();

        for (Admins admin : adminsList) {

            if (emailCandidate.equals(admin.getEmail())) {

                if (BCrypt.checkpw(passwordCandidate,admin.getPassword())) {

                    return admin;

                }

            }

        }

        return null;

    }

}