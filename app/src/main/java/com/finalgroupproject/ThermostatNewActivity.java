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
import android.widget.Spinner;
import android.widget.Toast;

import com.finalgroupproject.Objects.Thermostat;

import java.util.Calendar;

public class ThermostatNewActivity extends AppCompatActivity {

    private Spinner day;
    private Spinner hour;
    private Spinner minute;
    private EditText temp;
    private Button saveBtn, newBtn;

    private Thermostat thermostat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thermostat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        day = findViewById(R.id.thermostatDay);
        hour = findViewById(R.id.thermostatHour);
        minute = findViewById(R.id.thermostatMinute);
        temp = findViewById(R.id.thermostatTemp);
        saveBtn = findViewById(R.id.thermostatBtn);
        newBtn = findViewById(R.id.thermostatNewBtn);

        if(getIntent().getExtras()!=null){
            thermostat = Thermostat.findById(Thermostat.class, getIntent().getExtras().getLong("id"));
            day.setSelection(thermostat.getDay());
            hour.setSelection(thermostat.getHour());
            minute.setSelection(thermostat.getMinute());
            temp.setText(String.valueOf(thermostat.getTemp()));
            saveBtn.setText(R.string.update);
            newBtn.setEnabled(true);

        }else{
            saveBtn.setText(R.string.save);
            newBtn.setEnabled(false);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    if(thermostat==null){
                        thermostat = new Thermostat();
                    }

                    thermostat.setDay(day.getSelectedItemPosition());
                    thermostat.setHour(hour.getSelectedItemPosition());
                    thermostat.setMinute(minute.getSelectedItemPosition());
                    thermostat.setTemp(Double.parseDouble(temp.getText().toString()));

                    if(getIntent().getExtras()==null) {
                        thermostat.setCreated(Calendar.getInstance().getTimeInMillis());
                        thermostat.save();
                        Toast.makeText(v.getContext(), R.string.rule_created, Toast.LENGTH_SHORT).show();

                    }else{
                        thermostat.update();
                        Toast.makeText(v.getContext(), R.string.rule_updated, Toast.LENGTH_SHORT).show();
                    }

                    finish();

                }else{
                    Snackbar.make(v, R.string.missing_info_error, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thermostat = new Thermostat();
                thermostat.setDay(day.getSelectedItemPosition());
                thermostat.setHour(hour.getSelectedItemPosition());
                thermostat.setMinute(minute.getSelectedItemPosition());
                thermostat.setTemp(Double.parseDouble(temp.getText().toString()));
                thermostat.setCreated(Calendar.getInstance().getTimeInMillis());
                thermostat.save();
                Toast.makeText(v.getContext(), R.string.new_rule_created, Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().getExtras()!=null) {
            getMenuInflater().inflate(R.menu.menu_thermostat, menu);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ThermostatNewActivity.this);
                builder.setTitle(R.string.delete)
                        .setMessage(R.string.delete_msg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Thermostat.delete(Thermostat.findById(Thermostat.class, getIntent().getExtras().getLong("id")));
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
        if (TextUtils.isEmpty(temp.getText())) {
            temp.setError(getString(R.string.required));
            valid = false;
        } else {
            temp.setError(null);
        }

        return valid;
    }
}
