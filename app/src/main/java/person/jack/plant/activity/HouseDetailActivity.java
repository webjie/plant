package person.jack.plant.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import person.jack.plant.R;
import person.jack.plant.common.AppContext;
import person.jack.plant.ui.loopviewpager.AutoLoopViewPager;
import person.jack.plant.ui.swipebacklayout.SwipeBackActivity;
import person.jack.plant.ui.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 4.带图片的详细信息界面，暂未使用
 * git测试  李家旺
 *
 */
public class HouseDetailActivity extends SwipeBackActivity implements View.OnClickListener{

    @Bind(R.id.btnBack)
    Button btnBack;
    Button btnDelete;
    Button btnUpdate;
    @Bind(R.id.textHeadTitle)
    TextView textHeadTitle;
    @Bind(R.id.pager)
    AutoLoopViewPager pager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;

    private GalleryPagerAdapter galleryAdapter;
    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    void initView() {
        textHeadTitle.setText("世纪嘉园");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDelete=(Button)findViewById(R.id.btn_plantDelete);
        btnDelete.setOnClickListener(this);
        btnUpdate=(Button)findViewById(R.id.btn_plantUpdate);
                btnUpdate.setOnClickListener(this);
        imageViewIds = new int[] { R.drawable.house_background, R.drawable.house_background_1,
                R.drawable.house_background_2};

        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
    }

    private void initEvent() {
        Button btnShare = (Button) findViewById(R.id.btnShare);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 是否只有已登录用户才能打开分享选择页
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_plantUpdate:
                Toast.makeText(AppContext.getInstance(),"update",Toast.LENGTH_SHORT).show();;
                break;
            case R.id.btn_plantDelete:
                Toast.makeText(AppContext.getInstance(),"delete",Toast.LENGTH_SHORT).show();;
                break;
        }
    }


    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(HouseDetailActivity.this);
            item.setImageResource(imageViewIds[position]);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HouseDetailActivity.this, ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pager.stopAutoScroll();
    }
}
