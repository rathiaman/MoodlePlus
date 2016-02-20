package aau.corp.android.app.moodleplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aman Rathi on 20-02-2016.
 */

// here the data of the course names is stored

public class Courses_data {
    public static HashMap<String, List<String>> getInfo(){
        HashMap<String, List<String>> my_courses = new HashMap<String, List<String>>();
                List<String> My_Courses = new ArrayList<>();
                My_Courses.add("COP290: Design Practices");
                My_Courses.add("MCL136: Material Removal Process");
                My_Courses.add("HUL281: Technology and Governance");
                My_Courses.add("COP290: Design Practices");
                My_Courses.add("MCL136: Material Removal Process");
                My_Courses.add("HUL281: Technology and Governance");


        my_courses.put("MY COURSES", My_Courses);
        return my_courses;
    }
}
