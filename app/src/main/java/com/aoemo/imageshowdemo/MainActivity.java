package com.aoemo.imageshowdemo;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        Logger.e(getIntent().toString());

        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            String type = intent.getType();//image/gif
            Uri data = intent.getData();//null content://com.aoemo.giphydemo/my_image/image_manager_disk_cache/ff2851f7100d44541b03ea4df4d7b8d9df28f3017765b71c3477641cb6be9c84.0
            Logger.e("intent.getData()---------->" + data);
            String dataString = intent.getDataString();//null content://com.aoemo.giphydemo/my_image/image_manager_disk_cache/ff2851f7100d44541b03ea4df4d7b8d9df28f3017765b71c3477641cb6be9c84.0
            Uri uri = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ClipData clipData = intent.getClipData();//null
                Logger.e("intent.getClipData()---------->" + clipData);
                if (clipData != null) {
                    int itemCount = clipData.getItemCount();
                    if (itemCount != 0) {
                        ClipData.Item item = clipData.getItemAt(0);
                        uri = item.getUri();//content://com.aoemo.fileproviderdemo.MainActivity/my_cache_images/image_manager_disk_cache/cae5387ea937f868cabcbd73147b2f13506cc0bd0e7590ca2c48fa95108f2998.0
                    }
                }
            }
            mTextView.append("\n");
            mTextView.append("intent.getType():");
            mTextView.append("\n");
            mTextView.append(type + "");

            mTextView.append("\n");
            mTextView.append("intent.getData():");
            mTextView.append("\n");
            mTextView.append(data + "");

            mTextView.append("\n");
            mTextView.append("intent.getClipData():");
            mTextView.append("\n");
            mTextView.append(uri + "");

            if (!TextUtils.isEmpty(type) && type.contains("image")) {
                if (data != null) {
                    Glide.with(this)
                            .load(data)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(mImageView);
                } else if (uri != null) {
                    Glide.with(this)
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(mImageView);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
