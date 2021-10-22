package com.news2day.model.States;

public class StatePojoFour {
    private String hi;

    private String te;

    private String en;

    public String getHi ()
    {
        return hi;
    }

    public void setHi (String hi)
    {
        this.hi = hi;
    }

    public String getTe ()
    {
        return te;
    }

    public void setTe (String te)
    {
        this.te = te;
    }

    public String getEn ()
    {
        return en;
    }

    public void setEn (String en)
    {
        this.en = en;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [hi = "+hi+", te = "+te+", en = "+en+"]";
    }
}
