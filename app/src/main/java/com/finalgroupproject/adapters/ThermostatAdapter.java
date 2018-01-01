package com.finalgroupproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.finalgroupproject.Objects.Thermostat;
import com.finalgroupproject.R;

import java.util.List;


/**
 *
 */
public class ThermostatAdapter extends ArrayAdapter<Thermostat> {

    private List<Thermostat> listOfRecords;
    private Context context;

    /**
     *
     * @param context
     * @param resource
     * @param listOfRecords
     */
    public ThermostatAdapter(Context context, int resource, List<Thermostat> listOfRecords) {
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
    public Thermostat getItem(int position){
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
        View view = inflater.inflate(R.layout.thermostat_row, null);

        Thermostat record = getItem(position);

        TextView day = view.findViewById(R.id.day);
        TextView time = view.findViewById(R.id.time);
        TextView temp = view.findViewById(R.id.temp);

        day.setText(context.getResources().getStringArray(R.array.days)[record.getDay()]);
        time.setText(context.getResources().getStringArray(R.array.hours)[record.getHour()]
                + ":" + context.getResources().getStringArray(R.array.minutes)[record.getMinute()]);
        temp.setText(record.getTemp() + " C");

        return view;
    }
}