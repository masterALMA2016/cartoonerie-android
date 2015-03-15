package com.cartoonerie.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Maxime on 15/03/15.
 */
public class ProjectsAdapter  extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private List<Project> projects;

    public ProjectsAdapter(List<Project> projects) {
        this.projects = projects;

    }

    @Override
    public ProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtViewTitle.setText(projects.get(position).getName());
        viewHolder.txtViewInfo.setText(projects.get(position).getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView txtViewInfo;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.project_name);
            txtViewInfo = (TextView) itemLayoutView.findViewById(R.id.project_info);
        }
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}