package person.jack.plant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import person.jack.plant.R;
import person.jack.plant.ui.tabstrip.PagerSlidingTabStrip;

/**
 * 3.底部导航菜单，左右滑动切换界面
 */
public class MainPagerFragment extends Fragment {

    private static String[] TITLES;
    private static String[] URLS = new String[]{"", "", "", ""};

    private PagerSlidingTabStrip tabs;
    public ViewPager pager;

    public ViewPager getPager() {
        return pager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pager, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(1);
      tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TITLES = getResources().getStringArray(R.array.news_titles);

        FragmentPagerAdapter adapter = new NewsAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
    }

    class NewsAdapter extends FragmentPagerAdapter {
        Fragment curFragment;

        public Fragment getCurFragment() {
            return curFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            curFragment=(Fragment)object;
            super.setPrimaryItem(container, position, object);
        }

        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new DemoPtrFragment();
            }
            if (position == 1) {
               return new ValueSetFragment();
            }
            if (position == 2) {
                return new WarnFragment();
            }
            if(position==3){
               // return  new WaterFragment();
            }
            return HomeFragment.newInstance(URLS[position % URLS.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position % TITLES.length];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
