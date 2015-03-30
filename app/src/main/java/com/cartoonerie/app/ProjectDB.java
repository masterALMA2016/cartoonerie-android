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
                    + "videoPath TEXTE NOT NULL"
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

    public Project insert(Project project) {
        ContentValues toInsert = new ContentValues();
        toInsert.put("name", project.getName());
        toInsert.put("videoPath", project.getVideoPath());
        long id = db.insert(TABLE_NAME, null, toInsert);
        project.setId(id);
        return project;
    }

    public void removeProject(Project project) {
        ArrayList<Project> all = getProjects();
        clear();
        for(Project p : all) {
            if(p.getId() != project.getId()) {
                insert(p);
            }
        }
    }

    public ArrayList<Project> getProjects() {
        ArrayList<Project> output = new ArrayList<Project>();
        String[] colonnesARecup = new String[] { "id", "name", "videoPath" };

        Cursor cursorResults = db.query(TABLE_NAME, colonnesARecup, null,
                null, null, null, null, null);
        if (null != cursorResults) {
            if (cursorResults.moveToFirst()) {
                do {
                    int id = cursorResults.getColumnIndex("id");
                    int name = cursorResults.getColumnIndex("name");
                    int videoPath = cursorResults.getColumnIndex("videoPath");
                    Project p = new Project();
                    p.setId(cursorResults.getLong(id));
                    p.setName(cursorResults.getString(name));
                    p.setVideoPath(cursorResults.getString(videoPath));
                    output.add(p);
                } while (cursorResults.moveToNext());
            }
        }

        return output;
    }
}