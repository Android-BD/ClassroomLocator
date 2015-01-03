package com.example.classroom.DaoListInfImpl;

import android.app.Activity;
import android.database.Cursor;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import android.content.Context;
import com.example.classroom.DaoListInterface.RetrieveListInf;

import com.example.classroomlocator.DatabaseHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;

/**
 * @author Vineet
 * 
 *         This class is used to retrieve all the list used in the application.
 *         Values should be retrieved from the DATABASE.
 * 
 */
public class RetrieveListImpl extends Activity implements RetrieveListInf {
	List<String> direction = new ArrayList<String>();


	/* This method is used to retrieve all section details */
	@Override
	public List<String> getAllSections() {
		List<String> sectionList = new ArrayList<String>();
		sectionList.add("section");
       sectionList.add("001");
       sectionList.add("002");
        sectionList.add("003");

      /*  DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
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
       sectionList = dbHelper.getSectionName();*/
		return sectionList;
	}

	/* This method is used to retrieve all the course details */
	@Override
	public List<String> getAllCourses() {
		List<String> courseList = new ArrayList<String>();
		courseList.add("Coursename");
		courseList.add("CS6359");
		courseList.add("CS6360");
		courseList.add("CS6363");
       /* DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
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
        courseList = dbHelper.getClassName();*/
        return courseList;
		//return courseList;
	}

	/* This is used to get all the room numbers */
	@Override
	public List<String> getAllRooms() {
		List<String> roomList = new ArrayList<String>();
		roomList.add("Classroom");
		roomList.add("2.410");
		roomList.add("ECS411");
		roomList.add("ECS412");
		roomList.add("ECS413");

     /*  DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
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
        roomList = dbHelper.getClassName();*/
        return roomList;
	}

	/*
	 * This is used to get specific room number for a given course and section
	 * This can be used to fletch values from database
	 */
	@Override
	public List<String> getSpecificRoom(String course, String section) {
		List<String> roomList = new ArrayList<String>();
		if (course.equalsIgnoreCase("Coursename")
				&& section.equalsIgnoreCase("section")) {
			roomList = getAllRooms();
		} else if (course.equalsIgnoreCase("CS6359")
				&& section.equalsIgnoreCase("002")) {
			// exact fletching is done here
			roomList.add("2.410");
		}
		// here we can get the roomnumber from database and then put into
		// roomList List.
		return roomList;
	}

	/*
	 * This is used to get all the landmarks
	 */
	@Override
	public List<String> getAllLandmarks() {
		List<String> landmarkList = new ArrayList<String>();
		landmarkList.add("ECS entrance");
		landmarkList.add("OpenSource lab");
		landmarkList.add("ECS north Entrance");
		landmarkList.add("Exit towards SSB");
		landmarkList.add("Entrance frm Berkner hall");
		return landmarkList;
	}

	@Override
	public List<String> getDirections(String course, String section,
			String room, String landmark) {

        /*DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
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
        String temp_direction = dbHelper.returnDirections(course, section, room, landmark);

        for(String splitval : temp_direction.split(";")){
            direction.add(splitval);
        }
        dbHelper.close();*/

		// Query for the directions
		// get from database the direction list for given course,section, room
		// and landmark

		// RETURN a LIST<String>

       // myDbHelper = new DatabaseHelper(this);


		// dummy calculation of the direction list
		/*if (course.equalsIgnoreCase("CS 6359") && section
				.equalsIgnoreCase("002")
				&& landmark.equalsIgnoreCase("ECS entrance")) {
			direction.add("Enter the main Entrance, turn towards your East.");
			direction.add("Continue along the curved path for 150 feet.");
			direction.add("Spot your classroom ECS 2.410 on your right.");
		}*/
		return direction;
	}

	@Override
	public List<String> getDirectionsForRoom(String room, String landmark) {
		// get from database the direction list for room and landmark

		// RETURN a LIST<String>
		if (room.equalsIgnoreCase("ECS410")
				&& landmark.equalsIgnoreCase("ECS entrance")) {
			direction.add("Enter the main Entrance, turn towards your East.");
			direction.add("Continue along the curved path for 150 feet.");
			direction.add("Spot your classroom ECS 2.410 on your right.");
		}
		return direction;
	}

}
