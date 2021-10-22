package com.news2day.model.UpdateProfilePojos;

public class UpdateProfilePojo {
    private String success;

    private String message;

    private String status;

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
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
        return "ClassPojo [success = "+success+", message = "+message+", status = "+status+"]";
    }
}
