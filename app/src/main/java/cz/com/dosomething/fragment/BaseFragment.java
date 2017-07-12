package cz.com.dosomething.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.com.dosomething.R;
import cz.com.dosomething.adapter.RecyclerAdapter;
import cz.com.dosomething.bean.TaskInfo;

/**
 * Created by cz on 2017/6/28.
 */

public class BaseFragment extends Fragment {
    @BindView(R.id.rec_item)
    RecyclerView recItem;
   private List<TaskInfo>list=new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        ButterKnife.bind(this, view);
        initRec();

        return view;
    }

    private void initRec() {
        for (int i = 0; i < 10; i++) {
            TaskInfo task=new TaskInfo();
            task.setContent("吃饭");
            task.setTitle("对");
            list.add(task);
        }
        recyclerAdapter = new RecyclerAdapter(this.getContext(),list);
        recItem.setAdapter(recyclerAdapter);
        recItem.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

}
