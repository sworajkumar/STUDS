package com.cpetsolut.com.studs.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cpetsolut.com.studs.R;
import com.cpetsolut.com.studs.Utils.SessinSave;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

public class SpalshActivity extends AppCompatActivity {

    private final int WAIT_TIME = 1000;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private Button BtnNext;
    private static int TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnNext =(Button)findViewById(R.id.btnNext);
        permissions.add(CAMERA);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(RECORD_AUDIO);
        permissions.add(ACCESS_FINE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        checkPermissins();
        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissins();
            }
        });
    }

    private void checkPermissins() {
        if (ContextCompat.checkSelfPermission(SpalshActivity.this, Manifest.permission.CAMERA)    != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);     }
            checkPermissionOnAppPermScreen("Camera");
        }else if(ContextCompat.checkSelfPermission(SpalshActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(SpalshActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},ALL_PERMISSIONS_RESULT);          }
            checkPermissionOnAppPermScreen("Storage");
        }else if(ContextCompat.checkSelfPermission(SpalshActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(SpalshActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ALL_PERMISSIONS_RESULT);         }
            checkPermissionOnAppPermScreen("Location");
        }
        else {
            BtnNext.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SpalshActivity.this, ImageSliderActivity.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
           /* if (SessinSave.getsessin("profile_id", SpalshActivity.this).equalsIgnoreCase("")) {
                // Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SpalshActivity.this, ImageSliderActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SpalshActivity.this, NavigationActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }, TIME_OUT);
            }*/
        }
    }
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for(int i=0;i<permissionsToRequest.size();i++){
                    if (hasPermission(permissionsToRequest.get(i))) {
                        permissionsRejected.remove(permissionsToRequest.get(i));
                    } else {
                        permissionsRejected.add(permissionsToRequest.get(i));
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            checkPermissionOnAppPermScreen(permissionsRejected.get(0));
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                                permissionsRejected.clear();
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    public void checkPermissionOnAppPermScreen(String perm) {
        try {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(parentLayout, perm+" Permission are mandatory to access.", Snackbar.LENGTH_LONG)
                    .setAction("SETTINGS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
// Changing message text color
            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }catch (Exception e){e.printStackTrace();}
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();
    }


}
