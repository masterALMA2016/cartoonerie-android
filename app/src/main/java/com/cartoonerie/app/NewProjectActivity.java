package com.cartoonerie.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.iangclifton.android.floatlabel.FloatLabel;

import java.io.File;


public class NewProjectActivity extends Activity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int LOAD_IMAGE_RESULTS = 1;

    private ProjectDB db;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        project = new Project();

        db = new ProjectDB(this);

        Button takeVideo = (Button)findViewById(R.id.takeVideo);
        takeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                }
            }
        });

        Button openVideo = (Button)findViewById(R.id.openVideo);
        openVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openVideoIntent.addCategory(Intent.CATEGORY_OPENABLE);
                openVideoIntent.setType("video/*");
                try{
                    startActivityForResult(openVideoIntent, LOAD_IMAGE_RESULTS);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(NewProjectActivity.this, "There are no file explorer clients installed.", Toast.LENGTH_SHORT).show();
                }
                }
        });

        Button createProject = (Button)findViewById(R.id.createProject);
        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatLabel projectName = (FloatLabel) findViewById(R.id.projectName);
                project.setName(projectName.getEditText().getText().toString());
                db.insert(project);
                Intent intent = new Intent(getApplicationContext(), ProjectsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            project.setVideoPath(MediaFilePath.getPath(this, videoUri));
            VideoView videoView = (VideoView)findViewById(R.id.video_view);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setKeepScreenOn(true);
            videoView.setVideoPath(project.getVideoPath());
            videoView.start();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
