package com.cpetsolut.com.studs.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolut.com.studs.Fragment.ArticleFragment;
import com.cpetsolut.com.studs.Fragment.DashBoardFragment;
import com.cpetsolut.com.studs.Fragment.EventFragment;
import com.cpetsolut.com.studs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ONavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationview;
    private CircleImageView headerimage;
    private LinearLayout profileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_navigation);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);//Botom Navigation View
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.open, R.string.close);
        mdrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       // getSupportActionBar().setHomeButtonEnabled(false);

         getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         getSupportActionBar().setHomeButtonEnabled(false);

        navigationview=(NavigationView)findViewById(R.id.navigationview);
        navigationview.setNavigationItemSelectedListener(ONavigationActivity.this);

        View headerview=navigationview.getHeaderView(0);
        headerimage=headerview.findViewById(R.id.headerimage);
        // LoadImage();
        TextView name = (TextView) headerview.findViewById(R.id.name);
        TextView mail = (TextView) headerview.findViewById(R.id.mailid);
        profileLayout = (LinearLayout) headerview.findViewById(R.id.profileLayout);
        profileLayout.setOnClickListener(v ->{
            Toast.makeText(this, "Student Full Information", Toast.LENGTH_SHORT).show();
        });

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new DashBoardFragment()).commit();
            navigationview.setCheckedItem(R.id.dashboard);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.dashboard:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new DashBoardFragment()).commit();
                            return true;
                        case R.id.bottom_nav_Lib:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new ArticleFragment()).commit();
                            return true;
                        case R.id.bottom_nav_actical:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new ArticleFragment()).commit();
                            return true;

                    }
                    return false;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bottom_nav_search) {
            Toast.makeText(this, "Search Button", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.bottom_nav_notification){
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.bottom_nav_setting){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new DashBoardFragment()).commit();
        } else if (id == R.id.plus) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new EventFragment()).commit();
        }else if (id== R.id.freecourse){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new EventFragment()).commit();
        }
      /*  } else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ONavigationActivity.this);
            builder.setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
*/

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}