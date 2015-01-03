package com.example.classroomlocator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewItems extends Activity {

    
    private Button btnSave;
    private String coursename, section, roomnumber, landmark;
    DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dbHelper = dbCreate();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);
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


    // get the selected dropdown list value
    public void addListenerOnButton() {


    	btnSave = (Button) findViewById(R.id.btnSave);

    	btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               
                    roomnumber = ((EditText)findViewById(R.id.roomText)).getText().toString();
                    landmark = ((EditText)findViewById(R.id.landmarkText)).getText().toString();
                    coursename = ((EditText)findViewById(R.id.courseText)).getText().toString();
                    section =((EditText)findViewById(R.id.sectionText)).getText().toString();
                    
                    
                    if(roomnumber.isEmpty() || landmark.isEmpty() || coursename.isEmpty() || section.isEmpty())
                    {
                    	Toast.makeText(getApplicationContext(), "Enter all the required fields.", Toast.LENGTH_SHORT).show();
                    
                    }
                    else
                    {
                    	Map<String,String> parameterValues = new HashMap<String,String>();
                        parameterValues.put("room", roomnumber);
                        parameterValues.put("landmark", landmark);
                        parameterValues.put("course", coursename);
                        parameterValues.put("section", section);
                        dbHelper.insertCourse(parameterValues);
                    	Toast.makeText(getApplicationContext(), "New Entries are added successfully.", Toast.LENGTH_SHORT).show();
    				}
            }
    		
                    
      
    });
}
}