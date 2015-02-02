package com.android.imac.je_m_ennuie;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by bruno on 01/12/2014.
 */
public class Game {

    public static final int NB_QUESTIONS_PER_ROUND = 3;
    public static final int NB_ROUND = 2;
    public static final int NB_ACTIVITIES_TO_SHOW = 10;

    private static Game sInstance;

    LinkedList<ActivityToDo> activityGameArray; //Les activités possibles restantes
    ArrayList<Question> questionGameArray; //Les questions qui n'ont pas encore été posées

    HashMap< Question, Answer > questionAnsweredMap; //Les réponses données par le joueur pour chaque question
    LinkedList<ActivityToDo> activityToShowArray; //Les activités à montrer
    int roundNumber; //Le numéro du round
    int nbQuestionAnswered; //Le nombre de question posées
    int idCurrentQuestion; //L'index de la question courante dans l'array de question

    private final Context myContext;

    public static Game getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new Game(context);
        }
        return sInstance;
    }

    private Game(Context context)
    {
        activityGameArray = new LinkedList<ActivityToDo>();
        questionGameArray = new ArrayList<Question>();
        questionAnsweredMap = new HashMap<Question, Answer>();
        activityToShowArray = new LinkedList<ActivityToDo>();

        roundNumber = 0;
        idCurrentQuestion = -1;
        nbQuestionAnswered = 0;

        myContext = context;
    }

    //Commencement d'un nouveau jeu
    public void newGame()
    {
        //Nettoyage des différentes listes
        activityGameArray.clear();
        questionGameArray.clear();
        questionAnsweredMap.clear();

        //Recuperation de toutes les activités et questions
        DataBaseHelper dataBase = DataBaseHelper.getInstance(myContext);

        activityGameArray = (LinkedList<ActivityToDo>)dataBase.activities.clone();
        questionGameArray = (ArrayList<Question>)dataBase.questions.clone();

        roundNumber = 0;
        nbQuestionAnswered = 0;

        //Première question
        askQuestion();
    }

    //Poser une question
    void askQuestion()
    {
        //rand on question
        int idQuestion = (int)(Math.random() * questionGameArray.size());
        idCurrentQuestion = idQuestion;
        //Print
        System.out.println(questionGameArray.get(idQuestion).getNameQuestion());
    }

    //Repondre à la question courante
    void answerQuestion(Answer answer)
    {
        if( idCurrentQuestion == -1 )
        {
            System.out.println("No Question");
            return;
        }
        //Enregistrement de la réponse du joueur (on nettoiera à la fin du round)
        questionAnsweredMap.put( questionGameArray.get( idCurrentQuestion ), answer);

        //On supprime la question de la liste pour ne plus qu'elle ressorte
        questionGameArray.remove( idCurrentQuestion );

        idCurrentQuestion = -1;

        nbQuestionAnswered++;

        //Si on a posé assez de questions, on fini le round
        if( nbQuestionAnswered >= NB_QUESTIONS_PER_ROUND )
        {
            roundFinished();
        }
        //Sinon, on repose une autre question
        else {
            askQuestion();
        }
    }

    //Fin du round
    void roundFinished()
    {
        roundNumber++;

        System.out.println("Round " + roundNumber);

        // Remove des activities à faire
        removeActivities();

        //Selection des activités à montrer
        searchPossibleActivities();

        //Affichage des activités à proposer
        if( activityToShowArray.size() > 0 )
        {
            for(ActivityToDo act : activityToShowArray)
            {
                System.out.println("Activité : " + act);
            }
        }
        //... s'il yen a
        else
        {
            System.out.println("Désolé, aucune activité à proposer");
        }

        if( roundNumber >= NB_ROUND )
        {
            System.out.println("All" + roundNumber + "Round ");
        }
    }

    //Suppression des activités à la fin du round en fonction des decisions du joueur.
    void removeActivities()
    {

        //Pour chaque reponse à une question
        for(Map.Entry<Question, Answer> entry : questionAnsweredMap.entrySet()) {
            Question question = entry.getKey();
            Answer answer = entry.getValue();

            System.out.println(question.getNameQuestion() + " : " + answer.toString());

            //Si le joueur a répondu peu importe, on supprime aucune activité
            if( answer == Answer.NoMatter )
                continue;

            //Liste des activités à supprimer
            LinkedList<ActivityToDo> activitiesToSupress = new LinkedList<ActivityToDo>();
            DataBaseHelper dataBase = DataBaseHelper.getInstance(myContext);

            for(ActivityToDo activityToDo : activityGameArray )
            {
                /*Answer impact = activityToDo.getImpact(dataBase, question);

                if( impact != Answer.NoMatter && impact != answer )
                {
                    //Si l'activité doit être supprimé, on la met dans la liste des activités à supprimer
                    activitiesToSupress.push(activityToDo);
                    System.out.println("Activité " + activityToDo + " supprimée");
                }*/
            }

            //On supprime ces activités de la liste d'activités possibles
            for(ActivityToDo activityToSupress : activitiesToSupress)
            {
                activityGameArray.remove(activityToSupress);
            }

            activitiesToSupress.clear();
        }

        //On nettoie la liste des decisions
        questionAnsweredMap.clear();
    }

    //A la fin du round, après avoir supprimé, on va choisir des activités possibles pour les proposer au joueur
    void searchPossibleActivities()
    {
        activityToShowArray.clear();

        //Si la liste d'activités est plus petite que le nombre d'activités à proposer, on les propose toutes
        if(activityGameArray.size() <= NB_ACTIVITIES_TO_SHOW)
        {
            activityToShowArray = (LinkedList<ActivityToDo>)activityGameArray.clone();
        }
        //Sinon on en choisit NB_ACTIVITIES_TO_SHOW au hasard
        else
        {
            //On mélange et on prend les NB_ACTIVITIES_TO_SHOW premiers
            Collections.shuffle(activityGameArray);
            for(int i=0; i < NB_ACTIVITIES_TO_SHOW; ++i)
            {
                activityToShowArray.add(activityGameArray.get(i));
            }
        }
    }

}
