package com.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.view.View;

public class FaceDetectionActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(new MyView(this));
    }
    
    private class MyView extends View
    {
    	private Bitmap myBitmap;
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int NUMBER_OF_FACES=4;
    	private FaceDetector faceDetector;
    	private int NUMBER_OF_FACE_DETECTED;
    	private float eyeDistance;
    	
		public MyView(Context context) 
		{
			super(context);
			BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			myBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.faceswapping,bitmapFatoryOptions);
			width=myBitmap.getWidth();
			height=myBitmap.getHeight();
			detectedFaces=new FaceDetector.Face[NUMBER_OF_FACES];
			faceDetector=new FaceDetector(width,height,NUMBER_OF_FACES);
			NUMBER_OF_FACE_DETECTED=faceDetector.findFaces(myBitmap, detectedFaces);
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawBitmap(myBitmap, 0,0, null);
			Paint myPaint = new Paint();
            myPaint.setColor(Color.GREEN);
            myPaint.setStyle(Paint.Style.STROKE); 
            myPaint.setStrokeWidth(3);

            for(int count=0;count<NUMBER_OF_FACE_DETECTED;count++)
            {
            	Face face=detectedFaces[count];
            	PointF midPoint=new PointF();
            	face.getMidPoint(midPoint);
            	
            	eyeDistance=face.eyesDistance();
            	canvas.drawRect(midPoint.x-eyeDistance, midPoint.y-eyeDistance, midPoint.x+eyeDistance, midPoint.y+eyeDistance, myPaint);
            }
		}
    	
    }
}