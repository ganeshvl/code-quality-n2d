package com.news2day.model.Districts;

import java.util.List;

public class PojoTwo {
    private List<PojoThree> details;

    private String status;

    public List<PojoThree> getDetails ()
    {
        return details;
    }

    public void setDetails (List<PojoThree> details)
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
