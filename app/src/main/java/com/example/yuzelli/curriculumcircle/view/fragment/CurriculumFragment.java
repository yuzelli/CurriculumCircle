package com.example.yuzelli.curriculumcircle.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.yuzelli.curriculumcircle.constants.ConstantsUtils;
import com.example.yuzelli.curriculumcircle.Bean.Project;
import com.example.yuzelli.curriculumcircle.R;
import com.example.yuzelli.curriculumcircle.utils.CommonAdapter;
import com.example.yuzelli.curriculumcircle.utils.SharePreferencesUtil;
import com.example.yuzelli.curriculumcircle.utils.ViewHolder;
import com.example.yuzelli.curriculumcircle.view.activity.ProjectDetialActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 51644 on 2017/5/9.
 */

public class CurriculumFragment extends Fragment {
    @BindView(R.id.lv_project)
    ListView lvProject;
    Unbinder unbinder;
    private View curriculumFragmentView;
    private List<Project> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        curriculumFragmentView = inflater.inflate(R.layout.fragment_crtt, null);
        unbinder = ButterKnife.bind(this, curriculumFragmentView);

        return curriculumFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        list = (List<Project>) SharePreferencesUtil.readObject(getActivity(),ConstantsUtils.PROJECT_LIST);
       if (list==null){
           list = new ArrayList<>();
       }
        lvProject.setAdapter(new CommonAdapter<Project>(getActivity(),list,R.layout.cell_project) {
            @Override
            public void convert(ViewHolder helper, Project item) {
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_content,"任课老师："+item.getTeacher());
                helper.setText(R.id.tv_time,item.getTime());
            }
        });
        lvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectDetialActivity.actionStart(getActivity(),list.get(position));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
