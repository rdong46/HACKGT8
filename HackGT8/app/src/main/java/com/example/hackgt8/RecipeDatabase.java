package com.example.hackgt8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Hashtable;

public class RecipeDatabase {

    Hashtable<String, Hashtable> masterTable = new Hashtable<String, Hashtable>();

    public RecipeDatabase() {

        Hashtable<String, String[]> HamOmeletteInfo = new Hashtable<String, String[]>();
        masterTable.put("Ham Omelette", HamOmeletteInfo);

        HamOmeletteInfo.put("Image", new String[]{String.valueOf(R.drawable.hamomelette)});
        HamOmeletteInfo.put("Ingredients", new String[]{"ham", "eggs"});
        HamOmeletteInfo.put("Links", new String[]{"https://www.allrecipes.com/recipe/262697/ham-and-cheese-omelette/", "https://www.seriouseats.com/diner-style-ham-and-cheese-omelette-for-two-recipe-food-lab", "https://www.tasteofhome.com/recipes/deluxe-ham-omelet/", "https://www.thespruceeats.com/ham-and-cheese-omelet-recipe-5087799"});

    }

        public Hashtable<String, Hashtable> getMasterTable() {

            return masterTable;

        }

    }
