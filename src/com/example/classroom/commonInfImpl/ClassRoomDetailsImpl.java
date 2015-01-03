/**
 * 
 */
package com.example.classroom.commonInfImpl;

import java.util.ArrayList;
import java.util.List;
import com.example.classroom.DaoListInfImpl.RetrieveListImpl;
import com.example.classroom.DaoListInterface.RetrieveListInf;
import com.example.classroom.commonInterface.ClassRoomDetailsInf;

/**
 * @author Vineet
 * 
 */
public class ClassRoomDetailsImpl implements ClassRoomDetailsInf {

	// This is used to make call to methods in RetrieveListImpl, which is
	// used to get all the details.

	RetrieveListInf daoList = new RetrieveListImpl();

	// Setting output values of room details in roomList

	List<String> roomList = new ArrayList<String>();

	/*
	 * There are 2 scenarios. 1. To get the room details when course and section
	 * spinner values are courseNumber and sectionNumber respectively. When an
	 * user has not selected an input for both the spinners. 2.To get the room
	 * details when the course and section spinner values have been selected.
	 */
	@Override
	public List<String> getRoomDetails(String course, String section) {
		roomList = daoList.getSpecificRoom(course, section);
		return roomList;
	}

	@Override
	public List<String> getDirections(String course, String section,
			String room, String landmark) {
		Boolean flag = false;

		List<String> direction = new ArrayList<String>();
		if (!landmark.equalsIgnoreCase("Nearest Landmark")) {
			if (((course.equalsIgnoreCase("Coursename") || section
					.equalsIgnoreCase("Section")))) {
				if (room.equalsIgnoreCase("Classroom")) {
					flag = false;
				} else {
					direction = daoList.getDirectionsForRoom(room, landmark);
					flag = true;
				}
			} else {
				direction = daoList.getDirections(course, section, room,
						landmark);
				flag = true;
			}
		}
		// if (flag == true) {
		// if (((course.equalsIgnoreCase("Coursename") || section
		// .equalsIgnoreCase("Section")))) {
		// direction = daoList.getDirectionsForRoom(room, landmark);
		// } else {
		// direction = daoList.getDirections(course, section, room,
		// landmark);
		// }
		// }
		return direction;
	}
}