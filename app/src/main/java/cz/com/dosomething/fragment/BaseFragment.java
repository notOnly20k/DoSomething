package cz.com.dosomething.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


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
    private Toolbar toolbar;
    private int ischeck=0;
    private TextView title;
    private List<Long>poslist=new ArrayList<>();
    private boolean checkmode=false;
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
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        title = (TextView) getActivity().findViewById(R.id.title);
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
        final TaskInfoDao taskInfodao=MyApplication.getInstance().getDaoSession().getTaskInfoDao();
        QueryBuilder qb = taskInfodao.queryBuilder();
        qb.where(TaskInfoDao.Properties.CustomerId.eq(id));
        final List<TaskInfo>lis=qb.list();
        if (lis.size() > 0) {
            imgBack.setVisibility(View.GONE);
            list=lis;
            recyclerAdapter = new RecyclerAdapter(this.getContext(), list);

            recItem.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position,Long id) {
                  if (view.getTag()=="ischeck"&&checkmode){
                      view.setTag(null);
                      view.setBackground(getResources().getDrawable(R.drawable.ripper));
                      ischeck-=1;
                      if (ischeck==0){
                          toolbar.setNavigationIcon(R.mipmap.drawer);
                          toolbar.getMenu().clear();
                          toolbar.inflateMenu(R.menu.basetoolbar_menu);
                          title.setText(R.string.app_name);
                          for (int i = 0; i < poslist.size(); i++) {
                              if (poslist.get(i)==id){
                                  poslist.remove(i);
                              }
                          }
                          return;
                      }
                      title.setText("已选定"+ischeck+"个");
                  }else if (checkmode){
                      view.setTag("ischeck");
                      view.setBackgroundColor(getResources().getColor(R.color.gray));
                      ischeck+=1;
                      poslist.add(id);
                      title.setText("已选定"+ischeck+"个");
                  }else {

                  }

                }
            });
            recyclerAdapter.setOnItemLongClickListener(new RecyclerAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, final int position, Long id) {
                    if (view.getTag()==null) {
                        checkmode=true;
                        view.setTag("ischeck");
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.longclick_menu);
                        view.setBackgroundColor(getResources().getColor(R.color.gray));
                        ischeck+=1;
                        poslist.add(id);
                        title.setText("已选定"+ischeck+"个");
                        toolbar.setNavigationIcon(R.mipmap.cancle);
                        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_switch:
                                        break;
                                    case R.id.action_delet:
                                        checkmode=false;
                                        for (int i = 0; i <list.size() ; i++) {
                                            for (int j = 0; j < poslist.size(); j++) {
                                                if (list.get(i).getId()==poslist.get(j)){
                                                    list.remove(i);
                                                    QueryBuilder qb = taskInfodao.queryBuilder();
                                                    qb.where(TaskInfoDao.Properties.Id.eq(poslist.get(j)));
                                                    TaskInfo taskInfo= (TaskInfo) qb.unique();
                                                    taskInfodao.delete(taskInfo);
                                                }
                                            }
                                        }
                                        recyclerAdapter.notifyAdapter(list,false);
                                        break;
                                }
                                return true;
                            }
                        });
                    }
                }
            });
            recItem.setItemAnimator(new SlideInUpAnimator());
            recItem.getItemAnimator().setAddDuration(5000);
            recItem.setAdapter(recyclerAdapter);
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
