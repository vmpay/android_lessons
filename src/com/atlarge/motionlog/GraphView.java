package com.atlarge.motionlog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GraphView extends View {
	private int mGraphCount = 2;
	private final int LINES_COUNT	= 2;
	private final int SCROLL_VALUE = 10;
	private String mExampleString = "Example string"; // TODO: use a default from R.string...
	private int mExampleColor = Color.RED; // TODO: use a default from
											// R.color...
	private Paint mGridPaint;
	private Paint[] mReadingPaints;
	private float mExampleDimension = 0; // TODO: use a default from R.dimen...

	private TextPaint mTextPaint;
	private float mTextWidth;
	private float mTextHeight;
	private float mGridSize = 30;
	
	private Canvas mReadingsCanvas = new Canvas();
	private Bitmap mReadingsBitmap;	

	private int[] mReadingLag;
	private float[] mLastReadingY;
	private float[] mMaxRange;
	
	private float mScaleX = (float) 0.00002;
	
	private LinkedList<Datapoint> mDatapoints;
	private LinkedList<PointF> mDrawPoints;
	
	private int mWidth;
	private int mHeight;
	
	private Paint mTransparentPaint;
	
	private class Datapoint {
		float[] mData;
		long mTimestamp;
		int mPresent;
		
		Datapoint(int idx, float _data, long _timestamp) {
			mData = new float[mGraphCount];
			mData[idx] = _data;
			mTimestamp = _timestamp;
			mPresent = 1<<idx;
		}
		
		boolean isPresent(int idx) {
			return (mPresent & (1<<idx)) != 0;
		}
		
		float get(int idx) {
			return mData[idx];
		}
	}
	
	public GraphView(Context context) {
		super(context);
		init(null, 0);
	}

	public GraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public GraphView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GraphView, defStyle, 0);

		mExampleString = a.getString(R.styleable.GraphView_exampleString);
		if (mExampleString==null)
			mExampleString = "Test string";
		mExampleColor = a.getColor(R.styleable.GraphView_exampleColor,
				mExampleColor);
//		if (mExampleColor==null)
//			mExampleColor = Color.BLACK;
		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing
		// with
		// values that should fall on pixel boundaries.
		mExampleDimension = a.getDimension(
				R.styleable.GraphView_exampleDimension, mExampleDimension);

//		if (a.hasValue(R.styleable.GraphView_exampleDrawable)) {
//			mExampleDrawable = a.getDrawable(R.styleable.GraphView_exampleDrawable);
//			mExampleDrawable.setCallback(this);
//		}

		a.recycle();

		// Set up a default TextPaint object
		mTextPaint = new TextPaint();
		mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextAlign(Paint.Align.LEFT);

		mGridPaint = new Paint();
		mGridPaint.setARGB (0xFF,0x20,0x20,0x20);
		mGridPaint.setStyle(Paint.Style.STROKE);
		mGridPaint.setStrokeWidth(1);

		// Update TextPaint and text measurements from attributes
		invalidateTextPaintAndMeasurements();
		
		// Create the paint for the readings
		mReadingPaints = new Paint[LINES_COUNT];
		for (int i=0; i<LINES_COUNT; i++) {
			mReadingPaints[i] = new Paint();
			mReadingPaints[i].setStyle(Paint.Style.STROKE);
			mReadingPaints[i].setStrokeWidth(1);
		}		
		mReadingPaints[0].setARGB (0xFF, 0xFF, 0x00, 0x00);
		mReadingPaints[1].setARGB (0xFF, 0x00, 0xFF, 0x00);
		mReadingPaints[1].setStyle(Paint.Style.FILL);
		
		mDatapoints = new LinkedList<Datapoint>();
		mDrawPoints = new LinkedList<PointF>();
		

		
		// Create reading storage
		mLastReadingY = new float[LINES_COUNT];
		mReadingLag = new int[LINES_COUNT]; 
		mMaxRange = new float[LINES_COUNT]; 
		for (int i=0; i<LINES_COUNT; i++) {
			mLastReadingY[i] = Float.NEGATIVE_INFINITY;
			mMaxRange[i] = 1;
			mReadingLag[i] = 0;
		}		
		
//		mTransparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
		mTransparentPaint = new Paint();
		mTransparentPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
//		mTransparentPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_OUT)); 
		mTransparentPaint.setColor(Color.TRANSPARENT);
		mTransparentPaint.setStyle(Paint.Style.FILL);
	}

	protected void onSizeChanged (int w, int h, int oldw, int oldh) {
		Log.d("GraphView", String.format("onSizeChanged(%d,%d,%d,%d", w, h, oldw, oldh));
		mWidth = w;
		mHeight = h;
		
		// Create a bitmap for the readings
		if (mReadingsBitmap == null) {
			mReadingsBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
			mReadingsBitmap.eraseColor(Color.TRANSPARENT);
			mReadingsCanvas.setBitmap(mReadingsBitmap);
		}
	}
	
	private void invalidateTextPaintAndMeasurements() {
		mTextPaint.setTextSize(mExampleDimension);
		mTextPaint.setColor(mExampleColor);
		mTextWidth = mTextPaint.measureText(mExampleString);

		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		mTextHeight = fontMetrics.bottom;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d("GraphView", "draw()");  

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;

		// Draw the text.
		canvas.drawText(mExampleString, paddingLeft
				+ (contentWidth - mTextWidth) / 2, paddingTop
				+ (contentHeight + mTextHeight) / 2, mTextPaint);

		// Draw the grid
		for (float x=mGridSize;x<contentWidth;x+=mGridSize) {
			canvas.drawLine(x, 0, x, contentHeight, mGridPaint);
		}
		for (float y=mGridSize;y<contentHeight;y+=mGridSize) {
			canvas.drawLine(0, y, contentWidth, y, mGridPaint);
		}
		
		// Draw the graphs
		Iterator<Datapoint> it = mDatapoints.descendingIterator();
		if (it.hasNext()) {
			float x2 = mWidth - 1;
			Datapoint dp = it.next();
			long lastTime = dp.mTimestamp;
			float y2 = (dp.get(0) + mMaxRange[0]) / (mMaxRange[0] * 2) * mHeight;
			while (it.hasNext()) { 
				dp = it.next();
				float y1 = (dp.get(0) + mMaxRange[0]) / (mMaxRange[0] * 2) * mHeight;
				float x1 = x2 - (lastTime - dp.mTimestamp) * mScaleX;
				Log.d("GraphView", String.format("drawing a line from %f, %f, to %f,%f (%d)", x1, y1, x2, y2, (lastTime - dp.mTimestamp)));  
				canvas.drawLine(x1, y1, x2, y2, mReadingPaints[0]);
				y2=y1;
				x2=x1;
				lastTime = dp.mTimestamp;
			}

		}
	}

	/**
	 * Gets the example string attribute value.
	 * 
	 * @return The example string attribute value.
	 */
	public String getExampleString() {
		return mExampleString;
	}

	/**
	 * Sets the view's example string attribute value. In the example view, this
	 * string is the text to draw.
	 * 
	 * @param exampleString
	 *            The example string attribute value to use.
	 */
	public void setExampleString(String exampleString) {
		mExampleString = exampleString;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example color attribute value.
	 * 
	 * @return The example color attribute value.
	 */
	public int getExampleColor() {
		return mExampleColor;
	}

	/**
	 * Sets the view's example color attribute value. In the example view, this
	 * color is the font color.
	 * 
	 * @param exampleColor
	 *            The example color attribute value to use.
	 */
	public void setExampleColor(int exampleColor) {
		mExampleColor = exampleColor;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example dimension attribute value.
	 * 
	 * @return The example dimension attribute value.
	 */
	public float getExampleDimension() {
		return mExampleDimension;
	}

	/**
	 * Sets the view's example dimension attribute value. In the example view,
	 * this dimension is the font size.
	 * 
	 * @param exampleDimension
	 *            The example dimension attribute value to use.
	 */
	public void setExampleDimension(float exampleDimension) {
		mExampleDimension = exampleDimension;
		invalidateTextPaintAndMeasurements();
	}

	public void setMaxRange(int readingIndex, float maxRange) {
		mMaxRange[readingIndex] = maxRange;
	}
	
	public void clear() {
		mReadingsBitmap.eraseColor(Color.TRANSPARENT);
	}
	
	public void addReading(int readingIndex, float readingValue, long timestamp) {
		// Deal with the data
		Log.d("GraphView", String.format("Adding datapoint %f %d", readingValue, timestamp/1000));
		mDatapoints.add(new Datapoint(readingIndex, readingValue, timestamp/1000));
//		while ((timestamp - mDatapoints.peek().mTimestamp) * mScaleX > mWidth) {
//			mDatapoints.removeFirst();
//		}
		
		invalidate();
		
/*		
		float newReadingY = 
				(readingValue + mMaxRange[readingIndex]) / (mMaxRange[readingIndex] * 2) * mHeight;
		boolean haveLastReading = mLastReadingY[readingIndex] != Float.NEGATIVE_INFINITY;
		if (haveLastReading) {
			// Move the bitmap 
			int nNewLineX1;
			if (mReadingLag[readingIndex] == 0) {
				mReadingsCanvas.drawBitmap(mReadingsBitmap, -SCROLL_VALUE, 0, null);
				// Erase the new line
				mReadingsCanvas.drawRect(mWidth-SCROLL_VALUE - 1, 0, mWidth, mHeight, mTransparentPaint);
//				mReadingsCanvas.drawRect(mWidth-SCROLL_VALUE - 1, 0, mWidth, mHeight, mReadingPaints[1]);//mTransparentPaint);
				nNewLineX1 = mWidth - SCROLL_VALUE - 1;
			} else {
				nNewLineX1 = mWidth - 2 - mReadingLag[readingIndex] * SCROLL_VALUE;
			}
			
			// Draw the extra reading
			Log.d("GraphView", String.format("drawing a line from %d, %f, to %d,%f", nNewLineX1, mLastReadingY[readingIndex], mWidth-1, newReadingY));  
			mReadingsCanvas.drawLine(nNewLineX1, mLastReadingY[readingIndex], mWidth-1, newReadingY, mReadingPaints[readingIndex]);
			// Cue drawing update
			invalidate();
		}

		mLastReadingY[readingIndex] = newReadingY;
		
		// Update the readings lag
		if (mReadingLag[readingIndex] == 0) {
			for (int i=0; i<LINES_COUNT; i++) {
				if (i != readingIndex)
					mReadingLag[i]++;
			}
		} else {
			mReadingLag[readingIndex]--;
		}
*/		
	}
	
}
