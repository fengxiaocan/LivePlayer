package com.evil.liveplayer;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.evil.helper.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.helper.recycler.holder.ComRecyclerViewHolder;

public class LiveAdapter extends ComRecyclerViewAdapter<LiveInfo,LiveAdapter.ViewHolder> {
    @Override
    public boolean attachParent() {
        return false;
    }

    @Override
    public ViewHolder createViewHolder(View view,int i) {
        return new ViewHolder(view);
    }

    @Override
    public int onCreateLayoutRes(int i) {
        return R.layout.holder_live_adapter;
    }

    static class ViewHolder extends ComRecyclerViewHolder<LiveInfo> {
        public TextView mTvName;

        public ViewHolder(View rootView) {
            super(rootView);
        }

        @Override
        public void initView(View view) {
            this.mTvName = (TextView)view.findViewById(R.id.tv_name);
        }

        @Override
        public void setData(
                ComRecyclerViewAdapter adapter,LiveInfo liveInfo,int i
        )
        {
            try {
                mTvName.setText("📺" + liveInfo.getName());
            } catch (Exception e) {
                mTvName.setText(liveInfo.getName());
            }
            mTvName.setOnClickListener(new TOnClickListener<LiveInfo>(liveInfo){
                @Override
                public void onClick(View view) {
                    LiveInfo info = getData();
                    Intent intent = new Intent(getContext(),LiveActivity.class);
                    intent.putExtra("name",info.getName());
                    intent.putExtra("url",info.getUrl());
                    getContext().startActivity(intent);
                }
            });
        }
    }

    public static class TOnClickListener<T> implements View.OnClickListener {
        private T data;

        public TOnClickListener(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
