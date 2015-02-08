package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by Marie on 27/12/2014.
 */
public class ResultDisplayActivity extends Activity implements View.OnClickListener {

    final String ID_ACTIVITY = "id_activity";
    final String EXTRA_FAVORITE = "is_favorite";
    int num_result=0;
    TextView title_result;
    TextView result;
    Button btn_ok;
    Button btn_next;
    Game game;
    String text_result;
    ActivityToDo activity_result;
    DataBaseHelper database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_display);

        database = DataBaseHelper.getInstance(this);

        /* Récupération des éléments de la vue */
        title_result = (TextView) findViewById(R.id.title_result);
        result = (TextView) findViewById(R.id.text_result);
        btn_ok = (Button) findViewById(R.id.button_ok);
        btn_next = (Button) findViewById(R.id.button_next);

        game = Game.getInstance(this);

        /* Update des bonnes données */
        if(!game.activityToShowArray.isEmpty())
        {
            text_result=game.activityToShowArray.get(num_result).getNameActivity();
            activity_result = game.activityToShowArray.get(num_result);
            result.setText(text_result);
            num_result++;
        }
        else
        {
            Intent intent = new Intent(this, NoResultFoundActivity.class);
            ResultDisplayActivity.this.finish();
            startActivity(intent);
        }
        /* On charge la bonne police */
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        title_result.setTypeface(font);
        btn_ok.setTypeface(font);
        btn_next.setTypeface(font);

        /* Changement de couleur au clic */
        btn_ok.setBackgroundResource(R.drawable.selector);
        btn_next.setBackgroundResource(R.drawable.selector);

        /* Evenements au clic */
        btn_ok.setOnClickListener(this);
        btn_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // Detail activité
        if(v==btn_ok){
            ResultDisplayActivity.this.finish();
            Intent intent = new Intent(this, DetailedActivityActivity.class);
            intent.putExtra("text_result", text_result);
            intent.putExtra(EXTRA_FAVORITE, activity_result.getFavorite());
            intent.putExtra(ID_ACTIVITY, activity_result.idActivity);
            activity_result.setDiscovered(true);
            database.addActivityToDiscover(activity_result);
            startActivity(intent);
        }
        // Autre activité
        if(v==btn_next){
            // Si on est en dessous de 5 résultats
            if(num_result < game.activityToShowArray.size()) {
                text_result=game.activityToShowArray.get(num_result).getNameActivity();
                result.setText(text_result);
                num_result++;
            }
            // Sinon on dit qu'il n 'y a plus de résultat :(
            else{
                ResultDisplayActivity.this.finish();
                Intent intent = new Intent(this, NoMoreResultActivity.class);
                startActivity(intent);
            }

        }

    }
}