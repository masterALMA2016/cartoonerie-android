package com.cartoonerie.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class ProjectsActivity extends Activity implements View.OnClickListener {

    public final static String PROJECT = "com.cartoonerie.app.PROJECT";

    private List<Project> projects;
    private ProjectDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feedRecyclerView);

        findViewById(R.id.pink_icon).setOnClickListener(this);

        db = new ProjectDB(this);
        projects = db.getProjects();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProjectsAdapter mAdapter = new ProjectsAdapter(projects);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), ProjectActivity.class);
                        intent.putExtra(PROJECT, projects.get(position));
                        startActivity(intent);
                    }
                })
        );
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        projects = db.getProjects();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projects, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pink_icon) {
            Intent intent = new Intent(this, NewProjectActivity.class);
            startActivity(intent);
        }
    }
}
