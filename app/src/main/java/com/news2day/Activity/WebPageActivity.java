package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityWebPageBinding;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebPageActivity extends AppCompatActivity {
    ActivityWebPageBinding binding;
    String language,
            title, id, category_id, url, language_type, user_id,
            state_id,
            district_id;
    UserDetails userDetails;
    RetrofitService service;
    int page_count = 1, per_page_count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_page);
        userDetails = new UserDetails(this);

        language = userDetails.getLanguage();

        service = RetrofitInstance.createService(RetrofitService.class);
        Intent intent = getIntent();
        category_id = intent.getStringExtra("id");
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");


        // url = "https://www.livemint.com/news/india/for-it-professionals-govt-employment-survey-brings-in-more-good-news-11632734139860.html";
        binding.toolBar.titleTx.setText(title);

        language = userDetails.getLanguage();
        if (url.equals("") || url == null) {
            if (language.equals("ENGLISH")) {
                binding.noDataCard.setVisibility(View.VISIBLE);
                binding.noDataTv.setText(R.string.url_en);

            } else if (language.equals("TELUGU")) {
                binding.noDataCard.setVisibility(View.VISIBLE);
                binding.noDataTv.setText(R.string.url_te);


            } else if (language.equals("HINDI")) {
                binding.noDataCard.setVisibility(View.VISIBLE);
                binding.noDataTv.setText(R.string.url_hi);
            }
        } else {
            binding.noDataCard.setVisibility(View.GONE);
            startWebView(url);
        }


        user_id = userDetails.getUserId();
        state_id = userDetails.getStateId();
        district_id = userDetails.getDistrictId();
       /* if (id.equalsIgnoreCase("0")) {
            url = "https://www.sciencedirect.com/journal/data-in-brief/about/aims-and-scope";
        } else if (id.equalsIgnoreCase("1")) {
            url = "https://www.livemint.com/news/india/for-it-professionals-govt-employment-survey-brings-in-more-good-news-11632734139860.html";
        }*/


        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        binding.webview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(WebPageActivity.this);

                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        binding.webview.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        binding.webview.loadUrl(url);


    }

}