package com.virendra.mpd.meshcom.Database;

import android.content.Context;
import android.database.Cursor;

import com.virendra.mpd.meshcom.Database.SqliteHelper;

/**
 * Created by Virendra on 20-03-2017.
 * This file exists within the database package because all user info is stored into the sqlite db
 */

public class User {
    public static SqliteHelper sqlDatabase;
    public static String name;
    public static String emails;
    static Boolean userExists = false;




    public static Boolean getUserExists() {
        return userExists;
    }

    public User()
    {

    }

    public User(Context context, boolean userAlreadyExits)
    {
        sqlDatabase = new SqliteHelper(context, "DATABASE.db", null, 1);
        Cursor cursor = sqlDatabase.getUserName();
        cursor.moveToNext();
        this.name = cursor.getString(1);
    }

    public User(Context context) {
        sqlDatabase = new SqliteHelper(context, "DATABASE.db", null, 1);
        Cursor cursor = sqlDatabase.checkUser();
        if (cursor.moveToFirst())
        {
            userExists = true;
        }
    }

    public void setupUser(String userName, String emails) {

        this.name = userName;
        this.emails = emails;
        sqlDatabase.addUser(name, emails);
        userExists = true;
    }

    public void editUser(String userName)
    {
        name = userName;
        userExists = true;
        sqlDatabase.editUserName(userName);

    }

}
