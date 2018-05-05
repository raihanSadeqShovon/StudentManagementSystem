package bdjobs.lict.linearlayoutwithweight;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import bdjobs.lict.linearlayoutwithweight.adapter.CustomAdapter;
import bdjobs.lict.linearlayoutwithweight.database.DatabaseHelper;
import bdjobs.lict.linearlayoutwithweight.model.Student;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText etUserName, etEmail, etPhone, etCgpa, etPassword;
    ListView listView;
    ArrayList<Student> studentArrayList;
    /*ArrayAdapter<Student> arrayAdapter;*/
    CustomAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.eT_name);
        etEmail = (EditText) findViewById(R.id.eT_email);
        etPhone = (EditText) findViewById(R.id.eT_pneNo);
        etCgpa = (EditText) findViewById(R.id.eT_cgpa);
        etPassword = (EditText) findViewById(R.id.eT_pass);

        listView = (ListView) findViewById(R.id.lvStudents);
//        studentArrayList = new ArrayList<Student>();

        dbHelper= new DatabaseHelper(MainActivity.this);
        studentArrayList=dbHelper.getAllStudentData();

        for (Student student : studentArrayList)
            Log.d(TAG, student.getId());
        /*arrayAdapter = new ArrayAdapter<Student>(MainActivity.this, R.layout.list_item_layout, studentArrayList);*/
        adapter= new CustomAdapter(MainActivity.this, studentArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Student Details");
                dialog.setCancelable(false);
                dialog.setMessage(studentArrayList.get(position).toStringForDialog());
                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Delete");
                dialog.setMessage("Do u want to delete this student");
                dialog.setCancelable(false);

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Log.d(TAG, studentArrayList.get(position).getId());
                        if (dbHelper.deleteStudent(position)){
                            Toast.makeText(MainActivity.this, "Data is deleted succesfully", Toast.LENGTH_LONG).show();
                            updateAdapter();
                        }else Toast.makeText(MainActivity.this, "Data is not deleted succesfully", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();

                return false;
            }
        });
    }

    public void save(View view) {
        boolean error = false;
        Student obj = new Student();
        String UserName = etUserName.getText().toString().trim();
        if (UserName.isEmpty()) {
            etUserName.setError("UserName is missing");
        } else {
            obj.setUserName(UserName);
        }
        obj.setEmail(etEmail.getText().toString());

        String PhoneNo = etPhone.getText().toString();
        if (PhoneNo.isEmpty()) {
            error = true;
            etPhone.setError("PhoneNo is missing");
        } else if (PhoneNo.length() == 11 || PhoneNo.length() == 14) {
            if (PhoneNo.startsWith("01")) {
                obj.setPhoneNo(PhoneNo);
            } else {
                error = true;
                etPhone.setError("PhoneNo is not valid");
            }
        } else {
            error = true;
            etPhone.setError("PhoneNo is should be 11 or 14 digit");
        }
        obj.setPhoneNo(etPhone.getText().toString());

        Float Cgpa = 0.0f;
        String cGpa = etCgpa.getText().toString();
        if (!cGpa.isEmpty()) {
            Cgpa = Float.parseFloat(cGpa);
        }
        if (Cgpa <= 4.00 && Cgpa >= 0) {
            obj.setCGpa(Cgpa);
        } else {
            error = true;
            etCgpa.setError("CGPA is not valid");
        }

        obj.setPassWord(etPassword.getText().toString());

        if (error == true) {
            Toast.makeText(MainActivity.this, "Data is not saved", Toast.LENGTH_LONG).show();
        } else {
            //studentArrayList.add(obj);
            //adapter.notifyDataSetChanged();
            //clearData(view);
            //studentArrayList.add(obj);
            dbHelper=new DatabaseHelper(MainActivity.this);
            dbHelper.insertStudent(obj);
//            studentArrayList.clear();
//            studentArrayList.addAll(dbHelper.getAllStudentData());
//            Log.i("Get", studentArrayList.toString());
//            adapter.notifyDataSetChanged();
            updateAdapter();

            Toast.makeText(MainActivity.this, "Data is inserted", Toast.LENGTH_LONG).show();
            clearEditFields();
        }
    }

    private void updateAdapter() {
        studentArrayList.clear();
        studentArrayList.addAll(dbHelper.getAllStudentData());
        adapter.notifyDataSetChanged();
    }

    public void clearData(View view) {
        if (view.getId() == R.id.clrButton) {
            clearEditFields();
        } else {
            MainActivity.this.finish();
        }
    }

    private void clearEditFields() {
        etUserName.setText(null);
        etEmail.setText(null);
        etPhone.setText(null);
        etCgpa.setText(null);
        etPassword.setText(null);
    }


}
