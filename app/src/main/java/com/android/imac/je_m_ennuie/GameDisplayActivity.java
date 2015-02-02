package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Marie on 23/12/2014.
 */
public class GameDisplayActivity extends Activity implements View.OnClickListener {
    // Liste des questions (à recuperer de la BDD)
    final String[] questions = new String[]{
            "Le texte un tout petit plus long de la jolie petite question 1 ?",
            "Le texte un tout petit plus long de la jolie petite question 2 ?",
            "Le texte un tout petit plus long de la jolie petite question 3 ?",
            "Le texte un tout petit plus long de la jolie petite question 4 ?",
            "Le texte un tout petit plus long de la jolie petite question 5 ?",
            "Le texte un tout petit plus long de la jolie petite question 6 ?",
            "Le texte un tout petit plus long de la jolie petite question 7 ?",
            "Le texte un tout petit plus long de la jolie petite question 8 ?",
            "Le texte un tout petit plus long de la jolie petite question 9 ?",
            "Le texte un tout petit plus long de la jolie petite question 10 ?",
    };

    int num_question=0;
    TextView question;
    TextView number_question;
    Button button_yes;
    Button button_maybe;
    Button button_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);

         /* Récupération des éléments de la vue */
        question = (TextView) findViewById(R.id.question);
        number_question = (TextView) findViewById(R.id.number_question);
        button_yes = (Button) findViewById(R.id.btn_yes);
        button_maybe = (Button) findViewById(R.id.btn_maybe);
        button_no = (Button) findViewById(R.id.btn_no);

        /* Update des bonnes données */
        question.setText(questions[num_question]);
        number_question.setText((num_question+1)+"/10");

        num_question++;

        /* On charge la bonne police */
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        question.setTypeface(font);
        number_question.setTypeface(font);

        /* Changement de couleur au clic */
        button_yes.setBackgroundResource(R.drawable.selector);
        button_maybe.setBackgroundResource(R.drawable.selector);
        button_no.setBackgroundResource(R.drawable.selector);

        /* Evenements au clic */
        button_yes.setOnClickListener(this);
        button_maybe.setOnClickListener(this);
        button_no.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v==button_yes || v==button_maybe || v==button_no){

            // Si on est en dessous de 10 questions
            if(num_question<10) {
                question.setText(questions[num_question]);
                number_question.setText((num_question + 1) + "/10");
                num_question++;
                // enregistrement de la reponse
            }
            // Sinon on loade le résultat
            else{
                //On finit l'activité jeu
                GameDisplayActivity.this.finish();
                Intent intent = new Intent(this, ResultDisplayActivity.class);
                startActivity(intent);
            }
        }

    }
}
