package bdjobs.lict.linearlayoutwithweight.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import bdjobs.lict.linearlayoutwithweight.model.Student;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DB_NAME = "rss.db";
    private ArrayList<Student> studentList;

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TableAttributes obj= new TableAttributes();
        String query= obj.tableCreation();
        try{
            db.execSQL(query);
            Log.i("Table Create","Done");
        }
        catch (SQLException e){
            Log.e("SQL Error", e.toString());

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertStudent(Student obj) {
        ContentValues values = new ContentValues();
        values.put(TableAttributes.STUDENT_ID, UUID.randomUUID().toString());
        values.put(TableAttributes.STUDENT_USERNAME,obj.getUserName());
        values.put(TableAttributes.STUDENT_EMAIL,obj.getEmail());
        values.put(TableAttributes.STUDENT_PHONENO,obj.getPhoneNo());
        values.put(TableAttributes.STUDENT_CGPA,obj.getCGpa());
        values.put(TableAttributes.STUDENT_PASSWORD,obj.getPassWord());

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.insert(TableAttributes.TABLE_NAME, null, values);
            Log.i("Insert","Hoise");
        }catch (SQLException e){
            Log.e("Insert Error", e.toString());
        }

    }

    public ArrayList<Student> getAllStudentData(){

        studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query ="SELECT * FROM " + TableAttributes.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        try {
            if (cursor.getCount() > 0) {
                while (!cursor.isAfterLast()) {
                    Student std = new Student();
                    Log.d(TAG, cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_ID)));
                    std.setId(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_ID)));
                    std.setUserName(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_USERNAME)));
                    std.setEmail(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_EMAIL)));
                    std.setPhoneNo(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_PHONENO)));
                    std.setCGpa(cursor.getFloat(cursor.getColumnIndex(TableAttributes.STUDENT_CGPA)));
                    std.setPassWord(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_PASSWORD)));

                    studentList.add(std);

                    cursor.moveToNext();
                }
            }
        }
        catch (SQLException e){

        }finally {
            db.close();
        }

        for (Student student : studentList)
            Log.d(TAG, student.getId());

        return studentList;

    }

    public boolean deleteStudent(int position) {
        String id = studentList.get(position).getId();
        Log.d(TAG, id + "detected");
        boolean flag= false;
        SQLiteDatabase db= this.getWritableDatabase();
        try {
            db.delete(TableAttributes.TABLE_NAME, TableAttributes.STUDENT_ID + "='" + id + "'", null);
            Log.i("Delete", "Hoise");
            flag=true;
        }catch (SQLException e){
            Log.e("DeleteErr", e.toString());
        }
        return flag;
    }
}
