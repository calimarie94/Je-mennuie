package com.android.imac.je_m_ennuie;

/**
 * Created by bruno on 01/12/2014.
 */
public class Question {
    String nameQuestion;
    int id;

    Question(String name)
    {
        nameQuestion = name;
    }

    String getNameQuestion()
    {
        return nameQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nameQuestion;
    }
}
