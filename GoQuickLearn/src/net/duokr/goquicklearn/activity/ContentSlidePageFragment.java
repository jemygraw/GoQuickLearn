package net.duokr.goquicklearn.activity;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by jemy on 1/25/14.
 */
public class ContentSlidePageFragment extends Fragment {
	private WebView contentWebView;
	private LearnContent learnContent;

	public ContentSlidePageFragment() {
		super();
	}

	public void setLearnContent(LearnContent learnContent) {
		this.learnContent = learnContent;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("learnContent", learnContent);
		super.onSaveInstanceState(outState);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_content_slide_page, container, false);

		if (savedInstanceState != null) {
			this.learnContent = (LearnContent) savedInstanceState
					.getSerializable("learnContent");
		}
		this.contentWebView = (WebView) rootView
				.findViewById(R.id.contentWebView);
		this.contentWebView.getSettings().setJavaScriptEnabled(true);
		this.contentWebView.getSettings().setBuiltInZoomControls(true);
		this.contentWebView.setLongClickable(true);
		this.contentWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				((ProgressBar) getActivity().findViewById(
						R.id.contentLoadingProgressBar))
						.setVisibility(ProgressBar.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				((ProgressBar) getActivity().findViewById(
						R.id.contentLoadingProgressBar))
						.setVisibility(ProgressBar.INVISIBLE);
			}

		});
		// fix the white background which switching
		this.contentWebView.setBackgroundColor(0);
		this.contentWebView.loadUrl("file:///android_asset/tutorial/"
				+ learnContent.getChapter());
		return rootView;
	}

	public LearnContent getLearnContent() {
		return this.learnContent;
	}

}
