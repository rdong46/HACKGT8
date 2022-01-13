package com.example.hackgt8;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class RetrieveUserData extends AppCompatActivity {

    public ArrayList<String> testMethod(Context context) {

        System.out.println("in test method");

        SharedPreferences userDictionary = context.getApplicationContext().getSharedPreferences("dataStore", 0);
        Map<String,?> keys = userDictionary.getAll();
        ArrayList<String> userIngredients = new ArrayList<String>(); // All items in the user's fridge, in a String ArrayList

        RecipeDatabase rdObj = new RecipeDatabase();
        Hashtable<String, Hashtable> masterTable = rdObj.getMasterTable(); // All information about foods, in a dictionary

        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            userIngredients.add(entry.getKey());
            System.out.println(entry.getKey());

        }

        Enumeration<String> foods = masterTable.keys();
        ArrayList<String> possibleFoods = new ArrayList<String>();

        while(foods.hasMoreElements()) {

            String food = foods.nextElement();
            boolean hasAllIngredients = true;
            String[] neededIngredients = (String[])masterTable.get(food).get("Ingredients");

            // use userIngredients and see if it has all the ingredients from neededIngredients
            // if so, the user is able to cook this item so toggle the boolean to true and add the food to possibleFoods

            for(int i = 0; i < neededIngredients.length; i++) {

                boolean foundIngredient = false;

                for (int h = 0; h < userIngredients.size(); h++) {
                    if (userIngredients.get(h) != null) {
                        if (neededIngredients[i].equals(userIngredients.get(h))) {
                            foundIngredient = true;
                            break;
                        }
                    }
                }

                if (!foundIngredient) {

                    hasAllIngredients = false;
                    break;

                }

            }

            if (hasAllIngredients) {

                possibleFoods.add(food);

            }

            System.out.println(hasAllIngredients + " " + possibleFoods.size());

            for (int i = 0; i < possibleFoods.size(); i++) {

                System.out.println(possibleFoods.get(i));

            }

        }

        return possibleFoods;

    }

}
