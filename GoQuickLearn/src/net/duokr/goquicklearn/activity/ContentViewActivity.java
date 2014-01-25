package net.duokr.goquicklearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;

import java.util.List;

public class ContentViewActivity extends FragmentActivity implements ViewPager.OnPageChangeListener

{
    private ViewPager contentViewPager;
    private ContentSlidePagerAdapter contentPagerAdapter;
    private int currentContentIndex = -1;
    private boolean showTip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view_layout);

        this.contentViewPager = (ViewPager) this.findViewById(R.id.contentViewPager);
        List<LearnContent> learnContentList = LearnContentLoader.loadLearnContentList(this);
        if (savedInstanceState != null) {
            this.currentContentIndex = savedInstanceState
                    .getInt("learnContentIndex");
        } else {
            Intent intent = this.getIntent();
            if (intent != null) {
                this.currentContentIndex = intent.getIntExtra("learnContentIndex",
                        -1);
            }
        }
        this.contentPagerAdapter = new ContentSlidePagerAdapter(getSupportFragmentManager(), learnContentList);
        this.contentViewPager.setAdapter(this.contentPagerAdapter);
        if (this.currentContentIndex != -1) {
            this.contentViewPager.setCurrentItem(this.currentContentIndex);
            this.setTitle(this.contentPagerAdapter.getPageTitle(this.currentContentIndex));
        }
        this.contentViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        if (this.contentViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            this.contentViewPager.setCurrentItem(this.contentViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("learnContentIndex", this.contentViewPager.getCurrentItem());
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
     * Sequence
     * 1 - scrolling
     * 2 - scrolled
     * 0 - do nothing
     */
    @Override
    public void onPageScrollStateChanged(int status) {
        if (status == 1) {
            int currentContentIndex = this.contentViewPager.getCurrentItem();
            if (currentContentIndex == 0 || currentContentIndex == this.contentPagerAdapter.getCount() - 1) {
                showTip = true;
            }
        }
        else if (status == 0) {
            int currentContentIndex = this.contentViewPager.getCurrentItem();
            if (showTip) {
                if (currentContentIndex == 0) {
                    Toast.makeText(this, R.string.no_previous_chapter_tip, Toast.LENGTH_SHORT).show();
                } else if (currentContentIndex == this.contentPagerAdapter.getCount() - 1) {
                    Toast.makeText(this, R.string.no_next_chapter_tip, Toast.LENGTH_SHORT).show();
                }
                showTip = false;
            }
        }
    }


}
