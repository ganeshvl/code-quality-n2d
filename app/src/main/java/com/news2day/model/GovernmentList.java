package com.news2day.model;

public class GovernmentList {
    private String job_qualifications;

    private String category_name;

    private String qualification_ids;

    private String job_description;

    private String job_created_datetime;

    private String sub_category_name;

    private String job_title;

    private String job_url;

    private String display_image;

    private String no_of_vacancies;

    public String getJob_qualifications ()
    {
        return job_qualifications;
    }

    public void setJob_qualifications (String job_qualifications)
    {
        this.job_qualifications = job_qualifications;
    }

    public String getCategory_name ()
    {
        return category_name;
    }

    public void setCategory_name (String category_name)
    {
        this.category_name = category_name;
    }

    public String getQualification_ids ()
    {
        return qualification_ids;
    }

    public void setQualification_ids (String qualification_ids)
    {
        this.qualification_ids = qualification_ids;
    }

    public String getJob_description ()
    {
        return job_description;
    }

    public void setJob_description (String job_description)
    {
        this.job_description = job_description;
    }

    public String getJob_created_datetime ()
    {
        return job_created_datetime;
    }

    public void setJob_created_datetime (String job_created_datetime)
    {
        this.job_created_datetime = job_created_datetime;
    }

    public String getSub_category_name ()
    {
        return sub_category_name;
    }

    public void setSub_category_name (String sub_category_name)
    {
        this.sub_category_name = sub_category_name;
    }

    public String getJob_title ()
    {
        return job_title;
    }

    public void setJob_title (String job_title)
    {
        this.job_title = job_title;
    }

    public String getJob_url ()
    {
        return job_url;
    }

    public void setJob_url (String job_url)
    {
        this.job_url = job_url;
    }

    public String getDisplay_image ()
    {
        return display_image;
    }

    public void setDisplay_image (String display_image)
    {
        this.display_image = display_image;
    }

    public String getNo_of_vacancies ()
    {
        return no_of_vacancies;
    }

    public void setNo_of_vacancies (String no_of_vacancies)
    {
        this.no_of_vacancies = no_of_vacancies;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [job_qualifications = "+job_qualifications+", category_name = "+category_name+", qualification_ids = "+qualification_ids+", job_description = "+job_description+", job_created_datetime = "+job_created_datetime+", sub_category_name = "+sub_category_name+", job_title = "+job_title+", job_url = "+job_url+", display_image = "+display_image+", no_of_vacancies = "+no_of_vacancies+"]";
    }

}
