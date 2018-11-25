package com.gainwise.multlight;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import spencerstudios.com.fab_toast.FabToast;

public class Main3Activity extends AppCompatActivity implements Animation.AnimationListener {

    RelativeLayout layout1;
    RelativeLayout layout2;
    AlphaAnimation animation1;
    int brightness;
    boolean firstTapDone = false;
    long time = 1000;
    TextView tv2;





    int size;
    int pos;
    ArrayList<String> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();
        colors = Main6Activity.colors;


        String type = getIntent().getStringExtra("type");
        if(type.equals("all")) {
            int timeint = getIntent().getIntExtra("time", 0);

            convertTime(timeint);

            Collections.shuffle(colors);
        }else if(type.equals("custom")){
            int timeint = getIntent().getIntExtra("time", 0);
            colors = new ArrayList<String>();
           convertTime(timeint);

            ArrayList<Integer> positions = getIntent().getIntegerArrayListExtra("positions");
            Log.i("JOSH ", "custom called with size" + positions.size());
            for(int i = 0; i<positions.size();i++){
                Log.i("JOSH", ""+positions.get(i));

                colors.add(Main6Activity.colors.get(positions.get(i)));

            }
            Collections.shuffle(colors);
        }

        TextView textView = (TextView)findViewById(R.id.tv2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        brightness = Settings.System.getInt(getApplicationContext().getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0
        );
        //   Toast.makeText(getApplicationContext(), "" + brightness, Toast.LENGTH_LONG).show();
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        pos = 0;
        size = colors.size();
        tv2 = (TextView)findViewById(R.id.tv2);
        animation1 = new AlphaAnimation(0f,1f);

        layout1.setBackgroundColor(Color.parseColor(colors.get(pos)));
       // layout2.setAlpha(0);

        layout1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Main3Activity.this.finish();
                return false;
            }
        });
        layout2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Main3Activity.this.finish();
                return false;
            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstTapDone){
                    tv2.setVisibility(View.INVISIBLE);
                    firstTapDone = true;
                    initAnimations(time);
                }else{
                    FabToast.makeText(getApplicationContext(),"long press to exit", Toast.LENGTH_LONG,
                            FabToast.INFORMATION,FabToast.POSITION_CENTER).show();
                }
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstTapDone){
                    tv2.setVisibility(View.INVISIBLE);
                    firstTapDone = true;
                    initAnimations(time);
                }else{
                    FabToast.makeText(getApplicationContext(),"long press to exit", Toast.LENGTH_LONG,
                            FabToast.INFORMATION,FabToast.POSITION_CENTER).show();
                }
            }
        });

        if(colors.get(pos).equals("#000000")){
            textView.setTextColor(Color.parseColor("#ffffff"));
        }else if(colors.get(pos).equals("#ffffff")){
            textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Log.i("JOSH", "helper3");
layout1.setBackgroundColor(Color.parseColor(colors.get(pos)));
pos++;
if(pos>size-1){
    pos = 0;
}
//layout2.setAlpha(0f);
layout2.setBackgroundColor(Color.parseColor(colors.get(pos)));
layout2.startAnimation(animation1);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void initAnimations(long time){

        Log.i("JOSH", "helper1");
        animation1.setDuration(time);
        ++pos;
        Log.i("JOSH pos", " current "+ pos);
        layout2.setBackgroundColor(Color.parseColor(colors.get(pos)));

        animation1.setAnimationListener(Main3Activity.this);
        layout2.startAnimation(animation1);

    }
    public void hideActionBar() {
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void convertTime(int timeIn){

        switch (timeIn){
            case 0:
            time = 4000;
            break;
            case 1:
                time = 3000;
                break;
            case 2:
                time = 2000;
                break;
            case 3:
                time = 1500;
                break;

            case 4:
                time = 1000;
                break;

            case 5:
                time = 800;
                break;
            case 6:
                time = 500;
                break;

        }

    }

}
