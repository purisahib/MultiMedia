package com.pradeep.allinone.pdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pradeep.allinone.R;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
/*
Created by Puri Sahib
*/
public class PdfActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String TAG="PdfActivity";
    int position=-1;
    //floating action Btn
    private FloatingActionButton convertBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        init();
    }
    private void init(){
        pdfView= (PDFView)findViewById(R.id.pdfView);
        position = getIntent().getIntExtra("position",-1);
        displayFromSdcard();
        convertBtn =(FloatingActionButton)findViewById(R.id.convertBtn);
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File myfile=new File("my.txt");
                    myfile.createNewFile();
                    FileOutputStream fOut=new FileOutputStream(myfile);
                    OutputStreamWriter myOutwriter=new OutputStreamWriter(fOut);
                    //myOutwriter.append(textData.getText());
                    myOutwriter.close();
                    fOut.close();
                    Toast.makeText(PdfActivity.this, "Done...", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(PdfActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PdfActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void displayFromSdcard() {
        pdfFileName = PdfList.fileList.get(position).getName();
        pdfView.fromFile(PdfList.fileList.get(position))
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    @Override
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
    }
}

