package com.example.hospital;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final String DB_NAME ="UserLogin";
    private static final int DB_VERSION = 1;
    private String tableName = "User";

    public SQLiteDB(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String user = "CREATE TABLE "+ tableName +" (id INTEGER PRIMARY KEY AUTOINCREMENT ,email VARCHAR , password VARCHAR)";
            sqLiteDatabase.execSQL(user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       /* String user = "DROP TABLE IF EXISTS " + tableName;
        sqLiteDatabase.execSQL(user);
        onCreate(sqLiteDatabase);*/
    }

    //used when logout
    public void deleteTable()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String user = "DROP TABLE IF EXISTS " + tableName;
        sqLiteDatabase.execSQL(user);
    }

    public void addUser (String email , String pass)
    {
        onCreate(getReadableDatabase());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email",email);
        values.put("password",pass);
        db.insert(tableName,null,values);
    }

    public Pair<String,String> getUser()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " +tableName;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return new Pair<String,String>(cursor.getString(cursor.getColumnIndex("email"))
                ,cursor.getString(cursor.getColumnIndex("password")));
    }

    public boolean isTableExist()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name = '" + tableName + "'";
        Cursor mCursor = db.rawQuery(query, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }
}
