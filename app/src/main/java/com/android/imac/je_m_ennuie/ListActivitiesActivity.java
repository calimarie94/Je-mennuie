package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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

        // A remplacer par les vraies activités avec une boucle

        final Boolean favorite=false;

        final DataBaseHelper database = DataBaseHelper.getInstance(this);
        System.out.println("Size of discover" + database.discoveredActivities.size());

        for(ActivityToDo activityToDo : database.discoveredActivities)
        {
            values.add(activityToDo.getNameActivity());
        }

        final ListActivityAdapter adapter = new ListActivityAdapter(getApplicationContext(), values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(ListActivitiesActivity.this, DetailedActivityActivity.class);
                intent.putExtra(EXTRA_FAVORITE,favorite);
                startActivity(intent);
            }

        });
    }


}
