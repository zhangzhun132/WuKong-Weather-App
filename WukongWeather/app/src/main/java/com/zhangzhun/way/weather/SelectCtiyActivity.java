package com.zhangzhun.way.weather;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zhangzhun.way.apapter.CityAdapter;
import com.zhangzhun.way.apapter.HotCityGridAdapter;
import com.zhangzhun.way.apapter.SearchCityAdapter;
import com.zhangzhun.way.app.Application;
import com.zhangzhun.way.bean.City;
import com.zhangzhun.way.db.CityDB;
import com.zhangzhun.way.plistview.BladeView;
import com.zhangzhun.way.plistview.BladeView.OnItemClickListener;
import com.zhangzhun.way.plistview.PinnedHeaderListView;
import com.zhangzhun.way.util.L;
import com.zhangzhun.way.util.NetUtil;
import com.zhangzhun.way.util.T;

public class SelectCtiyActivity extends Activity implements TextWatcher,
		OnClickListener, Application.EventHandler {
	private EditText mSearchEditText;
	// private Button mCancelSearchBtn;
	private ImageButton mClearSearchBtn;
	private View mCityContainer;
	private View mSearchContainer;
	private PinnedHeaderListView mCityListView;
	private BladeView mLetter;
	private ListView mSearchListView;
	private List<City> mCities;
	private SearchCityAdapter mSearchCityAdapter;
	private CityAdapter mCityAdapter;
	// 首字母集
	private List<String> mSections;
	// 根据首字母存放数据
	private Map<String, List<City>> mMap;
	// 首字母位置集
	private List<Integer> mPositions;
	// 首字母对应的位置
	private Map<String, Integer> mIndexer;
	private CityDB mCityDB;
	private Application mApplication;
	private InputMethodManager mInputMethodManager;

	private TextView mTitleTextView;
	private ImageView mBackBtn;
	private ProgressBar mTitleProgressBar;
	private FrameLayout mFrameLayout;
	View hotcityall;
	String[] hotcity = new String[]{"北京", "上海", "广州","深圳","杭州","西安","商丘","天津","哈尔滨"};
	LayoutInflater localLayoutInflater;
	GridView localGridView;
	View cityhot_header_blank;
	private View city_locating_state;
	private View city_locate_failed;
	private TextView city_locate_state;
	private ProgressBar city_locating_progress;
	private ImageView city_locate_success_img;
	private LocationClient locationClient = null;
	private City mCity_locate;
	private RelativeLayout mRelativeLayout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		localLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biz_plugin_weather_select_city);
		Application.mListeners.add(this);
		initView();
		initData();

	}

	private void initView() {
		mRelativeLayout= (RelativeLayout) findViewById(R.id.city_locate);
		mTitleTextView = (TextView) findViewById(R.id.title_name);
		mBackBtn = (ImageView) findViewById(R.id.title_back);
		mBackBtn.setOnClickListener(this);
		mTitleProgressBar = (ProgressBar) findViewById(R.id.title_update_progress);
		mTitleProgressBar.setVisibility(View.VISIBLE);
		mTitleTextView.setText(Application.getInstance()
				.getSharePreferenceUtil().getCity());

		mSearchEditText = (EditText) findViewById(R.id.search_edit);
		mSearchEditText.addTextChangedListener(this);
		mClearSearchBtn = (ImageButton) findViewById(R.id.ib_clear_text);
		mClearSearchBtn.setOnClickListener(this);

		mCityContainer = findViewById(R.id.city_content_container);
		mSearchContainer = findViewById(R.id.search_content_container);
		mCityListView = (PinnedHeaderListView) findViewById(R.id.citys_list);
		mCityListView.setEmptyView(findViewById(R.id.citys_list_empty));

		cityhot_header_blank=findViewById(R.id.city_locate);
		city_locating_state = cityhot_header_blank.findViewById(R.id.city_locating_state);
		city_locate_state = ((TextView) cityhot_header_blank.findViewById(R.id.city_locate_state));
		city_locating_progress = ((ProgressBar) cityhot_header_blank.findViewById(R.id.city_locating_progress));
		city_locate_success_img = ((ImageView) cityhot_header_blank.findViewById(R.id.city_locate_success_img));
		city_locate_failed = cityhot_header_blank.findViewById(R.id.city_locate_failed);


		mLetter = (BladeView) findViewById(R.id.citys_bladeview);
		mLetter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				if (mIndexer.get(s) != null) {
					mCityListView.setSelection(mIndexer.get(s));
				}
			}
		});
		mLetter.setVisibility(View.GONE);
		mSearchListView = (ListView) findViewById(R.id.search_list);
		mSearchListView.setEmptyView(findViewById(R.id.search_empty));
		mSearchContainer.setVisibility(View.GONE);
		mSearchListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mInputMethodManager.hideSoftInputFromWindow(
						mSearchEditText.getWindowToken(), 0);
				return false;
			}
		});
		mCityListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
						// TODO Auto-generated method stub
						L.i(mCityAdapter.getItem(position).toString());
						startActivity(mCityAdapter.getItem(position));
					}
				});

		mSearchListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						L.i(mSearchCityAdapter.getItem(position).toString());
						startActivity(mSearchCityAdapter.getItem(position));
					}
				});
		mRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCity_locate!=null){
					startActivity(mCity_locate);

				}
				else{
					Toast.makeText(getApplicationContext(),"未定位所在城市,请稍后重试！",Toast.LENGTH_LONG).show();
				}


			}
		});
	}

	private void startActivity(City city) {
		Intent i = new Intent();
		i.putExtra("city", city);
		setResult(RESULT_OK, i);
		finish();
	}

	private void initData() {
		mApplication = Application.getInstance();
		mCityDB = mApplication.getCityDB();
		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		if (mApplication.isCityListComplite()) {
			mCities = mApplication.getCityList();
			mSections = mApplication.getSections();
			mMap = mApplication.getMap();
			mPositions = mApplication.getPositions();
			mIndexer = mApplication.getIndexer();

			mCityAdapter = new CityAdapter(SelectCtiyActivity.this, mCities,
					mMap, mSections, mPositions);
			mCityListView.setAdapter(mCityAdapter);
			mCityListView.setOnScrollListener(mCityAdapter);
			mCityListView.setPinnedHeaderView(LayoutInflater.from(
					SelectCtiyActivity.this).inflate(
					R.layout.biz_plugin_weather_list_group_item, mCityListView,
					false));
			mTitleProgressBar.setVisibility(View.GONE);
			mLetter.setVisibility(View.VISIBLE);
			loadLocation();


		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// do nothing
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mSearchCityAdapter = new SearchCityAdapter(SelectCtiyActivity.this,
				mCities);
		mSearchListView.setAdapter(mSearchCityAdapter);
		mSearchListView.setTextFilterEnabled(true);
		if (mCities.size() < 1 || TextUtils.isEmpty(s)) {
			mCityContainer.setVisibility(View.VISIBLE);
			mSearchContainer.setVisibility(View.INVISIBLE);
			mClearSearchBtn.setVisibility(View.GONE);
		} else {
			mClearSearchBtn.setVisibility(View.VISIBLE);
			mCityContainer.setVisibility(View.INVISIBLE);
			mSearchContainer.setVisibility(View.VISIBLE);
			mSearchCityAdapter.getFilter().filter(s);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// 如何搜索字符串长度为0，是否隐藏输入法
		// if(TextUtils.isEmpty(s)){
		// mInputMethodManager.hideSoftInputFromWindow(
		// mSearchEditText.getWindowToken(), 0);
		// }

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_clear_text:
			if (!TextUtils.isEmpty(mSearchEditText.getText().toString())) {
				mSearchEditText.setText("");
				mInputMethodManager.hideSoftInputFromWindow(
						mSearchEditText.getWindowToken(), 0);
			}
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Application.mListeners.remove(this);
	}

	@Override
	public void onCityComplite() {
		// 城市列表加载完的回调函数
		mCities = mApplication.getCityList();
		mSections = mApplication.getSections();
		mMap = mApplication.getMap();
		mPositions = mApplication.getPositions();
		mIndexer = mApplication.getIndexer();

		mCityAdapter = new CityAdapter(SelectCtiyActivity.this, mCities, mMap,
				mSections, mPositions);
		mLetter.setVisibility(View.VISIBLE);
		mCityListView.setAdapter(mCityAdapter);
		mCityListView.setOnScrollListener(mCityAdapter);
		mCityListView.setPinnedHeaderView(LayoutInflater.from(
				SelectCtiyActivity.this).inflate(
				R.layout.biz_plugin_weather_list_group_item, mCityListView,
				false));
		// mActionBar.setProgressBarVisibility(View.INVISIBLE);
		mTitleProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onNetChange() {
		if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE)
			T.showLong(this, R.string.net_err);
	}
	public void loadLocation() {

		city_locate_failed.setVisibility(View.GONE);
		city_locate_state.setVisibility(View.VISIBLE);
		city_locating_progress.setVisibility(View.VISIBLE);
		city_locate_success_img.setVisibility(View.GONE);
		city_locate_state.setText(getString(R.string.locating));
		if (locationClient == null) {
			locationClient = new LocationClient(getApplicationContext());
			locationClient.registerLocationListener(new LocationListenner());
			LocationClientOption option = new LocationClientOption();
			option.setAddrType("all");
			option.setOpenGps(false);

			option.setCoorType("bd09ll");
			option.setScanSpan(2000);
			locationClient.setLocOption(option);
		}

		locationClient.start();
	}
	private class LocationListenner implements BDLocationListener {
		public void onReceiveLocation(BDLocation location) {
			city_locating_progress.setVisibility(View.GONE);

			if (location != null) {


				if (location.getCity() != null
						&& !location.getCity().equals("")) {

					locationClient.stop();
					city_locate_failed.setVisibility(View.GONE);
					city_locate_state.setVisibility(View.VISIBLE);
					city_locating_progress.setVisibility(View.GONE);
					city_locate_success_img.setVisibility(View.VISIBLE);
					city_locate_state.setText(location.getCity());
					mCity_locate=mCityDB.getCity(location.getCity());




				} else {
					locationClient.requestLocation();

					city_locating_state.setVisibility(View.GONE);
					city_locate_failed.setVisibility(View.VISIBLE);
				}
			} else {
				// 定位失败
				System.out.println("((((((((("+"定位失败");
				city_locating_state.setVisibility(View.GONE);
				city_locate_failed.setVisibility(View.VISIBLE);

			}

		}

		public void onReceivePoi(BDLocation bdLocation) {

		}


	}

}
