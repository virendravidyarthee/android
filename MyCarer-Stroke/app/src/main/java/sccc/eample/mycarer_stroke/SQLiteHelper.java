package sccc.eample.mycarer_stroke;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS PATIENT (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR, DOB VARCHAR, NOTES VARCHAR, IMG BLOB, CARETAKERNAME VARCHAR, CARETAKERNUMBER VARCHAR, CARETAKER1NAME VARCHAR, CARETAKER1NUMBER VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS RELATIVES (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR,  RELATIONSHIP VARCHAR, IMAGE BLOG, DESCRIPTION VARCHAR, DOB VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS SCORESXXX (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE VARCHAR, DATE VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS SCORESXXXX (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE VARCHAR, DATE VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS PHRASES (ID INTEGER PRIMARY KEY AUTOINCREMENT, PHRASES VARCHAR)");
    }

    public void insertDataScore4x4(String score, String date){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO SCORESXXXX VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, score);
        statement.bindString(2, date);

        statement.executeInsert();
    }

    public void insertDataScore3x4(String score, String date){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO SCORESXXX VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, score);
        statement.bindString(2, date);

        statement.executeInsert();
    }

    public Cursor getAllScoresXXX()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT rowid _id,* FROM SCORESXXX", null);
        return result;
    }

    public Cursor getAllScoresXXXX()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT rowid _id,* FROM SCORESXXXX", null);
        return result;
    }

    public void insertDataPhrases(String phrases){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PHRASES VALUES (NULL, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, phrases);

        statement.executeInsert();
    }

    public Cursor getAllPhrases()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT rowid _id,* FROM PHRASES", null);
        return result;
    }


    public void insertData(String name, String relationship, byte[] image, String notes, String dob){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO RELATIVES VALUES (NULL, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, relationship);
        statement.bindBlob(3, image);
        statement.bindString(4, notes);
        statement.bindString(5, dob);

        statement.executeInsert();

    }

    public void insertDataPatient(String name, String dob, String notes, byte[] patientImage, String caretakername, String caretakernumber, String caretaker1name, String carertaker1number){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PATIENT VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, dob);
        statement.bindString(3, notes);
        statement.bindBlob(4, patientImage);
        statement.bindString(5, caretakername);
        statement.bindString(6, caretakernumber);
        statement.bindString(7, caretaker1name);
        statement.bindString(8, carertaker1number);

        statement.executeInsert();
    }

    public void updateDataRelative(String name, String relationship, byte[] image, String notes, String dob, int id){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("RELATIONSHIP", relationship);
        contentValues.put("IMAGE", image);
        contentValues.put("DESCRIPTION", notes);
        contentValues.put("DOB", dob);
        String[] args = {Integer.toString(id)};
        database.update("RELATIVES",contentValues, "ID=?", args);
    }

    public void updateDataPatient(String name, String dob, String notes, byte[] patientImage, String caretakername, String caretakernumber, String caretaker1name, String carertaker1number, int id){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("DOB", dob);
        contentValues.put("NOTES", notes);
        contentValues.put("IMG", patientImage);
        contentValues.put("CARETAKERNAME", caretakername);
        contentValues.put("CARETAKERNUMBER", caretakernumber);
        contentValues.put("CARETAKER1NAME", caretaker1name);
        contentValues.put("CARETAKER1NUMBER", carertaker1number);
        String[] args = {Integer.toString(id)};
        database.update("PATIENT",contentValues, "ID=?", args);
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM RELATIVES", null);
        return result;
    }

    public void deleteRelative(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM RELATIVES WHERE ID = "+Integer.toString(id)+";");
    }

    public void queryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getRowFromID(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM RELATIVES WHERE ID = "+Integer.toString(id)+";", null);
        return result;
    }

    public Cursor getRowFromIDPatient()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM PATIENT WHERE ID = 1;", null);
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
