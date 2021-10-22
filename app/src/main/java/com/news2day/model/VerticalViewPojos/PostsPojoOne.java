package com.news2day.model.VerticalViewPojos;

import java.util.List;

public class PostsPojoOne {
    private List<PostsPojoTwo> data;

    private String success;

    private String status;

    private String message;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }


    public List<PostsPojoTwo> getData ()
    {
        return data;
    }

    public void setData (List<PostsPojoTwo> data)
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
