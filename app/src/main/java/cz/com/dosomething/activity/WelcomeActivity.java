package cz.com.dosomething.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.com.dosomething.R;
import cz.com.dosomething.adapter.ViewPageAdapter;
import cz.com.dosomething.bean.Task;
import cz.com.dosomething.bean.TaskInfo;
import cz.com.dosomething.fragment.BaseFragment;

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
    private List<Fragment>fraglist=new ArrayList<>();
    private List<String>titlelist=new ArrayList<>();
    private ViewPageAdapter adapter;
    private List<Task>taskList=new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initTask();
        initToolbar();
       initFragment();
        initViewPage();
        initTabs();
    }

    private void initTask() {
        for (int i = 0; i <5 ; i++) {
            Task task=new Task();
            task.setType("任务"+i);
            task.setNum(i);
         List<TaskInfo>taskinfoList=new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                TaskInfo taskinfo=new TaskInfo();
                taskinfo.setContent("吃饭");
                taskinfo.setTitle("对");
                taskinfoList.add(taskinfo);
            }
            task.setList(taskinfoList);
            taskList.add(task);
        }

    }

    private void initTabs() {
        tabs.setupWithViewPager(viewpager);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.black));
        for (int i = 0; i <fraglist.size() ; i++) {
            tabs.getTabAt(i).setText(titlelist.get(i));
        }
    }

    private void initViewPage() {
        adapter=new ViewPageAdapter(getSupportFragmentManager(),fraglist);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
    }

    private void initFragment() {

        for (int i = 0; i < taskList.size(); i++) {
            BaseFragment basefragment=new BaseFragment();
            fraglist.add(basefragment);
            titlelist.add(taskList.get(i).getType());
            Bundle bundle = new Bundle();
            basefragment.setArguments(bundle);
        }

    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.basetoolbar_menu);
        title.setText(R.string.app_name);
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
