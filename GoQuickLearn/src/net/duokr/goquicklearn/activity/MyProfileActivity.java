package net.duokr.goquicklearn.activity;

import net.duokr.goquicklearn.R;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;

public class MyProfileActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile_layout);
		// set action bar behavior
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		this.loadMyProfile();
	}

	private void loadMyProfile() {
		WebView profileWebView = (WebView) this
				.findViewById(R.id.myProfileWebview);
		profileWebView.loadUrl("file:///android_asset/profile.html");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
