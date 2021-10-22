package com.news2day.model.SubCategoryPojos;

import java.util.List;

public class SubCatMyPojo {
    private List<SubCatDataPojo> data;

    private String success;

    private String status;

    public List<SubCatDataPojo> getData ()
    {
        return data;
    }

    public void setData (List<SubCatDataPojo> data)
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
