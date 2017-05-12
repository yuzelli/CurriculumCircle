package com.example.yuzelli.curriculumcircle.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuzelli.curriculumcircle.Bean.Project;
import com.example.yuzelli.curriculumcircle.Bean.TieZi;
import com.example.yuzelli.curriculumcircle.R;
import com.example.yuzelli.curriculumcircle.constants.ConstantsUtils;
import com.example.yuzelli.curriculumcircle.utils.CommonAdapter;
import com.example.yuzelli.curriculumcircle.utils.DateUtils;
import com.example.yuzelli.curriculumcircle.utils.SharePreferencesUtil;
import com.example.yuzelli.curriculumcircle.utils.ShareUtil;
import com.example.yuzelli.curriculumcircle.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectDetialActivity extends Activity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.lv_tiezi)
    ListView lvTiezi;
    @BindView(R.id.tv_shared)
    Button btnShared;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_look)
    Button btnLook;
    @BindView(R.id.activity_project_detial)
    RelativeLayout activityProjectDetial;
    private Context context;
    @OnClick({R.id.tv_shared,R.id.btn_add,R.id.btn_look})
    public void onviewClick(View v){
        switch (v.getId()){
            case R.id.tv_shared:
                doShard();
                break;
            case R.id.btn_add:
                doAdd();
                break;
            case R.id.btn_look:

                doLook();
                break;
        }
    }

    private void doAdd() {
        showDialogTiezi();
    }

    private void showDialogTiezi() {
        final Dialog dialog = new Dialog(this,R.style.PhotoDialog);
        final View view = LayoutInflater.from(ProjectDetialActivity.this).inflate(R.layout.personal_head_select_diallog,null);
        dialog.setContentView(view);
        final EditText edit = (EditText) view.findViewById(R.id.et_input);

       view .findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getText().toString().trim();
                if (content.equals("")){
                    Toast.makeText(ProjectDetialActivity.this,"请输入！",Toast.LENGTH_SHORT).show();
                }else {
                    Long time = System.currentTimeMillis();
                    List<TieZi> list;

                    list = (List<TieZi>) SharePreferencesUtil.readObject(ProjectDetialActivity.this,project.getName()+ConstantsUtils.PROJECT_HUIFU);
                    if (list==null){
                        list = new ArrayList<>();
                    }
                    list.add(new TieZi(time,content));
                    SharePreferencesUtil.saveObject(ProjectDetialActivity.this,project.getName()+ConstantsUtils.PROJECT_HUIFU,list);
                    updataView();
                    dialog.dismiss();
                }
            }
        });
        view .findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void doLook() {
        List<Project> look = (List<Project>) SharePreferencesUtil.readObject(this, ConstantsUtils.PROJECT_look);
        if (look==null){
            look = new ArrayList<>();
        }
        Toast.makeText(context,"已关注！",Toast.LENGTH_SHORT).show();

        for (Project p :look){
            if (p.getName().equals(project.getName())){

                return;
            }
        }
        look.add(project);
        SharePreferencesUtil.saveObject(this,ConstantsUtils.PROJECT_look,look);

    }

    private void doShard() {
        ShareUtil share = new ShareUtil(this);
        share.shareText(null, null, project.getTeacher()+":"+project.getName(), "课程圈", "分享课程");
    }
private Project project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detial);
        ButterKnife.bind(this);
        context = this;
        Intent intent = getIntent();
        project = (Project) intent.getSerializableExtra("project");
        updataView();
    }
  private   List<TieZi> list ;
    private void updataView() {
        tvName.setText(project.getName());
        tvTime.setText(project.getTime());
        tvContent.setText(project.getContent());
        tvTeacher.setText(project.getTeacher());

        list = (List<TieZi>) SharePreferencesUtil.readObject(this,project.getName()+ConstantsUtils.PROJECT_HUIFU);
        if (list==null){
            list = new ArrayList<>();
        }
        lvTiezi.setAdapter(new CommonAdapter<TieZi>(this,list,R.layout.cell_tiezi) {
            @Override
            public void convert(ViewHolder helper, TieZi item) {
                helper.setText(R.id.tv_time, "发布时间："+DateUtils.converTime(item.getTime()));
                helper.setText(R.id.tv_content,"标题："+item.getTitle());
            }
        });
        lvTiezi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     TieziDetialActivity.action(ProjectDetialActivity.this,list.get(position));
            }
        });

    }

    public static void actionStart(Context context, Project project) {
        Intent intent = new Intent(context, ProjectDetialActivity.class);
        intent.putExtra("project", project);
        context.startActivity(intent);
    }
}
