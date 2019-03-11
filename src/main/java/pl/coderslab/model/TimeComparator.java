package pl.coderslab.model;

import java.util.Comparator;

public class TimeComparator implements Comparator<Plan> {
    @Override
    public int compare(Plan plan1, Plan plan2) {
        return plan2.getCreated().compareTo(plan1.getCreated());
    }
}
