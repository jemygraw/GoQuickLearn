package net.duokr.goquicklearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;

import java.util.List;

public class ContentViewActivity extends AppCompatActivity implements
		ViewPager.OnPageChangeListener {
	private ViewPager contentViewPager;
	private ContentSlidePagerAdapter contentPagerAdapter;
	private int currentContentIndex = -1;
	private int courseResourceId;
	private boolean showTip = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson_list_view_layout);
		Toolbar toolbar = (Toolbar) findViewById(R.id.content_toolBar);
		setSupportActionBar(toolbar);
		this.contentViewPager = (ViewPager) this
				.findViewById(R.id.contentViewPager);

		if (savedInstanceState != null) {
			this.currentContentIndex = savedInstanceState
					.getInt("learnContentIndex");
			this.courseResourceId = savedInstanceState.getInt(
					"courseResourceId", R.xml.basic_course_list);
		} else {
			Intent intent = this.getIntent();
			if (intent != null) {
				this.currentContentIndex = intent.getIntExtra(
						"learnContentIndex", -1);
				this.courseResourceId = intent.getIntExtra("courseResourceId",
						R.xml.basic_course_list);
			}
		}
		List<LearnContent> learnContentList = LearnContentLoader
				.loadLearnContentList(this.courseResourceId, this);
		this.contentPagerAdapter = new ContentSlidePagerAdapter(
				getSupportFragmentManager(), learnContentList);
		this.contentViewPager.setAdapter(this.contentPagerAdapter);
		if (this.currentContentIndex != -1) {
			this.contentViewPager.setCurrentItem(this.currentContentIndex);
			this.setTitle(this.contentPagerAdapter
					.getPageTitle(this.currentContentIndex));
		}
		this.contentViewPager.setOnPageChangeListener(this);

		// set action bar behavior
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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

	@Override
	public void onBackPressed() {
		if (this.contentViewPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			this.contentViewPager.setCurrentItem(this.contentViewPager
					.getCurrentItem() - 1);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("learnContentIndex",
				this.contentViewPager.getCurrentItem());
		outState.putInt("courseResourceId", this.courseResourceId);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {

	}

	@Override
	public void onPageSelected(int i) {
		this.setTitle(this.contentPagerAdapter.getPageTitle(i));
	}

	/**
	 * Sequence 1 - scrolling 2 - scrolled 0 - do nothing
	 */
	@Override
	public void onPageScrollStateChanged(int status) {
		if (status == 1) {
			int currentContentIndex = this.contentViewPager.getCurrentItem();
			if (currentContentIndex == 0
					|| currentContentIndex == this.contentPagerAdapter
							.getCount() - 1) {
				showTip = true;
			}
		} else if (status == 0) {
			int currentContentIndex = this.contentViewPager.getCurrentItem();
			if (showTip) {
				if (currentContentIndex == 0) {
					Toast.makeText(this, R.string.no_previous_chapter_tip,
							Toast.LENGTH_SHORT).show();
				} else if (currentContentIndex == this.contentPagerAdapter
						.getCount() - 1) {
					Toast.makeText(this, R.string.no_next_chapter_tip,
							Toast.LENGTH_SHORT).show();
				}
				showTip = false;
			}
		}
	}

}
