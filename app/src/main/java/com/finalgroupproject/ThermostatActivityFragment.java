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

import com.finalgroupproject.Objects.Thermostat;
import com.finalgroupproject.adapters.ThermostatAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThermostatActivityFragment extends Fragment {

    private ListView listView;
    private TextView emptyView;
    private ProgressBar progressBar;

    private List<Thermostat> listOfThermostat;

    public ThermostatActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thermostat, container, false);

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
                Intent intent = new Intent(getActivity(), ThermostatNewActivity.class);
                intent.putExtra("id", listOfThermostat.get(position).getId());
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
    private class LoadData extends AsyncTask<String, Integer, List<Thermostat>> {
        @Override
        protected List<Thermostat> doInBackground(String... params) {
            List<Thermostat> oList = new ArrayList<>();

            //get all records
            Iterator iterator = Thermostat.findAll(Thermostat.class);
            for (Iterator<Thermostat> iter = iterator; iter.hasNext();){
                oList.add(iter.next());
            }

            return oList;
        }

        @Override
        protected void onPostExecute(List<Thermostat> result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            listOfThermostat = result;

            if(listOfThermostat==null || listOfThermostat.isEmpty()){
                emptyView.setVisibility(View.VISIBLE);
                listView.setEmptyView(emptyView);
            }else{
                emptyView.setVisibility(View.GONE);
            }

            listView.setAdapter(new ThermostatAdapter(getActivity(), 0, listOfThermostat));
        }
    }
}
