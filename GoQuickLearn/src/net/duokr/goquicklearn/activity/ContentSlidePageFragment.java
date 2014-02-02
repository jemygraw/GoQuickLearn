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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_content_slide_page, container, false);

		return rootView;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			this.learnContent = (LearnContent) savedInstanceState
					.getSerializable("learnContent");
		}
		this.contentWebView = (WebView) this.getView().findViewById(
				R.id.contentWebView);
		this.contentWebView.getSettings().setJavaScriptEnabled(true);
		this.contentWebView.getSettings().setBuiltInZoomControls(true);
		this.contentWebView.setLongClickable(true);
		this.contentWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				try {
					((ProgressBar) getActivity().findViewById(
							R.id.contentLoadingProgressBar))
							.setVisibility(ProgressBar.VISIBLE);
				} catch (Exception ex) {
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				try {
					((ProgressBar) getActivity().findViewById(
							R.id.contentLoadingProgressBar))
							.setVisibility(ProgressBar.INVISIBLE);
				} catch (Exception ex) {
				}
			}

		});
		// fix the white background which switching
		this.contentWebView.setBackgroundColor(0);

		this.contentWebView.loadUrl("file:///android_asset/tutorial/"
				+ learnContent.getChapter());

	}

	public LearnContent getLearnContent() {
		return this.learnContent;
	}

}
