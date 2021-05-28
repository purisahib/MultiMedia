package com.pradeep.allinone.pacakagestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pradeep.allinone.MainActivity;
import com.pradeep.allinone.R;
import com.pradeep.allinone.audio.AudioList;
import com.pradeep.allinone.doc_text.DocTextList;
import com.pradeep.allinone.pdf.PdfList;
import com.pradeep.allinone.video.VideoActivity;
import com.pradeep.allinone.video.VideoList;

public class StartActivity extends AppCompatActivity {
private Button btn1,btn2,btn3,btn4,btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //init
        btn1=findViewById(R.id.button1);//pdf
        btn2=findViewById(R.id.button2);//doc/text
        btn3=findViewById(R.id.button3);//video
        btn4=findViewById(R.id.button4);//audio
        btn5=findViewById(R.id.button5);//image
        //set on click listener
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, PdfList.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, DocTextList.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, VideoList.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, AudioList.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
