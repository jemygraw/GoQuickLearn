package net.duokr.goquicklearn.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import net.duokr.goquicklearn.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);

        TextView versionTextView = (TextView) this
                .findViewById(R.id.versionNumber);
        String version = versionTextView.getText().toString();
        try {
            versionTextView.setText(version + this.getVersionName());
        } catch (Exception e) {
            // pass
        }
    }

    private String getVersionName() throws Exception {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        String version = packInfo.versionName;
        return version;
    }
}
