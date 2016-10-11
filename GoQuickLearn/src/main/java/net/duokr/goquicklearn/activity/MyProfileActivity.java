package net.duokr.goquicklearn.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import net.duokr.goquicklearn.R;

public class MyProfileActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile_layout);
		Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolBar);
		setSupportActionBar(toolbar);
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
