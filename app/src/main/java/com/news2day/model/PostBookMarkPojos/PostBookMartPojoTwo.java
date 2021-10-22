package com.news2day.model.PostBookMarkPojos;

public class PostBookMartPojoTwo {
    private String created_on;

    private String __v;

    private String _id;

    private String postId;

    private String userId;

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

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getPostId ()
    {
        return postId;
    }

    public void setPostId (String postId)
    {
        this.postId = postId;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_on = "+created_on+", __v = "+__v+", _id = "+_id+", postId = "+postId+", userId = "+userId+"]";
    }
}
