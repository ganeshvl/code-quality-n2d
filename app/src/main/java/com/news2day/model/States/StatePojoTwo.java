package com.news2day.model.States;

import java.util.List;

public class StatePojoTwo {
    private List<StatePojoThree> details;

    private String status;

    public List<StatePojoThree> getDetails ()
    {
        return details;
    }

    public void setDetails (List<StatePojoThree> details)
    {
        this.details = details;
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
        return "ClassPojo [details = "+details+", status = "+status+"]";
    }
}
