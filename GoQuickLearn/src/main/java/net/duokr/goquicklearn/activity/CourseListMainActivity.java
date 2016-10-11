package net.duokr.goquicklearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import net.duokr.goquicklearn.R;

public class CourseListMainActivity extends AppCompatActivity {
	private ViewPager courseViewPager;
	private CourseListMainPagerAdapter courseListMainPagerAdapter;
	private int currentNavIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_view_pager_layout);
		Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolBar);
		setSupportActionBar(toolbar);
		this.courseViewPager = (ViewPager) this
				.findViewById(R.id.courseViewPager);

		SpinnerAdapter navSpinnerAdapter = ArrayAdapter.createFromResource(
				this, R.array.course_name_list,
				R.layout.meta_course_nav_dropdown_list);
		ActionBar supportActionBar = this.getSupportActionBar();
		supportActionBar.setDisplayShowTitleEnabled(false);
		supportActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		CourseNavigationListener navListener = new CourseNavigationListener();
		supportActionBar.setListNavigationCallbacks(navSpinnerAdapter,
				navListener);

		this.courseListMainPagerAdapter = new CourseListMainPagerAdapter(
				this.getSupportFragmentManager());
		this.courseViewPager.setAdapter(this.courseListMainPagerAdapter);
		this.courseViewPager
				.setOnPageChangeListener(new CoursePageChangeListener());
		if (savedInstanceState != null) {
			this.currentNavIndex = savedInstanceState.getInt("navIndex");
		}
		supportActionBar.setSelectedNavigationItem(this.currentNavIndex);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setSelectedNavigationItem(this.currentNavIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		this.currentNavIndex = savedInstanceState.getInt("navIndex", 0);
		getSupportActionBar().setSelectedNavigationItem(this.currentNavIndex);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("navIndex", this.courseViewPager.getCurrentItem());
		super.onSaveInstanceState(outState);
	}

	class CourseNavigationListener implements ActionBar.OnNavigationListener {

		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			currentNavIndex = itemPosition;
			courseViewPager.setCurrentItem(itemPosition);
			return true;
		}

	}

	class CoursePageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int status) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			getSupportActionBar().setSelectedNavigationItem(position);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_course_list_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
		case R.id.popupMenu:
			Intent intent1 = new Intent(CourseListMainActivity.this,
					AboutActivity.class);
			this.startActivity(intent1);
			return true;
		case R.id.app_my_profile_menu_item:
			Intent intent2 = new Intent(CourseListMainActivity.this,
					MyProfileActivity.class);
			this.startActivity(intent2);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
