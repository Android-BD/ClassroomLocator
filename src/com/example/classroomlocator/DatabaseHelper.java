package com.example.classroomlocator;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 /**
 * Created by Mithi.yan on 10/19/2014
 * Modified and Updated by Reethu on 11/19/2014
 * Modified and Updated by Raghavi on 11/19/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.classroomlocator/databases/";

    private static String DB_NAME = "OOAD.sqlite";
    private static String TABLE_NAME = "ooad_table";
    private static String WHERE_CLAUSE = "Coursename=course and Section=section and RoomNumber=room and Image=image and Landmark=landmark";
    private static String directions = "direction";

    private SQLiteDatabase myDataBase;

    private final Context myContext;
    

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public String returnDirections(String course, String section, String room, String landmark){
        String direction = "";
        String sqlquery = "SELECT Directions from ooad_table where  Coursename= '" + course+ "' and Section= '" + section+ "' and RoomNumber= '" +room+ "' and Landmark = '" +landmark+ "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {

            do {
                direction = cursor.getString(0);
                //  direction.add(c.getString(1));
            } while (cursor.moveToNext());
        }
        //return myDataBase.query(TABLE_NAME, new String[] {directions}, null, null,null, null, null);
        return direction;

    }

    public String returnRoomDirections(String room, String landmark)
    {
        String direction = "";
        String sqlquery = "SELECT Directions from ooad_table where  RoomNumber= '" +room+ "' and Landmark = '" +landmark+ "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {

            do {
                direction = cursor.getString(0);
                //  direction.add(c.getString(1));
            } while (cursor.moveToNext());
        }
        //return myDataBase.query(TABLE_NAME, new String[] {directions}, null, null,null, null, null);
        return direction;

    }

    public List<String> returnRoomBasedOnClass(String classname, String section)
    {
        List<String> roomnumber = new ArrayList<String>();
        roomnumber.add("RoomNumber");
        String sqlquery = "SELECT  DISTINCT RoomNumber from ooad_table where  Coursename = '" +classname+ "' and Section = '" +section+ "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {

            do {
                roomnumber.add(cursor.getString(0));
                //  direction.add(c.getString(1));
            } while (cursor.moveToNext());
        }
        //return myDataBase.query(TABLE_NAME, new String[] {directions}, null, null,null, null, null);
        return roomnumber;

    }


    public List<String> returnSectionBasedOnClass(String classname)
    {
        List<String> section = new ArrayList<String>();
        section.add("Section");
        String sqlquery = "SELECT DISTINCT Section from ooad_table where  Coursename = '" +classname+ "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {

            do {
                section.add(cursor.getString(0));
                //  direction.add(c.getString(1));
            } while (cursor.moveToNext());
        }
        //return myDataBase.query(TABLE_NAME, new String[] {directions}, null, null,null, null, null);
        return section;

    }

    public String returnImageName(String room, String landmark)
    {
        String image = "";
        String sqlquery = "SELECT ImageId FROM ooad_table where  RoomNumber= '" +room+ "' and Landmark = '" +landmark+ "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {

            do {
                image = cursor.getString(0);
                //  direction.add(c.getString(1));
            } while (cursor.moveToNext());
        }
        return image;

    }
    
    public String returnSteps(String room, String landmark)
    {
        String count = "";
        String sqlquery = "SELECT steps FROM ooad_table where  RoomNumber= '" +room+ "' and Landmark = '" +landmark+ "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {

            do {
                count = cursor.getString(0);
                Log.d("DatabaseHelper:",""+count);
                //  direction.add(c.getString(1));
            } while (cursor.moveToNext());
        }
        return count;

    }
    


    public List<String> getClassName(){
        List<String> classname = new ArrayList<String>();
        classname.add("Coursename");
        // Select All Query
        String classQuery = "SELECT  DISTINCT Coursename FROM ooad_table ORDER BY Coursename ASC";
        Cursor cursor1 = getReadableDatabase().rawQuery(classQuery, null);
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                classname.add(cursor1.getString(0));
            } while (cursor1.moveToNext());
        }

        // closing connection
        cursor1.close();
        //db.close();

        // returning lables
        return classname;
    }

    public List<String> getSectionName(){
        List<String> sectionname = new ArrayList<String>();
        sectionname.add("Section");
        // Select All Query
        String classQuery = "SELECT  DISTINCT Section FROM ooad_table ORDER BY Section ASC";
        Cursor cursor1 = getReadableDatabase().rawQuery(classQuery, null);
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                sectionname.add(cursor1.getString(0));
            } while (cursor1.moveToNext());
        }

        // closing connection
        cursor1.close();
        //db.close();

        // returning lables
        return sectionname;
    }

    public List<String> getRoomName(){
        List<String> roomname = new ArrayList<String>();
        roomname.add("Room Number");
        // Select All Query
        String classQuery = "SELECT  DISTINCT RoomNumber FROM ooad_table ORDER BY RoomNumber ASC ";
        Cursor cursor1 = getReadableDatabase().rawQuery(classQuery, null);
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                roomname.add(cursor1.getString(0));
            } while (cursor1.moveToNext());
        }

        // closing connection
        cursor1.close();
        //db.close();

        // returning lables
        return roomname;
    }

    public List<String> getLandmarkName(){
        List<String> landmarkname = new ArrayList<String>();
        landmarkname.add("Landmark");
        // Select All Query
        String classQuery = "SELECT  DISTINCT Landmark FROM ooad_table ORDER BY Landmark ASC";
        Cursor cursor1 = getReadableDatabase().rawQuery(classQuery, null);
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                landmarkname.add(cursor1.getString(0));
            } while (cursor1.moveToNext());
        }

        // closing connection
        cursor1.close();
        //db.close();

        // returning lables
        return landmarkname;
    }


    public boolean isValidUser(String userName, String password)
    {
    
    	  SQLiteStatement stmt = getReadableDatabase().compileStatement("SELECT Count(*) FROM login WHERE username = ? and password=?");
    	  stmt.bindString(1, userName);
    	  stmt.bindString(2, password);
    	  Long count = stmt.simpleQueryForLong();
    	  if(count>=1)
    		  return true;
    	  return false;
    }
    
    public void insertCourse(Map<String, String> parameterValues)
    {
    	SQLiteDatabase database = this.getWritableDatabase(); 
    	ContentValues values = new ContentValues();
    	values.put("coursename", parameterValues.get("course")); 
    	values.put("section", parameterValues.get("section")); 
    	values.put("landmark", parameterValues.get("landmark")); 
    	values.put("roomnumber", parameterValues.get("room")); 
    	
    	database.insert("ooad_table", null, values); 
    	database.close(); 
    } 
}
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.



