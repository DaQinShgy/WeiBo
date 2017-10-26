package com.zy.weibo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private NavigationView navigationView;

    private List<TextView> textList = new ArrayList<>();

    private TextView tv1;

    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv1 = (TextView) findViewById(R.id.tab1);
        tv2 = (TextView) findViewById(R.id.tab2);

        tv1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
        tv1.setTextSize(20);
        tv2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.gray));
        tv2.setTextSize(17);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText("页面" + (i + 1));
            textView.setGravity(Gravity.CENTER);
            textList.add(textView);
        }

        viewPager.setAdapter(
                new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return 2;
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        TextView textView = textList.get(position);
                        container.addView(textView);
                        return textView;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView(textList.get(position));
                    }
                }

        );

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // position :当前页面，及你点击滑动的页面；positionOffset:当前页面偏移的百分比；positionOffsetPixels:当前页面偏移的像素位置
                Log.e("onPageScrolled", "====" + positionOffset);
                navigationView.setCurrentCount(positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                //当前选中的页面
                Log.e("onPageSelected", "====" + position);
                navigationView.setPageSelected(position);
                if (position == 0) {
                    tv1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                    tv1.setTextSize(20);
                    tv2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.gray));
                    tv2.setTextSize(17);
                } else {
                    tv2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                    tv2.setTextSize(20);
                    tv1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.gray));
                    tv1.setTextSize(17);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //1表示正在滑动，2表示滑动完毕了，0表示什么都没做。
            }
        });
    }

}
