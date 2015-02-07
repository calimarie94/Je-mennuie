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

        // Récupération du jeu
        final Game game = Game.getInstance(this);

        /* Update des bonnes données */
        question.setText(game.getCurrentQuestionText());
        number_question.setText(game.nbQuestionAnswered+1 + "/" + Math.min((int)game.NB_QUESTIONS_PER_ROUND, game.nbQuestionAnswered + game.questionGameArray.size() ));

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
        // Récupération du jeu
        final Game game = Game.getInstance(this);

        if(v==button_yes || v==button_maybe || v==button_no){
                if (v==button_yes)
                    game.answerQuestion(Answer.Yes);
                else if (v==button_maybe)
                    game.answerQuestion(Answer.NoMatter);
                else if (v==button_no)
                    game.answerQuestion(Answer.No);


            if(game.round_finished) {
                //On finit l'activité jeu
                GameDisplayActivity.this.finish();
                Intent intent = new Intent(this, ResultDisplayActivity.class);
                startActivity(intent);
            }
            else
            {
                question.setText(game.getCurrentQuestionText());
                number_question.setText(game.nbQuestionAnswered+1 + "/" + Math.min((int)game.NB_QUESTIONS_PER_ROUND, game.nbQuestionAnswered + game.questionGameArray.size() ));
            }
        }

    }
}
