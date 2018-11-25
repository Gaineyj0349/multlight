package com.gainwise.multlight;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import spencerstudios.com.fab_toast.FabToast;

public class Main2Activity extends AppCompatActivity implements SwipeInterface{

    int brightness;


    ArrayList<String> colors;

    LinearLayout layoutbg;
    TextView tv1;
    int pos;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
       // hideActionBar();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        brightness = Settings.System.getInt(getApplicationContext().getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0
        );
     //   Toast.makeText(getApplicationContext(), "" + brightness, Toast.LENGTH_LONG).show();
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
        colors = Main6Activity.colors;
        int startingColor = getIntent().getIntExtra("single",0);
        TextView textView = (TextView)findViewById(R.id.tv1);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setVisibility(View.GONE);
        layoutbg = (LinearLayout)findViewById(R.id.layoutbg);
        layoutbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FabToast.makeText(getApplicationContext(),"swipe left or right for more colors and up or down to exit", Toast.LENGTH_LONG,
                        FabToast.INFORMATION,FabToast.POSITION_CENTER).show();
            }
        });
        ActivitySwipeDetector swipe = new ActivitySwipeDetector(this);

        layoutbg.setOnTouchListener(swipe);
         size = colors.size();
        pos = startingColor;
        layoutbg.setBackgroundColor(Color.parseColor(colors.get(pos)));
        if(colors.get(pos).equals("#000000")){
            textView.setTextColor(Color.parseColor("#ffffff"));
        }else if(colors.get(pos).equals("#ffffff")){
            textView.setTextColor(Color.parseColor("#000000"));
        }




    }


    @Override
    public void bottom2top(View v) {
        Main2Activity.this.finish();
    }

    @Override
    public void left2right(View v) {
        tv1.setVisibility(View.INVISIBLE);

        pos--;
        if(pos < 0){
            pos = size-1;
        }
        layoutbg.setBackgroundColor(Color.parseColor(colors.get(pos)));
    }

    @Override
    public void right2left(View v) {
        tv1.setVisibility(View.INVISIBLE);
        pos++;
        if(pos>size-1){
            pos = 0;
        }

        layoutbg.setBackgroundColor(Color.parseColor(colors.get(pos)));
    }

    @Override
    public void top2bottom(View v) {
        Main2Activity.this.finish();
    }

    public void hideActionBar(){
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
