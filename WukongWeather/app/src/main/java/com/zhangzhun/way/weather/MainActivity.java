package com.zhangzhun.way.weather;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.adapter.BaseDrawerAdapter;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerItemViewHelper;
import com.zhangzhun.way.apapter.DrawerCityAdapter;
import com.zhangzhun.way.apapter.FragmentAdapter;
import com.zhangzhun.way.app.Application;
import com.zhangzhun.way.app.StaticNumber;
import com.zhangzhun.way.bean.City;
import com.zhangzhun.way.bean.Pm2d5;
import com.zhangzhun.way.bean.Weatherinfo;
import com.zhangzhun.way.db.DatabaseHelper;
import com.zhangzhun.way.fragment.Fragment1;
import com.zhangzhun.way.support.Constant;
import com.zhangzhun.way.support.Setting;
import com.zhangzhun.way.weather.about.AboutActivity;

public class MainActivity extends AppCompatActivity implements
        Application.EventHandler, OnClickListener,Drawer.OnDrawerItemClickListener{


    private City onNewCity;
    private DatabaseHelper databaseHelper;
    public static Drawer drawer;
    private Fragment1 fragment1;
    private AccountHeader header;
    private ListView mListView;
    private DrawerCityAdapter drawerCityAdapter;
    private List cityData;
    private List tempData;
    private List imgData;
    TextView bottomLeft;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private Weatherinfo fragment_weatherinfo;
    private Pm2d5 fragment_pm;
    private City mCity;

    public City getmCity() {
        return mCity;
    }

    public void setmCity(City mCity) {
        this.mCity = mCity;
    }

    public Pm2d5 getFragment_pm() {
        return fragment_pm;
    }

    public void setFragment_pm(Pm2d5 fragment_pm) {
        this.fragment_pm = fragment_pm;
    }

    public Weatherinfo getFragment_weatherinfo() {
        return fragment_weatherinfo;
    }

    public void setFragment_weatherinfo(Weatherinfo fragment_weatherinfo) {
        this.fragment_weatherinfo = fragment_weatherinfo;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper=new DatabaseHelper(this,"myMenu.db3",null,1);
        fragment1=new Fragment1();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main,fragment1);
        fragmentTransaction.commit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = getWindow();

            // 透明状态栏

            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,

                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// 透明导航栏

        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//不让布局移动


        //main_viewpager.setPadding(main_viewpager.getPaddingLeft(), getStatusBarHeight(MainActivity.this), main_viewpager.getPaddingRight(), main_viewpager.getPaddingBottom());



        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.alert);
        header = new AccountHeaderBuilder().withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.main_nav_header_bg)
                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.ic_launcher).withEmail(getString(R.string.author_email)).withName(getString(R.string.author_name)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Intent intent=new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        return false;
                    }
                })
                .build();


        // set creator
        drawer = new DrawerBuilder().withActivity(this)
                .withAccountHeader(header)
                .addDrawerItems(new PrimaryDrawerItem().withName("添加城市").withIcon(R.drawable.behind_add_city).withIdentifier(R.drawable.behind_add_city)

                )

                .build();
        drawer.setOnDrawerItemClickListener(this);
        addAllMenuItem(databaseHelper.getReadableDatabase());


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCityComplite() {
        // do nothing
    }

    @Override
    public void onNetChange() {

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub




    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(getIntent());
    }

    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            onNewCity=(City) data.getSerializableExtra("city");
            fragment1.setmNewIntentCity(onNewCity);
            fragment1.setNewIntent();
            System.out.println("+++++++++++++++-" + onNewCity.getNumber());
            addMenuItem(onNewCity.getCity(),onNewCity.getNumber());

        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void insertData(SQLiteDatabase db,String cityname,String citynumber){
        db.execSQL("insert into menu(cityName,cityNumber) values(?,?)",new String[]{cityname,citynumber});
        db.close();
    }

    public String queryData(SQLiteDatabase db,String cityname){
        String result="";
        Cursor cursor=db.query("menu",new String[]{"cityNumber"},"cityName like ?",new String[]{cityname},null,null,null);
        int numberIndex=cursor.getColumnIndex("cityNumber");
        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
            result = result +cursor.getString(numberIndex) ;
        }
        db.close();
        return result;
    }
    public String queryIdData(SQLiteDatabase db,String cityid){
        String result="";
        Cursor cursor=db.query("menu",new String[]{"cityName"},"cityNumber like ?",new String[]{cityid},null,null,null);
        int nameIndex=cursor.getColumnIndex("cityName");
        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
            result = result +cursor.getString(nameIndex) ;
        }
        db.close();
        return result;
    }
    public void addAllMenuItem(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from menu",null);
        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()){
            int nameID=cursor.getColumnIndex("cityName");
            int numberID=cursor.getColumnIndex("cityNumber");
            final String cityNAME=cursor.getString(nameID);
            final int  cityNUMBER=cursor.getInt(numberID);
            final String cityNUMBER_STRING=cursor.getString(numberID);
            drawer.addItem(new PrimaryDrawerItem().withName(cityNAME).withIdentifier(cityNUMBER));
            drawer.setOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                @Override
                public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                    final int p=position;
                    AlertDialog.Builder deleteDialog=new AlertDialog.Builder(MainActivity.this);
                    deleteDialog.setMessage("是否删除该城市？");
                    deleteDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            drawer.removeItemByPosition(p);

                        }
                    });
                    deleteDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    deleteDialog.show();
                    return false;
                }
            });
        }

    }
    public void addMenuItem(final String cityname, final String citynum){
        if (queryData(databaseHelper.getReadableDatabase(),cityname).equals("")){
            insertData(databaseHelper.getReadableDatabase(), cityname, citynum);

            final int  cityId=Integer.parseInt(citynum);
            drawer.addItem(new PrimaryDrawerItem().withName(cityname).withIdentifier(cityId));
            drawer.setOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                @Override
                public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                    final int p=position;
                    AlertDialog.Builder deleteDialog=new AlertDialog.Builder(getApplicationContext());
                    deleteDialog.setMessage("是否删除该城市？");
                    deleteDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            drawer.removeItemByPosition(p);

                        }
                    });
                    deleteDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    deleteDialog.show();
                    return false;
                }
            });

        }
    }




    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (drawerItem.getIdentifier()==R.drawable.behind_add_city){
            Intent i = new Intent(MainActivity.this, SelectCtiyActivity.class);
            startActivityForResult(i, 0);

        }
        else{
            int itemId=drawerItem.getIdentifier();
            String itemIdString=Integer.toString(itemId);

            String itemNameString=queryIdData(databaseHelper.getReadableDatabase(), itemIdString);
            fragment1.setNewIntentCity(itemNameString,itemIdString);
            fragment1.setNewIntent();


        }
        return false;
    }


}
