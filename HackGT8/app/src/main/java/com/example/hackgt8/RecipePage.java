package com.example.hackgt8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

public class RecipePage extends AppCompatActivity {

    private Button cookingbutton;
    private ImageView foodImage;
    private TextView foodTitle;
    private TextView link1;
    private TextView link2;
    private TextView link3;
    private TextView link4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        cookingbutton = (Button) findViewById(R.id.cookingbutton);
        foodImage = (ImageView) findViewById(R.id.foodPicture);
        foodTitle = (TextView) findViewById(R.id.foodTitle);

        link1 = (TextView) findViewById(R.id.link1);
        link2 = (TextView) findViewById(R.id.link2);
        link3 = (TextView) findViewById(R.id.link3);
        link4 = (TextView) findViewById(R.id.link4);

        cookingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridge();
            }
        });

        String inputFood = getIntent().getStringExtra("key");

        if (inputFood != null) {

            RecipeDatabase rdObj = new RecipeDatabase();
            Hashtable<String, Hashtable> masterTable = rdObj.getMasterTable(); // All information about foods, in a dictionary

            String[] linkArray = (String[])masterTable.get(inputFood).get("Links");

            link1.setText(linkArray[0]);
            link2.setText(linkArray[1]);
            link3.setText(linkArray[2]);
            link4.setText(linkArray[3]);

            foodTitle.setText(inputFood);

            String[] imageArray = (String[])masterTable.get(inputFood).get("Image");
            int imageId = Integer.valueOf(imageArray[0]);
            foodImage.setImageResource(imageId);

        }

    }

    public void openFridge() {
        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }

}