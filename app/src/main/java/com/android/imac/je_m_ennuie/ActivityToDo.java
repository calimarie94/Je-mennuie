package com.android.imac.je_m_ennuie;

/**
 * Created by bruno on 01/12/2014.
 */
public class ActivityToDo {
    String nameActivity;
    int idActivity;
    boolean isFavorite;
    boolean isDiscovered;

    ActivityToDo(String name, int id)
    {
        idActivity = id;
        nameActivity = name;
        isFavorite = false;
        isDiscovered = false;
    }

    Answer getImpact(DataBase dataBase, Question question)
    {
        return dataBase.getImpactActivity(idActivity, question);
    }

    @Override
    public String toString() {
        return nameActivity;
    }

    /***************** useful for ActivityDatabase *************/
    void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    int getIdActivity() {
        return idActivity;
    }

    void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    void setDiscovered(boolean isDiscovered) {
        this.isDiscovered = isDiscovered;
    }

    void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    String getNameActivity() {
        return nameActivity;
    }

    boolean getDiscovered() {
        return this.isDiscovered;
    }

    boolean getFavorite() {
        return this.isFavorite;
    }

}
