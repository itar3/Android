package com.example.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.provider.Item;
import com.example.myapplication.provider.ItemViewModel;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {
    private EditText itemName; //
    private EditText quantity; //
    private EditText price; //
    private EditText description;// =
    private ToggleButton frozen;
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    ArrayList<Item> data = new ArrayList<>();
    String fr;

    private ItemViewModel mItemViewModel;

    int xDown;
    int yDown;
    int xUp;
    int yUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        drawerlayout = findViewById(R.id.layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        itemName = (EditText) findViewById(R.id.itemName);
        quantity = (EditText) findViewById(R.id.Quantity);
        price = (EditText) findViewById(R.id.Cost);
        description = (EditText) findViewById(R.id.Description);
        frozen = (ToggleButton) findViewById(R.id.toggleButton);

        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        itemName.setInputType(InputType.TYPE_CLASS_TEXT);
        quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        price.setInputType(InputType.TYPE_CLASS_NUMBER);
        description.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        description.setSingleLine(false);
        description.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));


        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSharedPreferences();
                Snackbar.make(view, "Item (" + itemName.getText().toString() + ") has been added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                addItem();

            }
        });


        View myView = findViewById(R.id.main_activity);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventType = motionEvent.getActionMasked();

                if (eventType == MotionEvent.ACTION_DOWN){
                    xDown = (int) motionEvent.getX();
                    yDown = (int) motionEvent.getY();
                }else if(eventType == motionEvent.ACTION_UP){
                    xUp = (int) motionEvent.getX();
                    yUp = (int) motionEvent.getY();

                    int dirX = xDown - xUp;
                    int dirY = yDown - yUp;

                    if (dirY == 0 &&  dirX < 0){
                        saveSharedPreferences();
                        Snackbar.make(view, "Item (" + itemName.getText().toString() + ") has been added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                        addItem();
                    }else if ( dirY == 0 && dirX > 0){
                        clearItem();
                    }
                }
                return true;
            }
        });


    }
    public void addItem() {
        if (frozen.isChecked()) {
            fr = "true";
        } else {
            fr = "false";
        }
        Item item = new Item(itemName.getText().toString(), price.getText().toString(), quantity.getText().toString(), description.getText().toString(), fr);
        //data.add(item);
        mItemViewModel.insert(item);


    }

    public  void clearItem(){
        frozen.setChecked(false);
        itemName.setText("");
        price.setText("");
        quantity.setText("");
        description.setText("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_id_1) {
            saveSharedPreferences();
            Toast.makeText(getApplicationContext(), "New Item (" + itemName.getText().toString() + " ) has been added ", Toast.LENGTH_SHORT).show();
            addItem();
        } else if (id == R.id.item_id_2) {
            itemName.getText().clear();
            quantity.getText().clear();
            price.getText().clear();
            description.getText().clear();
            frozen.setChecked(false);

            saveSharedPreferences();
        } else if (id == R.id.item_id_3){
            Intent intent = new Intent(this, CardActivity.class);
//            Gson gson = new Gson();
//            String list = gson.toJson(data);
//            intent.putExtra("key2", list);
            startActivity(intent);

        }
        return true;
    }

        protected void onStart() {
        restoreSharedPreferences();
        super.onStart();
    }

    protected void onPause() {
        //saveSharedPreferences();
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //only gets executed if inState != null so no need to check for null Bundle
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }

    public void saveSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("f", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("itemName", itemName.getText().toString());
        editor.putString("description", description.getText().toString());

        if (price.getText().toString().length() != 0) {
            editor.putInt("price", Integer.parseInt(price.getText().toString()));
        } else {
            editor.putInt("price", 0);
        }
        if (quantity.getText().toString().length() != 0) {
            editor.putInt("q", Integer.parseInt(quantity.getText().toString()));
        } else {
            editor.putInt("q", 0);
        }

        if (frozen.isChecked()) {
            editor.putBoolean("frozen", true);
        } else {
            editor.putBoolean("frozen", false);
        }

        editor.apply();

    }

    public void restoreSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("f", 0);

        itemName.setText(sharedPreferences.getString("itemName", ""));
        price.setText(String.valueOf(sharedPreferences.getInt("price", 0)));
        quantity.setText(String.valueOf(sharedPreferences.getInt("q", 0)));
        description.setText(sharedPreferences.getString("description", ""));
        frozen.setChecked(sharedPreferences.getBoolean("frozen", false));


    }

    public void setOnAdd(View view) {
        saveSharedPreferences();
        Toast.makeText(getApplicationContext(), "New Item (" + itemName.getText().toString() + " ) has been added ", Toast.LENGTH_SHORT).show();
        addItem();
    }

    public void setOnClear(View view) {

        itemName.getText().clear();
        quantity.getText().clear();
        price.getText().clear();
        description.getText().clear();
        frozen.setChecked(false);

        saveSharedPreferences();


    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);


            StringTokenizer sT = new StringTokenizer(msg, ";");
            String item = sT.nextToken();
            String qq = sT.nextToken();
            String pp = sT.nextToken();
            String d = sT.nextToken();
            String fr = sT.nextToken();
            fr = fr.toLowerCase();


            itemName.setText(item);
            quantity.setText(qq);
            price.setText(pp);
            description.setText(d);
            if (fr.equalsIgnoreCase("true")) {
                frozen.setChecked(true);


            } else {
                frozen.setChecked(false);
            }

        }
    }
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.item_id_1) {
                saveSharedPreferences();
                Toast.makeText(getApplicationContext(), "New Item (" + itemName.getText().toString() + " ) has been added ", Toast.LENGTH_SHORT).show();
                addItem();
            } else if (id == R.id.item_id_2) {
                itemName.getText().clear();
                quantity.getText().clear();
                price.getText().clear();
                description.getText().clear();
                frozen.setChecked(false);

                saveSharedPreferences();
            } else if (id == R.id.item_id_3){
                Intent intent;
                intent = new Intent(MainActivity.this, CardActivity.class);
//                Gson gson = new Gson();
//                String list = gson.toJson(data);
//                intent.putExtra("key2", list);
                startActivity(intent);

            }
            return true;
        }
    }

}
