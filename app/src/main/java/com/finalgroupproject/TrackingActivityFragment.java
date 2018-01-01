package com.finalgroupproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalgroupproject.Objects.PhysicalActivity;
import com.finalgroupproject.adapters.TrackingAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrackingActivityFragment extends Fragment {

    private ListView listView;
    private TextView perMonth, lastMonth;
    private TextView emptyView;
    private ProgressBar progressBar;

    private List<PhysicalActivity> listOfActivities;
    private List<PhysicalActivity> listOfActivitiesLastMonth;

    public TrackingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        perMonth = view.findViewById(R.id.perMonth);
        lastMonth = view.findViewById(R.id.lastMonth);
        listView = view.findViewById(R.id.listView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyView = view.findViewById(android.R.id.empty);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TrackingNewActivity.class);
                intent.putExtra("id", listOfActivities.get(position).getId());
                startActivity(intent);
            }
        });

        new LoadData().execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        new LoadData().execute();
    }

    /**
     *
     */
    private class LoadData extends AsyncTask<String, Integer, List<PhysicalActivity>> {
        @Override
        protected List<PhysicalActivity> doInBackground(String... params) {
            List<PhysicalActivity> oList = new ArrayList<>();

            //get all records
            Iterator iterator = PhysicalActivity.findAll(PhysicalActivity.class);
            for (Iterator<PhysicalActivity> iter = iterator; iter.hasNext();){
                oList.add(iter.next());
            }

            listOfActivitiesLastMonth = PhysicalActivity.find(PhysicalActivity.class,"month = ?",
                    String.valueOf(Calendar.getInstance().get(Calendar.MONTH)-1));

            return oList;
        }

        @Override
        protected void onPostExecute(List<PhysicalActivity> result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            listOfActivities = result;

            if(listOfActivities==null || listOfActivities.isEmpty()){
                emptyView.setVisibility(View.VISIBLE);
                listView.setEmptyView(emptyView);
            }else{
                emptyView.setVisibility(View.GONE);

                int totalMinutes = 0;
                for(PhysicalActivity activity: listOfActivities){
                    totalMinutes += activity.getMinutes();
                }
                perMonth.setText((totalMinutes / 12) + " minute(s) per month");
                lastMonth.setText(listOfActivitiesLastMonth.size() + " activities were completed last month");
            }

            listView.setAdapter(new TrackingAdapter(getActivity(), 0, listOfActivities));
        }
    }
}