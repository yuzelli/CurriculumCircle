package com.example.yuzelli.curriculumcircle.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yuzelli.curriculumcircle.Bean.Project;
import com.example.yuzelli.curriculumcircle.R;
import com.example.yuzelli.curriculumcircle.constants.ConstantsUtils;
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

public class MineFragment extends Fragment {
    @BindView(R.id.lv_look)
    ListView lvLook;
    Unbinder unbinder;
    private View mineFragmentView;
    private List<Project> look;
 
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mineFragmentView = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, mineFragmentView);
        return mineFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        intView();
    }

    private void intView() {
        look = (List<Project>) SharePreferencesUtil.readObject(getActivity(), ConstantsUtils.PROJECT_look);
        if (look==null){
            look = new ArrayList<>();
        }
        lvLook.setAdapter(new CommonAdapter<Project>(getActivity(),look,R.layout.cell_project) {
            @Override
            public void convert(ViewHolder helper, Project item) {
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_content,"任课老师："+item.getTeacher());
                helper.setText(R.id.tv_time,item.getTime());
            }
        });
        lvLook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectDetialActivity.actionStart(getActivity(),look.get(position));
            }
        });
        lvLook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(look.get(position));
                return false;
            }
        });
    }

    private void showDialog(final Project project) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());// 构建
        builder.setTitle("提示框");
        builder.setMessage("你确定要删除么");
        // 添加确定按钮 listener事件是继承与DialogInerface的
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // 完成业务逻辑代码
                look.remove(project);
                SharePreferencesUtil.saveObject(getActivity(), ConstantsUtils.PROJECT_look,look);
                intView();
            }
        });
        // 添加取消按钮
        builder.setNegativeButton("取消删除",null);
        builder.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
