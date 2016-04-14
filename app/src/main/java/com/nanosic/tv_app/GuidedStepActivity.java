/*
 * Copyright (c) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nanosic.tv_app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist.Guidance;
import android.support.v17.leanback.widget.GuidedAction;
import android.util.Log;

import java.util.List;

/**
 * Activity that showcases different aspects of GuidedStepFragments.
 */
public class GuidedStepActivity extends Activity {

    private Device mDevice;

    private static final int EDIT = 0;
    private static final int DELETE = 1;
    private static final int OPTION_CHECK_SET_ID = 10;


    private static final String[] OPTION_BRANDS = {"海尔", "长虹", "小米", "乐事", "三星", "苹果"};
    private static final String[] OPTION_REMOTES = {"001", "002", "003", "004", "005"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDevice = (Device) getIntent().getSerializableExtra(Constants.EXTRA_DEVICE_CARD);
        if (null != mDevice && null == savedInstanceState) {
            if (mDevice.isSetUp()) {
                GuidedStepFragment.addAsRoot(this, new FirstStepFragment(), android.R.id.content);
            }else {
                GuidedStepFragment.addAsRoot(this, new SecondStepFragment(), android.R.id.content);
            }
        }
    }

    private static void addAction(List<GuidedAction> actions, long id, String title) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .build());
    }

    private static void addCheckedAction(List<GuidedAction> actions, int iconResId, Context context,
                                         String title, String desc, boolean checked) {
        GuidedAction guidedAction = new GuidedAction.Builder()
                .title(title)
                .description(desc)
                .checkSetId(OPTION_CHECK_SET_ID)
                .iconResourceId(iconResId, context)
                .build();
        guidedAction.setChecked(checked);
        actions.add(guidedAction);
    }

    public static class FirstStepFragment extends GuidedStepFragment {
        @Override
        public int onProvideTheme() {
            return R.style.Theme_Example_Leanback_GuidedStep_First;
        }

        @Override
        @NonNull
        public Guidance onCreateGuidance(@NonNull Bundle savedInstanceState) {
            String title = "请选择操作";
            String breadcrumb = "";
            String description = "";
            Drawable icon = getActivity().getDrawable(R.mipmap.ic_launcher);
            return new Guidance(title, description, breadcrumb, icon);
        }

        @Override
        public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, EDIT,
                    "编辑");
            addAction(actions, DELETE,
                    "删除");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            FragmentManager fm = getFragmentManager();
            if (action.getId() == EDIT) {
                GuidedStepFragment.add(fm, new SecondStepFragment());
            } else {
                getActivity().finishAfterTransition();
            }
        }
    }

    public static class SecondStepFragment extends GuidedStepFragment {

        @Override
        @NonNull
        public Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "请选择设备品牌";
            String breadcrumb = "配置向导";
            String description = "第1步";
            Drawable icon = getActivity().getDrawable(R.mipmap.ic_launcher);
            return new Guidance(title, description, breadcrumb, icon);
        }

        /*@Override
        public GuidanceStylist onCreateGuidanceStylist() {
            return new GuidanceStylist() {
                @Override
                public int onProvideLayoutId() {
                    return R.layout.guidedstep_second_guidance;
                }
            };
        }*/

        @Override
        public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {

            for (int i = 0; i < OPTION_BRANDS.length; i++) {
                addAction(actions, i, OPTION_BRANDS[i]);
            }
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            FragmentManager fm = getFragmentManager();
//            ThirdStepFragment next = ThirdStepFragment.newInstance(getSelectedActionPosition() - 1);
            ThirdStepFragment next = ThirdStepFragment.newInstance((int) action.getId());
            GuidedStepFragment.add(fm, next);
        }

    }

    public static class ThirdStepFragment extends GuidedStepFragment {
        private final static String ARG_OPTION_IDX = "arg.option.idx";

        public static ThirdStepFragment newInstance(final int option) {
            final ThirdStepFragment f = new ThirdStepFragment();
            final Bundle args = new Bundle();
            args.putInt(ARG_OPTION_IDX, option);
            f.setArguments(args);
            return f;
        }

        @Override
        @NonNull
        public Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "请选择" + OPTION_BRANDS[getArguments().getInt(ARG_OPTION_IDX)]+"设备遥控器";
            String breadcrumb = "配置向导";
            String description = "第2步";
            Drawable icon = getActivity().getDrawable(R.mipmap.ic_launcher);
            return new Guidance(title, description, breadcrumb, icon);
        }

        @Override
        public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
            /*addAction(actions, EDIT, "Done", "All finished");
            addAction(actions, DELETE, "Back", "Forgot something...");*/

            for (int i = 0; i < OPTION_REMOTES.length; i++) {
                addAction(actions, i, OPTION_REMOTES[i]);
            }
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            /*if (action.getId() == EDIT) {
                getActivity().finishAfterTransition();
            } else {
                getFragmentManager().popBackStack();
            }*/
            Log.d("ThirdFrag", "clicked");
            getActivity().finishAfterTransition();
        }

    }

}
