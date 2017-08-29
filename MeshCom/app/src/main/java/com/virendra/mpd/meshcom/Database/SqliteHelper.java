package com.virendra.mpd.meshcom.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Virendra on 20-03-2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {


    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void query (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void addUser(String name, String emails)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR, EMAILS VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS CHATS (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENDER VARCHAR, MESSAGE VARCHAR)");
        String query = "INSERT INTO USER VALUES (NULL, ?, ?)";

        SQLiteStatement statement = db.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, emails);

        statement.executeInsert();
    }

   public void addToChatHistory(String name)
   {
       SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("CREATE TABLE IF NOT EXISTS CHATHISTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR)");

       String query = "INSERT INTO CHATHISTORY VALUES (NULL, ?)";
       SQLiteStatement statement = db.compileStatement(query);
       statement.clearBindings();
       statement.bindString(1, name);

       statement.executeInsert();
   }

   public void addToMessageHistory(String senderName, String message)
   {
       SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("CREATE TABLE IF NOT EXISTS MESSAGEHISTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENDER VARCHAR, MESSAGE VARCHAR)");

       String query = "INSERT INTO MESSAGEHISTORY VALUES (NULL, ?, ?)";
       SQLiteStatement statement = db.compileStatement(query);
       statement.clearBindings();
       statement.bindString(1, senderName);
       statement.bindString(2, message);

       statement.executeInsert();
   }


    /*public void addToChats(String sender, String message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO CHATS VALUES (NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1, sender);
        statement.bindString(2, message);
        statement.executeInsert();
    }*/

    public Cursor checkUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM sqlite_master WHERE name ='USER' and type='table';", null);
    }

    public Cursor getUserName()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM USER WHERE ID = 1;", null);
    }

    public int editUserName(String newUserName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE USER SET NAME = ? WHERE ID = 1;";

        SQLiteStatement statement = db.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,newUserName);
        return statement.executeUpdateDelete();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
