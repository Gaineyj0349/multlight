package com.gainwise.multlight;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import osmandroid.project_basics.Task;
import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

public class Main6Activity extends AppCompatActivity {
    private Camera camera;
    private Camera.Parameters parameter;
    private boolean deviceHasFlash;
    private boolean isFlashLightOn = false;
    CameraManager camManager;
    private boolean hasAccess = false;
    static final Integer CAMERA = 0x5;
    String cameraId = null;
    ArrayList<Integer> positions;
    AdView adView;
    Random random;
    public static int pos1;
    Dialog dialog3;

    public static ArrayList<String> colors;


    String[] colorMain = new String[] {"#000000", "#ffffff","#ff1744","#ff4081","#ab47bc","#e040fb","#9575cd","#651fff",
            "#7986cb","#3dfafe","#64b5f6","#2979ff","#81d4fa","#00b0ff","#80deea","#00e5ff",
            "#4db6ac","#1de9b6","#81c764","#00e676","#aed581","#76ff03","#dce775","#c6ff00"
            ,"#fff59d","#ffea00","#ffd54f","#ffc400","#ffb74d","#ff9100","#ff8a65","#ff3d00","#a1887f","#4e342e"
            ,"#e0e0e0","#424242","#90a4ae","#37474f"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
random = new Random();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        deviceHasFlash = getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        askForPermission(Manifest.permission.CAMERA,CAMERA);
        colors = new ArrayList<String>();
        Collections.addAll(colors, colorMain);

        adView = (AdView) findViewById(R.id.adView22);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        populateMoreColors();
        Log.i("JOSH master", ""+colors.size());
        Collections.shuffle(colors);

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


        if (id == R.id.follow) {

            Task.FollowOnFb(Main6Activity.this,"1953614828228585","https://www.facebook.com/RealGainWise/");
            return true;
        }
        if (id == R.id.rate) {
            Task.RateApp(Main6Activity.this, "com.gainwise.multlight");
            return true;
        }

        if (id == R.id.share) {
            Task.ShareApp(Main6Activity.this, "com.gainwise.multlight", "Awesome app",
                    "Awesome multi-color light app");
            return true;
        }


        if (id == R.id.otherapps) {
            Task.MoreApps(Main6Activity.this, "GainWise");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void button1click(){


        if(!isFlashLightOn){
            Log.i("JOSH", "helper1");
            turnOnTheFlash();
        }else{
            Log.i("JOSH", "helper2");
            turnOffTheFlash();
        }

    }

    public void initCam(){

        if(!deviceHasFlash){
            FabToast.makeText(Main6Activity.this, "Sorry, you device does not have any camera", Toast.LENGTH_LONG,
                    FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
            return;
        }
        else{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Usually back camera is at 0 position.
                try {
                    cameraId = camManager.getCameraIdList()[0];
                    //Turn ON
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }else{

                camera = Camera.open();
                parameter = camera.getParameters();}
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(Main6Activity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main6Activity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(Main6Activity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(Main6Activity.this, new String[]{permission}, requestCode);
            }
        } else {
            //permission is already granted
            initCam();
            hasAccess = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            initCam();
            hasAccess = true;
        }else{
            Main6Activity.this.finish();
            FabToast.makeText(getApplicationContext(),"You must allow permission for this app to function",
                    Toast.LENGTH_LONG,FabToast.ERROR,FabToast.POSITION_CENTER).show();
        }
    }

    private void turnOffTheFlash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                camManager.setTorchMode(cameraId, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            isFlashLightOn = false;
        } else {

            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameter);
            camera.stopPreview();
            isFlashLightOn = false;

        }
    }

    private void turnOnTheFlash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                camManager.setTorchMode(cameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            isFlashLightOn = true;

        }else{
            Log.i("JOSH", "helper3");
            if(camera != null){
                Log.i("JOSH", "helper4");
                parameter = camera.getParameters();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameter);
                camera.startPreview();
                isFlashLightOn = true;


            }}
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(hasAccess) {
            if (this.camera != null) {
                this.camera.release();
                this.camera = null;
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(hasAccess) {
            turnOffTheFlash();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(dialog3 != null && dialog3.isShowing()){
            dialog3.dismiss();
        }
        if(positions !=null){
            positions.clear();}
        if(hasAccess) {
            if (deviceHasFlash) {
                turnOffTheFlash();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void button2Click(){


        dialog3 = new Dialog(Main6Activity.this);
        View myView3 = getLayoutInflater().inflate(R.layout.dialog2, null);
        MyCustomAdapter2 adapter2 = new MyCustomAdapter2(this, colors);

        final ListView listview = myView3.findViewById(R.id.listview2);
        listview.setAdapter(adapter2);

        dialog3.setContentView(myView3);
        dialog3.show();
    }
    public void button3Click(){

        final Dialog dialog2 = new Dialog(Main6Activity.this);
        View myView2 = getLayoutInflater().inflate(R.layout.dialogseek, null);
        Button button2 = (Button)myView2.findViewById(R.id.dialogseekbutton);
        final SeekBar seekBar2 = (SeekBar)myView2.findViewById(R.id.dialogseekbarmini);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main6Activity.this, Main3Activity.class);
                intent.putExtra("type", "all");
                intent.putExtra("time", seekBar2.getProgress());

                startActivity(intent);
                Bungee.spin(Main6Activity.this);
                dialog2.dismiss();
            }
        });
        dialog2.setContentView(myView2);
        dialog2.show();

    }

    public void button4Click() {

        final Dialog dialog = new Dialog(Main6Activity.this);
        final View myView = getLayoutInflater().inflate(R.layout.dialog, null);

        MyCustomAdapter adapter = new MyCustomAdapter(getApplicationContext(), colors);
        final SeekBar seekBar = myView.findViewById(R.id.dialogseekbar);
        final ListView listview = myView.findViewById(R.id.listview);
        listview.setAdapter(adapter);

        final Button button = (Button) myView.findViewById(R.id.dialogbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                positions = MyCustomAdapter.positionAL;
                if(positions.size()>1){
                    Intent intent = new Intent(Main6Activity.this, Main3Activity.class);
                    intent.putExtra("type", "custom");
                    intent.putExtra("time", seekBar.getProgress());

                    intent.putIntegerArrayListExtra("positions", positions);

                    startActivity(intent);
                    Bungee.spin(Main6Activity.this);
                    dialog.dismiss();}else{
                    FabToast.makeText(getApplicationContext(), "You must select 2 colors minimum.", Toast.LENGTH_LONG,
                            FabToast.INFORMATION, FabToast.POSITION_DEFAULT).show();
                }

            }
        });



        dialog.setContentView(myView);
        dialog.show();


    }
    public void dummyClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.flash_light:
                button1click();
                break;
            case R.id.flash_light2:
                button2Click();
                break;
            case R.id.flash_light3:
                button3Click();
                break;
            case R.id.flash_light4:
                button4Click();
                break;
        }
    }

    public void populateMoreColors(){
        for(int i = 0; i<262; i++){
            // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
            int nextInt = random.nextInt(256*256*256);

            // format it as hexadecimal string (with hashtag and leading zeros)
            String colorCode = String.format("#%06x", nextInt);
            colors.add(colorCode);
            // print it
            Log.i("JOSH color", "" + colorCode);}
    }


}
