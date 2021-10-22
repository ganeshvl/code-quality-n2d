package com.news2day.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.Activity.ContestsActivity;
import com.news2day.Activity.EditProfileActivity;
import com.news2day.Activity.NewsActivity;
import com.news2day.Activity.SpecialVideosListActivity;
import com.news2day.Adapter.DiscoveryLiveInfoAdapter;
import com.news2day.Adapter.DiscoveryNewsAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.FragmentDiscoverBinding;
import com.news2day.model.DiscoveryPojos.DiscoveryMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment {
    // FragmentHomeBinding fragmentHomeBinding;
    RecyclerView rv_trending, rv_startup, rv_special, rv_political;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> name_selected = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<Integer> special = new ArrayList<>();
    ArrayList<Integer> startup = new ArrayList<>();
    ArrayList<String> startupname = new ArrayList<>();
    ArrayList<Integer> political = new ArrayList<>();
    ArrayList<String> politicalname = new ArrayList<>();

    RetrofitService service;
    UserDetails userDetails;
    FragmentDiscoverBinding fragmentDiscoverBinding;
    String userId, language,
            dis_contests_category_id,
            dis_survey_category_id,
            dis_brief_category_id,
            dis_special_videos_category_id,
            dis_news_category_id,
            dis_entertainment_category_id,
            dis_buzz_category_id,
            dis_career_category_id,
            dis_technology_category_id,
            dis_liv_info_category_id,
            dis_trending_now_category_id, brief_data_str,
            servey_str,contests_str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  View view = inflater.inflate(R.layout.fragment_discover, container, false);
        fragmentDiscoverBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false);

        service = RetrofitInstance.createService(RetrofitService.class);
        userDetails = new UserDetails(getActivity());
        userId = userDetails.getUserId();


        language = userDetails.getLanguage();
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);

        service.get_Category(userId).enqueue(new Callback<DiscoveryMyPojo>() {
            @Override
            public void onResponse(Call<DiscoveryMyPojo> call, Response<DiscoveryMyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    DiscoveryMyPojo discoveryMyPojo = response.body();
                    pd.dismiss();
                    if (discoveryMyPojo.getSuccess().equals("true")) {
                        if (discoveryMyPojo.getData() != null) {


                            dis_contests_category_id = discoveryMyPojo.getData().get(0).get_id();
                            dis_survey_category_id = discoveryMyPojo.getData().get(2).get_id();
                            dis_brief_category_id = discoveryMyPojo.getData().get(1).get_id();
                            dis_special_videos_category_id = discoveryMyPojo.getData().get(3).get_id();
                            dis_news_category_id = discoveryMyPojo.getData().get(4).get_id();
                            dis_entertainment_category_id = discoveryMyPojo.getData().get(5).get_id();
                            dis_buzz_category_id = discoveryMyPojo.getData().get(6).get_id();
                            dis_career_category_id = discoveryMyPojo.getData().get(7).get_id();
                            dis_technology_category_id = discoveryMyPojo.getData().get(8).get_id();
                            dis_liv_info_category_id = discoveryMyPojo.getData().get(9).get_id();
                            dis_trending_now_category_id = discoveryMyPojo.getData().get(10).get_id();


                            fragmentDiscoverBinding.userNameTv.setText(discoveryMyPojo.getUserdata().get(0).getName());
                            fragmentDiscoverBinding.emailIdTv.setText(discoveryMyPojo.getUserdata().get(0).getEmail());
                            fragmentDiscoverBinding.mobileNumberTv.setText(discoveryMyPojo.getUserdata().get(0).getPhone());
                            fragmentDiscoverBinding.noOfReferalsTv.setText(discoveryMyPojo.getUserdata().get(0).get__v());

                            if (language.equals("ENGLISH")) {
                                brief_data_str = discoveryMyPojo.getData().get(1).getName().getEn();
                                servey_str = discoveryMyPojo.getData().get(2).getName().getEn();
                                contests_str = discoveryMyPojo.getData().get(0).getName().getEn();
                                fragmentDiscoverBinding.contestTv.setText(contests_str);
                                fragmentDiscoverBinding.briefDataTv.setText(brief_data_str);
                                fragmentDiscoverBinding.surveysTv.setText(servey_str);
                                fragmentDiscoverBinding.specialVideosTv.setText(discoveryMyPojo.getData().get(3).getName().getEn());
                                fragmentDiscoverBinding.disNewsTv.setText(discoveryMyPojo.getData().get(4).getName().getEn());
                                fragmentDiscoverBinding.disEntertainmentTv.setText(discoveryMyPojo.getData().get(5).getName().getEn());
                                fragmentDiscoverBinding.disBuzzTv.setText(discoveryMyPojo.getData().get(6).getName().getEn());
                                fragmentDiscoverBinding.disCareerTv.setText(discoveryMyPojo.getData().get(7).getName().getEn());
                                fragmentDiscoverBinding.disTechnologyTv.setText(discoveryMyPojo.getData().get(8).getName().getEn());
                                fragmentDiscoverBinding.liveInfoTv.setText(discoveryMyPojo.getData().get(9).getName().getEn());
                                fragmentDiscoverBinding.trendingNowTv.setText(discoveryMyPojo.getData().get(10).getName().getEn());

                                fragmentDiscoverBinding.viewAllLiv.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllTrendingNow.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllSpecialVideos.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllNews.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllBuzz.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllCareer.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllEntertainment.setText(R.string.view_all_en);
                                fragmentDiscoverBinding.viewAllTechnology.setText(R.string.view_all_en);

                                fragmentDiscoverBinding.liveInfoNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.trendingNowNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.specialVideosNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.newsNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.buzzNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.careerNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.entertainmentNoDataTv.setText(R.string.no_sub_categories_available_en);
                                fragmentDiscoverBinding.technologyNoDataTv.setText(R.string.no_sub_categories_available_en);

                            } else if (language.equals("TELUGU")) {
                                brief_data_str = discoveryMyPojo.getData().get(1).getName().getTe();
                                servey_str = discoveryMyPojo.getData().get(2).getName().getTe();
                                contests_str = discoveryMyPojo.getData().get(0).getName().getTe();
                                fragmentDiscoverBinding.contestTv.setText(contests_str);
                                fragmentDiscoverBinding.briefDataTv.setText(brief_data_str);
                                fragmentDiscoverBinding.surveysTv.setText(servey_str);
                                fragmentDiscoverBinding.specialVideosTv.setText(discoveryMyPojo.getData().get(3).getName().getTe());
                                fragmentDiscoverBinding.disNewsTv.setText(discoveryMyPojo.getData().get(4).getName().getTe());
                                fragmentDiscoverBinding.disEntertainmentTv.setText(discoveryMyPojo.getData().get(5).getName().getTe());
                                fragmentDiscoverBinding.disBuzzTv.setText(discoveryMyPojo.getData().get(6).getName().getTe());
                                fragmentDiscoverBinding.disCareerTv.setText(discoveryMyPojo.getData().get(7).getName().getTe());
                                fragmentDiscoverBinding.disTechnologyTv.setText(discoveryMyPojo.getData().get(8).getName().getTe());
                                fragmentDiscoverBinding.liveInfoTv.setText(discoveryMyPojo.getData().get(9).getName().getTe());
                                fragmentDiscoverBinding.trendingNowTv.setText(discoveryMyPojo.getData().get(10).getName().getTe());

                                fragmentDiscoverBinding.viewAllLiv.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllTrendingNow.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllSpecialVideos.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllNews.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllBuzz.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllCareer.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllEntertainment.setText(R.string.view_all_te);
                                fragmentDiscoverBinding.viewAllTechnology.setText(R.string.view_all_te);


                                fragmentDiscoverBinding.liveInfoNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.trendingNowNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.specialVideosNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.newsNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.buzzNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.careerNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.entertainmentNoDataTv.setText(R.string.no_sub_categories_available_te);
                                fragmentDiscoverBinding.technologyNoDataTv.setText(R.string.no_sub_categories_available_te);

                            } else if (language.equals("HINDI")) {
                                brief_data_str = discoveryMyPojo.getData().get(1).getName().getHi();
                                servey_str = discoveryMyPojo.getData().get(2).getName().getHi();
                                contests_str=discoveryMyPojo.getData().get(0).getName().getHi();
                                fragmentDiscoverBinding.contestTv.setText(contests_str);
                                fragmentDiscoverBinding.briefDataTv.setText(brief_data_str);
                                fragmentDiscoverBinding.surveysTv.setText(servey_str);
                                fragmentDiscoverBinding.specialVideosTv.setText(discoveryMyPojo.getData().get(3).getName().getHi());
                                fragmentDiscoverBinding.disNewsTv.setText(discoveryMyPojo.getData().get(4).getName().getHi());
                                fragmentDiscoverBinding.disEntertainmentTv.setText(discoveryMyPojo.getData().get(5).getName().getHi());
                                fragmentDiscoverBinding.disBuzzTv.setText(discoveryMyPojo.getData().get(6).getName().getHi());
                                fragmentDiscoverBinding.disCareerTv.setText(discoveryMyPojo.getData().get(7).getName().getHi());
                                fragmentDiscoverBinding.disTechnologyTv.setText(discoveryMyPojo.getData().get(8).getName().getHi());
                                fragmentDiscoverBinding.liveInfoTv.setText(discoveryMyPojo.getData().get(9).getName().getHi());
                                fragmentDiscoverBinding.trendingNowTv.setText(discoveryMyPojo.getData().get(10).getName().getHi());

                                fragmentDiscoverBinding.viewAllLiv.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllTrendingNow.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllSpecialVideos.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllNews.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllBuzz.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllCareer.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllEntertainment.setText(R.string.view_all_hi);
                                fragmentDiscoverBinding.viewAllTechnology.setText(R.string.view_all_hi);


                                fragmentDiscoverBinding.liveInfoNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.trendingNowNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.specialVideosNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.newsNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.buzzNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.careerNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.entertainmentNoDataTv.setText(R.string.no_sub_categories_available_hi);
                                fragmentDiscoverBinding.technologyNoDataTv.setText(R.string.no_sub_categories_available_hi);

                            }
                            if (discoveryMyPojo.getData().get(9).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(9).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.liveInfoNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.liveInfoRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.liveInfoNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.liveInfoRv.setVisibility(View.VISIBLE);
                                    DiscoveryLiveInfoAdapter discoveryLiveInfoAdapter = new DiscoveryLiveInfoAdapter(getContext(), discoveryMyPojo.getData().get(9).getSubcat_data(), language, discoveryMyPojo.getData().get(0).getName());
                                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.liveInfoRv.setLayoutManager(linearLayoutManager1);
                                    fragmentDiscoverBinding.liveInfoRv.setAdapter(discoveryLiveInfoAdapter);
                                }

                            }
                            if (discoveryMyPojo.getData().get(10).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(10).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.trendingNowNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.trendingNowRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.trendingNowNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.trendingNowRv.setVisibility(View.VISIBLE);
                                    DiscoveryLiveInfoAdapter discoveryLiveInfoAdapter1 = new DiscoveryLiveInfoAdapter(getContext(), discoveryMyPojo.getData().get(10).getSubcat_data(), language, discoveryMyPojo.getData().get(0).getName());
                                    LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.trendingNowRv.setLayoutManager(linearLayoutManager3);
                                    fragmentDiscoverBinding.trendingNowRv.setAdapter(discoveryLiveInfoAdapter1);
                                }
                            }
                            if (discoveryMyPojo.getData().get(4).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(4).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.newsNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.newsRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.newsNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.newsRv.setVisibility(View.VISIBLE);
                                    DiscoveryNewsAdapter discoveryNewsAdapter = new DiscoveryNewsAdapter(getContext(), discoveryMyPojo.getData().get(4).getSubcat_data(), language);
                                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.newsRv.setLayoutManager(linearLayoutManager2);
                                    fragmentDiscoverBinding.newsRv.setAdapter(discoveryNewsAdapter);
                                }
                            }
                            if (discoveryMyPojo.getData().get(5).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(5).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.entertainmentNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.entertainmentRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.entertainmentNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.entertainmentRv.setVisibility(View.VISIBLE);
                                    DiscoveryNewsAdapter discoveryNewsAdapter = new DiscoveryNewsAdapter(getContext(), discoveryMyPojo.getData().get(5).getSubcat_data(), language);
                                    LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.entertainmentRv.setLayoutManager(linearLayoutManager6);
                                    fragmentDiscoverBinding.entertainmentRv.setAdapter(discoveryNewsAdapter);
                                }
                            }
                            if (discoveryMyPojo.getData().get(6).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(6).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.buzzNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.buzzRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.buzzNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.buzzRv.setVisibility(View.VISIBLE);
                                    DiscoveryNewsAdapter discoveryNewsAdapter = new DiscoveryNewsAdapter(getContext(), discoveryMyPojo.getData().get(6).getSubcat_data(), language);
                                    LinearLayoutManager linearLayoutManager7 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.buzzRv.setLayoutManager(linearLayoutManager7);
                                    fragmentDiscoverBinding.buzzRv.setAdapter(discoveryNewsAdapter);
                                }
                            }
                            if (discoveryMyPojo.getData().get(7).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(7).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.careerNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.careerRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.careerNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.careerRv.setVisibility(View.VISIBLE);
                                    DiscoveryNewsAdapter discoveryNewsAdapter = new DiscoveryNewsAdapter(getContext(), discoveryMyPojo.getData().get(7).getSubcat_data(), language);
                                    LinearLayoutManager linearLayoutManager8 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.careerRv.setLayoutManager(linearLayoutManager8);
                                    fragmentDiscoverBinding.careerRv.setAdapter(discoveryNewsAdapter);

                                }

                            }

                            if (discoveryMyPojo.getData().get(8).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(8).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.technologyNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.technologyRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.technologyNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.technologyRv.setVisibility(View.VISIBLE);
                                    DiscoveryNewsAdapter discoveryNewsAdapter = new DiscoveryNewsAdapter(getContext(), discoveryMyPojo.getData().get(8).getSubcat_data(), language);
                                    LinearLayoutManager linearLayoutManager9 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.technologyRv.setLayoutManager(linearLayoutManager9);
                                    fragmentDiscoverBinding.technologyRv.setAdapter(discoveryNewsAdapter);
                                }
                            }
                            if (discoveryMyPojo.getData().get(3).getSubcat_data() != null) {
                                if (discoveryMyPojo.getData().get(3).getSubcat_data().size() == 0) {
                                    fragmentDiscoverBinding.specialVideosNoDataTv.setVisibility(View.VISIBLE);
                                    fragmentDiscoverBinding.specialVideosRv.setVisibility(View.GONE);
                                } else {
                                    fragmentDiscoverBinding.specialVideosNoDataTv.setVisibility(View.GONE);
                                    fragmentDiscoverBinding.specialVideosRv.setVisibility(View.VISIBLE);
                                   /* DiscoverySpecialVideosAdapter discoveryLiveInfoAdapter = new DiscoverySpecialVideosAdapter(getActivity(), discoveryMyPojo.getData(), language);
                                    LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                    fragmentDiscoverBinding.specialVideosRv.setLayoutManager(linearLayoutManager4);
                                    fragmentDiscoverBinding.specialVideosRv.setAdapter(discoveryLiveInfoAdapter);*/
                                }
                            }
                        }

                    } else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<DiscoveryMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        });


        fragmentDiscoverBinding.rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SpecialVideosListActivity.class);
                startActivity(intent);
            }
        });


       /* fragmentDiscoverBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
*/


        fragmentDiscoverBinding.contestsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContestsActivity.class);
                intent.putExtra("category_id", dis_contests_category_id);
                intent.putExtra("title", contests_str);
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.briefDataRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContestsActivity.class);
                intent.putExtra("category_id", dis_brief_category_id);
                intent.putExtra("title", brief_data_str);
                intent.putExtra("id", "0");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.serveyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContestsActivity.class);
                intent.putExtra("title", servey_str);
                intent.putExtra("category_id", dis_survey_category_id);
                intent.putExtra("id", "1");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllLiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_liv_info_category_id);
                intent.putExtra("title", "Live Info");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllTrendingNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_trending_now_category_id);
                intent.putExtra("title", "Trending Now");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_news_category_id);
                intent.putExtra("title", "News");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_entertainment_category_id);
                intent.putExtra("title", "Entertainment");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllBuzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_buzz_category_id);
                intent.putExtra("title", "Buzz");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_career_category_id);
                intent.putExtra("title", "Career");
                startActivity(intent);
            }
        });
        fragmentDiscoverBinding.viewAllTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category_id", dis_technology_category_id);
                intent.putExtra("title", "Technology");
                startActivity(intent);
            }
        });


        fragmentDiscoverBinding.cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return fragmentDiscoverBinding.getRoot();
    }

}
