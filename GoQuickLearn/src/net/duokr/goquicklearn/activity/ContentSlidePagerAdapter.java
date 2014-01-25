package net.duokr.goquicklearn.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import net.duokr.goquicklearn.config.LearnContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jemy on 1/25/14.
 */
public class ContentSlidePagerAdapter extends FragmentPagerAdapter {
    private int contentListSize;
    private List<ContentSlidePageFragment> contentFragmentList;
    public ContentSlidePagerAdapter(FragmentManager fm, List<LearnContent> learnContentList){
        super(fm);
        this.contentFragmentList=new ArrayList<ContentSlidePageFragment>();
        this.contentListSize=learnContentList.size();
        for(LearnContent learnContent : learnContentList){
            ContentSlidePageFragment fragment=new ContentSlidePageFragment();
            fragment.setLearnContent(learnContent);
            this.contentFragmentList.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position){
        return this.contentFragmentList.get(position);
    }

    @Override
    public int getCount(){
        return this.contentListSize;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.contentFragmentList.get(position).getLearnContent().getContentName();
    }
}


