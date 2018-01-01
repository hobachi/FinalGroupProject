package com.finalgroupproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finalgroupproject.Objects.Nutrition;

import java.util.Calendar;

public class NutritionNewActivity extends AppCompatActivity {

    private EditText name;
    private EditText calories;
    private EditText fat;
    private EditText carbs;
    private Button saveBtn;

    private Nutrition nutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nutrition);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.nutritionName);
        calories = findViewById(R.id.nutritionCallories);
        fat = findViewById(R.id.nutritionFat);
        carbs = findViewById(R.id.nutritionCarb);
        saveBtn = findViewById(R.id.nutritionBtn);

        if(getIntent().getExtras()!=null){
            nutrition = Nutrition.findById(Nutrition.class, getIntent().getExtras().getLong("id"));
            name.setText(nutrition.getName());
            calories.setText(String.valueOf(nutrition.getCalories()));
            fat.setText(String.valueOf(nutrition.getTotalFat()));
            carbs.setText(String.valueOf(nutrition.getTotalCarbs()));
            saveBtn.setText(R.string.update);

        }else{
            saveBtn.setText(R.string.save);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()) {
                    if (nutrition == null) {
                        nutrition = new Nutrition();
                    }

                    nutrition.setName(name.getText().toString());
                    nutrition.setCalories(Double.parseDouble(calories.getText().toString()));
                    nutrition.setTotalCarbs(Double.parseDouble(carbs.getText().toString()));
                    nutrition.setTotalFat(Double.parseDouble(fat.getText().toString()));

                    if (getIntent().getExtras() == null) {
                        nutrition.setCreated(Calendar.getInstance().getTimeInMillis());
                        nutrition.save();
                        Toast.makeText(v.getContext(), R.string.nutrition_added, Toast.LENGTH_SHORT).show();

                    } else {
                        nutrition.update();
                        Toast.makeText(v.getContext(), R.string.nutrition_updated, Toast.LENGTH_SHORT).show();
                    }

                    finish();

                }else{
                    Snackbar.make(v, R.string.missing_info_error, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().getExtras()!=null) {
            getMenuInflater().inflate(R.menu.menu_nutrition, menu);
        }
        return true;
    }

    /**
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(NutritionNewActivity.this);
                builder.setTitle(R.string.delete)
                        .setMessage(R.string.delete_msg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Nutrition.delete(Nutrition.findById(Nutrition.class, getIntent().getExtras().getLong("id")));
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setIcon(android.R.drawable.ic_menu_help)
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isFormValid(){
        boolean valid = true;
        if (TextUtils.isEmpty(name.getText())) {
            name.setError(getString(R.string.required));
            valid = false;
        } else {
            name.setError(null);
        }

        if (TextUtils.isEmpty(calories.getText())) {
            calories.setError(getString(R.string.required));
            valid = false;
        } else {
            calories.setError(null);
        }

        if (TextUtils.isEmpty(fat.getText())) {
            fat.setError(getString(R.string.required));
            valid = false;
        } else {
            fat.setError(null);
        }

        if (TextUtils.isEmpty(carbs.getText())) {
            carbs.setError(getString(R.string.required));
            valid = false;
        } else {
            carbs.setError(null);
        }

        return valid;
    }
}
