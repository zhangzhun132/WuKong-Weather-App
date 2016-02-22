

package com.zhangzhun.way.weather.about;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ProgressBar;

import com.zhangzhun.way.util.Utils;
import com.zhangzhun.way.weather.R;


public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

    private Preference mAppIntro;
    private Preference mCheckUpdate;
    private Preference mStarProject;
    private Preference mShare;
    private Preference mBlog;
    private Preference mGitHub;
    private Preference mEmail;


    private final String APP_INTRO = "app_intro";
    private final String DEMO_VIDEO = "demo_video";
    private final String CHECK_UPDATE = "check_update";
    private final String START_PROJECT = "star_project";
    private final String SHARE = "share";
    private final String BLOG = "blog";
    private final String GITHUB = "github";
    private final String EMAIL = "email";


    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mAppIntro = findPreference(APP_INTRO);
        mCheckUpdate = findPreference(CHECK_UPDATE);
        mStarProject = findPreference(START_PROJECT);
        mShare = findPreference(SHARE);
        mBlog = findPreference(BLOG);
        mGitHub = findPreference(GITHUB);
        mEmail = findPreference(EMAIL);

        mAppIntro.setOnPreferenceClickListener(this);
        mCheckUpdate.setOnPreferenceClickListener(this);
        mStarProject.setOnPreferenceClickListener(this);
        mShare.setOnPreferenceClickListener(this);
        mBlog.setOnPreferenceClickListener(this);
        mGitHub.setOnPreferenceClickListener(this);
        mEmail.setOnPreferenceClickListener(this);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressbar);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {

        if(mAppIntro == preference){
//            Intent toIntro = new Intent(getActivity(),AppIntroActivity.class);
//            startActivity(toIntro);
        }else if(mCheckUpdate == preference){
//            progressBar.setVisibility(View.VISIBLE);
//
//            Request.Builder builder = new Request.Builder();
//            builder.url(CONSTANT.VERSION_URL);
//            Request request = builder.build();
//            HttpUtil.enqueue(request, new Callback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//                    Snackbar.make(getView(), "Fail to get version info,please check your network setting", Snackbar.LENGTH_SHORT).show();
//                    handle.sendEmptyMessage(1);
//                }
//
//                @Override
//                public void onResponse(Response response) throws IOException {
//                    String latestVersion = response.body().string();
//                    if (CONSTANT.CURRENT_VERSION.equals(latestVersion.trim())) {
//                        Snackbar.make(getView(), getString(R.string.notify_current_is_latest), Snackbar.LENGTH_SHORT).show();
//                    } else {
//                        Snackbar.make(getView(), getString(R.string.notify_find_new_version) + latestVersion, Snackbar.LENGTH_SHORT).show();
//                    }
//                    handle.sendEmptyMessage(1);
//                }
//            });

        }else if(mStarProject == preference){
            Utils.copyToClipboard(getView(),"https://github.com/zhangzhun132");
        }else if(mShare == preference){
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share_info));
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.text_share_wukong)));

        }else if(mBlog == preference){
            Utils.copyToClipboard(getView(),"http://blog.csdn.net/zzzhangzhun");

        }else if(mGitHub == preference){
            Utils.copyToClipboard(getView(),"https://github.com/zhangzhun132");

        }else if(mEmail == preference){
            Utils.copyToClipboard(getView(),"460093533@qq.com");

        }
        return false;
    }

    private Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    });
}
