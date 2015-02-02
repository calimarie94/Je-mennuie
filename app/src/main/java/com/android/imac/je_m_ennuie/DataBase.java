package com.android.imac.je_m_ennuie;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by bruno on 01/12/2014.
 */
public class DataBase {
    private
    LinkedList<ActivityToDo> activityDBArray;
    ArrayList<Question> questionDBArray;

    public
    DataBase()
    {

    }

    //Initialisation à l'ouverture du jeu
    void initialize()
    {
        activityDBArray = new LinkedList<ActivityToDo>();
        questionDBArray = new ArrayList<Question>();

        //Remplissage d'activité avec la base de données
        activityDBArray.add( new ActivityToDo("Activité 1", 1) );
        activityDBArray.add( new ActivityToDo("Activité 2", 2) );
        activityDBArray.add( new ActivityToDo("Activité 3", 3) );
        activityDBArray.add( new ActivityToDo("Activité 4", 4) );
        activityDBArray.add( new ActivityToDo("Activité 5", 5) );
        activityDBArray.add( new ActivityToDo("Activité 6", 6) );
        activityDBArray.add( new ActivityToDo("Activité 7", 7) );
        activityDBArray.add( new ActivityToDo("Activité 8", 8) );
        activityDBArray.add( new ActivityToDo("Activité 9", 9) );
        activityDBArray.add( new ActivityToDo("Activité 10", 10) );

        //Remplissage de questions avec la base de données
        questionDBArray.add( new Question("Question 1") );
        questionDBArray.add( new Question("Question 2") );
        questionDBArray.add( new Question("Question 3") );
        questionDBArray.add( new Question("Question 4") );
        questionDBArray.add( new Question("Question 5") );
        questionDBArray.add( new Question("Question 6") );
        questionDBArray.add( new Question("Question 7") );
        questionDBArray.add( new Question("Question 8") );
        questionDBArray.add( new Question("Question 9") );
        questionDBArray.add( new Question("Question 10") );
    }

    LinkedList<ActivityToDo> getActivityDBArray()
    {
        return activityDBArray;
    }

    ArrayList<Question> getQuestionDBArray()
    {
        return questionDBArray;
    }

    ActivityToDo getActivity(int id)
    {
        return activityDBArray.get(id);
    }

    Question getQuestion(int id)
    {
        return questionDBArray.get(id);
    }

    //Impact d'une activité selon une question
    Answer getImpactActivity(int idActivity, Question question)
    {
        //Lecture en BD
        int rand = (int)(Math.random() * 3);

        switch (rand)
        {
            case 0:
                return Answer.Yes;
            case 1:
                return Answer.NoMatter;
            case 2:
                return Answer.No;
            default:
                return Answer.NoMatter;
        }
    }

}
