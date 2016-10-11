package net.duokr.goquicklearn.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ExampleListFragment extends ListFragment {

	private List<LearnContent> learnContentList;
	private String CONTENT_NAME = "contentName";
	private String CONTENT_ABSTRACT = "contentAbstract";
	private Intent contentViewIntent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_example_list_layout,
				container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.learnContentList = LearnContentLoader.loadLearnContentList(
				R.xml.example_course_list, this.getActivity());
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (LearnContent content : this.learnContentList) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put(CONTENT_NAME, content.getContentName());
			listItem.put(CONTENT_ABSTRACT, content.getContentAbstract());

			listItems.add(listItem);
		}

		SimpleAdapter contentSimpleAdapter = new SimpleAdapter(
				this.getActivity(), listItems,
				R.layout.meta_lesson_list_item_layout, new String[] {
						CONTENT_NAME, CONTENT_ABSTRACT }, new int[] {
						R.id.contentName, R.id.contentAbstract });

		ListView contentListView = (ListView) this.getView().findViewById(
				android.R.id.list);
		contentListView.setAdapter(contentSimpleAdapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (this.contentViewIntent == null) {
			this.contentViewIntent = new Intent(this.getActivity(),
					ContentViewActivity.class);
		}
		Bundle bundleData = new Bundle();
		bundleData.putInt("learnContentIndex", position);
		bundleData.putInt("courseResourceId", R.xml.example_course_list);
		this.contentViewIntent.putExtras(bundleData);

		this.startActivity(this.contentViewIntent);

	}
}
