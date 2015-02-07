package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Marie on 26/12/2014.
 */
public class NoMoreResultActivity extends Activity {

    Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nomoreresult_display);

        /* Récupération des éléments de la vue */
        TextView title_activity = (TextView) findViewById(R.id.title_nomoreresult);
        Button btn_back = (Button) findViewById(R.id.button_back);
        Button btn_continue = (Button) findViewById(R.id.button_continue);
        Button btn_myactivities = (Button) findViewById(R.id.button_myactivities);

        /* On charge la bonne police */
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        title_activity.setTypeface(font);
        btn_back.setTypeface(font);
        btn_continue.setTypeface(font);
        btn_myactivities.setTypeface(font);

        /* Changement de couleur au clic */
        btn_back.setBackgroundResource(R.drawable.selector);
        btn_continue.setBackgroundResource(R.drawable.selector);

        game = Game.getInstance(this);

        /* Evenements au clic */
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NoMoreResultActivity.this.finish();
                Intent intent = new Intent(NoMoreResultActivity.this, ResultDisplayActivity.class);
                startActivity(intent);
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!game.questionGameArray.isEmpty() && !game.activityGameArray.isEmpty() )
                {
                    game.beginRound();
                    NoMoreResultActivity.this.finish();
                    Intent intent = new Intent(NoMoreResultActivity.this, GameDisplayActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Plus de questions disponibles",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_myactivities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //On supprime l'activité No more result
                NoMoreResultActivity.this.finish();
                Intent intent = new Intent(NoMoreResultActivity.this, ListActivitiesActivity.class);
                startActivity(intent);
            }
        });

    }
}
