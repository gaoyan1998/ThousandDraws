package com.ml.thousandsdraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class logo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.lg_start).setOnClickListener(this);
        findViewById(R.id.lg_prt).setOnClickListener(this);
        findViewById(R.id.lg_update).setOnClickListener(this);
        findViewById(R.id.lg_about).setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lg_start:
                startActivity(new Intent(logo.this,Worker.class));
                break;
            case R.id.lg_prt:
                startActivity(new Intent(logo.this,ViewPager.class));
                break;
            case R.id.lg_update:
                startActivity(new Intent(logo.this,Worker.class));
                break;
            case R.id.lg_about:
                startActivity(new Intent(logo.this,Worker.class));
                break;
        }
    }
}
