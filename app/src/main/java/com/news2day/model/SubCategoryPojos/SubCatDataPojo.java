package com.news2day.model.SubCategoryPojos;

public class SubCatDataPojo {
    private String image;

    private String category_id;

    private String __v;

    private SubCatNamePojo name;

    private String _id;

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

    public SubCatNamePojo getName() {
        return name;
    }

    public void setName(SubCatNamePojo name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "ClassPojo [image = " + image + ", category_id = " + category_id + ", __v = " + __v + ", name = " + name + ", _id = " + _id + "]";
    }
}
