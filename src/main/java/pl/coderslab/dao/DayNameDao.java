package pl.coderslab.dao;

import pl.coderslab.model.Day;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {

    //Zapytania SQL
    private static final String FIND_ALL_DAYS_QUERY = "SELECT * FROM day_name;";


    /**
    * Return all days
    *
    * @return
    * */

    public List<Day> findAll() {
        List<Day> dayList = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAYS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Day dayToAdd = new Day();
                dayToAdd.setId(resultSet.getInt("id"));
                dayToAdd.setName(resultSet.getString("name"));
                dayToAdd.setOrder(resultSet.getInt("order"));
                dayList.add(dayToAdd);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dayList;

    }

}
