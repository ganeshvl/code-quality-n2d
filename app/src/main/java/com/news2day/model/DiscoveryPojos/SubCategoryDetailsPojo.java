package com.news2day.model.DiscoveryPojos;

public class SubCategoryDetailsPojo {
    private String image;

    private String category_id;

    private String __v;

    private NamePojo name;

    private String _id;
    private String url;

    private String type;

    private String status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public NamePojo getName() {
        return name;
    }

    public void setName(NamePojo name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [image = " + image + ", category_id = " + category_id + ", __v = " + __v + ", name = " + name + ", _id = " + _id + ", type = " + type + ", status = " + status + "]";
    }

}
