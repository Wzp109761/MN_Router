package com.wzp109761.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 中间人
 */
public class ARouter {
    public static ARouter aRouter = new ARouter();
    //上下文
    private Context context;
    private HashMap<String, Class<? extends Activity>> map;

    private ARouter() {
        map = new HashMap<>();
    }

    public static ARouter getInstance() {
        return aRouter;
    }
    public void init(Context context){
        this.context=context;
        List<String> classNames = getClassName("com.wzp109761.util");
        for (String className : classNames) {
            try {
                Class<?> utilClass = Class.forName(className);
                if(IRouter.class.isAssignableFrom(utilClass)){
                    IRouter iRouter=(IRouter) utilClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 将类对象添加进路由表
     *
     * @param key
     * @param clazz
     */
    public void addActivity(String key, Class<? extends Activity> clazz) {
        if (key != null && clazz != null && !map.containsKey(key)) {
            map.put(key, clazz);
        }
    }

    public void jumpActivity(String key, Bundle bundle){
        Class<? extends Activity> activityClass=map.get(key);
        if(activityClass!=null){
            Intent intent=new Intent(context,activityClass);
            if(bundle!=null){
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }


    /**
     * 获取class集合
     * @param packageName
     * @return
     */
    private List<String> getClassName(String packageName){
        List<String> classList=new ArrayList<>();
        try {
            //把当前所有的apk存储路径给 dexFile
            DexFile df=new DexFile(context.getPackageCodePath());
            Enumeration<String> entries=df.entries();
            while (entries.hasMoreElements()){
                String className=(String) entries.nextElement();
                if(className.contains(packageName)){
                    classList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classList;
    }
}
