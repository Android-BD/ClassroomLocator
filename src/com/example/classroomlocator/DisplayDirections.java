package com.example.classroomlocator;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DisplayDirections extends Activity {
    private ImageView splash;
    private Button imagebutton;
    DataHandler Handler;

    private EditText textViewSteps;
    private Button buttonReset;

    private SensorManager sensorManager;

    private int numSteps;

    private float mLimit;
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = { new float[3 * 2], new float[3 * 2] };
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;

    String image;
    LinearLayout linear ;
    ArrayList<String> directionList;
    ArrayList<String> stepsList;
    int index=0;

    public DisplayDirections() {
        int h = 480; // TODO: remove this constant
        mLimit = 6;
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        textViewSteps = (EditText) findViewById(R.id.textSteps);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        numSteps = 0;

        setSplash((ImageView) findViewById(R.id.image));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        Bundle b = getIntent().getExtras();
        Intent intent = getIntent();
        directionList = intent
                .getStringArrayListExtra("direction");
        //steps
        stepsList = intent
                .getStringArrayListExtra("stepsList");
        Log.d("DisplayDirections:",""+stepsList);

        image = intent.getStringExtra("image");
        String Totstep=intent.getStringExtra("steps");
      
        EditText ed = new EditText(this);
		linear = (LinearLayout) findViewById(R.id.linearLayout2);
		for (int i = 0; i < directionList.size(); i++) {
			ed = new EditText(DisplayDirections.this);
			ed.setId(i);
			ed.setText(directionList.get(i));
			ed.setTypeface(null, Typeface.BOLD);
			ed.setTextColor(Color.BLACK);
			ed.setBackgroundColor(Color.WHITE);
			ed.setEnabled(false);
			ed.setTag(false);
			linear.addView(ed);
		}
		
        enableAccelerometerListening();
        addListenerOnButton();
    }

    public ImageView getSplash() {
        return splash;
    }

    public void setSplash(ImageView splash) {
        this.splash = splash;
    }

    public void addListenerOnButton() {

        final Context context = this;

        imagebutton = (Button) findViewById(R.id.button1);

        imagebutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Bundle b = new Bundle();
                b.putString("image", image);
                Intent intent = new Intent(context, DisplayMap.class);
                intent.putExtras(b);
                startActivity(intent);

            }

        });
        addResetOnButton();
    }

    public void addResetOnButton() {

        buttonReset = (Button) findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
               textViewSteps = (EditText) findViewById(R.id.textSteps);
                String steps = "0";
                numSteps = 0;
                textViewSteps.setText(steps);
                textViewSteps.setTypeface(null, Typeface.BOLD);
                textViewSteps.setTextColor(Color.BLACK);
                textViewSteps.setEnabled(false);
                for(int i=0;i<stepsList.size();i++){
                	EditText ed = (EditText)linear.findViewById(i);
                	ed.setBackgroundColor(Color.WHITE);
                	Log.d("DisplayDirections",""+ed.getText());
                }
                index=0;
            }

        });
    }

    private void enableAccelerometerListening() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {
    	
        @Override
        public void onSensorChanged(SensorEvent event) {

            float vSum = 0;
            int j = 1;

            for (int i = 0; i < 3; i++) {
                final float v = mYOffset + event.values[i] * mScale[j];
                vSum += v;
            }

            int k = 0;
            float v = vSum / 3;

            float direction = (v > mLastValues[k] ? 1
                    : (v < mLastValues[k] ? -1 : 0));

            if (direction == -mLastDirections[k]) {
                // Direction changed
                int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
                mLastExtremes[extType][k] = mLastValues[k];
                float diff = Math.abs(mLastExtremes[extType][k]
                        - mLastExtremes[1 - extType][k]);

                if (diff > mLimit) {

                    boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                    boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                    boolean isNotContra = (mLastMatch != 1 - extType);

                    if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough
                            && isNotContra) {
                        numSteps++;
                        mLastMatch = extType;
                    } else {
                        mLastMatch = -1;
                    }
                }
                mLastDiff[k] = diff;
            }
            mLastDirections[k] = direction;
            mLastValues[k] = v;

            textViewSteps = (EditText) findViewById(R.id.textSteps);
            textViewSteps.setEnabled(true);
            textViewSteps.setText(String.valueOf(numSteps));
            textViewSteps.setTypeface(null, Typeface.BOLD);
            textViewSteps.setTextColor(Color.BLACK);
            textViewSteps.setEnabled(false);
            
            /**
             * Check numSteps and change color accordingly
             */
            if(index<stepsList.size()){
	            if(stepsList.get(index).equals(String.valueOf(numSteps))){
	            	EditText ed = (EditText)linear.findViewById(index);
	            	ed.setTag(true);
	            	ed.setBackgroundColor(Color.GREEN);
	            	Log.d("DisplayDirections","Step "+ed.getText());
	            	index++;
	            	numSteps = 0;
	            }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

    };

    public void ResetSteps(View v) {
        numSteps = 0;
        Log.d("DisplayDirections","ResetSteps");
        textViewSteps.setText(String.valueOf(numSteps));
    }
}