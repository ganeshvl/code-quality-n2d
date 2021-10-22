package com.news2day.model.States;

public class StatePojoOne {


    private StatePojoTwo data;

    private String message;

    private String status;

    public StatePojoTwo getData ()
    {
        return data;
    }

    public void setData (StatePojoTwo data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", message = "+message+", status = "+status+"]";
    }
}
