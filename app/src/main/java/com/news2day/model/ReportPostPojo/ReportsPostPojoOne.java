package com.news2day.model.ReportPostPojo;

public class ReportsPostPojoOne {

    private ReportsPostPojoTwo data;

    private String success;

    private String status;

    public ReportsPostPojoTwo getData ()
    {
        return data;
    }

    public void setData (ReportsPostPojoTwo data)
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
