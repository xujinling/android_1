package com.android.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;



public class DataAdapter extends BaseAdapter {

    private List<InfoBean> listBean;
    private LayoutInflater layoutInflater;

    public DataAdapter(List<InfoBean> listBean,Context context){
        this.listBean=listBean;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int i) {
        return listBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            view=layoutInflater.inflate(R.layout.item_layout,null); //加载布局
            viewHolder.title= (TextView) view.findViewById(R.id.title);
            viewHolder.money= (TextView) view.findViewById(R.id.money);
            viewHolder.time= (TextView) view.findViewById(R.id.time);
            view.setTag(viewHolder);        //将view与viewHolder关联
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(listBean.get(i).getTitle());
        viewHolder.money.setText(listBean.get(i).getMoney());
        viewHolder.time.setText(listBean.get(i).getTime());
        return view;
    }

    class ViewHolder{
        public TextView title;
        public TextView money;
        public TextView time;
    }

}
