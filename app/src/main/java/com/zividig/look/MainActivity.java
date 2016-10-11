package com.zividig.look;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.zividig.look.activity.AboutActivity;
import com.zividig.look.activity.BaseActivity;
import com.zividig.look.fragment.MeiZiFragment;
import com.zividig.look.fragment.WangYiFragment;
import com.zividig.look.fragment.ZhiHuFragment;
import com.zividig.look.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zividig.look.R.id.drawer;
import static com.zividig.look.R.id.toolbar;

public class MainActivity extends BaseActivity {

    MenuItem currentMenuItem;
    Fragment currentFragment;

    @BindView(R.id.main_container)
    LinearLayout mContainer;
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(drawer)
    DrawerLayout mDrawer;

    int navigationId;

    SimpleArrayMap<Integer, String> mTitleArryMap = new SimpleArrayMap<>();

    int mainColor;
    long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);

        addFragmentsAndTitle();

        mDrawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        StatusBarUtil.setColorForDrawerLayout(this, mDrawer, getResources().getColor(R.color.colorPrimaryDark));

        if (savedInstanceState == null) {
            navigationId = SharePreferenceUtil.getNavigationItem(this);
            if (navigationId != -1) {
                currentMenuItem = mNavView.getMenu().findItem(navigationId);
            }
            if (currentMenuItem == null) {
                currentMenuItem = mNavView.getMenu().findItem(R.id.zhuhu_item);
            }
            if (currentMenuItem != null) {
                currentMenuItem.setChecked(true);
                Fragment fragment = getFragmentById(currentMenuItem.getItemId());
                String title = mTitleArryMap.get((Integer) currentMenuItem.getItemId());
                if (fragment != null) {
                    switchFragment(fragment, title);
                }
            }
        } else {
            if (currentMenuItem != null) {
                Fragment fragment = getFragmentById(currentMenuItem.getItemId());
                String title = mTitleArryMap.get((Integer) currentMenuItem.getItemId());
                if (fragment != null) {
                    switchFragment(fragment, title);
                }
            } else {
                switchFragment(new ZhiHuFragment(), " ");
                currentMenuItem = mNavView.getMenu().findItem(R.id.zhuhu_item);

            }
        }

        //侧边导航栏的监听
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (currentMenuItem != item && currentMenuItem != null) {
                    currentMenuItem.setChecked(false);
                    int id = item.getItemId();
                    SharePreferenceUtil.putNavigationItem(MainActivity.this, id);
                    currentMenuItem = item;
                    currentMenuItem.setChecked(true);
                    switchFragment(getFragmentById(currentMenuItem.getItemId()), mTitleArryMap.get(currentMenuItem.getItemId()));
                }
                mDrawer.closeDrawer(GravityCompat.START, true);
                return true;
            }
        });

    }

    private Fragment getFragmentById(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.zhuhu_item:
                fragment = new ZhiHuFragment();
                break;
            case R.id.wangyi_item:
                fragment=new WangYiFragment();
                break;
            case R.id.meizi_item:
                fragment=new MeiZiFragment();
                break;

        }
        return fragment;
    }

    private void addFragmentsAndTitle() {
        mTitleArryMap.put(R.id.zhuhu_item, getResources().getString(R.string.zhihu_title));
        mTitleArryMap.put(R.id.wangyi_item, getResources().getString(R.string.wangyi_title));
        mTitleArryMap.put(R.id.meizi_item, getResources().getString(R.string.meizi_title));

    }

    private void switchFragment(Fragment fragment,String title){
        if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName()))
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment)
                    .commit();
        currentFragment = fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 菜单选项的监听
     */
    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_about:
                    System.out.println("关于");
                    goAboutActivity();
                    break;
                case R.id.menu_open:
                    System.out.println("打开");
                    mDrawer.openDrawer(GravityCompat.START);
                    break;
            }
            return true;
        }
    };

    private  void goAboutActivity(){
        Intent intent=new Intent(this, AboutActivity.class);
        this.startActivity(intent);
    }
}
