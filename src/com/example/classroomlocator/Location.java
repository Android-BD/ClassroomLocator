package com.example.classroomlocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Location extends Activity {

    private Spinner course_name, sections, landmarks, classroom;
    private Button btnSubmit;
    private String coursename, section, roomnumber, landmark;
    DatabaseHelper dbHelper;
    private String stepcount;
   // private static String TABLE_NAME = "ooad_table";
    private static String RoomLocatorLog = "ClassRoomLocator";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dbHelper = dbCreate();
        Log.d(RoomLocatorLog, "oncreate() method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        course_name = (Spinner) findViewById(R.id.course_name);
        sections = (Spinner) findViewById(R.id.sections);
        classroom = (Spinner) findViewById(R.id.classroom);
        landmarks = (Spinner) findViewById(R.id.landmarks);
        addItemsOnCoursename();
        addAllSections();
        addAllRooms();
        addAllLandmarks();
       addListenerOnButton();
    }

    public DatabaseHelper dbCreate(){
        dbHelper = new DatabaseHelper(getBaseContext());
        try {

            dbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
        try {

            dbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
        return dbHelper;
    }

    public void addAllSections(){
        List<String> sectionList = new ArrayList<String>();
        sectionList = dbHelper.getSectionName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                sectionList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sections.setAdapter(dataAdapter);
        dbHelper.close();
    }

    public void addAllRooms(){
        List<String> roomList = new ArrayList<String>();
        roomList = dbHelper.getRoomName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                roomList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classroom.setAdapter(dataAdapter);
        dbHelper.close();
    }


    public void addAllLandmarks(){
        List<String> landList = new ArrayList<String>();
        landList = dbHelper.getLandmarkName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                landList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        landmarks.setAdapter(dataAdapter);
        dbHelper.close();
    }

    public void addItemsOnCoursename(){
        List<String> courseList = new ArrayList<String>();
        //courseList.add("Coursename");
        courseList = dbHelper.getClassName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                courseList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_name.setAdapter(dataAdapter);
        course_name.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                coursename = (String.valueOf(course_name.getSelectedItem()));
                addItemsOnSection(coursename);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dbHelper.close();
    }
    // add items into spinner dynamically
    public void addItemsOnSection(String Course) {
        List<String> sectionList = new ArrayList<String>();
        sectionList.add("Section");
        //RetrieveListInf sectionList = new RetrieveListImpl();
        if(Course.contains("Coursename"))
        {
            Toast.makeText(getApplicationContext(), "Please Select a Course name",
                    Toast.LENGTH_LONG).show();
        }
        else
        {

            sectionList = dbHelper.returnSectionBasedOnClass(Course);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,
                    sectionList);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sections.setAdapter(dataAdapter);
            sections.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    coursename = (String.valueOf(course_name.getSelectedItem()));
                    section = (String.valueOf(sections.getSelectedItem()));
                    addItemsOnClassroom(coursename,section);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            dbHelper.close();
        }
    }

    public void addItemsOnClassroom(String Course, String section) {
        List<String> roomsList = new ArrayList<String>();
        if (Course.equalsIgnoreCase("Coursename")
                && section.equalsIgnoreCase("section")) {
            roomsList = dbHelper.getRoomName();
        }
        else
        {
            roomsList = dbHelper.returnRoomBasedOnClass(Course, section);

        }
        dbHelper.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roomsList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classroom.setAdapter(dataAdapter);
        roomnumber = String.valueOf(classroom.getSelectedItem());
        System.out.println(coursename);


    }


    // get the selected dropdown list value
    public void addListenerOnButton() {


        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                String temp_direction = null;
                List<String> room = new ArrayList<String>();
                String image_name = null;
                Bundle b = new Bundle();
                List<String> direction = new ArrayList<String>();
                List<String> Steps = new ArrayList<String>();
                if (v.getId() == R.id.btnSubmit)
                {
                    roomnumber = String.valueOf(classroom.getSelectedItem());
                    landmark = String.valueOf(landmarks.getSelectedItem());
                    coursename = (String.valueOf(course_name.getSelectedItem()));
                    section = (String.valueOf(sections.getSelectedItem()));


                    if(coursename.contains("Coursename"))
                    {
                        if( section.contains("Section"))
                        {
                            if(roomnumber.contains("Room Number") || landmark.contains("Landmark"))
                            {
                                Toast.makeText(getApplicationContext(), "Please Select both Roomnumber" +
                                                "and landmark",
                                        Toast.LENGTH_LONG).show();
                                flag=0;

                            }
                            else
                            {
                                temp_direction = dbHelper.returnRoomDirections(roomnumber, landmark);
                                image_name = dbHelper.returnImageName(roomnumber, landmark);
                                stepcount = dbHelper.returnSteps(roomnumber, landmark);
                                Log.d(RoomLocatorLog, ""+stepcount);
                                for(String splitstep : stepcount.split(";")){
                                    Steps.add(splitstep);
                                } 
                                for(int i = 0;i<Steps.size();i++){
                                	System.out.println(Steps.get(i));
                                }
                                
                                ArrayList<String> stepsList =(ArrayList<String>) Steps;
                                //       ArrayList<String> noofsteps =(ArrayList<String>) Steps;
                                       b.putStringArrayList("stepsList", stepsList);
                                Log.d("",""+stepsList.size());
                                flag=1;
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Section is only valid if coursename if selected",
                                    Toast.LENGTH_LONG).show();
                            flag=0;
                        }

                    }
                    else
                    {
                        if( section.contains("Section"))
                        {
                            Toast.makeText(getApplicationContext(), "Please Select a section name",
                                    Toast.LENGTH_LONG).show();
                            flag=0;
                        }
                        else
                        {
                            if(roomnumber.contains("RoomNumber") || landmark.contains("Landmark"))
                            {
                                Toast.makeText(getApplicationContext(), "Please Select both Room number" +
                                                "and landmark",
                                        Toast.LENGTH_LONG).show();
                                flag=0;
                            }
                            else
                            {
                                room = dbHelper.returnRoomBasedOnClass(coursename, section);
                                temp_direction = dbHelper.returnRoomDirections(roomnumber, landmark);
                                Toast.makeText(getApplicationContext(), "Room: "+room,
                                        Toast.LENGTH_LONG).show();
                                image_name = dbHelper.returnImageName(roomnumber, landmark);
                                stepcount = dbHelper.returnSteps(roomnumber, landmark);
                                Log.d(RoomLocatorLog, ""+stepcount);
                                for(String splitstep : stepcount.split(";")){
                                    Steps.add(splitstep);
                                } 
                                for(int i = 0;i<Steps.size();i++){
                                	System.out.println(Steps.get(i));
                                }
                                
                                ArrayList<String> stepsList =(ArrayList<String>) Steps;
                                //       ArrayList<String> noofsteps =(ArrayList<String>) Steps;
                                       b.putStringArrayList("stepsList", stepsList);
                                Log.d("",""+stepsList.size());
                                flag=1;
                            }
                        }
                    }

                    dbHelper.close();

                    if(flag == 1)
                    {
                        flag=0;
                        for(String splitval : temp_direction.split(";")){
                            direction.add(splitval);
                        }
                        
                        if(!(direction.isEmpty())){
                            Intent intent = new Intent(Location.this,
                                    DisplayDirections.class);
                           
                            ArrayList<String> directionKey =(ArrayList<String>) direction;
                     //       ArrayList<String> noofsteps =(ArrayList<String>) Steps;
                            b.putStringArrayList("direction", directionKey);
                         //  b.putStringArrayList("stepcount",noofsteps);
                            b.putString("image", image_name);
                            b.putString("steps",stepcount);
                            System.out.println(direction.get(0));
                           
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please make a valid selection",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}