package com.pradeep.allinone.video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.pradeep.allinone.R;
import com.pradeep.allinone.pdf.PdfAdapter;
import com.pradeep.allinone.pdf.PdfList;

import java.io.File;
import java.util.ArrayList;

/*
created by puri sahib
 */
public class VideoList extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    //create list view for vsual list
    ListView lv_pdf;
    public static ArrayList<File> fileList = new ArrayList<File>();
    VideoAdapter obj_adapter;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    File dir;
    private VideoView videoViewActivity;
    int currvideo=0;
    int ii=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        init();
        videoViewActivity=(VideoView)findViewById(R.id.videoViewActivity);
        videoViewActivity.setMediaController(new MediaController(this));
        videoViewActivity.setOnCompletionListener(this);
        // video name should be in lower case alphabet
//        videolist.add(R.raw.one);
        //      videolist.add(R.raw.two);
        //    videolist.add(R.raw.three);
        fileList=fileList;

    }//Environment.getExternalStoragePublicDirectory(".School/")
    public void setVideo(File id){
        String uriPath= String.valueOf(VideoList.fileList.get(ii));
        //"android.resource://"+getPackageName()+"/"+id;
        Uri uri=Uri.parse(uriPath);
        videoViewActivity.setVideoURI(uri);
        videoViewActivity.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        AlertDialog.Builder obj=new AlertDialog.Builder(this);
        obj.setTitle("Play Back Finished...");
        obj.setIcon(R.mipmap.ic_launcher);
        VideoActivity.MyListener m=new VideoActivity.MyListener() ;
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next",m);
        obj.setMessage("Wait to replay or play next Video?");
        obj.show();
    }
    class MyListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which==-1){
                videoViewActivity.seekTo(0);
                videoViewActivity.start();
            }else{
                ++currvideo;
                if (currvideo==fileList.size()){
                    currvideo=0;
                }
                setVideo(fileList.get(currvideo));
            }
        }
    }

    private void init() {
        lv_pdf = (ListView) findViewById(R.id.videoFiles);
        dir = new File( Environment.getExternalStorageDirectory().getAbsolutePath());
        fn_permission();
        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ii=i;
                setVideo(fileList.get(0));
                //Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                //intent.putExtra("position", i);
                //startActivity(intent);

                Log.e("Position", i +"");
            }
        });
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(VideoList.this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(VideoList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;
            getfile(dir);
            obj_adapter = new VideoAdapter(getApplicationContext(), fileList);
            lv_pdf.setAdapter(obj_adapter);
        }
    }

    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);
                } else {
                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".mp4")) {
                        for (int j = 0; j < fileList.size(); j++) {
                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {
                            }
                        }

                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            fileList.add(listFile[i]);

                        }
                    }
                }
            }
        }
        return fileList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;
                getfile(dir);

                obj_adapter = new VideoAdapter(getApplicationContext(), fileList);
                lv_pdf.setAdapter(obj_adapter);

            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.pdfFiles){
            menu.add(0,0,0,"Delete");
        }
    }

    private MutableLiveData<String> mText;
}