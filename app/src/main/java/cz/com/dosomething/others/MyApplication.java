package cz.com.dosomething.others;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.Stack;

/**
 * Created by cz on 2017/6/11.
 */

public class MyApplication extends Application{
    private static Stack<Activity> activityStack;
    private static MyApplication singleton;
    private static  final String TAG="MyApplication";
    // Returns the application instance
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        Log.e(TAG, "App ");
    }
    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity){
        if(activityStack ==null){
            activityStack =new Stack<Activity>();
        }
        activityStack.add(activity);
        Log.e(TAG, "addActivity: "+activityStack.size());
    }
    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
        Log.e(TAG, "finishActivity: "+activityStack.size());
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {

        try {
            finishAllActivity();
        } catch (Exception e) {
        }
        Log.e(TAG, "AppExit: "+activityStack.size());
    }
}
