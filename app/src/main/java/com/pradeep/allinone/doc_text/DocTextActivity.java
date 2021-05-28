package com.pradeep.allinone.doc_text;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pradeep.allinone.R;
import com.shockwave.pdfium.PdfDocument;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

/*
Created by Puri Sahib
*///implements OnPageChangeListener, OnLoadCompleteListener
public class DocTextActivity extends AppCompatActivity  {
    //Integer pageNumber = 0;
    String textFileName;
    String setString="";
    //String TAG="PdfActivity";
    int position=-1;
    private TextView textData;
    // Floating action button
    private TextToSpeech textToSpeech;
    private FloatingActionButton speekBtn,stopBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_text);
        init();
        all();
    }

    private void all() {
        textToSpeech = new TextToSpeech( getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    //if there is no error
                    textToSpeech.setLanguage( Locale.ENGLISH);

                }else
                {
                    Toast.makeText( DocTextActivity.this,"Error",Toast.LENGTH_SHORT ).show();
                }
            }

        } );
    }

    private void init(){
        textData= (TextView) findViewById(R.id.textData);
        position = getIntent().getIntExtra("position",-1);
        displayFromSdcard();
        speekBtn=(FloatingActionButton)findViewById(R.id.speakBtn);
        stopBtn=(FloatingActionButton)findViewById(R.id.stopBtn);
        speekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Texttospeak();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Texttostop();
            }
        });
    }

    private void Texttospeak() {
        //get text from edit text
        if(setString.equals( "" )){
            // Thete is no text in edit text
            Toast.makeText( DocTextActivity.this,"Please enter text...",Toast.LENGTH_SHORT ).show();
        }
        else
        {
            Toast.makeText( DocTextActivity.this,setString,Toast.LENGTH_SHORT ).show();
            //Speak the text
            textToSpeech.speak( setString, TextToSpeech.QUEUE_FLUSH,null );
        }
    }

    private void Texttostop() {
        //Stope btn clijck
        if(textToSpeech.isSpeaking()){
            //if is speaking the stop
            textToSpeech.stop();
            //mTTS.shutdown();
        }
        else{
            //not speaking
            Toast.makeText( DocTextActivity.this,"Not speaking",Toast.LENGTH_SHORT ).show();
        }
    }

    private void displayFromSdcard() {
        //DocTextList.fileList.get(position).getName()
        textFileName = String.valueOf(DocTextList.fileList.get(position));
        try {
            File myfile=new File(textFileName);
            FileInputStream fileInputStream= new FileInputStream(myfile);
            BufferedReader myreader=new BufferedReader(new InputStreamReader(fileInputStream));
            String aDataRow=" ";
            String aBuffer=" ";
            String space="\n";
            while ((aDataRow=myreader.readLine())!=null){
                aBuffer+=aDataRow+space;
            }
            textData.setText(aBuffer);
            setString=aBuffer;
            myreader.close();
            Toast.makeText(this, "Done...", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        if(textToSpeech!=null || textToSpeech.isSpeaking()){
            //if is speaking the stop
            textToSpeech.stop();
            //mTTS.shutdown();
        }
        super.onPause();
    }
    /*@Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta=pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(),"-");
    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }*/
}


