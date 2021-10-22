package com.news2day.model.GetProfilePojos;

public class GetProfileDataPojo {
    private String mobile_otp;

    private String gender;

    private String phone;

    private String DOB;

    private String __v;

    private String name;

    private String _id;

    private String email;

    public String getMobile_otp ()
    {
        return mobile_otp;
    }

    public void setMobile_otp (String mobile_otp)
    {
        this.mobile_otp = mobile_otp;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getDOB ()
    {
        return DOB;
    }

    public void setDOB (String DOB)
    {
        this.DOB = DOB;
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

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [mobile_otp = "+mobile_otp+", gender = "+gender+", phone = "+phone+", DOB = "+DOB+", __v = "+__v+", name = "+name+", _id = "+_id+", email = "+email+"]";
    }
}
