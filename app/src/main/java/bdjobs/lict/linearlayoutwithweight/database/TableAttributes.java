package bdjobs.lict.linearlayoutwithweight.database;

import org.w3c.dom.Text;



public class TableAttributes {

    public static final String TABLE_NAME = "student";
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_USERNAME = "name";
    public static final String STUDENT_EMAIL = "email";
    public static final String STUDENT_PHONENO = "phone";
    public static final String STUDENT_CGPA = "cgpa";
    public static final String STUDENT_PASSWORD = "password";

    public String tableCreation()
    {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + STUDENT_ID + " TEXT PRIMARY KEY, "
                + STUDENT_USERNAME + " TEXT, "
                + STUDENT_EMAIL + " TEXT, "
                + STUDENT_PHONENO + " TEXT, "
                + STUDENT_CGPA + " TEXT, "
                + STUDENT_PASSWORD + " TEXT)";
        return query;
    }
}
