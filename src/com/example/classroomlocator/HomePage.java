package com.example.classroomlocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomePage extends Activity {
	
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_app_activity);
        addListenerOnButton();
        
        
       
    }
    
    public void addListenerOnButton() {


       Button  btnAdmin = (Button) findViewById(R.id.btnAdmin);

       btnAdmin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomePage.this,Login.class);
				 startActivity(intent);
				
				
			}
        	
        	
        });
       
       Button  btnStudent = (Button) findViewById(R.id.btnStudent);

       btnStudent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(HomePage.this,Location.class);
				 startActivity(intent);
				
			}
        	
        	
        });
    }
	

}
