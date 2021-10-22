package com.news2day.model;

public class LoginPojo {
    private String user_id;

    private String success;

    private String otp;

    private String status;

    private String message;
    public String getMessage ()
    {
        return message;
    }

    public void getMessage (String message)
    {
        this.message = message;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    public String getOtp ()
    {
        return otp;
    }

    public void setOtp (String otp)
    {
        this.otp = otp;
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
        return "ClassPojo [user_id = "+user_id+", success = "+success+", otp = "+otp+", status = "+status+"]";
    }
}
