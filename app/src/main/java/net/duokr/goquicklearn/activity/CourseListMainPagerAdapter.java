package net.duokr.goquicklearn.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CourseListMainPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> courseFragmentList;

	public CourseListMainPagerAdapter(FragmentManager fm) {
		super(fm);
		this.courseFragmentList = new ArrayList<Fragment>();
		this.courseFragmentList.add(new BasicListFragment());
		this.courseFragmentList.add(new ExampleListFragment());

	}

	@Override
	public Fragment getItem(int position) {
		return this.courseFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return this.courseFragmentList.size();
	}

}
