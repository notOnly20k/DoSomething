package cz.com.dosomething.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.com.dosomething.R;
import cz.com.dosomething.adapter.RecyclerAdapter;
import cz.com.dosomething.bean.MessageEvent;
import cz.com.dosomething.bean.TaskInfo;
import cz.com.dosomething.db.TaskDao;
import cz.com.dosomething.db.TaskInfoDao;
import cz.com.dosomething.others.MyApplication;

/**
 * Created by cz on 2017/6/28.
 */

public class BaseFragment extends Fragment {
    @BindView(R.id.rec_item)
    RecyclerView recItem;
    @BindView(R.id.fragment)
    LinearLayout fragment;
    @BindView(R.id.img_back)
    ImageView imgBack;
    private List<TaskInfo> list = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private long id;
    private TaskDao taskDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        ButterKnife.bind(this, view);
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getArguments();
        id = (bundle.getLong("id"));
        initRec();
        return view;
    }
    @Subscribe
    public void onMessageEvent(MessageEvent s) {
        if (s.getId()==id) {
          initRec();
        }
    }
    private void initRec() {
        TaskInfoDao taskInfodao=MyApplication.getInstance().getDaoSession().getTaskInfoDao();
        QueryBuilder qb = taskInfodao.queryBuilder();
        qb.where(TaskInfoDao.Properties.CustomerId.eq(id));
        List<TaskInfo>lis=qb.list();
        if (lis.size() > 0) {
            imgBack.setVisibility(View.GONE);
            list=lis;
            recyclerAdapter = new RecyclerAdapter(this.getContext(), list);
            recItem.setAdapter(recyclerAdapter);
            recItem.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
