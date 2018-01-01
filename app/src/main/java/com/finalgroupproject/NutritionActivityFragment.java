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

import com.finalgroupproject.Objects.Nutrition;
import com.finalgroupproject.adapters.NutritionAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NutritionActivityFragment extends Fragment {

    private ListView listView;
    private TextView emptyView;
    private TextView perDay, lastDay;
    private ProgressBar progressBar;

    private List<Nutrition> listOfNutritions;
    private List<Nutrition> listOfNutritionsLastDay;

    public NutritionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        perDay = view.findViewById(R.id.perDay);
        lastDay = view.findViewById(R.id.lastDay);
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
                Intent intent = new Intent(getActivity(), NutritionNewActivity.class);
                intent.putExtra("id", listOfNutritions.get(position).getId());
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
    private class LoadData extends AsyncTask<String, Integer, List<Nutrition>> {
        @Override
        protected List<Nutrition> doInBackground(String... params) {
            List<Nutrition> oList = new ArrayList<>();

            //get all records
            Iterator iterator = Nutrition.findAll(Nutrition.class);
            for (Iterator<Nutrition> iter = iterator; iter.hasNext();){
                oList.add(iter.next());
            }

            listOfNutritionsLastDay = Nutrition.find(Nutrition.class,"date = ?", String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1));

            return oList;
        }

        @Override
        protected void onPostExecute(List<Nutrition> result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            listOfNutritions = result;

            if(listOfNutritions==null || listOfNutritions.isEmpty()){
                emptyView.setVisibility(View.VISIBLE);
                listView.setEmptyView(emptyView);
            }else{
                emptyView.setVisibility(View.GONE);

                int totalCalories = 0;
                for(Nutrition nutrition: listOfNutritions){
                    totalCalories += nutrition.getCalories();
                }
                perDay.setText((totalCalories / 12) + " calories per day");

                totalCalories=0;
                for(Nutrition nutrition: listOfNutritionsLastDay){
                    totalCalories += nutrition.getCalories();
                }
                lastDay.setText(totalCalories + " calories eaten yesterday");
            }

            listView.setAdapter(new NutritionAdapter(getActivity(), 0, listOfNutritions));
        }
    }
}
