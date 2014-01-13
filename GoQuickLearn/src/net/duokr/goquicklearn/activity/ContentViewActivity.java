package net.duokr.goquicklearn.activity;

import java.util.List;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class ContentViewActivity extends Activity implements OnClickListener,
		OnTouchListener {
	private List<LearnContent> learnContentList;
	private LearnContent currentLearnContent;
	private TextView contentTitleTextView;
	private WebView contentWebView;
	private Button previousChapterButton;
	private Button nextChapterButton;
	private int currentChapterIndex;
	private ContentViewGestureListener gestureListener;
	private GestureDetector gestureDector;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_view_layout);

		this.learnContentList = LearnContentLoader.loadLearnContentList(this);

		this.contentTitleTextView = (TextView) this
				.findViewById(R.id.contentTitleTextView);
		this.contentWebView = (WebView) this.findViewById(R.id.contentWebView);
		this.contentWebView.getSettings().setJavaScriptEnabled(true);
		this.contentWebView.setOnTouchListener(this);
		this.contentWebView.setLongClickable(true);
		// fix the white background which switching
		this.contentWebView.setBackgroundColor(0);
		this.gestureListener = new ContentViewGestureListener(this);
		this.gestureDector = new GestureDetector(
				this.contentWebView.getContext(), this.gestureListener);

		this.previousChapterButton = (Button) this
				.findViewById(R.id.previousChapterButton);
		this.nextChapterButton = (Button) this
				.findViewById(R.id.nextChapterButton);

		Intent intent = this.getIntent();
		this.currentChapterIndex = intent.getIntExtra("learnContentIndex", -1);
		this.currentLearnContent = (LearnContent) intent
				.getSerializableExtra("learnContent");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("learnContentIndex", this.currentChapterIndex);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		this.currentChapterIndex = savedInstanceState
				.getInt("learnContentIndex");
		this.currentLearnContent = this.learnContentList
				.get(this.currentChapterIndex);
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
		this.contentTitleTextView.setText(learnContent.getContentName());

		this.contentWebView.loadUrl("file:///android_asset/tutorial/"
				+ learnContent.getChapter());

		if (learnContent.getPreviousChapter() != null
				&& !learnContent.getPreviousChapter().trim().equals("")) {
			this.previousChapterButton.setVisibility(View.VISIBLE);
			this.previousChapterButton.setOnClickListener(this);
		} else {
			this.previousChapterButton.setVisibility(View.INVISIBLE);
		}
		if (learnContent.getNextChapter() != null
				&& !learnContent.getNextChapter().trim().equals("")) {
			this.nextChapterButton.setVisibility(View.VISIBLE);
			this.nextChapterButton.setOnClickListener(this);
		} else {
			this.nextChapterButton.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.previousChapterButton:
			int previousChapterIndex = this.currentChapterIndex - 1;
			LearnContent learnContentPrevious = this.learnContentList
					.get(previousChapterIndex);
			if (learnContentPrevious != null) {
				this.currentChapterIndex = previousChapterIndex;
				this.displayCurrentChapter(learnContentPrevious);
			}
			break;
		case R.id.nextChapterButton:
			int nextChapterIndex = this.currentChapterIndex + 1;
			LearnContent learnContentNext = this.learnContentList
					.get(nextChapterIndex);
			if (learnContentNext != null) {
				this.currentChapterIndex = nextChapterIndex;
				this.displayCurrentChapter(learnContentNext);
			}
			break;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		return this.gestureDector.onTouchEvent(event);
	}

}
