package cz.com.dosomething.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.com.dosomething.R;
import cz.com.dosomething.adapter.ViewPageAdapter;
import cz.com.dosomething.bean.Task;
import cz.com.dosomething.db.TaskDao;
import cz.com.dosomething.db.TaskInfoDao;
import cz.com.dosomething.fragment.BaseFragment;
import cz.com.dosomething.others.MyApplication;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.activity_welcome)
    DrawerLayout activityWelcome;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String TAG="WelcomeActivity";
    private List<Fragment> fraglist = new ArrayList<>();
    private List<String> titlelist = new ArrayList<>();
    private ViewPageAdapter adapter;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View v=findViewById(R.id.activity_welcome);
        v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.e("onLayoutChange", "onLayoutChange: " );
                    disappearRed();
                }
            }
        });
        setDate();
        initToolbar();
        initFragment();
        initViewPage();
        initTabs();
        setFab();

    }

    private void setDate() {
        TaskInfoDao taskinfoDao = MyApplication.getInstance().getDaoSession().getTaskInfoDao();
        TaskDao taskDao = MyApplication.getInstance().getDaoSession().getTaskDao();

        if (taskDao.loadAll().size()==0){
            String[] s={"家","公司","学校"};
            for (int i = 0; i < 3; i++) {
                Task t=new Task();
                t.setType(s[i]);
                t.setNum(i);
                taskDao.insert(t);
            }
        }

        taskList.addAll(taskDao.loadAll());
    }

    private void setFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeActivity.this,WriteTaskActivity.class);
                int i=tabs.getSelectedTabPosition();
                for (int j = 0; j <taskList.size() ; j++) {
                    Task t=taskList.get(j);
                 if (t.getNum()==i){
                     intent.putExtra("id",t.getId());
                 }
                }
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        WelcomeActivity.this,fab,"fab");

                 //ActivityCompat是android支持库中用来适应不同android版本的
                ActivityCompat.startActivity(WelcomeActivity.this, intent, activityOptions.toBundle());
                //startActivity(intent);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void disappearRed() {

        int cx = fab.getWidth() / 2;
        int cy = fab.getHeight() / 2;

        Animator animator = ViewAnimationUtils.createCircularReveal(fab, cx, cy,0, cy);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(200);
        animator.start();
    }


    private void initTabs() {
        tabs.setupWithViewPager(viewpager);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.black));
        for (int i = 0; i < fraglist.size(); i++) {
            tabs.getTabAt(i).setText(titlelist.get(i));
        }
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

    }

    private void initViewPage() {
        adapter = new ViewPageAdapter(getSupportFragmentManager(), fraglist);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
    }

    private void initFragment() {

        for (int i = 0; i < taskList.size(); i++) {
            BaseFragment basefragment = new BaseFragment();
            fraglist.add(basefragment);
            titlelist.add(taskList.get(i).getType());
            Bundle bundle = new Bundle();
            bundle.putLong("id",taskList.get(i).getId());
            basefragment.setArguments(bundle);
        }

    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.basetoolbar_menu);
        toolbar.setNavigationIcon(R.mipmap.drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
