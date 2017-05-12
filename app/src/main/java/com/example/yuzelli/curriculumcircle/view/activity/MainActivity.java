package com.example.yuzelli.curriculumcircle.view.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.yuzelli.curriculumcircle.Bean.Project;
import com.example.yuzelli.curriculumcircle.R;
import com.example.yuzelli.curriculumcircle.constants.ConstantsUtils;
import com.example.yuzelli.curriculumcircle.utils.SharePreferencesUtil;
import com.example.yuzelli.curriculumcircle.view.fragment.CurriculumFragment;
import com.example.yuzelli.curriculumcircle.view.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    //定义FragmentTabHost对象
    private FragmentTabHost tabHost;
    //定义一个布局
    private LayoutInflater layoutInflater;
    //定义数组来存放user的Fragment界面
    private Class fragmentArray[] = {CurriculumFragment.class, MineFragment.class};
    //定义数组来存放的按钮图片
    private int tabImageViewArray[] = {R.drawable.ic_ke,R.drawable.ic_mine_nol};
    //Tab选项卡的文字
    private String tabtTextViewArray[] = {"话题", "文章", "我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        List<Project> list = (List<Project>) SharePreferencesUtil.readObject(this, ConstantsUtils.PROJECT_LIST);
        if (list==null||list.size()==0){
            list = new ArrayList<>();
            list.add(new Project("Android","安卓开发技术","20课时","赵四"));
            list.add(new Project("Git","Git开发技术","30课时","钱风"));
            list.add(new Project("PHP","PHP开发技术","25课时","孙特"));
            list.add(new Project("Python","Python开发技术","24课时","李四"));
            list.add(new Project("Linux","Linux开发技术","28课时","周非"));
            list.add(new Project("IOS","IOS开发技术","40课时","单都分"));
            list.add(new Project("JAVAEE","JAVAEE开发技术","40课时","郑王"));
            list.add(new Project("SWIFT","SWIFT开发技术","50课时","王芬"));
            list.add(new Project("JS","JS开发技术","22课时","邵天"));
            list.add(new Project("JSP","JSP开发技术","26课时","李例"));
            list.add(new Project("HTML5","HTML5开发技术","34课时","宋江"));
            list.add(new Project("爬虫","爬虫开发技术","15课时","冯号"));
        }
        SharePreferencesUtil.saveObject(this,ConstantsUtils.PROJECT_LIST,list);
    }

    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_pageContent);



        //得到fragment的个数
        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabtTextViewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }
    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.main_tab_select_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_tabIcon);

        imageView.setImageResource(tabImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_tabText);
        textView.setText(tabtTextViewArray[index]);
        return view;
    }
}
