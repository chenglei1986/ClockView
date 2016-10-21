package com.github.chenglei1986.clock;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ClockView extends View {

    private static final float MIN_WIDTH_DP = 50;
    private static final float MIN_HEIGHT_DP = 50;

    private static final int DEFAULT_CLOCK_FACE_COLOR = Color.parseColor("#A9ADB0");
    private static final int DEFAULT_OUTER_RIM_COLOR = Color.BLACK;
    private static final int DEFAULT_INNER_RIM_COLOR = Color.BLACK;
    private static final int DEFAULT_THICK_MARKER_COLOR = Color.BLACK;
    private static final int DEFAULT_THIN_MARKER_COLOR = Color.BLACK;
    private static final int DEFAULT_NUMBER_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_HOUR_HAND_COLOR = Color.BLACK;
    private static final int DEFAULT_MINUTE_HAND_COLOR = Color.BLACK;
    private static final int DEFAULT_SWEEP_HAND_COLOR = Color.BLACK;

    private static final float DEFAULT_OUTER_RIM_WIDTH = dipToPx(1);
    private static final float DEFAULT_INNER_RIM_WIDTH = dipToPx(1);
    private static final float DEFAULT_THICK_MARKER_WIDTH = dipToPx(3);
    private static final float DEFAULT_THIN_MARKER_WIDTH = dipToPx(1);
    private static final float DEFAULT_NUMBER_TEXT_SIZE = dipToPx(18);
    private static final float DEFAULT_HOUR_HAND_WIDTH = dipToPx(5);
    private static final float DEFAULT_MINUTE_HAND_WIDTH = dipToPx(3);
    private static final float DEFAULT_SWEEP_HAND_WIDTH = dipToPx(1);

    private int mClockFaceColor;
    private int mOuterRimColor;
    private int mInnerRimColor;
    private int mThickMarkerColor;
    private int mThinMarkerColor;
    private int mNumberTextColor;
    private int mHourHandColor;
    private int mMinuteHandColor;
    private int mSweepHandColor;

    private float mOuterRimWidth;
    private float mInnerRimWidth;
    private float mThickMarkerWidth;
    private float mThinMarkerWidth;
    private float mNumberTextSize;
    private float mHourHandWidth;
    private float mMinuteHandWidth;
    private float mSweepHandWidth;

    private boolean mShowThickMarkers = true;
    private boolean mShowThinMarkers = true;
    private boolean mShowNumbers = true;
    private boolean mShowSweepHand = true;

    private int mMinWidth = (int) dipToPx(MIN_WIDTH_DP);
    private int mMinHeight = (int) dipToPx(MIN_HEIGHT_DP);

    private Rect mPaintRect = new Rect();

    private Paint mColckFacePaint = new Paint();
    private Paint mOuterRimPaint = new Paint();
    private Paint mInnerRimPaint = new Paint();
    private Paint mThickMarkerPaint = new Paint();
    private Paint mThinMarkerPaint = new Paint();
    private TextPaint mNumberPaint = new TextPaint();
    private Paint mHourHandPaint = new Paint();
    private Paint mMinuteHandPaint = new Paint();
    private Paint mSweepHandpaint = new Paint();

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        init();
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        mClockFaceColor = attr.getColor(R.styleable.ClockView_clockFaceColor, DEFAULT_CLOCK_FACE_COLOR);
        mOuterRimColor = attr.getColor(R.styleable.ClockView_outerRimColor, DEFAULT_OUTER_RIM_COLOR);
        mInnerRimColor = attr.getColor(R.styleable.ClockView_innerRimColor, DEFAULT_INNER_RIM_COLOR);
        mThickMarkerColor = attr.getColor(R.styleable.ClockView_thickMarkerColor, DEFAULT_THICK_MARKER_COLOR);
        mThinMarkerColor = attr.getColor(R.styleable.ClockView_thinMarkerColor, DEFAULT_THIN_MARKER_COLOR);
        mNumberTextColor = attr.getColor(R.styleable.ClockView_numberTextColor, DEFAULT_NUMBER_TEXT_COLOR);
        mHourHandColor = attr.getColor(R.styleable.ClockView_hourHandColor, DEFAULT_HOUR_HAND_COLOR);
        mMinuteHandColor = attr.getColor(R.styleable.ClockView_minuteHandColor, DEFAULT_MINUTE_HAND_COLOR);
        mSweepHandColor = attr.getColor(R.styleable.ClockView_sweepHandColor, DEFAULT_SWEEP_HAND_COLOR);

        mOuterRimWidth = attr.getDimension(R.styleable.ClockView_outerRimWidth, DEFAULT_OUTER_RIM_WIDTH);
        mInnerRimWidth = attr.getDimension(R.styleable.ClockView_innerRimWidth, DEFAULT_INNER_RIM_WIDTH);
        mThickMarkerWidth = attr.getDimension(R.styleable.ClockView_thickMarkerWidth, DEFAULT_THICK_MARKER_WIDTH);
        mThinMarkerWidth = attr.getDimension(R.styleable.ClockView_thinMarkerWidth, DEFAULT_THIN_MARKER_WIDTH);
        mNumberTextSize = attr.getDimension(R.styleable.ClockView_numberTextSize, DEFAULT_NUMBER_TEXT_SIZE);
        mHourHandWidth = attr.getDimension(R.styleable.ClockView_hourHandWidth, DEFAULT_HOUR_HAND_WIDTH);
        mMinuteHandWidth = attr.getDimension(R.styleable.ClockView_minuteHandWidth, DEFAULT_MINUTE_HAND_WIDTH);
        mSweepHandWidth = attr.getDimension(R.styleable.ClockView_sweepHandWidth, DEFAULT_SWEEP_HAND_WIDTH);

        mShowThickMarkers = attr.getBoolean(R.styleable.ClockView_showThickMarkers, mShowThickMarkers);
        mShowThinMarkers = attr.getBoolean(R.styleable.ClockView_showThinMarkers, mShowThinMarkers);
        mShowNumbers = attr.getBoolean(R.styleable.ClockView_showNumbers, mShowNumbers);
        mShowSweepHand = attr.getBoolean(R.styleable.ClockView_showSweepHand, mShowSweepHand);
        attr.recycle();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        mColckFacePaint.setAntiAlias(true);
        mColckFacePaint.setColor(mClockFaceColor);
        mColckFacePaint.setStyle(Paint.Style.FILL);

        mOuterRimPaint.setAntiAlias(true);
        mOuterRimPaint.setColor(mOuterRimColor);
        mOuterRimPaint.setStyle(Paint.Style.STROKE);
        mOuterRimPaint.setStrokeWidth(mOuterRimWidth);

        mInnerRimPaint.setAntiAlias(true);
        mInnerRimPaint.setColor(mInnerRimColor);
        mInnerRimPaint.setStyle(Paint.Style.STROKE);
        mInnerRimPaint.setStrokeWidth(mInnerRimWidth);

        mThickMarkerPaint.setAntiAlias(true);
        mThickMarkerPaint.setColor(mThickMarkerColor);
        mThickMarkerPaint.setStyle(Paint.Style.STROKE);
        mThickMarkerPaint.setStrokeWidth(mThickMarkerWidth);

        mThinMarkerPaint.setAntiAlias(true);
        mThinMarkerPaint.setColor(mThinMarkerColor);
        mThinMarkerPaint.setStyle(Paint.Style.STROKE);
        mThinMarkerPaint.setStrokeWidth(mThinMarkerWidth);

        mNumberPaint.setAntiAlias(true);
        mNumberPaint.setColor(mNumberTextColor);
        mNumberPaint.setTextSize(mNumberTextSize);

        mHourHandPaint.setAntiAlias(true);
        mHourHandPaint.setColor(mHourHandColor);
        mHourHandPaint.setStyle(Paint.Style.STROKE);
        mHourHandPaint.setStrokeWidth(mHourHandWidth);

        mMinuteHandPaint.setAntiAlias(true);
        mMinuteHandPaint.setColor(mMinuteHandColor);
        mMinuteHandPaint.setStyle(Paint.Style.STROKE);
        mMinuteHandPaint.setStrokeWidth(mMinuteHandWidth);

        mSweepHandpaint.setAntiAlias(true);
        mSweepHandpaint.setColor(mSweepHandColor);
        mSweepHandpaint.setStyle(Paint.Style.STROKE);
        mSweepHandpaint.setStrokeWidth(mSweepHandWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mMinWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mMinHeight;
        }

        int centerX = width / 2;
        int centerY = height / 2;
        int rectSize = Math.min(width, height);
        mPaintRect.set(centerX - rectSize / 2, centerY - rectSize / 2, centerX + rectSize / 2, centerY + rectSize / 2);

        setMeasuredDimension(width, height);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mPaintRect.centerX(), mPaintRect.centerY());
        drawBoarder(canvas);
        canvas.restore();
    }

    private void drawBoarder(Canvas canvas) {
        canvas.drawCircle(0, 0, mPaintRect.width() / 2, mColckFacePaint);
    }

    private static float dipToPx(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                Resources.getSystem().getDisplayMetrics());
    }
}
