package com.example.hackgt8;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class FridgeActivity extends AppCompatActivity {

    private EditText Quantity;
    private EditText Ingredient;
    private Button addButton;
    private Button subtractButton;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        SharedPreferences data = getApplicationContext().getSharedPreferences("dataStore", 0);
        SharedPreferences.Editor editor = data.edit();

        Quantity = (EditText) findViewById(R.id.QuantityBox);
        Ingredient = (EditText) findViewById(R.id.IngredientBox);
        addButton = (Button) findViewById(R.id.addbutton);
        subtractButton = (Button) findViewById(R.id.subtractbutton);
        mainLayout = (LinearLayout) findViewById(R.id.MainLayout);

        Hashtable<String, Integer> ingredientTable = new Hashtable<String, Integer>();
        Hashtable<TextView, TextView> inputtedIngredientsTable = new Hashtable<TextView, TextView>();

        Map<String,?> keys = data.getAll();

        System.out.println(keys.size());

        if (keys.size() > 0) {

            System.out.println("going into loop");

            for (Map.Entry<String, ?> entry : keys.entrySet()) {

                LinearLayout linearLayout = new LinearLayout(FridgeActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                mainLayout.addView(linearLayout);

                TextView userIngredient = new TextView(FridgeActivity.this);
                userIngredient.setText(entry.getKey());
                linearLayout.addView(userIngredient);

                userIngredient.setTypeface(Typeface.DEFAULT_BOLD);
                userIngredient.setBackgroundColor(Color.parseColor("#E1E1E1"));
                userIngredient.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                userIngredient.setLayoutParams(new LinearLayout.LayoutParams(623, LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView userQty = new TextView(FridgeActivity.this);
                userQty.setText(entry.getValue().toString());
                linearLayout.addView(userQty);

                userQty.setTypeface(Typeface.DEFAULT);
                userQty.setBackgroundColor(Color.parseColor("#CCCCCC"));
                userQty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                userQty.setLayoutParams(new LinearLayout.LayoutParams(210, LinearLayout.LayoutParams.WRAP_CONTENT));
                userQty.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                ingredientTable.put(userIngredient.getText().toString(), Integer.valueOf(userQty.getText().toString()));
                inputtedIngredientsTable.put(userIngredient, userQty);

            }

        }

        addButton.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                String ingredName = Ingredient.getText().toString().toLowerCase();
                int ingredQty = Integer.valueOf(Quantity.getText().toString());

                if (ingredName != null && !ingredientTable.containsKey(ingredName)) {

                    ingredientTable.put(ingredName, ingredQty);
                    System.out.println("Assigned key " + ingredName + " with value " + ingredQty);

                    LinearLayout linearLayout = new LinearLayout(FridgeActivity.this);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setTag(ingredName);

                    mainLayout.addView(linearLayout);

                    TextView userIngredient = new TextView(FridgeActivity.this);
                    userIngredient.setText(ingredName);
                    linearLayout.addView(userIngredient);

                    userIngredient.setTypeface(Typeface.DEFAULT_BOLD);
                    userIngredient.setBackgroundColor(Color.parseColor("#BDBDBD"));
                    userIngredient.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                    userIngredient.setLayoutParams(new LinearLayout.LayoutParams(623, LinearLayout.LayoutParams.WRAP_CONTENT));

                    TextView userQty = new TextView(FridgeActivity.this);
                    userQty.setText(String.valueOf(ingredQty));
                    linearLayout.addView(userQty);

                    userQty.setTypeface(Typeface.DEFAULT);
                    userQty.setBackgroundColor(Color.parseColor("#CFCFCF"));
                    userQty.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                    userQty.setLayoutParams(new LinearLayout.LayoutParams(210, LinearLayout.LayoutParams.WRAP_CONTENT));
                    userQty.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    inputtedIngredientsTable.put(userIngredient, userQty);

                    editor.putInt(userIngredient.getText().toString(), Integer.valueOf(userQty.getText().toString()));
                    editor.apply();

                } else if (ingredientTable.containsKey(ingredName)) {

                    int newIngredQty = ingredientTable.get(ingredName) + ingredQty;
                    ingredientTable.replace(ingredName, newIngredQty);
                    System.out.println("Incremented key " + ingredName + " by value " + ingredQty + ", new value is " + newIngredQty);

                    Set<TextView> keys = inputtedIngredientsTable.keySet();

                    for (TextView lbl : keys) {

                        if (lbl.getText().equals(ingredName)) {

                            inputtedIngredientsTable.get(lbl).setText(String.valueOf(newIngredQty));
                            editor.putInt(lbl.getText().toString(), newIngredQty);
                            editor.apply();
                            break;

                        }

                    }

                }



            }

        });

        subtractButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                String ingredName = Ingredient.getText().toString().toLowerCase();
                int ingredQty = Integer.valueOf(Quantity.getText().toString());

                if (ingredientTable.containsKey(ingredName)) {

                    int newIngredQty = ingredientTable.get(ingredName) - ingredQty;
                    ingredientTable.replace(ingredName, newIngredQty);
                    System.out.println("Incremented key " + ingredName + " by value " + ingredQty + ", new value is " + newIngredQty);

                    Set<TextView> keys = inputtedIngredientsTable.keySet();

                    for (TextView lbl : keys) {

                        if (lbl.getText().equals(ingredName)) {

                            if (newIngredQty > 0) {

                                inputtedIngredientsTable.get(lbl).setText(String.valueOf(newIngredQty));
                                editor.putInt(lbl.getText().toString(), newIngredQty);
                                editor.apply();

                            } else {

                                inputtedIngredientsTable.get(lbl).setVisibility(View.GONE);
                                lbl.setVisibility(View.GONE);
                                ingredientTable.remove(ingredName);
                                inputtedIngredientsTable.remove(lbl);
                                editor.remove(ingredName);
                                editor.apply();

                            }

                            break;

                        }

                    }

                }

            }

        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        RetrieveUserData rud = new RetrieveUserData();
        ArrayList<String> possibleFoods = rud.testMethod(getApplicationContext());
        intent.putExtra("key", possibleFoods);
        startActivity(intent);


    }

}