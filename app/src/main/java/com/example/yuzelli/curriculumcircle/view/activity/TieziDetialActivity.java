package com.example.yuzelli.curriculumcircle.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuzelli.curriculumcircle.Bean.TieZi;
import com.example.yuzelli.curriculumcircle.R;
import com.example.yuzelli.curriculumcircle.constants.ConstantsUtils;
import com.example.yuzelli.curriculumcircle.utils.CommonAdapter;
import com.example.yuzelli.curriculumcircle.utils.SharePreferencesUtil;
import com.example.yuzelli.curriculumcircle.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TieziDetialActivity extends Activity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_tiezi)
    ListView tvTiezi;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.activity_tiezi_detial)
    RelativeLayout activityTieziDetial;
    private TieZi t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiezi_detial);
        ButterKnife.bind(this);
        Intent intent = getIntent();
       t = (TieZi) intent.getSerializableExtra("tiezi");
        textView.setText("标题:"+t.getTitle());

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTe();

            }
        });
        updataView();
    }

    private void updataView() {
        List<String> list = (List<String>) SharePreferencesUtil.readObject(TieziDetialActivity.this,t.getTime()+ ConstantsUtils.PROJECT_HUIFU_tiezi);
        if (list==null){
            list = new ArrayList<>();
        }
        tvTiezi.setAdapter(new CommonAdapter<String>(this,list,R.layout.cell_tiezi) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.tv_time,item);

            }
        });
    }

    private void showDialogTe() {
        final Dialog dialog = new Dialog(this,R.style.PhotoDialog);
        final View view = LayoutInflater.from(TieziDetialActivity.this).inflate(R.layout.personal_head_select_diallog,null);
        dialog.setContentView(view);
        final EditText edit = (EditText) view.findViewById(R.id.et_input);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setText("回复：");
        view .findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getText().toString().trim();
                if (content.equals("")){
                    Toast.makeText(TieziDetialActivity.this,"请输入！",Toast.LENGTH_SHORT).show();
                }else {
                    Long time = System.currentTimeMillis();

                    List<String> list = (List<String>) SharePreferencesUtil.readObject(TieziDetialActivity.this,t.getTime()+ ConstantsUtils.PROJECT_HUIFU_tiezi);
                    if (list==null){
                        list = new ArrayList<>();
                    }
                    list.add(content);
                    SharePreferencesUtil.saveObject(TieziDetialActivity.this,t.getTime()+ ConstantsUtils.PROJECT_HUIFU_tiezi,list);
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

    public static void action(Context context,TieZi tiezi){
        Intent intent = new Intent(context,TieziDetialActivity.class);
        intent.putExtra("tiezi",tiezi);
        context.startActivity(intent);
    }
}
