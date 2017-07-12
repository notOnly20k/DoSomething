package cz.com.dosomething.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.com.dosomething.R;

/**
 * Created by cz on 2017/6/28.
 */

public class BaseFragment extends Fragment {
    @BindView(R.id.rec_item)
    RecyclerView recItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
