package com.news2day.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.news2day.Activity.ContestsActivity;
import com.news2day.Activity.EditProfileActivity;
import com.news2day.Activity.NewsActivity;
import com.news2day.Adapter.DiscoveryCategoryAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.FragmentDiscoveryNewBinding;
import com.news2day.model.DiscoveryPojos.DiscoveryMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoveryNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoveryNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoveryNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoveryNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoveryNewFragment newInstance(String param1, String param2) {
        DiscoveryNewFragment fragment = new DiscoveryNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentDiscoveryNewBinding fragmentDiscoverBinding;

    RetrofitService service;
    UserDetails userDetails;
    String userId;
    String language,
            brief_data_str,
            servey_str, contests_str, dis_contests_category_id,
            dis_survey_category_id,
            dis_brief_category_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_discovery_new, container, false);
        fragmentDiscoverBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_discovery_new, container, false);

        service = RetrofitInstance.createService(RetrofitService.class);
        userDetails = new UserDetails(getActivity());
        userId = userDetails.getUserId();

        language = userDetails.getLanguage();
        fragmentDiscoverBinding.cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDetails.isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });
        apicall();

        if (userDetails.isLoggedIn()) {

        } else {
            fragmentDiscoverBinding.userNameTv.setText("Guest");

            fragmentDiscoverBinding.noOfReferalsTv.setVisibility(View.GONE);
        }

        fragmentDiscoverBinding.swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                apicall();

                fragmentDiscoverBinding.swiperefreshlayout.setRefreshing(false);

            }
        });


        fragmentDiscoverBinding.contestsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContestsActivity.class);
                intent.putExtra("category_id", dis_contests_category_id);
                intent.putExtra("title", contests_str);
                getActivity().startActivity(intent);
            }
        });
        fragmentDiscoverBinding.briefDataRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContestsActivity.class);
                intent.putExtra("category_id", dis_brief_category_id);
                intent.putExtra("title", brief_data_str);
                intent.putExtra("id", "0");
                getActivity().startActivity(intent);
            }
        });
        fragmentDiscoverBinding.serveyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContestsActivity.class);
                intent.putExtra("title", servey_str);
                intent.putExtra("category_id", dis_survey_category_id);
                intent.putExtra("id", "1");
                getActivity().startActivity(intent);
            }
        });
        return fragmentDiscoverBinding.getRoot();
    }

    public void apicall() {
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
                            for (int i = 0; i < discoveryMyPojo.getUserdata().size(); i++) {
                                fragmentDiscoverBinding.userNameTv.setText(discoveryMyPojo.getUserdata().get(i).getName());
                                fragmentDiscoverBinding.emailIdTv.setText(discoveryMyPojo.getUserdata().get(i).getEmail());
                                fragmentDiscoverBinding.mobileNumberTv.setText(discoveryMyPojo.getUserdata().get(i).getPhone());
                                fragmentDiscoverBinding.noOfReferalsTv.setText(discoveryMyPojo.getUserdata().get(i).get__v());
                            }
                            dis_contests_category_id = discoveryMyPojo.getData().get(0).get_id();
                            dis_survey_category_id = discoveryMyPojo.getData().get(2).get_id();
                            dis_brief_category_id = discoveryMyPojo.getData().get(1).get_id();
                            if (language.equals("ENGLISH")) {
                                brief_data_str = discoveryMyPojo.getData().get(1).getName().getEn();
                                servey_str = discoveryMyPojo.getData().get(2).getName().getEn();
                                contests_str = discoveryMyPojo.getData().get(0).getName().getEn();
                                fragmentDiscoverBinding.contestTv.setText(contests_str);
                                fragmentDiscoverBinding.briefDataTv.setText(brief_data_str);
                                fragmentDiscoverBinding.surveysTv.setText(servey_str);

                            } else if (language.equals("TELUGU")) {
                                brief_data_str = discoveryMyPojo.getData().get(1).getName().getTe();
                                servey_str = discoveryMyPojo.getData().get(2).getName().getTe();
                                contests_str = discoveryMyPojo.getData().get(0).getName().getTe();
                                fragmentDiscoverBinding.contestTv.setText(contests_str);
                                fragmentDiscoverBinding.briefDataTv.setText(brief_data_str);
                                fragmentDiscoverBinding.surveysTv.setText(servey_str);

                            } else if (language.equals("HINDI")) {
                                brief_data_str = discoveryMyPojo.getData().get(1).getName().getHi();
                                servey_str = discoveryMyPojo.getData().get(2).getName().getHi();
                                contests_str = discoveryMyPojo.getData().get(0).getName().getHi();
                                fragmentDiscoverBinding.contestTv.setText(contests_str);
                                fragmentDiscoverBinding.briefDataTv.setText(brief_data_str);
                                fragmentDiscoverBinding.surveysTv.setText(servey_str);


                            }
                            DiscoveryCategoryAdapter categoryAdapter = new DiscoveryCategoryAdapter(getActivity(), discoveryMyPojo.getData(), userDetails.getLanguage());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            fragmentDiscoverBinding.discoveryCategoryRv.setLayoutManager(linearLayoutManager);
                            fragmentDiscoverBinding.discoveryCategoryRv.setAdapter(categoryAdapter);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<DiscoveryMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentDiscoverBinding.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                  getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=USERID"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/USERID_OR_PROFILENAME"));
                }
              startActivity(intent);
            }
        });
        fragmentDiscoverBinding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/news2dayindia/"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
                }
            }
        });
        fragmentDiscoverBinding.insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/news2dayapp/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/news2dayapp/")));
                }
            }
        });
    }
}