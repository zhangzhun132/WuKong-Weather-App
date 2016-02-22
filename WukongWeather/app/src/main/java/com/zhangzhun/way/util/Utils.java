
package com.zhangzhun.way.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;


import com.zhangzhun.way.app.Application;
import com.zhangzhun.way.weather.R;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by zhangzhun on 15-11-13.
 */
public class Utils {

    private static boolean DEBUG = true;
    private static Context mContext = Application.getInstance();






    public static void copyToClipboard(View view,String info) {
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("msg", info);
        cm.setPrimaryClip(cd);
        Snackbar.make(view,R.string.notif_info_copied,Snackbar.LENGTH_LONG).show();
    }





}
