package model;

/**
 * Created by Sangeetha on 12/12/17.
 */
public class Course {
    private String name;
    private int id;
    private String prerequisites;
    private boolean academicStanding;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }


}
