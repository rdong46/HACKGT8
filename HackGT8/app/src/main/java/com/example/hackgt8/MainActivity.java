package com.example.hackgt8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageButton button;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.imageButton2);
        linearLayout = (LinearLayout) findViewById(R.id.IWANTSLEEP);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        ArrayList<String> possibleFoods = getIntent().getStringArrayListExtra("key");
        RecipeDatabase rdObj = new RecipeDatabase();
        Hashtable<String, Hashtable> masterTable = rdObj.getMasterTable(); // All information about foods, in a dictionary

        if (possibleFoods != null) {

            for (int i = 0; i < possibleFoods.size(); i++) {

                String selectedFood = possibleFoods.get(i);

                String[] imageArray = (String[])masterTable.get(selectedFood).get("Image");
                int imageId = Integer.valueOf(imageArray[0]);
                System.out.println(imageId);

                ImageButton newRecipe = new ImageButton(this);
                newRecipe.setImageResource(imageId);

                linearLayout.addView(newRecipe);
                int width = 550;
                int height = 550;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
                newRecipe.setLayoutParams(params);
                newRecipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openRecipe(selectedFood);
                    }
                });
                // newRecipe.setsomething idk

            }

        }

    }

    public void openActivity2() {
        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }
    public void openRecipe(String inputFood) {
        Intent intent = new Intent(this, RecipePage.class);
        intent.putExtra("key", inputFood);
        startActivity(intent);

    }

}