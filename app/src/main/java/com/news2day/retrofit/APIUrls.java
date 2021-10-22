package com.news2day.retrofit;

public class APIUrls {


    //public static final String BASE_URL = "http://15.206.41.132:5001/";
    public static final String BASE_URL = "http://18.118.90.146:5001//";

    public static final String STATES = BASE_URL + "admin/" + "stateslist";

    public static final String DISTRICTS = BASE_URL + "admin/" + "districtslist" + "/";

    public static final String CHECK_MOBILES = BASE_URL + "user/" + "check-mobile";

    public static final String OTP = BASE_URL + "user/" + "verify-otp";

    public static final String RESEND_OTP = BASE_URL + "user/" + "resend-otp";

    public static final String POSTS = BASE_URL + "user/" + "getposts";

    public static final String GET_ADS = BASE_URL + "user/" + "getadds";

    public static final String BOOKMARK_POST = BASE_URL + "user/" + "bookmark-post";

    public static final String GET_REPORTS = BASE_URL + "user/" + "get-report-mistakes";

    public static final String SHARE_POSTS = BASE_URL + "user/" + "share-post";

    public static final String REPORT_POST = BASE_URL + "user/" + "report-post";

    public static final String LIKE_POST = BASE_URL + "user/" + "like-post";

    public static final String DOWNLOAD_POST = BASE_URL + "user/" + "download-post";

    public static final String UPDATE_PROFILE = BASE_URL + "user/" + "update-profile";

    public static final String GET_PROFILE = BASE_URL + "user/" + "get-profile";

    public static final String GET_CATEGORY = BASE_URL + "user/" + "get-cat";

    public static final String GET_FOOTER_CATEGORY = BASE_URL + "user/" + "get-footer-cat";

    public static final String GET_SUB_CATEGORY = BASE_URL + "user/" + "get-sub-cat/";

    public static final String GET_ABOUT_US = BASE_URL + "user/" + "dynamicpages";

    public static final String GET_CONTACT_US = BASE_URL + "user/" + "contactus";

    public static final String GET_GUEST_LOGIN = BASE_URL + "user/" + "guestlogin";

    public static final String GET_FEEDBACK = BASE_URL + "user/" + "createfeedback";

    public static final String GET_BOOKMARK = BASE_URL + "user/" + "get_bookmarks";

    public static final String GET_TERMS_AND_CONDITIONS = BASE_URL + "user/" + "termsandconditions";

    public static final String GET_POSTS_BY_CAT_SUB_CATEGORY = BASE_URL + "user/" + "getpost_by_cat_subcat";
}
