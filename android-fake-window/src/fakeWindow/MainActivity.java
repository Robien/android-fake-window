package fakeWindow;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.example.testfacedetection.R;

import misc.SettingsFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.FaceDetector;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import bluetooth.DeviceListActivity;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements FaceDetectionListener,SensorEventListener {

	
	private Camera mCamera;
	private Preview mPreview;
	private ImageView imageView;
	Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
	private Rect rect = null;
	private SensorManager mSensorManager;
	private Sensor accelerometer;
	static public boolean isSettingOpen;
	
    private BluetoothAdapter mBluetoothAdapter = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
		 
		 setContentView(R.layout.activity_main);
		
		
		 mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		 List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		 accelerometer = deviceSensors.get(0);
		
		
		SettingsFragment.vitesseH = getResources().getInteger(R.integer.vitesse_horizontale);
		SettingsFragment.vitesseV = getResources().getInteger(R.integer.vitesse_verticale);
		SettingsFragment.vitesseZ = getResources().getInteger(R.integer.vitesse_zoom);
		
		isSettingOpen = false;
		 mCamera = getCameraInstance();
	     mCamera.setFaceDetectionListener(this);
	        // Prepare the preview
	        mPreview = new Preview(this, mCamera);
	        FrameLayout preview = (FrameLayout) findViewById(R.id.preview);
	        preview.addView(mPreview);	
	        findViewById(R.id.preview).setAlpha(0);
	        
	     //   Bitmap newBitmap = Bitmap.createBitmap(getResources()., START_X, START_Y, WIDTH_PX, HEIGHT_PX, null, false);
	        
	        imageView = (ImageView) findViewById(R.id.imageView1);
	        imageView.setImageBitmap(
	        	    decodeSampledBitmapFromResource(getResources(), R.drawable.panorama, 256, 256));
	        imageView.setScaleType(ImageView.ScaleType.MATRIX);
	        imageView.bringToFront();

	     //   Drawable panorama = getResources().getDrawable(R.drawable.panorama);
	      //  panorama.setBounds(10, 20, 110, 120);
	   //     imageView.setImageDrawable(panorama);
	        
//	        Bitmap image2 = (Bitmap) getdata.getExtras().get("data");
//	        img.setImageBitmap(image2);
//	        String incident_ID = IncidentFormActivity.incident_id;
//
//	        imagepath="/sdcard/RDMS/"+incident_ID+ x + ".PNG";
//	        File file = new File(imagepath);
//	            try {
//	                double xFactor = 0;
//	                double width = Double.valueOf(image2.getWidth());
//	                Log.v("WIDTH", String.valueOf(width));
//	                double height = Double.valueOf(image2.getHeight());
//	                Log.v("height", String.valueOf(height));
//	                if(width>height){
//	                xFactor = 841/width;
//	            }
//	            else{
//	                xFactor = 595/width;
//	            }
//
//
//	        Log.v("Nheight", String.valueOf(width*xFactor));
//	        Log.v("Nweight", String.valueOf(height*xFactor));
	        
	        
	    // mCamera.startFaceDetection();
	        
	        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        
//	        Intent serverIntent = new Intent(this, DeviceListActivity.class);
//	            startActivityForResult(serverIntent, 0);
	            
//	        ensureDiscoverable();
	        
//	        SilentServer ss = new SilentServer(mBluetoothAdapter, true);
//	        ss.start();
	        
	}

//	@Override
//	public void onBackPressed()
//	{
//	//	mCamera.release();
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_settings:
	    	startActivity(new Intent(this, SettingsFragment.class));
	    	
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(1); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    	 e.printStackTrace();
	 	    try {
		        c = Camera.open(); // attempt to get a Camera instance
		    }
		    catch (Exception e2){
		        // Camera is not available (in use or does not exist)
		    	 e2.printStackTrace();
		    }
	    }
	    return c; // returns null if camera is unavailable
	}

	@Override
	public void onFaceDetection(Face[] arg0, Camera arg1) {
		// TODO Auto-generated method stub
	//	Log.d("DEBUG", arg0.length + "FACE(S) found !");
		 for (int i = 0; i < arg0.length; ++i) {
		     Face face = arg0[i];
		     //Log.d("DEBUG", "Face " + i + " found with " + face.rect.top + "");
		     //imageView.getDrawable().setBounds(face.rect);
		    // imageView.setScaleType(ScaleType.)
		     //new Rect(10*face.rect.right, 5*face.rect.bottom, 10*face.rect.left, 5*face.rect.top)
		     matrix = new Matrix();
		    if (rect == null)
		    {
		    	rect = face.rect;
		    }

		    	rect.left = (rect.left + face.rect.left)/2;
		    	rect.right = (rect.right + face.rect.right)/2;
		    	rect.top =  (rect.top + face.rect.top)/2;
		    	rect.bottom =  (rect.bottom + face.rect.bottom)/2;

		    	
		    	float distance=0, offcetRight=0, offcetTop=0, zoom=0;
		    switch (getWindowManager().getDefaultDisplay().getRotation()) {
			case Surface.ROTATION_0:
				distance = Math.abs(rect.top - rect.bottom);
				offcetRight = (((float) (rect.top + rect.bottom))/2+1000)/2000;
				offcetTop = (((float) (rect.left + rect.right))/2+1000)/2000;
				zoom = SettingsFragment.vitesseZ*1000/distance;
				break;
			case Surface.ROTATION_90:
				distance = Math.abs(rect.left - rect.right);
				offcetRight = (((float) (rect.left + rect.right))/2+1000)/2000;
				offcetTop = ((((float) (-rect.top + -rect.bottom))/2+1000)/2000);
				zoom = SettingsFragment.vitesseZ*1000/distance;
				break;
			case Surface.ROTATION_180:
				distance = Math.abs(rect.top - rect.bottom);
				offcetRight = (((float) (-rect.top + -rect.bottom))/2+1000)/2000;
				offcetTop = ((((float) (rect.left + rect.right))/2+1000)/2000);
				zoom = SettingsFragment.vitesseZ*1000/distance;
				break;
			case Surface.ROTATION_270:
				distance = Math.abs(rect.left - rect.right);
				offcetRight = (((float) (rect.left + rect.right))/2+1000)/2000;
				offcetTop = (((float) (rect.top + rect.bottom))/2+1000)/2000;
				zoom = SettingsFragment.vitesseZ*1000/distance;
				break;

			default:
				break;
			}	
		     
		     
		    
		    if (face.leftEye == null)
		    {
		    	Log.e("DEBUG", "eye recognition is not available");
		    }
		     
//		    Log.d("DEBUG", "Face " + i + " found with " + 128+SettingsFragment.vitesseH*offcetRight + " - " + rect.top + " - " + rect.bottom);
		    matrix.postScale(zoom,zoom,zoom+SettingsFragment.vitesseH*offcetRight,zoom+SettingsFragment.vitesseV*offcetTop);	
//		     matrix.postScale(Math.abs(2000/((float) rect.left - (float)rect.right)),Math.abs(2000/((float) rect.left - (float)rect.right)),((rect.top+5000)/3),((rect.right+2000)/5));
		    
		     imageView.setImageMatrix(matrix);
		     
		     
		     
		     
		     
		     
		     
		     
//			    Matrix matrix2 = new Matrix();
//			    CameraInfo info = null;
//			     Camera.getCameraInfo(1, info);
//			    // Need mirror for front camera.
//			    boolean mirror = (info.facing == CameraInfo.CAMERA_FACING_FRONT);
//			    matrix2.setScale(mirror ? -1 : 1, 1);
//			    // This is the value for android.hardware.Camera.setDisplayOrientation.
////			    matrix.postRotate(displayOrientation);
//			    // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
//			    // UI coordinates range from (0, 0) to (width, height).
//			    matrix2.postScale(256 / 2000f, 256 / 2000f);
//			    matrix2.postTranslate(256 / 2f, 256 / 2f);
		     
		     
		  }
	}
	
	

	public Rect findFace(Bitmap bmp) {
	    // Ask for 1 face
	    android.media.FaceDetector.Face[] faces = new FaceDetector.Face[1];
	    FaceDetector detector = new FaceDetector( bmp.getWidth(), bmp.getHeight(), 1 );
	    int count = detector.findFaces( bmp, faces );

	    android.media.FaceDetector.Face face = null;

	    if( count > 0 ) {
	        face = faces[0];

	        PointF midEyes = new PointF();
	        face.getMidPoint( midEyes );
	        Log.i( "DEBUG",
	                "Found face. Confidence: " + face.confidence() + ". Eye Distance: " + face.eyesDistance() + " Pose: ("
	                        + face.pose( FaceDetector.Face.EULER_X ) + "," + face.pose( FaceDetector.Face.EULER_Y ) + ","
	                        + face.pose( FaceDetector.Face.EULER_Z ) + "). Eye Midpoint: (" + midEyes.x + "," + midEyes.y + ")" );

	        float eyedist = face.eyesDistance();
	        PointF lt = new PointF( midEyes.x - eyedist * 2.0f, midEyes.y - eyedist * 2.5f );
	        // Create rectangle around face.  Create a box based on the eyes and add some padding.
	        // The ratio of head height to width is generally 9/5 but that makes the rect a bit to tall.
	        return new Rect(
	            Math.max( (int) ( lt.x ), 0 ),
	            Math.max( (int) ( lt.y ), 0 ),
	            Math.min( (int) ( lt.x + eyedist * 4.0f ), bmp.getWidth() ),
	            Math.min( (int) ( lt.y + eyedist * 5.5f ), bmp.getHeight() )
	        );
	    }

	    return null;
	}
	
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	  

    private void ensureDiscoverable() 
    {
        if (mBluetoothAdapter.getScanMode() !=  BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        BluetoothSocket tmp = null;
        try {
			tmp = device.createRfcommSocketToServiceRecord(new UUID(0,42));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			tmp.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.e("DEBUG","connecte !");
        // Attempt to connect to the device
       // mChatService.connect(device, secure);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.e("DEBUG", resultCode + " - " + Activity.RESULT_OK);
        switch (resultCode) {
        case RESULT_OK:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
//        case REQUEST_CONNECT_DEVICE_INSECURE:
//            // When DeviceListActivity returns with a device to connect
//            if (resultCode == Activity.RESULT_OK) {
//                connectDevice(data, false);
//            }
//            break;
        case RESULT_CANCELED:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
//                setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                finish();
            }
        }
    }

    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	  mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	synchronized public void onSensorChanged(SensorEvent event) {
		if (!isSettingOpen && Math.abs(event.values[0]) + Math.abs(event.values[0]) + Math.abs(event.values[0]) > 40)
		{
			isSettingOpen = true;
			startActivity(new Intent(this, SettingsFragment.class));
		}
	}
    
	@Override
	synchronized public void onPause()
	{
		super.onPause();
		 mSensorManager.unregisterListener(this);
	}
    
}
