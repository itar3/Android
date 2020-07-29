package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import static com.example.myapplication.R.id.toggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText itemName = (EditText) findViewById(R.id.itemName);
        final EditText quantity = (EditText) findViewById(R.id.Quantity);
        final EditText price = (EditText) findViewById(R.id.Cost);
        final EditText description = (EditText) findViewById(R.id.Description);


        quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        price.setInputType(InputType.TYPE_CLASS_NUMBER);

        description.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        description.setSingleLine(false);
        description.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);



        Button add = (Button) findViewById(R.id.add);
        Button clear = (Button) findViewById(R.id.Clear);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "New Item (" + itemName.getText().toString() + " ) has been added ", Toast.LENGTH_SHORT).show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton frozen = (ToggleButton) findViewById(toggleButton);

                itemName.setText(" ");
                quantity.setText(" ");
                price.setText(" ");
                description.setText(" ");
                frozen.setChecked(false);



            }
        });


    }

}


