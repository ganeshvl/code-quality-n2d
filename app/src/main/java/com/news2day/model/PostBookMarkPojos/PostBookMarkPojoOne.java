package com.news2day.model.PostBookMarkPojos;

import java.util.List;

public class PostBookMarkPojoOne {
    private PostBookMartPojoTwo data;

    private String success;

    private String status;

    public PostBookMartPojoTwo getData ()
    {
        return data;
    }

    public void setData (PostBookMartPojoTwo data)
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
