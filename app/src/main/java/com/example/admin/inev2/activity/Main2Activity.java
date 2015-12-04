package com.example.admin.inev2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.admin.inev2.R;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private Toolbar appbar;
    private Spinner sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8,sp9,sp10,spb1,spb2,spb3,spb4,spb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp1=(Spinner)findViewById(R.id.spinnera1);listas(5, sp1);
        sp2=(Spinner)findViewById(R.id.spinnera2);listas(5, sp2);
        sp3=(Spinner)findViewById(R.id.spinnera3);listas(5, sp3);
        sp4=(Spinner)findViewById(R.id.spinnera4);listas(5,sp4);
        sp5=(Spinner)findViewById(R.id.spinnera5);listas(5, sp5);
        sp6=(Spinner)findViewById(R.id.spinnera6);listas(5, sp6);
        sp7=(Spinner)findViewById(R.id.spinnera7);listas(5, sp7);
        sp8=(Spinner)findViewById(R.id.spinnera8);listas(5, sp8);
        sp9=(Spinner)findViewById(R.id.spinnera9);listas(5, sp9);
        sp10=(Spinner)findViewById(R.id.spinnera10);listas(5, sp10);

        spb1=(Spinner)findViewById(R.id.spinnerb1);listas(5,spb1);
        spb2=(Spinner)findViewById(R.id.spinnerb2);listas(5,spb2);
        spb3=(Spinner)findViewById(R.id.spinnerb3);listas(5,spb3);
        spb4=(Spinner)findViewById(R.id.spinnerb4);listas(5,spb4);
        spb5=(Spinner)findViewById(R.id.spinnerb5);listas(5,spb5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    public void listas(int tam,Spinner sp) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < tam; i++) {
            list.add("items" + i);
        }

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        sp.setAdapter(adp);
    }
}
