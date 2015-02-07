package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Marie on 26/12/2014.
 */
public class NoResultFoundActivity extends Activity {

    Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noresultfound_display);

        /* Récupération des éléments de la vue */
        TextView title_noresult = (TextView) findViewById(R.id.title_noresultfound);
        Button btn_makeagain = (Button) findViewById(R.id.button_makeagain);
        Button btn_nofoundactivities = (Button) findViewById(R.id.button_nofoundactivities);

        /* On charge la bonne police */
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        title_noresult.setTypeface(font);
        btn_makeagain.setTypeface(font);
        btn_nofoundactivities.setTypeface(font);

        /* Changement de couleur au clic */
        btn_makeagain.setBackgroundResource(R.drawable.selector);

        game = Game.getInstance(this);

        /* Evenements au clic */

        btn_makeagain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!game.questionGameArray.isEmpty() && !game.activityGameArray.isEmpty() )
                {
                    game.beginRound();
                    Intent intent = new Intent(NoResultFoundActivity.this, GameDisplayActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_nofoundactivities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //On supprime l'activité No more result
                NoResultFoundActivity.this.finish();
                Intent intent = new Intent(NoResultFoundActivity.this, ListActivitiesActivity.class);
                startActivity(intent);
            }
        });

    }
}
