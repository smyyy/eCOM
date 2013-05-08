package com.ericsson.ecom.ui;

import android.app.ActionBar;
import android.app.Activity;
import com.ericsson.ecom.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.MenuItem;


/**
 * DESCRIPTION
 *
 * @author eranibl
 * @date 2013-04-19, 10:18
 */
public class Preferences extends Activity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Preferences");

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
