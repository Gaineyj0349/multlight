package com.gainwise.multlight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

/**
 * Created by gaine on 2/12/2018.
 */

public class MyCustomAdapter2 extends BaseAdapter {

    ArrayList<String> colors = new ArrayList<>();
    Context context;
    public static ArrayList<Integer> positionAL = new ArrayList<>();
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>(); // array list for store state of each checkbox;



public MyCustomAdapter2(Context contextIn, ArrayList<String> colorsIn){
    this.colors = colorsIn;
    this.context = contextIn;
    for (int i = 0; i < colors.size(); i++) { // c.getCount() return total number of your Cursor
        itemChecked.add(i, false); // initializes all items value with false
    }
}



    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    final int position1 = position;
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.listviewitem, parent, false);
            }

            // get current item to be displayed
            String currentColor = (String) getItem(position);

            // get the TextView for item name and item description
            CheckBox checkBox = (CheckBox)
                    convertView.findViewById(R.id.listviewcheckbox);
        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.listviewlayout);
        layout.setBackgroundColor(Color.parseColor(colors.get(position)));

        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("single", position1);

                context.startActivity(intent);
                Bungee.spin(context);

            }
                                    }   );


            // returns the view for the current row
            return convertView;
    }

}
