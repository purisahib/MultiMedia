package com.pradeep.allinone.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pradeep.allinone.R;

import java.io.File;
import java.util.ArrayList;

/*
Created by Puri SabHib
 */
public class VideoAdapter extends ArrayAdapter<File> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<File> al_pdf;
    public VideoAdapter(Context context3, ArrayList<File> al3_pdf) {
        super(context3, R.layout.activity_video_adapter, al3_pdf);
        this.context = context3;
        this.al_pdf = al3_pdf;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        if (al_pdf.size() > 0) {
            return al_pdf.size();
        } else {
            return 1;
        }
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_video_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_filename = (TextView) view.findViewById(R.id.tv_nameVideo);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_filename.setText(al_pdf.get(position).getName());
        return view;
    }
    public class ViewHolder {
        TextView tv_filename;
    }
}

