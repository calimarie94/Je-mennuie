package com.android.imac.je_m_ennuie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Marie on 24/12/2014.
 */
public class ListActivityAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ListActivityAdapter(Context context, String[] values) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.activity_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);

        // Mettre une etoile pleine si il est favori, etoile vide sinon

        /*String s = values[position];

        if (s.isFavorite()) {
            imageView.setImageResource(R.drawable.full_star);
        } else {
            imageView.setImageResource(R.drawable.empty_star);
        }*/

        return rowView;
    }
}

