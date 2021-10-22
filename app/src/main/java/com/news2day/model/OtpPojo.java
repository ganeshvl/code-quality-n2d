package com.news2day.model;

public class OtpPojo {
    private Boolean success;

    private String message;

    private String status;

    public Boolean getSuccess ()
    {
        return success;
    }

    public void setSuccess (Boolean success)
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
