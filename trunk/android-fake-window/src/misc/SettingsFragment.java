package misc;

import com.example.testfacedetection.R;

import fakeWindow.MainActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SettingsFragment extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	
    
	public static float vitesseH;
	static public float vitesseV;
	static public float vitesseZ;
	
    @Override
    synchronized public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		// TODO Auto-generated method stub
	       if (key.equals("pref_vitesse_verticale"))
	        {
	        	vitesseV = Integer.valueOf(sharedPreferences.getString(key, String.valueOf(getResources().getInteger(R.integer.vitesse_verticale))));
	            // Set summary to be the user-description for the selected value
	        }
	        else if (key.equals("pref_vitesse_horizontale"))
	        {
	        	vitesseH = Integer.valueOf(sharedPreferences.getString(key, String.valueOf(getResources().getInteger(R.integer.vitesse_horizontale))));
	            // Set summary to be the user-description for the selected value
	        }
	        else if (key.equals("pref_vitesse_zoom"))
	        {
	        	vitesseZ = Integer.valueOf(sharedPreferences.getString(key, String.valueOf(getResources().getInteger(R.integer.vitesse_zoom))));
	            // Set summary to be the user-description for the selected value
	        }
	        
	}
	@Override
	synchronized public void onPause()
	{
		super.onPause();
		MainActivity.isSettingOpen = false;
	}
	
}