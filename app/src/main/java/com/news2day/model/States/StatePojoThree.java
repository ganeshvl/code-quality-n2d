package com.news2day.model.States;

public class StatePojoThree {
    private String image;

    private StatePojoFour name;

    private String _id;

    private String districts_count;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public StatePojoFour getName ()
    {
        return name;
    }

    public void setName (StatePojoFour name)
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

    public String getDistricts_count ()
    {
        return districts_count;
    }

    public void setDistricts_count (String districts_count)
    {
        this.districts_count = districts_count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", name = "+name+", _id = "+_id+", districts_count = "+districts_count+"]";
    }
}
