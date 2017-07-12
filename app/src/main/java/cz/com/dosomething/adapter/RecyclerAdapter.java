package cz.com.dosomething.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.com.dosomething.R;
import cz.com.dosomething.bean.TaskInfo;

/**
 * Created by cz on 2017/6/28.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<TaskInfo> list;
    public RecyclerAdapter(Context context, List<TaskInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskInfo taskInfo=list.get(position);
        if (taskInfo.ischecked()){
            holder.check.isChecked();
        }
        holder.itemTitle.setText(taskInfo.getTitle());
        holder.content.setText(taskInfo.getContent());
        if (taskInfo.getTime()!=null){
            holder.time.setText((CharSequence) taskInfo.getTime());
        }else {
            holder.time.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.check)
        CheckBox check;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
