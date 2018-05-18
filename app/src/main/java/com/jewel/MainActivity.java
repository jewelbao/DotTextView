package com.jewel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.jewel.components.DotTextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private DotTextView dotTextView;
    private SeekBar seekBarLeftPadding, seekBarRightPadding, seekBarBottomPadding, seekBarTopPadding;
    private RadioButton cbRightTop;
    private SeekBar seekBarRadius;
    private CheckBox cbLeft, cbRight, cbTop, cbBottom;
    private SeekBar seekBarWidth, seekBarHeight;

    private static final int DRAWABLE_NONE = TextViewUtil.NONE_RES_ID;

    private int leftDrawableRes = DRAWABLE_NONE;
    private int rightDrawableRes = DRAWABLE_NONE;
    private int topDrawableRes = DRAWABLE_NONE;
    private int bottomDrawableRes = DRAWABLE_NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotTextView = findViewById(R.id.dot);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initGravityRadioButton();
        initDrawablesCheckBox();
        initPaddingSeekBar();
        initParamSeekBar();

        final EditText etContent = findViewById(R.id.et_content);
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (TextUtils.isEmpty(etContent.getText())) {
                    return false;
                }
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
                    dotTextView.setText(etContent.getText());
                    etContent.setText("");
                }
                return true;
            }
        });

        seekBarRadius = findViewById(R.id.seekBar_radius);
        seekBarRadius.setOnSeekBarChangeListener(this);
    }

    private void initGravityRadioButton() {
        RadioButton cbLeftTop = findViewById(R.id.cb_left_top);
        cbRightTop = findViewById(R.id.cb_right_top);
        RadioButton cbLeftBottom = findViewById(R.id.cb_left_bottom);
        RadioButton cbRightBottom = findViewById(R.id.cb_right_bottom);
        RadioButton cbLeftCenter = findViewById(R.id.cb_left_center);
        RadioButton cbRightCenter = findViewById(R.id.cb_right_center);
        RadioButton cbLeftDrawableCenter = findViewById(R.id.cb_left_drawable_center);
        RadioButton cbRightDrawableCenter = findViewById(R.id.cb_right_drawable_center);

        cbLeftTop.setOnCheckedChangeListener(this);
        cbRightTop.setOnCheckedChangeListener(this);
        cbLeftBottom.setOnCheckedChangeListener(this);
        cbRightBottom.setOnCheckedChangeListener(this);
        cbLeftCenter.setOnCheckedChangeListener(this);
        cbRightCenter.setOnCheckedChangeListener(this);
        cbLeftDrawableCenter.setOnCheckedChangeListener(this);
        cbRightDrawableCenter.setOnCheckedChangeListener(this);
    }

    private void initDrawablesCheckBox() {
        cbLeft = findViewById(R.id.cb_left);
        cbRight = findViewById(R.id.cb_right);
        cbTop = findViewById(R.id.cb_top);
        cbBottom = findViewById(R.id.cb_bottom);

        cbLeft.setOnCheckedChangeListener(this);
        cbRight.setOnCheckedChangeListener(this);
        cbTop.setOnCheckedChangeListener(this);
        cbBottom.setOnCheckedChangeListener(this);
    }

    private void initPaddingSeekBar() {
        seekBarLeftPadding = findViewById(R.id.seekBar_left);
        seekBarRightPadding = findViewById(R.id.seekBar_right);
        seekBarBottomPadding = findViewById(R.id.seekBar_bottom);
        seekBarTopPadding = findViewById(R.id.seekBar_top);

        seekBarLeftPadding.setOnSeekBarChangeListener(this);
        seekBarRightPadding.setOnSeekBarChangeListener(this);
        seekBarBottomPadding.setOnSeekBarChangeListener(this);
        seekBarTopPadding.setOnSeekBarChangeListener(this);
    }

    private void initParamSeekBar() {
        seekBarWidth = findViewById(R.id.seekBar_view_width);
        seekBarHeight = findViewById(R.id.seekBar_view_height);

        seekBarWidth.setMax(ScreenUtils.getScreenWidth());
        seekBarHeight.setMax(ScreenUtils.getScreenHeight() / 2);

        seekBarWidth.setProgress(ScreenUtils.getScreenWidth());
        seekBarHeight.setProgress(ScreenUtils.getScreenHeight() / 2);

        seekBarWidth.setOnSeekBarChangeListener(this);
        seekBarHeight.setOnSeekBarChangeListener(this);

        changeLayoutParams(ScreenUtils.getScreenHeight() / 2, false);
    }

    private void reset() {
        dotTextView.setText(R.string.contentMulti);

        dotTextView.setRefreshIImmediately(false);  // 关闭立即刷新开关，执行以下代码set**时就不会更新View

        cbRightTop.setChecked(true);
        seekBarLeftPadding.setProgress(0);
        seekBarRightPadding.setProgress(0);
        seekBarBottomPadding.setProgress(0);
        seekBarTopPadding.setProgress(0);
        seekBarRadius.setProgress(10);

        cbLeft.setChecked(false);
        cbRight.setChecked(false);
        cbTop.setChecked(false);
        cbBottom.setChecked(false);

        seekBarWidth.setProgress(ScreenUtils.getScreenWidth());
        seekBarHeight.setProgress(ScreenUtils.getScreenHeight() / 2);
        changeLayoutParams(ScreenUtils.getScreenHeight() / 2, false);

        dotTextView.setRefreshIImmediately(true); // 重新开启刷新开关
        dotTextView.refresh(); // 刷新
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        reset();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar_bottom:
                dotTextView.setDotPaddingBottom(progress);
                break;
            case R.id.seekBar_left:
                dotTextView.setDotPaddingLeft(progress);
                break;
            case R.id.seekBar_right:
                dotTextView.setDotPaddingRight(progress);
                break;
            case R.id.seekBar_top:
                dotTextView.setDotPaddingTop(progress);
                break;
            case R.id.seekBar_view_height:
                changeLayoutParams(progress, false);
                break;
            case R.id.seekBar_view_width:
                changeLayoutParams(progress, true);
                break;
            case R.id.seekBar_radius:
                dotTextView.setDotRadius(progress);
                break;
            default:
                break;
        }
    }

    private void changeLayoutParams(int changeSize, boolean isWidth) {
        ViewGroup.LayoutParams params = dotTextView.getLayoutParams();
        if (isWidth) {
            params.width = changeSize;
        } else {
            params.height = changeSize;
        }
        dotTextView.setLayoutParams(params);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView instanceof RadioButton && isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_left_bottom:
                    dotTextView.setDotGravity(DotTextView.LEFT_BOTTOM);
                    break;
                case R.id.cb_left_top:
                    dotTextView.setDotGravity(DotTextView.LEFT_TOP);
                    break;
                case R.id.cb_right_bottom:
                    dotTextView.setDotGravity(DotTextView.RIGHT_BOTTOM);
                    break;
                case R.id.cb_right_top:
                    dotTextView.setDotGravity(DotTextView.RIGHT_TOP);
                    break;
                case R.id.cb_left_center:
                    dotTextView.setDotGravity(DotTextView.LEFT_CENTER);
                    break;
                case R.id.cb_right_center:
                    dotTextView.setDotGravity(DotTextView.RIGHT_CENTER);
                    break;
                case R.id.cb_left_drawable_center:
                    dotTextView.setDotGravity(DotTextView.LEFT_DRAWABLE_CENTER);
                    break;
                case R.id.cb_right_drawable_center:
                    dotTextView.setDotGravity(DotTextView.RIGHT_DRAWABLE_CENTER);
                    break;
                default:
                    break;
            }
        }

        if (buttonView instanceof CheckBox) {
            switch (buttonView.getId()) {
                case R.id.cb_left:
                    leftDrawableRes = isChecked ? R.drawable.ic_notifications_active_black_24dp : DRAWABLE_NONE;
                    break;
                case R.id.cb_right:
                    rightDrawableRes = isChecked ? R.drawable.ic_navigate_next_black_24dp : DRAWABLE_NONE;
                    break;
                case R.id.cb_top:
                    topDrawableRes = isChecked ? R.drawable.ic_pan_tool_black_24dp : DRAWABLE_NONE;
                    break;
                case R.id.cb_bottom:
                    bottomDrawableRes = isChecked ? R.drawable.ic_system_update_alt_black_24dp : DRAWABLE_NONE;
                    break;
                default:
                    break;
            }
            TextViewUtil.setDrawables(dotTextView, leftDrawableRes, rightDrawableRes, topDrawableRes, bottomDrawableRes);
        }
    }
}
