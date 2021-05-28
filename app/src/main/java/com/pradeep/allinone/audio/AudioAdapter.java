package com.pradeep.allinone.audio;

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

public class AudioAdapter extends ArrayAdapter<File> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<File> al_Audio;
    public AudioAdapter(Context context3, ArrayList<File> al3_Audio) {
        super(context3, R.layout.activity_audio_adapter, al3_Audio);
        this.context = context3;
        this.al_Audio = al3_Audio;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        if (al_Audio.size() > 0) {
            return al_Audio.size();
        } else {
            return 1;
        }
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_audio_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_filename = (TextView) view.findViewById(R.id.tv_nameAudio);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_filename.setText(al_Audio.get(position).getName());
        return view;
    }
    public class ViewHolder {
        TextView tv_filename;
    }
}

