package com.finalgroupproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.finalgroupproject.Objects.Nutrition;
import com.finalgroupproject.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 */
public class NutritionAdapter extends ArrayAdapter<Nutrition> {

    private List<Nutrition> listOfRecords;
    private Context context;

    /**
     *
     * @param context
     * @param resource
     * @param listOfRecords
     */
    public NutritionAdapter(Context context, int resource, List<Nutrition> listOfRecords) {
        super(context, 0, listOfRecords);

        this.context = context;
        this.listOfRecords = listOfRecords;
    }

    /**
     *
     * @return
     */
    public int getCount(){
        return listOfRecords.size();
    }

    /**
     *
     * @param position
     * @return
     */
    public Nutrition getItem(int position){
        return listOfRecords.get(position);
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.nutrition_row, null);

        Nutrition record = getItem(position);

        TextView date = view.findViewById(R.id.date);
        TextView name = view.findViewById(R.id.name);
        TextView callories = view.findViewById(R.id.callories);
        TextView fat = view.findViewById(R.id.fat);
        TextView carbs = view.findViewById(R.id.carbs);

        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(record.getCreated());

        date.setText(c.get(Calendar.YEAR) + " / " + (c.get(Calendar.MONTH)+1) + " / " + c.get(Calendar.DAY_OF_MONTH));
        name.setText(record.getName());
        callories.setText("" + record.getCalories());
        fat.setText("" + record.getTotalFat());
        carbs.setText("" + record.getTotalCarbs());

        return view;
    }
}