package kr.saintdev.projectmna.views.staff.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.staff.adapters.MainViewPagerAdapter;
import kr.saintdev.projectmna.views.staff.fragments.main.HomeFragment;
import kr.saintdev.projectmna.views.staff.fragments.main.RequestJoinFragment;
import kr.saintdev.projectmna.views.staff.fragments.main.SettingsFragment;
import kr.saintdev.projectmna.views.staff.fragments.main.WorkLogFragment;

/**
 * Created by 5252b on 2018-05-04.
 */

public class StaffMainActivity extends AppCompatActivity {
    ViewPager contentPager = null;
    MainViewPagerAdapter viewAdapter = null;
    ImageButton[] buttons = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);   // 메인 화면을 띄웁니다.

        this.contentPager = findViewById(R.id.staff_main_container);
        this.buttons = new ImageButton[] {
                findViewById(R.id.staff_main_nav_home),     // 홈 화면
                findViewById(R.id.staff_main_nav_worklog),  // 근무 기록
                findViewById(R.id.staff_main_nav_reqjoin),  // 입사 요청
                findViewById(R.id.staff_main_nav_settings) // 설정
        };

        // Fragment 를 생성합니다.
        SuperFragment[] fragments = new SuperFragment[] {
                new HomeFragment(),
                new RequestJoinFragment(),
                new WorkLogFragment(),
                new SettingsFragment()
        };

        // adapter 을 생성합니다.
        this.viewAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        this.contentPager.setAdapter(this.viewAdapter);
    }

    class OnButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.staff_main_nav_home:
                    break;
                case R.id.staff_main_nav_worklog:
                    break;
                case R.id.staff_main_nav_reqjoin:
                    break;
                case R.id.staff_main_nav_settings:
                    break;
            }
        }
    }
}
