package com.thinklearing.mem;


import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.StaticData;

import androidx.drawerlayout.widget.DrawerLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FragmentTransaction fmTr;

    protected void initMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void showFragment(BaseFragment fragment) {
        fmTr = getSupportFragmentManager().beginTransaction();
        fmTr.replace(R.id.content, fragment);
        fmTr.addToBackStack(null);
        fmTr.commit();
    }

    public void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (f instanceof mainFragment) {
            Log.i("BACK PRESSED", "BACK PRESSED");
        }
        else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            showFragment(new mainFragment());
        } else if (id == R.id.nav_report) {
            StaticData.Title = StaticData.Title_Report;
            StaticData.Category = DataBaseArg.TBL_klm;
            showFragment(new raportFragment());

        } else if (id == R.id.nav_word) {
            //Özel Kelimelerim
            StaticData.Title = StaticData.Title_Ozel_Klm;
            StaticData.Category = DataBaseArg.TBL_klm;
            showFragment(new levelsFragment(DataBaseArg.TBL_klm));
        } else if (id == R.id.nav_error) {
            //firelerim

        } else if (id == R.id.nav_about) {
            //hakkında


        } else if (id == R.id.nav_conn) {
            //iletişim

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
