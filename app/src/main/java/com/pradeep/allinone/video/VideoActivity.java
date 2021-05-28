package com.pradeep.allinone.video;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pradeep.allinone.R;

import java.io.File;
import java.util.ArrayList;

/*
Created by Puri Sahib
*/
public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
private VideoView videoViewActivity;
ArrayList<File> videolist=new ArrayList<File>();
int currvideo=0;
    String setString="";
    int position=-1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        position = getIntent().getIntExtra("position", -1);
        videoViewActivity=(VideoView)findViewById(R.id.videoViewActivity);
        videoViewActivity.setMediaController(new MediaController(this));
        videoViewActivity.setOnCompletionListener(this);
        // video name should be in lower case alphabet
//        videolist.add(R.raw.one);
  //      videolist.add(R.raw.two);
    //    videolist.add(R.raw.three);
        videolist=VideoList.fileList;
        setVideo(videolist.get(0));
        //displayFromSdcard();
    }
    public void setVideo(File id){
        String uriPath= String.valueOf(VideoList.fileList.get(position));
                //"android.resource://"+getPackageName()+"/"+id;
        Uri uri=Uri.parse(uriPath);
        videoViewActivity.setVideoURI(uri);
        videoViewActivity.start();
    }


    private void displayFromSdcard() {
        //DocTextList.fileList.get(position).getName()
        //textFileName = String.valueOf(VideoList.fileList.get(position));

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        AlertDialog.Builder obj=new AlertDialog.Builder(this);
        obj.setTitle("Play Back Finished...");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m=new MyListener() ;
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next",m);
        obj.setMessage("Wait to replay or play next Video?");
        obj.show();
    }
    static class MyListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which==-1){
             //   videoViewActivity.seekTo(0);
               // videoViewActivity.start();
            }else{
               // ++currvideo;
               // if (currvideo==videolist.size()){
                 //   currvideo=0;
                }
                //setVideo(videolist.get(currvideo));
            }
        }
    }
