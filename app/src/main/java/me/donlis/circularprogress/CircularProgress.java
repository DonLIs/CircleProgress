package me.donlis.circularprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgress extends View {

    private Context mContext;//上下文

    private Paint mTextPaint;//文字画笔

    private Paint mCircularPaint;//背景圆环画笔

    private Paint mProgressPaint;//进度条画笔

    private float mProgressWidth;//进度条宽度

    private int mProgressColor = Color.BLUE;//进度条默认颜色

    private int mCircularColor = Color.parseColor("#E5E8E8");//背景圆环颜色

    private int mTextColor;//文字颜色

    private float mTextSize;//文字大小

    private String mText;//显示的文字

    private int max;//进度条总长度

    private int mProgress;//进度条进度


    public CircularProgress(Context context) {
        this(context,null);
    }

    public CircularProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircularProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    /**
     * 初始化画笔和自定义属性
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){
        mContext = context;

        mProgressWidth = dip2px(context,4);
        mTextSize = dip2px(context,14);

        //初始化自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgress);
        mProgressWidth = typedArray.getDimension(R.styleable.CircularProgress_progress_width,mProgressWidth);
        mProgressColor = typedArray.getColor(R.styleable.CircularProgress_progress_color,mProgressColor);
        mTextColor =  typedArray.getColor(R.styleable.CircularProgress_text_color,mProgressColor);//默认进度条颜色等于文字颜色
        mTextSize = typedArray.getDimension(R.styleable.CircularProgress_text_size,mTextSize);
        max = typedArray.getInteger(R.styleable.CircularProgress_max,100);
        mProgress = typedArray.getInteger(R.styleable.CircularProgress_progress,0);
        typedArray.recycle();

        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//消除锯齿
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        //初始化背景圆环画笔
        mCircularPaint = new Paint();
        mCircularPaint.setAntiAlias(true);
        mCircularPaint.setStyle(Paint.Style.STROKE);
        mCircularPaint.setColor(mCircularColor);
        mCircularPaint.setStrokeWidth(mProgressWidth);
        mCircularPaint.setStrokeCap(Paint.Cap.ROUND);

        //初始化进度条画笔
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    /**
     * 重写测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWith = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        //对比宽和高，获取测量最短的一边为宽和高
        int w = Math.min(measureWith,measureHeight);

        setMeasuredDimension(w,w);
    }

    /**
     * 重写绘制方法，自定义绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mText == null){
            mText = "";
        }

        if(mProgress > max){
            mProgress = max;
        }

        //绘制一个背景圆环
        RectF rectF = new RectF(mProgressWidth / 2, mProgressWidth / 2, getWidth() - mProgressWidth / 2, getHeight() - mProgressWidth / 2);
        canvas.drawArc(rectF, 0, 360, false, mCircularPaint);

        //绘制进度条进度
        float angle = 360 * mProgress / max;
        canvas.drawArc(rectF, -90, angle, false, mProgressPaint);

        //测量文字占用空间，绘制文字位置
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);

        int x = getMeasuredWidth() / 2 - bounds.width() / 2;
        int y = getMeasuredHeight() - ((getMeasuredHeight() - bounds.height()) / 2);
        canvas.drawText(mText,x,y,mTextPaint);
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(String text){
        this.mText = text;
    }

    public int getProgress(){
        return mProgress;
    }

    /**
     * 设置进度条进度，刷新绘制
     * @param progress
     */
    public void setProgress(int progress){
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 设置进度条总数
     * @param max
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * dp(dip)转px
     * @param context
     * @param dpValue
     * @return
     */
    private int dip2px(Context context , float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
