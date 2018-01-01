package com.finalgroupproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.finalgroupproject.Objects.PhysicalActivity;
import com.finalgroupproject.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 */
public class TrackingAdapter extends ArrayAdapter<PhysicalActivity> {

    private List<PhysicalActivity> listOfRecords;
    private Context context;

    /**
     *
     * @param context
     * @param resource
     * @param listOfRecords
     */
    public TrackingAdapter(Context context, int resource, List<PhysicalActivity> listOfRecords) {
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
    public PhysicalActivity getItem(int position){
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
        View view = inflater.inflate(R.layout.physical_activity_row, null);

        PhysicalActivity record = getItem(position);

        TextView date = view.findViewById(R.id.date);
        TextView type = view.findViewById(R.id.type);
        TextView time = view.findViewById(R.id.minutes);

        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(record.getCreated());

        date.setText(c.get(Calendar.YEAR) + " / " + (c.get(Calendar.MONTH)+1) + " / " + c.get(Calendar.DAY_OF_MONTH));
        type.setText(context.getResources().getStringArray(R.array.type)[record.getType()]);
        time.setText(record.getMinutes() + " min(s)");

        return view;
    }
}