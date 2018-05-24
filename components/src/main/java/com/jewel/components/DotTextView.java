package com.jewel.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 圆角红点Text
 *
 * @author Jewel
 * @version 1.0
 * @since 2018/05/16
 */
public class DotTextView extends android.support.v7.widget.AppCompatTextView {

    /**
     * 默认圆点半径
     */
    public static final int DEFAULT_RADIUS = 10;

    private Paint dotPaint;
    private Rect textRect;
    /**
     * 圆点颜色
     */
    private int dotColor;
    /**
     * 圆点X坐标偏移量
     */
    private int dotOffsetX;
    /**
     * 圆点Y坐标偏移量
     */
    private int dotOffsetY;

    /**
     * 圆点显示位置
     */
    @DotGravity
    private int dotGravity;

    /**
     * 圆点半径
     */
    private int dotRadius;
    /**
     * 是否显示圆点
     */
    private boolean isShowDot;
    /**
     * 是否开启立即刷新UI
     */
    private boolean refreshIImmediately = true;

    private Paint debugPaint;
    private boolean isDebug;

    public DotTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotTextView);

            dotColor = typedArray.getColor(R.styleable.DotTextView_dotColor, Color.RED);
            dotOffsetX = typedArray.getDimensionPixelSize(R.styleable.DotTextView_dotOffsetX, 0);
            dotOffsetY = typedArray.getDimensionPixelSize(R.styleable.DotTextView_dotOffsetY, 0);
            dotGravity = typedArray.getInteger(R.styleable.DotTextView_dotGravity, RIGHT_TOP);
            dotRadius = typedArray.getInt(R.styleable.DotTextView_dotRadius, DEFAULT_RADIUS);
            isShowDot = typedArray.getBoolean(R.styleable.DotTextView_showDot, true);
            isDebug = typedArray.getBoolean(R.styleable.DotTextView_isDebug, false);

            typedArray.recycle();
        }

        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(dotColor);
        dotPaint.setStyle(Paint.Style.FILL);

        textRect = new Rect();

        if (isDebug) {
            debugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            debugPaint.setColor(dotColor);
            debugPaint.setTextSize(30);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(getText())) {
            return;
        }

        if(!isShowDot) {
            return;
        }

        final Layout textLayout = getLayout();
        final float contentTextLeftX = textLayout.getPrimaryHorizontal(0);  //
        final float contentTextRightX = contentTextLeftX + textLayout.getLineWidth(0);

        getPaint().getTextBounds(getText().toString(), 0, getText().length(), textRect); // 获取View本身文本的Paint对象，再得到文本的Rect对象

        final float circleX;
        final float circleY;

        switch (dotGravity) {
            case LEFT_TOP:
                circleX = contentTextLeftX + dotOffsetX + getTotalPaddingStart();
                circleY = getTotalPaddingTop() + dotOffsetY;
                break;
            case RIGHT_TOP:
                circleX = contentTextRightX + dotOffsetX + getTotalPaddingStart();
                circleY = getTotalPaddingTop() + dotOffsetY;
                break;
            case LEFT_BOTTOM:
                circleX = contentTextLeftX + dotOffsetX + getTotalPaddingStart();
                circleY = getTotalPaddingTop() + getLineHeight() * textLayout.getLineCount() + dotOffsetY; // 总内间距 + 每行文字高度 * 文字行数 + 圆点上边距
                break;
            case RIGHT_BOTTOM:
                circleX = contentTextRightX + dotOffsetX + getTotalPaddingStart();
                circleY = getTotalPaddingTop() + getLineHeight() * textLayout.getLineCount() + dotOffsetY;  // 同 LEFT_BOTTOM
                break;
            case LEFT_CENTER:
                circleX = contentTextLeftX + dotOffsetX + getTotalPaddingStart();
                circleY = getTotalPaddingTop() + (getLineHeight() * textLayout.getLineCount()) / 2 + dotOffsetY;
                break;
            case RIGHT_CENTER:
                circleX = contentTextRightX + dotOffsetX + getTotalPaddingStart();
                circleY = getTotalPaddingTop() + (getLineHeight() * textLayout.getLineCount()) / 2 + dotOffsetY;
                break;
            case LEFT_DRAWABLE_CENTER:
                if (getCompoundDrawables()[0] == null) {
                    circleX = contentTextLeftX + dotOffsetX + getTotalPaddingStart();
                } else {
                    circleX = contentTextLeftX + dotOffsetX;
                }
                circleY = getTotalPaddingTop() + (getLineHeight() * textLayout.getLineCount()) / 2 + dotOffsetY;
                break;
            case RIGHT_DRAWABLE_CENTER:
                if (getCompoundDrawables()[2] == null) {
                    circleX = contentTextRightX + dotOffsetX + getTotalPaddingStart();
                } else {
                    circleX = contentTextRightX + dotOffsetX + getTotalPaddingEnd() + getTotalPaddingStart();
                }
                circleY = getTotalPaddingTop() + (getLineHeight() * textLayout.getLineCount()) / 2 + dotOffsetY;
                break;
            default:
                circleX = 0;
                circleY = 0;
                break;
        }

        canvas.drawCircle(circleX, circleY, dotRadius, dotPaint); // 画圆点

        if (isDebug) {
            final int textWidth = textRect.width();
            final int textHeight = textRect.height();

            final String textAttr = String.format("[(文字总宽度=%s, 高度=%s, 行数=%s)]", textWidth, textHeight, textLayout.getLineCount());
            canvas.drawText(textAttr, 0, textHeight, debugPaint);
            final String viewAttr = String.format("[(DotTextView宽度=%s, 高度=%s)]", getWidth(), getHeight());
            canvas.drawText(viewAttr, 0, textHeight * 2, debugPaint);
            final String appAttr = String.format("[(每行文字高度(包含文字间距)=%s)]", getLineHeight());
            canvas.drawText(appAttr, 0, textHeight * 3, debugPaint);
            final String drawableAttr = String.format("[(左图标间距=%s，右图标间距=%s，上图标间距=%s，下图标间距=%s)]", getCompoundPaddingStart(), getCompoundPaddingEnd(), getCompoundPaddingTop(), getCompoundPaddingBottom());
            canvas.drawText(drawableAttr, 0, textHeight * 4, debugPaint);
        }
    }

    /**
     * 设置是否立即刷新View.
     *
     * @param refreshIImmediately 默认true
     */
    public void setRefreshIImmediately(boolean refreshIImmediately) {
        this.refreshIImmediately = refreshIImmediately;
    }

    /**
     * 设置圆点颜色
     *
     * @param dotColor 默认{@link Color#RED}
     */
    public void setDotColor(@ColorInt int dotColor) {
        this.dotColor = dotColor;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 圆点左内边距，调用此方法后，此前设置的{@link #setDotPaddingRight(int)}将无效。
     *
     * @param dotPaddingLeft 左内边距.
     * @see #setDotOffsetX(int)
     */
    public void setDotPaddingLeft(int dotPaddingLeft) {
        this.dotOffsetX = dotPaddingLeft;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 圆点右内边距，调用此方法后，此前设置的{@link #setDotPaddingLeft(int)}将无效。
     *
     * @param dotPaddingRight 右内边距.
     * @see #setDotOffsetX(int)
     */
    public void setDotPaddingRight(int dotPaddingRight) {
        this.dotOffsetX = -dotPaddingRight;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 圆点上内边距，调用此方法后，此前设置的{@link #setDotPaddingBottom(int)}将无效。
     *
     * @param dotPaddingTop 上内边距
     * @see #setDotOffsetY(int)
     */
    public void setDotPaddingTop(int dotPaddingTop) {
        this.dotOffsetY = dotPaddingTop;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 圆点下内边距，调用此方法后，此前设置的{@link #setDotPaddingTop(int)}将无效。
     *
     * @param dotPaddingBottom 下内边距
     * @see #setDotOffsetY(int)
     */
    public void setDotPaddingBottom(int dotPaddingBottom) {
        this.dotOffsetY = -dotPaddingBottom;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 设置圆点X轴上的偏移量
     *
     * @param dotOffsetX 偏移量，以圆点中心位置为原点（0,0），往左偏移为负值，反之。
     * @see #setDotPaddingLeft(int)
     * @see #setDotPaddingRight(int)
     */
    public void setDotOffsetX(int dotOffsetX) {
        this.dotOffsetX = dotOffsetX;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 设置圆点Y轴上的偏移量.
     *
     * @param dotOffsetY 偏移量，以圆点中心位置为原点（0,0），往上偏移为负值，反之。
     * @see #setDotPaddingTop(int)
     * @see #setDotPaddingBottom(int)
     */
    public void setDotOffsetY(int dotOffsetY) {
        this.dotOffsetY = dotOffsetY;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 圆点位置
     *
     * @param dotGravity 默认为{@link #RIGHT_TOP}
     */
    public void setDotGravity(@DotGravity int dotGravity) {
        this.dotGravity = dotGravity;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 设置圆角半径,默认半径为10
     *
     * @param dotRadius 半径
     */
    public void setDotRadius(int dotRadius) {
        this.dotRadius = dotRadius;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * debug模式会在整个TextView的左上角显示测试数据, 默认false
     *
     * @param debug 默认false
     */
    public void setDebug(boolean debug) {
        isDebug = debug;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 立刻刷新View，实际上调用了{@link #postInvalidate()}
     */
    public void refresh() {
        postInvalidate();
    }

    /**
     * 是否显示圆点
     * @param showDot 默认true
     */
    public void setShowDot(boolean showDot) {
        isShowDot = showDot;
        if (refreshIImmediately) postInvalidate();
    }

    /**
     * 小原点的位置
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM, LEFT_CENTER, RIGHT_CENTER, LEFT_DRAWABLE_CENTER, RIGHT_DRAWABLE_CENTER})
    private @interface DotGravity {

    }

    /**
     * 小圆点处于文本左上角位置
     */
    public static final byte LEFT_TOP = 1;
    /**
     * 小圆点处于文本右上角位置
     */
    public static final byte RIGHT_TOP = 2;
    /**
     * 小圆点处于文本左下角位置
     */
    public static final byte LEFT_BOTTOM = 3;
    /**
     * 小圆点处于文本右下角位置
     */
    public static final byte RIGHT_BOTTOM = 4;
    /**
     * 小圆点处于文本左居中位置
     */
    public static final byte LEFT_CENTER = 5;
    /**
     * 小圆点处于文本右居中位置
     */
    public static final byte RIGHT_CENTER = 6;
    /**
     * 小圆点处于左图标居中位置，设置了{@link android.support.v7.widget.AppCompatTextView#setCompoundDrawables(Drawable, Drawable, Drawable, Drawable)}[第一个参数有效]生效。
     * 否则按{@linkplain #LEFT_CENTER 文本左居中位置}处理
     */
    public static final byte LEFT_DRAWABLE_CENTER = 7;
    /**
     * 小圆点处于右图标居中位置，设置了{@link android.support.v7.widget.AppCompatTextView#setCompoundDrawables(Drawable, Drawable, Drawable, Drawable)}[第三个参数有效]生效。
     * 否则按{@linkplain #RIGHT_CENTER 文本右居中位置}处理
     */
    public static final byte RIGHT_DRAWABLE_CENTER = 8;

}

