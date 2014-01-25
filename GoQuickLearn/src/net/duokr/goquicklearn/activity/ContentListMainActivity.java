package net.duokr.goquicklearn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.duokr.goquicklearn.R;
import net.duokr.goquicklearn.config.LearnContent;
import net.duokr.goquicklearn.config.LearnContentLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentListMainActivity extends Activity implements
        OnItemClickListener {
    private List<LearnContent> learnContentList;
    private String CONTENT_NAME = "contentName";
    private String CONTENT_ABSTRACT = "contentAbstract";
    private Intent contentViewIntent;

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
        if (this.contentViewIntent == null) {
            this.contentViewIntent = new Intent(ContentListMainActivity.this,
                    ContentViewActivity.class);
        }
        Bundle bundleData = new Bundle();
        bundleData.putInt("learnContentIndex", position);
        this.contentViewIntent.putExtras(bundleData);

        this.startActivity(this.contentViewIntent);
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
