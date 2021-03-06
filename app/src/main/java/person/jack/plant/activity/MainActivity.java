
package person.jack.plant.activity;

import android.media.AsyncPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import person.jack.plant.R;
import person.jack.plant.fragment.BufferKnifeFragment;
import person.jack.plant.fragment.DemoPtrFragment;
import person.jack.plant.fragment.KnowLedgeFragment;
import person.jack.plant.fragment.MainPagerFragment;
import person.jack.plant.fragment.MemberFragment;
import person.jack.plant.fragment.PlantsAddFragment;
import person.jack.plant.ui.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 2.主界面，包括底部导航按钮  cl
 */
public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    public static int currIndex = 0;

    public RadioGroup group;

    private ArrayList<String> fragmentTags;
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        fragmentManager = getSupportFragmentManager();
        initData(savedInstanceState);
        initView();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("DemoPtrFragment", "ImFragment",
                "PlantAddFragment", "InterestFragment", "MemberFragment"));
        currIndex = 0;
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    /**
     * 初始化底部按钮
     */
    private void initView() {
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        currIndex = 0;
                        break;
                    case R.id.foot_bar_im:
                        currIndex = 1;
                        break;
                    case R.id.foot_bar_add:
                        currIndex = 2;
                        break;
                    case R.id.foot_bar_interest:
                        currIndex = 3;
                        break;
                    case R.id.main_footbar_user:
                        currIndex = 4;
                        break;
                    default:
                        break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    public void showFragment() {
        if (currIndex == 4) {
            if (!UIHelper.isLogin()) {
                //不让登录界面自动弹出
               // UIHelper.showLogin(MainActivity.this);
            }
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new DemoPtrFragment();
            case 1:
                return new BufferKnifeFragment();
            case 2:
                return new PlantsAddFragment();
            case 3:
                return new KnowLedgeFragment();
            case 4:
                return new MemberFragment();
            default:
                return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        BufferKnifeFragment bufferKnifeFragment1 = (BufferKnifeFragment) getSupportFragmentManager().findFragmentByTag("ImFragment");
        if (bufferKnifeFragment1 != null) {
            bufferKnifeFragment1.setLoadAll(false);
            bufferKnifeFragment1.loadData();

        }
        int result = getIntent().getIntExtra("result", 0);
        Log.d("result", result + "");
        if (result == 1) {
            currIndex = 1;
            group.check(R.id.foot_bar_im);
            showFragment();

        }
        if (result == 2) {
             currIndex = 2;
            group.check(R.id.foot_bar_add);
            showFragment();

        }
        if (result == 4) {
            currIndex = 4;
            group.check(R.id.main_footbar_user);
        }


        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
