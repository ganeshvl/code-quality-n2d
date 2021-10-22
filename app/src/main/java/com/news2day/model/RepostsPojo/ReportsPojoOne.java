package com.news2day.model.RepostsPojo;

import java.util.List;

public class ReportsPojoOne {
    private List<ReportsPojoTwo> data;

    private String success;

    private String status;

    public List<ReportsPojoTwo> getData ()
    {
        return data;
    }

    public void setData (List<ReportsPojoTwo> data)
    {
        this.data = data;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
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
        return "ClassPojo [data = "+data+", success = "+success+", status = "+status+"]";
    }
}
