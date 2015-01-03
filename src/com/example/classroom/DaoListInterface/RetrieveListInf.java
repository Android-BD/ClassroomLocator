/**
 * 
 */
package com.example.classroom.DaoListInterface;

import java.util.List;

/**
 * @author Vineet
 * 
 *         All the DATABASE calls are done from these Interfaces
 */
public interface RetrieveListInf {
	public List<String> getAllSections();

	public List<String> getAllCourses();

	public List<String> getAllRooms();

	public List<String> getSpecificRoom(String course, String section);

	public List<String> getAllLandmarks();

	// this is callec when course, section, room and landmark are provided
	public List<String> getDirections(String course, String section,
			String room, String landmark);

	// This is called when room and landmark are provided
	public List<String> getDirectionsForRoom(String room, String landmark);

}
