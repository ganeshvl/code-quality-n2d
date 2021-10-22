package com.news2day.retrofit;


import com.news2day.model.AboutUsPojo.AboutUsMyPojo;
import com.news2day.model.AdsPojo.AdsMyPojo;
import com.news2day.model.BookMarkPojo.BookMarkMyPojo;
import com.news2day.model.CategoryIDPojos.MyPojo;
import com.news2day.model.ContactUsPojo.ContactUsMyPojo;
import com.news2day.model.DiscoveryPojos.DataPojo;
import com.news2day.model.DiscoveryPojos.DiscoveryMyPojo;
import com.news2day.model.DiscoveryPojos.SubCategoryDetailsPojo;
import com.news2day.model.DiscoveryPojos.UserDataPojo;
import com.news2day.model.Districts.PojoOne;
import com.news2day.model.DownloadPostPojos.DownloadPostResponsePojo;
import com.news2day.model.FeedbackPojo.FeedbackMyPojo;
import com.news2day.model.GetProfilePojos.GetProfileResponsePojo;
import com.news2day.model.LikePostPojos.LikePostPojoOne;
import com.news2day.model.LoginPojo;
import com.news2day.model.OtpPojo;
import com.news2day.model.PostBookMarkPojos.PostBookMarkPojoOne;
import com.news2day.model.ReportPostPojo.ReportsPostPojoOne;
import com.news2day.model.RepostsPojo.ReportsPojoOne;
import com.news2day.model.ResendOtpPojo;
import com.news2day.model.SharePostsPojos.SharePostPojoOne;
import com.news2day.model.SkipPojo.SkipMyPojo;
import com.news2day.model.States.StatePojoOne;
import com.news2day.model.SubCategoryPojos.SubCatMyPojo;
import com.news2day.model.TermsAndConditionsPojo.TermsMyPojo;
import com.news2day.model.UpdateProfilePojos.UpdateProfilePojo;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by FAMILY on 14-12-2017.
 */

public interface RetrofitService {


    @GET(APIUrls.STATES)
    Call<StatePojoOne> states();

    @GET(APIUrls.DISTRICTS + "{stateId}")
    Call<PojoOne> districts(@Path("stateId") String stateId);

    @FormUrlEncoded
    @POST(APIUrls.CHECK_MOBILES)
    Call<LoginPojo> check_mobile(@Field("phone") String phone);

    @FormUrlEncoded
    @POST(APIUrls.OTP)
    Call<OtpPojo> OTP(@Field("phone") String phone,
                      @Field("otp") String otp);

    @FormUrlEncoded
    @POST(APIUrls.RESEND_OTP)
    Call<ResendOtpPojo> Resend_OTP(@Field("phone") String phone,
                                   @Field("otp") String otp);


    @FormUrlEncoded
    @POST(APIUrls.GET_ADS)
    Call<AdsMyPojo> get_ads(
                             @Field("state_id") String state_id,
                             @Field("district_id") String district_id,
                             @Field("language_type") String language_type,
                             @Field("userId") String userId);


    @FormUrlEncoded
    @POST(APIUrls.POSTS + "/{page}" + "/{perPage}")
    Call<PostsPojoOne> Posts(@Path("page") int page,
                             @Path("perPage") int perPage,
                             @Field("category_id") String category_id,
                             @Field("state_id") String state_id,
                             @Field("district_id") String district_id,
                             @Field("language_type") String language_type,
                             @Field("userId") String userId);

    @FormUrlEncoded
    @POST(APIUrls.POSTS + "/{page}" + "/{perPage}")
    Call<PostsPojoOne> Posts_special(@Path("page") int page,
                                     @Path("perPage") int perPage,
                                     @Field("category_id") String category_id,
                                     @Field("state_id") String state_id,
                                     @Field("district_id") String district_id,
                                     @Field("language_type") String language_type,
                                     @Field("userId") String userId,
                                     @Field("specialvideo") int specialvideo);

    @FormUrlEncoded
    @POST(APIUrls.BOOKMARK_POST)
    Call<PostBookMarkPojoOne> bookmark_posts(@Field("userId") String userId,
                                             @Field("postId") String postId);

    @GET(APIUrls.GET_REPORTS)
    Call<ReportsPojoOne> get_report();

    @FormUrlEncoded
    @POST(APIUrls.SHARE_POSTS)
    Call<SharePostPojoOne> share_posts(@Field("userId") String userId,
                                       @Field("postId") String postId);


    @FormUrlEncoded
    @POST(APIUrls.REPORT_POST)
    Call<ReportsPostPojoOne> report_post(@Field("reportId") String reportId,
                                         @Field("userId") String userId,
                                         @Field("postId") String postId);

    @FormUrlEncoded
    @POST(APIUrls.LIKE_POST)
    Call<LikePostPojoOne> like_post(@Field("userId") String userId,
                                    @Field("postId") String postId);


    @FormUrlEncoded
    @POST(APIUrls.DOWNLOAD_POST)
    Call<DownloadPostResponsePojo> download_post(@Field("userId") String userId,
                                                 @Field("postId") String postId);

    @FormUrlEncoded
    @POST(APIUrls.UPDATE_PROFILE)
    Call<UpdateProfilePojo> update_profile(@Field("name") String name,
                                           @Field("email") String email,
                                           @Field("gender") String gender,
                                           @Field("DOB") String DOB,
                                           @Field("userId") String userId);

    @FormUrlEncoded
    @POST(APIUrls.GET_PROFILE)
    Call<GetProfileResponsePojo> get_profile(@Field("userId") String userId);

    @FormUrlEncoded
    @POST(APIUrls.GET_CATEGORY)
    Call<DiscoveryMyPojo> get_Category(@Field("userId") String userId);


    @GET(APIUrls.GET_FOOTER_CATEGORY)
    Call<MyPojo> get_footer_category();

    @GET(APIUrls.GET_SUB_CATEGORY + "{catId}")
    Call<SubCatMyPojo> get_sub_category(@Path("catId") String catId);

    @FormUrlEncoded
    @POST(APIUrls.GET_ABOUT_US)
    Call<AboutUsMyPojo> get_about_us(@Field("lan") String lan);


    @GET(APIUrls.GET_CONTACT_US)
    Call<ContactUsMyPojo> get_contact_us();

    @FormUrlEncoded
    @POST(APIUrls.GET_TERMS_AND_CONDITIONS)
    Call<TermsMyPojo> get_terms_and_conditions(@Field("lan") String lan);

    @FormUrlEncoded
    @POST(APIUrls.GET_FEEDBACK)
    Call<FeedbackMyPojo> get_feedback_guest(@Field("title") String title,
                                      @Field("feedback")
                                           String feedback,
                                      @Field("mobile")
                                              String mobile,
                                      @Field("email")
                                              String email,
                                      @Field("userId")
                                           String userId);
    @FormUrlEncoded
    @POST(APIUrls.GET_FEEDBACK)
    Call<FeedbackMyPojo> get_feedback(@Field("title") String title,
                                      @Field("feedback")
                                              String feedback,

                                      @Field("userId")
                                              String userId);
    @FormUrlEncoded
    @POST(APIUrls.GET_BOOKMARK)
    Call<BookMarkMyPojo> get_bookmark(@Field("userId")
                                              String userId);
    @FormUrlEncoded
    @POST(APIUrls.GET_GUEST_LOGIN)
    Call<SkipMyPojo> get_guest_login(@Field("device_id")
                                              String device_id);
    @FormUrlEncoded
    @POST(APIUrls.GET_POSTS_BY_CAT_SUB_CATEGORY + "/{page}" + "/{perPage}")
    Call<PostsPojoOne> Get_Posts_by_cat_id_sub_cat_id(@Path("page") int page,
                                                      @Path("perPage") int perPage,
                                                      @Field("category_id") String category_id,
                                                      @Field("subcategory_id") String subcategory_id,
                                                      @Field("state_id") String state_id,
                                                      @Field("district_id") String district_id,
                                                      @Field("language_type") String language_type,
                                                      @Field("userId") String userId);
}

