package com.ml.thousandsdraw;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageButton;

public class PreviewPageActivity extends Activity implements View.OnClickListener {

    private TextView previewTitle;
    private ImageView previewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_page);

        previewTitle = (TextView) findViewById(R.id.preview_title);
        previewImage = (ImageView) findViewById(R.id.preview_image);
        findViewById(R.id.preview_rename).setOnClickListener(this);
        findViewById(R.id.preview_delete).setOnClickListener(this);
        findViewById(R.id.preview_editor).setOnClickListener(this);
        findViewById(R.id.preview_save).setOnClickListener(this);
        findViewById(R.id.preview_share).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.preview_rename:
                //TODO implement
                break;
            case R.id.preview_delete:
                //TODO implement
                break;
            case R.id.preview_editor:
                //TODO implement
                break;
            case R.id.preview_save:
                //TODO implement
                break;
            case R.id.preview_share:
                //TODO implement
                break;
        }
    }
}
