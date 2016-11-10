package com.bwf.qingdan.gui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwf.qingdan.R;
import com.bwf.qingdan.gui.fragment.RankingThingFragment;
import com.bwf.qingdan.utils.Contants;
import com.bwf.qingdan.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LG on 2016/11/8.
 * Tips:
 */

public class RepuationThingActivity extends ActivityBase {


    @BindView(R.id.textview_title_subview_title)
    TextView textviewTitleSubviewTitle;
    @BindView(R.id.imageView_back_subview_title)
    ImageView imageViewBackSubviewTitle;
    @BindView(R.id.search_edit_input)
    EditText searchEditInput;
    @BindView(R.id.img_search_delete)
    ImageView imgSearchDelete;
    @BindView(R.id.tv_text_search_cancel)
    TextView tvTextSearchCancel;
    @BindView(R.id.top_input)
    RelativeLayout topInput;
    @BindView(R.id.tab_list)
    RadioGroup tabList;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    public static final String RANKING_ID = "rankingId";
    private List<RankingThingFragment> fragments;
    private FragmentManager fragmentManager;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_repuation_thing;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        //监听EditText文本的变化
        searchEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    imgSearchDelete.setVisibility(View.GONE);
                }else{
                    imgSearchDelete.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchEditInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //去执行搜索
                    doSearch(searchEditInput.getText().toString());
                }
                return false;
            }
        });
        //监听RadioGroup
        tabList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tv_review_tab:
                        switchFragment(0);
                        break;
                    case R.id.tv_rating_tab:
                        switchFragment(1);
                        break;
                    case R.id.tv_brand_tab:
                        switchFragment(2);
                        break;
                }
            }
        });
    }
    /**
     * 执行搜索
     * @param
     */
    private void doSearch(String searchKey) {
        //让当前的Fragment去显示搜索的数据
        ((RankingThingFragment)lastFragment).showFragment(searchKey);
        this.searchKey = searchKey;
        isSearch = true;
    }

    private Fragment lastFragment;
    /**是否是搜索状态**/
    private boolean isSearch;
    /**搜索的关键字**/
    private String searchKey="";
    /**
     * 切换Fragment
     * @param index
     */
    private void switchFragment(int index) {
        RankingThingFragment fragment = fragments.get(index);
        if(fragment == lastFragment){
            //如果正在显示
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(fragment.isAdded()){//如果已经添加到Ａｃｔｉｖｉｔｙ中过来了
            fragment.showFragment(searchKey);
            transaction.show(fragment);
        }else{
            fragment.setSearchKey(searchKey);
            transaction.add(R.id.container,fragment);
        }
        //如果上一个fragment不为空,就隐藏掉
        if(lastFragment != null){
            transaction.hide(lastFragment);
        }
        transaction.commit();
        lastFragment = fragment; //把正要显示的Fragment变成 上一个Fraagmment
    }
    @Override
    protected void initDatas() {
        int rankingId = getIntent().getIntExtra(RANKING_ID,0);
        fragments = new ArrayList<>();
        fragments.add(RankingThingFragment.newInstance(rankingId, Contants.SORT_BY_REVIEW_COUNT));
        fragments.add(RankingThingFragment.newInstance(rankingId, Contants.SORT_BY_rating_score));
        fragments.add(RankingThingFragment.newInstance(rankingId, Contants.SORT_BY_BRAND_NAME));
        fragmentManager = getSupportFragmentManager();
        switchFragment(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.imageView_back_subview_title, R.id.search_edit_input, R.id.img_search_delete, R.id.tv_text_search_cancel,R.id.img_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_back_subview_title:
                break;
            case R.id.search_edit_input:
                tvTextSearchCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.img_search_delete:
                searchEditInput.getText().clear();
                break;
            case R.id.tv_text_search_cancel:
                //点击取消之后，让EditText清空、删除按钮消失、软键盘消失、Fragment中的数据也可能要变化
                tvTextSearchCancel.setVisibility(View.GONE);
                searchEditInput.getText().clear();
                KeyBoardUtils.closeKeybord(searchEditInput,this);
                doSearch("");
                break;
            case R.id.img_search:
                doSearch(searchEditInput.getText().toString());
                break;
        }
    }
}
