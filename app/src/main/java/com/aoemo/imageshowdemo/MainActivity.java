package com.aoemo.imageshowdemo;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.socks.library.KLog;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 203) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 203);
            return;
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });
        KLog.e(getIntent().toString());//Intent { act=android.intent.action.SEND typ=image/gif flg=0x13400001 cmp=com.aoemo.imageshowdemo/.MainActivity (has clip) (has extras) }

        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            String type = intent.getType();//image/gif
            Uri data = intent.getData();//null content://com.aoemo.giphydemo/my_image/image_manager_disk_cache/ff2851f7100d44541b03ea4df4d7b8d9df28f3017765b71c3477641cb6be9c84.0
//            KLog.e("intent.getData()---------->" + data);//intent.getData()---------->null
            String dataString = intent.getDataString();//null content://com.aoemo.giphydemo/my_image/image_manager_disk_cache/ff2851f7100d44541b03ea4df4d7b8d9df28f3017765b71c3477641cb6be9c84.0
            Uri uri = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ClipData clipData = intent.getClipData();//null
//                KLog.e("intent.getClipData()---------->" + clipData);//intent.getClipData()---------->ClipData { image/gif {U:file:///storage/emulated/0/-84513526/gifCache/sticker_-84513526_2130837886.gif} }
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

            Toast.makeText(this, data + " " + uri, Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(type) && type.contains("image")) {
                if (data != null) {
                    Glide.with(this)
                            .load(data)
//                            .asGif()
                            .placeholder(R.drawable.gif_loading)
                            .error(R.drawable.loading_error)
//                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .listener(new RequestListener<Uri, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_LONG).show();
                                    KLog.e(e + " " + model);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(mImageView);
                } else if (uri != null) {
                    Glide.with(this)
                            .load(uri)
//                            .asGif()
                            .placeholder(R.drawable.gif_loading)
                            .error(R.drawable.loading_error)
//                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .listener(new RequestListener<Uri, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_LONG).show();
                                    KLog.e(e + " " + model);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
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
