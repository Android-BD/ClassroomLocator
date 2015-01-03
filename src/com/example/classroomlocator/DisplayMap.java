/**
 *
 */
package com.example.classroomlocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;


public class DisplayMap extends Activity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageView imageView = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        int res = getResources().getIdentifier(image, "drawable", this.getPackageName());
        imageView.setImageResource(res);
    }
}