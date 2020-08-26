package com.example.componentesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.componentesbasicos.Fragments.Tab1;
import com.example.componentesbasicos.Fragments.Tab2;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Tabs e Option Menu");
        setContentView(R.layout.activity_2);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Tab 1", Tab1.class)
                .add("Tab 2", Tab2.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.opcao1) {
            Toast.makeText(getApplicationContext(), "Opção 1 Clicada", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.opcao2) {
            Toast.makeText(getApplicationContext(), "Opção 2 Clicada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Opção 3 Clicada", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
