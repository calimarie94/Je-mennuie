package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marie on 24/12/2014.
 */
public class ListActivitiesActivity extends Activity {

    final String EXTRA_FAVORITE = "is_favorite";
    final String ID_ACTIVITY = "id_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activities);

         /* Loading the fonts */
        TextView title = (TextView) findViewById(R.id.title_activity);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        title.setTypeface(font);

        final ListView listview = (ListView) findViewById(R.id.list_activity);
        final ArrayList<String> values = new ArrayList<String>();
        final ArrayList<Integer> ids = new ArrayList<Integer>();

        final DataBaseHelper database = DataBaseHelper.getInstance(this);

        for(ActivityToDo activityToDo : database.activities)
        {
            if(activityToDo.getDiscovered())
            {
                values.add(activityToDo.getNameActivity());
                ids.add(activityToDo.idActivity);
            }

        }

        final ListActivityAdapter adapter = new ListActivityAdapter(getApplicationContext(), values, ids);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(ListActivitiesActivity.this, DetailedActivityActivity.class);
                ListActivityAdapter adapter = (ListActivityAdapter)parent.getAdapter();
                int id_current_activity = adapter.id_activites.get((int)position);
                intent.putExtra(EXTRA_FAVORITE,database.activities.get(id_current_activity).getFavorite());
                intent.putExtra(ID_ACTIVITY,database.activities.get(id_current_activity).idActivity);
                startActivity(intent);
            }

        });
    }


}