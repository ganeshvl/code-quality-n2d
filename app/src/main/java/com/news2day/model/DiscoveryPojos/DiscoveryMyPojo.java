package com.news2day.model.DiscoveryPojos;

import java.util.List;

public class DiscoveryMyPojo {
    private List<UserDataPojo> userdata;

    private List<DataPojo> data;

    private String success;

    private String status;

    public List<UserDataPojo> getUserdata ()
    {
        return userdata;
    }

    public void setUserdata (List<UserDataPojo> userdata)
    {
        this.userdata = userdata;
    }

    public List<DataPojo> getData ()
    {
        return data;
    }

    public void setData (List<DataPojo> data)
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
        return "ClassPojo [userdata = "+userdata+", data = "+data+", success = "+success+", status = "+status+"]";
    }
}
