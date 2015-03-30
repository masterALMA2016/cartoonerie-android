package com.cartoonerie.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;


public class ProjectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Project project = (Project) intent.getSerializableExtra(ProjectsActivity.PROJECT);
        getActionBar().setTitle(project.getName());

        File f = new File(project.getUri());

        Toast.makeText(ProjectActivity.this, f.exists()+"ok", Toast.LENGTH_LONG).show();

        VideoView videoView = (VideoView)findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setKeepScreenOn(true);
        videoView.setVideoPath(project.getUri());
        videoView.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
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
