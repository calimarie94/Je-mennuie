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
 * Created by Marie on 27/12/2014.
 */
public class ResultDisplayActivity extends Activity implements View.OnClickListener {
    // Liste des résultats (à calculer en fonction des réponses)
    final String[] results = new String[]{
            "Le texte de la jolie petite réponse 1 ",
            "Le texte de la jolie petite réponse 2 ",
            "Le texte de la jolie petite réponse 3 ",
            "Le texte de la jolie petite réponse 4 ",
            "Le texte de la jolie petite réponse 5 ",
    };

    int num_result=0;
    TextView title_result;
    TextView result;
    Button btn_ok;
    Button btn_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_display);

        /* Récupération des éléments de la vue */
        title_result = (TextView) findViewById(R.id.title_result);
        result = (TextView) findViewById(R.id.text_result);
        btn_ok = (Button) findViewById(R.id.button_ok);
        btn_next = (Button) findViewById(R.id.button_next);

        /* Update des bonnes données */
        result.setText(results[num_result]);
        num_result++;

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
        if(v==btn_ok){
            Intent intent = new Intent(this, DetailedActivityActivity.class);
            startActivity(intent);
        }
        if(v==btn_next){
            // Si on est en dessous de 5 résultats
            if(num_result<5) {
                result.setText(results[num_result]);
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
