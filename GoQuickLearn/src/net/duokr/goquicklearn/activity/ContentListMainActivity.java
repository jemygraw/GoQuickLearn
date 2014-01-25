package net.duokr.goquicklearn.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ContentListMainActivity extends Activity implements
        OnItemClickListener {
    private List<LearnContent> learnContentList;
    private String CONTENT_NAME = "contentName";
    private String CONTENT_ABSTRACT = "contentAbstract";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list_layout);

        this.learnContentList = LearnContentLoader.loadLearnContentList(this);
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (LearnContent content : this.learnContentList) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put(CONTENT_NAME, content.getContentName());
            listItem.put(CONTENT_ABSTRACT, content.getContentAbstract());

            listItems.add(listItem);
        }

        SimpleAdapter contentSimpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.content_list_item_layout, new String[]{CONTENT_NAME,
                CONTENT_ABSTRACT}, new int[]{R.id.contentName,
                R.id.contentAbstract});

        ListView contentListView = (ListView) this
                .findViewById(R.id.contentListView);
        contentListView.setOnItemClickListener(this);
        contentListView.setAdapter(contentSimpleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.content_list_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        LearnContent learnContent = this.learnContentList.get(position);
        if (learnContent != null) {
            Intent intent = new Intent(ContentListMainActivity.this,
                    ContentViewActivity.class);
            Bundle bundleData = new Bundle();
            bundleData.putSerializable("learnContent", learnContent);
            bundleData.putInt("learnContentIndex", position);
            intent.putExtras(bundleData);
            this.startActivity(intent);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.app_about_menu_item:
                Intent intent = new Intent(ContentListMainActivity.this,
                        AboutActivity.class);
                this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
