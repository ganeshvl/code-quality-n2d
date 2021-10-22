package com.news2day.model.DiscoveryPojos;

import java.util.List;

public class DataPojo {
    private String image;

    private String __v;

    private NamePojo name;

    private String _id;

    private String type;

    private String status;

    private List<SubCategoryDetailsPojo> subcat_data;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public NamePojo getName ()
    {
        return name;
    }

    public void setName (NamePojo name)
    {
        this.name = name;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public List<SubCategoryDetailsPojo> getSubcat_data ()
    {
        return subcat_data;
    }

    public void setSubcat_data (List<SubCategoryDetailsPojo> subcat_data)
    {
        this.subcat_data = subcat_data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", __v = "+__v+", name = "+name+", _id = "+_id+", type = "+type+", status = "+status+", subcat_data = "+subcat_data+"]";
    }
}
