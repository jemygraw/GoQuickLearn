package net.duokr.goquicklearn.activity;

import java.util.List;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContentViewActivity extends Activity implements
        OnTouchListener {
    private List<LearnContent> learnContentList;
    private LearnContent currentLearnContent;
    private WebView contentWebView;
    private Button previousChapterButton;
    private Button nextChapterButton;
    private int currentChapterIndex;
    private ContentViewGestureListener gestureListener;
    private GestureDetector gestureDector;
    private boolean showTip;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view_layout);

        this.learnContentList = LearnContentLoader.loadLearnContentList(this);

        this.contentWebView = (WebView) this.findViewById(R.id.contentWebView);
        this.contentWebView.getSettings().setJavaScriptEnabled(true);
        this.contentWebView.setOnTouchListener(this);
        this.contentWebView.setLongClickable(true);
        // fix the white background which switching
        this.contentWebView.setBackgroundColor(0);
        this.gestureListener = new ContentViewGestureListener(this);
        this.gestureDector = new GestureDetector(
                this.contentWebView.getContext(), this.gestureListener);

        if (savedInstanceState != null) {
            this.currentChapterIndex = savedInstanceState
                    .getInt("learnContentIndex");
            this.currentLearnContent = this.learnContentList
                    .get(this.currentChapterIndex);
        } else {
            Intent intent = this.getIntent();
            this.currentChapterIndex = intent.getIntExtra("learnContentIndex",
                    -1);
            this.currentLearnContent = (LearnContent) intent
                    .getSerializableExtra("learnContent");
        }
        SharedPreferences pref=this.getSharedPreferences("preference_file",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor=pref.edit();
        showTip=pref.getBoolean("showTip",true);
        if(showTip){
            Toast.makeText(this,R.string.chapter_switch_tip,Toast.LENGTH_LONG).show();
            showTip=false;
            prefEditor.putBoolean("showTip",showTip);
            prefEditor.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("learnContentIndex", this.currentChapterIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.displayCurrentChapter(this.currentLearnContent);
    }

    public LearnContent getPreviousChapter() {
        LearnContent learnContent = null;
        if (this.currentChapterIndex - 1 >= 0) {
            learnContent = this.learnContentList
                    .get(this.currentChapterIndex - 1);
            this.currentChapterIndex--;
        }
        return learnContent;
    }

    public LearnContent getNextChapter() {
        LearnContent learnContent = null;
        if (this.currentChapterIndex + 1 < this.learnContentList.size()) {
            learnContent = this.learnContentList
                    .get(this.currentChapterIndex + 1);
            this.currentChapterIndex++;
        }
        return learnContent;
    }

    public void displayCurrentChapter(LearnContent learnContent) {
        this.setTitle(learnContent.getContentName());
        this.contentWebView.loadUrl("file:///android_asset/tutorial/"
                + learnContent.getChapter());
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return this.gestureDector.onTouchEvent(event);
    }

}
