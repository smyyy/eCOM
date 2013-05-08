package com.ericsson.ecom.ui;

import com.ericsson.ecom.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/**
 * Created with IntelliJ IDEA.
 * User: eranibl
 * Date: 2013-05-07
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class AboutActivity extends Activity {
    private WebView web;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.about);
        web = (WebView) findViewById(R.id.web);
    }

    @Override
    protected void onStart() {
        super.onStart();
        web.loadUrl(getString(R.string.about_url));
    }

    public void done(View v) {
        finish();
    }

}