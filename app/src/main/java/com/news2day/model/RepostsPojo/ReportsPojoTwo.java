package com.news2day.model.RepostsPojo;

public class ReportsPojoTwo {
    private String created_on;

    private String __v;

    private String name;

    private String _id;

    public String getCreated_on ()
    {
        return created_on;
    }

    public void setCreated_on (String created_on)
    {
        this.created_on = created_on;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
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

    @Override
    public String toString()
    {
        return "ClassPojo [created_on = "+created_on+", __v = "+__v+", name = "+name+", _id = "+_id+"]";
    }
}
