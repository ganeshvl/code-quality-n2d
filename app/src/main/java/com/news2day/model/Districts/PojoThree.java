package com.news2day.model.Districts;

public class PojoThree {
    private PojoFour name;

    private String _id;

    private String state_id;

    public PojoFour getName ()
    {
        return name;
    }

    public void setName (PojoFour name)
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

    public String getState_id ()
    {
        return state_id;
    }

    public void setState_id (String state_id)
    {
        this.state_id = state_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", _id = "+_id+", state_id = "+state_id+"]";
    }
}
