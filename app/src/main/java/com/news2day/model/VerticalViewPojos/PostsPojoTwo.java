package com.news2day.model.VerticalViewPojos;

public class PostsPojoTwo {
    private String shares;

    private String[] image;

    private String reports;

    private String[] video_url;

    private String content_type;

    private int downloads;

    private String name;

    private String url;



    private String description;

    private String _id;

    private String[] language_type;

    private String likes;


    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }


    public String getShares ()
    {
        return shares;
    }

    public void setShares (String shares)
    {
        this.shares = shares;
    }

    public String[] getImage ()
    {
        return image;
    }

    public void setImage (String[] image)
    {
        this.image = image;
    }

    public String getReports ()
    {
        return reports;
    }

    public void setReports (String reports)
    {
        this.reports = reports;
    }

    public String[] getVideo_url ()
    {
        return video_url;
    }

    public void setVideo_url (String[] video_url)
    {
        this.video_url = video_url;
    }

    public String getContent_type ()
    {
        return content_type;
    }

    public void setContent_type (String content_type)
    {
        this.content_type = content_type;
    }

    public int getDownloads ()
    {
        return downloads;
    }

    public void setDownloads (int downloads)
    {
        this.downloads = downloads;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String[] getLanguage_type ()
    {
        return language_type;
    }

    public void setLanguage_type (String[] language_type)
    {
        this.language_type = language_type;
    }

    public String getLikes ()
    {
        return likes;
    }

    public void setLikes (String likes)
    {
        this.likes = likes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [shares = "+shares+", image = "+image+", reports = "+reports+", video_url = "+video_url+", content_type = "+content_type+", downloads = "+downloads+", name = "+name+", description = "+description+", _id = "+_id+", language_type = "+language_type+", likes = "+likes+"]";
    }
}
