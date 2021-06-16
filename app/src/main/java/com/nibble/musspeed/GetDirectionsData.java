package com.nibble.musspeed;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDirectionsData extends AsyncTask<Object, String, String> {
    GoogleMap gMap;
    String url;
    LatLng startLatLng, endLatLng;

    HttpURLConnection httpURLConnection = null;
    String data = "";
    InputStream inputStream = null;
    @SuppressLint("StaticFieldLeak")
    MainActivity mainActivity;

    GetDirectionsData(MainActivity mainActivity){this.mainActivity = mainActivity;}

    @Override
    protected String doInBackground(Object... params) {
        Object[] p = (Object[]) params[0];
        gMap = (GoogleMap)p[0];
        url = (String)p[1];
        startLatLng = (LatLng)p[2];
        endLatLng = (LatLng)p[3];

        try {
            URL myURL =new URL(url);
            httpURLConnection = (HttpURLConnection)myURL.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                sb.append(line);
            data = sb.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject legs =jsonObject.getJSONArray("routes").getJSONObject(0)
                    .getJSONArray("legs").getJSONObject(0);
            JSONArray steps = legs.getJSONArray("steps");
            JSONObject distance =legs.getJSONObject("distance");
            JSONObject duration =legs.getJSONObject("duration");
            String distanceText = distance.getString("text");
            Integer distanceValue = distance.getInt("value");
            String durationText = duration.getString("text");
            Integer durationValue = distance.getInt("value");

            mainActivity.setTimeLeftStr("Оставшееся время:\n"+duration.getString("text"));
            mainActivity.setCurrentPathStr("Осталось пройти:\n"+distance.getString("text"));
            mainActivity.setTimeLeft(duration.getInt("value"));
            mainActivity.setCurrentPath(distance.getInt("value"));

            if(mainActivity.currentModel.getClass().getName().equals("com.nibble.musspeed.MapModel")){
                mainActivity.setTotalPath(distance.getInt("value"));
                mainActivity.setTotalTime(duration.getInt("value"));
                ((MapModel)mainActivity.currentModel).setTexts(duration.getString("text"),distance.getString("text"));
            }
            if(mainActivity.currentModel.getClass().getName().equals("com.nibble.musspeed.ProgressModel"))
                ((ProgressModel)mainActivity.currentModel).setTexts("Оставшееся время:\n"+duration.getString("text"),"Осталось пройти:\n"+distance.getString("text"));

            int count = steps.length();
            String[] polyline_array = new String[count];

            JSONObject jsonObject2;

            for (int i = 0; i<count; i++){
                jsonObject2 = steps.getJSONObject(i);

                String polygone = jsonObject2.getJSONObject("polyline").getString("points");

                polyline_array[i] = polygone;
            }

            int count2 = polyline_array.length;

            gMap.clear();
            mainActivity.getPolyline().clear();
            for(int i = 0; i<count2;i++){
                PolylineOptions options2 = new PolylineOptions();
                options2.color(Color.GREEN);
                options2.width(12);
                options2.addAll(PolyUtil.decode(polyline_array[i]));
                gMap.addPolyline(options2);
                mainActivity.getPolyline().add(options2);
            }
            MarkerOptions mo = new MarkerOptions();
            mo.position(endLatLng);
            mo.title("Пункт назначения");
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( endLatLng, 15f));
            gMap.addMarker(mo);
            mo.position(startLatLng);
            mo.title("Вы");
            gMap.addMarker(mo);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

}
