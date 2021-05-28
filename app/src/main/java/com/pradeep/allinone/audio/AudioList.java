package com.pradeep.allinone.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.pradeep.allinone.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioList extends AppCompatActivity {
    //create list view for vsual list
    ListView lv_Audio;
    public static ArrayList<File> fileList = new ArrayList<File>();
    AudioAdapter obj_adapter;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    File dir;
    int ii=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        init();
    }//Environment.getExternalStoragePublicDirectory(".School/")

    private void init() {
        lv_Audio = (ListView) findViewById(R.id.audioFiles);
        dir = new File( Environment.getExternalStorageDirectory().getAbsolutePath());
        fn_permission();
        lv_Audio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Intent intent = new Intent(getApplicationContext(), AudioActivity.class);
               // intent.putExtra("position", i);
                //startActivity(intent);
                ii=i;
                Log.e("Position", i +"");

            }
        });
    }
    public void playPauseMusi (View playPause) {
        String url = String.valueOf(AudioList.fileList.get(ii)); // your URL here
        final MediaPlayer mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer){
                mediaPlayer.start();
            }
        });
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            ((ImageButton)playPause).setImageResource(R.drawable.play);
        } else if(!(mediaPlayer.isPlaying())) {
            ((ImageButton)playPause).setImageResource(R.drawable.ic_pause);
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }
    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(AudioList.this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(AudioList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;

            getfile(dir);

            obj_adapter = new AudioAdapter(getApplicationContext(), fileList);
            lv_Audio.setAdapter(obj_adapter);

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
                    if (listFile[i].getName().endsWith(".mp3")) {

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

                obj_adapter = new AudioAdapter(getApplicationContext(), fileList);
                lv_Audio.setAdapter(obj_adapter);

            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.audioFiles){
            menu.add(0,0,0,"Delete");
        }
    }

    private MutableLiveData<String> mText;
}

