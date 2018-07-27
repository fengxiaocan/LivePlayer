package com.evil.liveplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.evil.liveplayer.util.AppUtils;

public class SettingFragment extends Fragment {

    private ViewHolder mViewHolder;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    )
    {
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        mViewHolder = new ViewHolder(view);
        mViewHolder.mTvVersion.setText(AppUtils.getVersionName());
        initSwitch();
        mViewHolder.mSwitchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean b) {
                if (b) {
                    Setting.PLAYER_TYPE = 0;
                    mViewHolder.mSwitchExo2.setChecked(false);
                    mViewHolder.mSwitchSys.setChecked(false);
                }
            }
        });
        mViewHolder.mSwitchExo2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean b) {
                if (b) {
                    Setting.PLAYER_TYPE = 1;
                    mViewHolder.mSwitchDefault.setChecked(false);
                    mViewHolder.mSwitchSys.setChecked(false);
                }
            }
        });
        mViewHolder.mSwitchSys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean b) {
                if (b) {
                    Setting.PLAYER_TYPE = 2;
                    mViewHolder.mSwitchDefault.setChecked(false);
                    mViewHolder.mSwitchExo2.setChecked(false);
                }
            }
        });
    }

    private void initSwitch() {
        mViewHolder.mSwitchDefault.setChecked(Setting.PLAYER_TYPE == 0);
        mViewHolder.mSwitchExo2.setChecked(Setting.PLAYER_TYPE == 1);
        mViewHolder.mSwitchSys.setChecked(Setting.PLAYER_TYPE == 2);
    }

    public static class ViewHolder {
        public View rootView;
        public Switch mSwitchDefault;
        public Switch mSwitchExo2;
        public Switch mSwitchSys;
        public TextView mTvLocal;
        public TextView mTvVersion;
        public TextView mTvAbout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mSwitchDefault = (Switch)rootView.findViewById(R.id.switch_default);
            this.mSwitchExo2 = (Switch)rootView.findViewById(R.id.switch_exo2);
            this.mSwitchSys = (Switch)rootView.findViewById(R.id.switch_sys);
            this.mTvLocal = (TextView)rootView.findViewById(R.id.tv_local);
            this.mTvVersion = (TextView)rootView.findViewById(R.id.tv_version);
            this.mTvAbout = (TextView)rootView.findViewById(R.id.tv_about);
        }
    }
}
