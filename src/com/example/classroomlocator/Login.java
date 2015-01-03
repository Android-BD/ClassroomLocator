package com.example.classroomlocator;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

   private EditText  username=null;
   private EditText  password=null;
   private TextView attempts;
   private Button login;
   private DatabaseHelper dbHelper;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login);
      dbHelper = dbCreate();
      username = (EditText)findViewById(R.id.userNameText);
      password = (EditText)findViewById(R.id.passwordText);
      attempts = (TextView)findViewById(R.id.textView5);
      //attempts.setText(Integer.toString(counter));
      login = (Button)findViewById(R.id.button1);
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
   

   private void addListenerOnButton() {
	// TODO Auto-generated method stub
	   Button  login = (Button) findViewById(R.id.button1);

       login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName =((EditText)findViewById(R.id.userNameText)).getText().toString();
				String password =((EditText)findViewById(R.id.passwordText)).getText().toString();
				boolean validUser = dbHelper.isValidUser(userName, password);
				
				if(validUser)
				{
					Intent intent = new Intent(Login.this,AddNewItems.class);
					startActivity(intent);
				}
				else
				{
				   Toast.makeText(getApplicationContext(), "User is not valid. Please try again.", Toast.LENGTH_SHORT).show();
				}
			}
        	
        	
       });
   }
 
			


}