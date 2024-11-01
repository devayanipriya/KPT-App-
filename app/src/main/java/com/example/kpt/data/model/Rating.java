package com.example.kpt.data.model;

public class Rating {

    int id;
    String name;
    String date;
    String div;
    String rate;

    public Rating() {}

    public Rating(int id, String name, String date, String div, String rate)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this.div = div;
        this.rate = rate;
    }

    public int getID()
    {
        return this.id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDiv()
    {
        return this.div;
    }

    public void setDiv(String div)
    {
        this.div = div;
    }

    public String getRate()
    {
        return this.rate;
    }

    public void setRate(String rate)
    {
        this.rate = rate;
    }
}
