/**
 * 
 */
package com.example.classroom.commonInterface;

import java.util.List;

/**
 * @author Vineet
 * 
 */
public interface ClassRoomDetailsInf {

	public List<String> getRoomDetails(String course, String section);
	public List<String> getDirections(String course, String section, String room, String landmark);

}
