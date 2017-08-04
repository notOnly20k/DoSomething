package cz.com.dosomething.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private Context context;
    private List<TaskInfo> list;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {

         TaskInfo taskInfo=list.get(position);
        if (taskInfo.ischecked()){
            holder.check.isChecked();
        }
        holder.itemTitle.setText(taskInfo.getTitle());
        holder.content.setText(taskInfo.getContent());
        if (TextUtils.isEmpty(taskInfo.getTitle())){
            holder.itemTitle.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(taskInfo.getContent())){
            holder.content.setVisibility(View.GONE);
        }
        if (taskInfo.getTime()!=null){
            holder.time.setText((CharSequence) taskInfo.getTime());
        }else {
            holder.time.setVisibility(View.GONE);
        }
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position,list.get(position).getId()); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position,list.get(position).getId());
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
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


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position, Long id);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position, Long id);
    }
    public void notifyAdapter(List<TaskInfo> myLiveList,boolean isAdd){
        if (!isAdd){
            this.list=myLiveList;
        }else {
            this.list.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }
}
