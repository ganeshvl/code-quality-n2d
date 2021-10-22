package com.news2day.model;

import java.util.List;

public class GovermnentResponse {

    private String government_job_images_path;

    private String message;

    private List<GovernmentList> government_job_list;

    private String status;

    public String getGovernment_job_images_path ()
    {
        return government_job_images_path;
    }

    public void setGovernment_job_images_path (String government_job_images_path)
    {
        this.government_job_images_path = government_job_images_path;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<GovernmentList> getGovernment_job_list ()
    {
        return government_job_list;
    }

    public void setGovernment_job_list (List<GovernmentList> government_job_list)
    {
        this.government_job_list = government_job_list;
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
        return "ClassPojo [government_job_images_path = "+government_job_images_path+", message = "+message+", government_job_list = "+government_job_list+", status = "+status+"]";
    }
}
