package aau.corp.android.app.moodleplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aman Rathi on 20-02-2016.
 */

///////////////////////////////////
// here the data of the course names is stored
// This class was created initially to check whether the dropdown list
///////////////////////////////////

public class Courses_data {

    ///////////////////////////////////
    // Created list for dropdown button
    ///////////////////////////////////
    public static HashMap<String, List<String>> getInfo(){
        HashMap<String, List<String>> my_courses = new HashMap<String, List<String>>();
                List<String> My_Courses = new ArrayList<>();

                My_Courses.add("COP290: Design Practices");
                My_Courses.add("MCL136: Material Removal Process");
                My_Courses.add("HUL281: Technology and Governance");
                My_Courses.add("MCL311: CAD & FEM");
                My_Courses.add("MCL331: Micro & Nano Manufacturing");
                My_Courses.add("HUL361: Manufacturing Systems Design");
        my_courses.put("MY COURSES", My_Courses);
        return my_courses;
    }

    ///////////////////////////////////
    // raw data for the navigation drawer
    ///////////////////////////////////

    public static ArrayList<String> getcourselist(){
        ArrayList<String> My_Courses_list_2 = new ArrayList<>();
        My_Courses_list_2.add("Home");
        My_Courses_list_2.add("COP290: Design Practices");
        My_Courses_list_2.add("MCL136: Material Removal Process");
        My_Courses_list_2.add("HUL281: Technology and Governance");
        My_Courses_list_2.add("MCL311: CAD & FEM");
        My_Courses_list_2.add("MCL331: Micro & Nano Manufacturing");
        My_Courses_list_2.add("HUL361: Manufacturing Systems Design");

        return My_Courses_list_2;
    }
}
