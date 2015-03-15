package com.cartoonerie.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Maxime on 15/03/15.
 */
public class ProjectDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cartoonerie.db";
    private static final String TABLE_NAME = "projects";

    private SQLiteDatabase db;

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + "id integer PRIMARY KEY  AUTOINCREMENT,"
                    + "name TEXTE NOT NULL,"
                    + "uri TEXTE NOT NULL"
                    + ");";

    public ProjectDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clear(){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(Project project) {
        ContentValues toInsert = new ContentValues();
        toInsert.put("name", project.getName());
        toInsert.put("uri", project.getUri());
        return db.insert(TABLE_NAME, null, toInsert);
    }

    public ArrayList<Project> getProjects() {
        ArrayList<Project> output = new ArrayList<Project>();
        String[] colonnesARecup = new String[] { "name", "uri" };

        Cursor cursorResults = db.query(TABLE_NAME, colonnesARecup, null,
                null, null, null, null, null);
        if (null != cursorResults) {
            if (cursorResults.moveToFirst()) {
                do {
                    int name = cursorResults.getColumnIndex("name");
                    int uri = cursorResults.getColumnIndex("uri");
                    Project p = new Project();
                    p.setName(cursorResults.getString(name));
                    p.setUri(cursorResults.getString(uri));
                    output.add(p);
                } while (cursorResults.moveToNext());
            }
        }

        return output;
    }
}