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

import com.finalgroupproject.Objects.PhysicalActivity;

import java.util.Calendar;

public class TrackingNewActivity extends AppCompatActivity {

    private Spinner type;
    private EditText minutes;
    private EditText comments;
    private Button saveBtn;

    private PhysicalActivity physicalActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tracking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        type = findViewById(R.id.activityType);
        minutes = findViewById(R.id.activityTime);
        comments = findViewById(R.id.activityComments);
        saveBtn = findViewById(R.id.activityBtn);

        if(getIntent().getExtras()!=null){
            physicalActivity = PhysicalActivity.findById(PhysicalActivity.class, getIntent().getExtras().getLong("id"));
            type.setSelection(physicalActivity.getType());
            minutes.setText(String.valueOf(physicalActivity.getMinutes()));
            comments.setText(physicalActivity.getComments());
            saveBtn.setText(R.string.update);

        }else{
            saveBtn.setText(R.string.save);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    if(physicalActivity==null){
                        physicalActivity = new PhysicalActivity();
                    }

                    physicalActivity.setType(type.getSelectedItemPosition());
                    physicalActivity.setMinutes(Integer.parseInt(minutes.getText().toString()));
                    physicalActivity.setComments(comments.getText().toString());

                    if(getIntent().getExtras()==null) {
                        physicalActivity.setCreated(Calendar.getInstance().getTimeInMillis());
                        physicalActivity.save();
                        Toast.makeText(v.getContext(), R.string.activity_added, Toast.LENGTH_SHORT).show();


                    }else{
                        physicalActivity.update();
                        Toast.makeText(v.getContext(), R.string.activity_updated, Toast.LENGTH_SHORT).show();
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
            getMenuInflater().inflate(R.menu.menu_tracking, menu);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TrackingNewActivity.this);
                builder.setTitle(R.string.delete)
                        .setMessage(R.string.delete_msg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PhysicalActivity.delete(PhysicalActivity.findById(PhysicalActivity.class, getIntent().getExtras().getLong("id")));
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
        if (TextUtils.isEmpty(minutes.getText())) {
            minutes.setError(getString(R.string.required));
            valid = false;
        } else {
            minutes.setError(null);
        }

        if (TextUtils.isEmpty(comments.getText())) {
            comments.setError(getString(R.string.required));
            valid = false;
        } else {
            comments.setError(null);
        }

        return valid;
    }
}
