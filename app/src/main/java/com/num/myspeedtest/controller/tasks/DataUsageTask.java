package com.num.myspeedtest.controller.tasks;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.num.myspeedtest.controller.utils.DataUsageUtil;
import com.num.myspeedtest.db.datasource.DataUsageDataSource;
import com.num.myspeedtest.model.Application;
import com.num.myspeedtest.model.Usage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUsageTask implements Runnable {

    private Context context;
    private Handler handler;

    public DataUsageTask(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        List<Application> applications = DataUsageUtil.getApplications(context);
        List<Application> activeApplications = new ArrayList<>();

        DataUsageDataSource db = new DataUsageDataSource(context);
        db.open();

        for(Application app : applications) {
            Application tmp = (Application) db.insertBaseModelandReturn(app);
            if(tmp.getTotal() > 0) {
                activeApplications.add(tmp);
                Usage.totalRecv += tmp.getTotalRecv();
                Usage.totalSent += tmp.getTotalSent();
                if(tmp.getTotal() > Usage.maxUsage) {
                    Usage.maxUsage = tmp.getTotal();
                }
            }
        }
        Collections.sort(activeApplications);
        Usage usage = new Usage(activeApplications);

        Bundle bundle = new Bundle();
        bundle.putString("type", "usage");
        bundle.putParcelable("usage", usage);

        Message msg = new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);

    }
}