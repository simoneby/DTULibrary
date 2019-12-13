package com.helpers;
import com.models.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

//@Author s191218
public class SensorDataHelper {

    private static JSONObject jsonObject = null;
    private static JSONArray jsonArray = null;

    private static String loadJsonData (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static InitialSensorData[] getSensorData()
    {
        String url = "https://scadadataapi.azurewebsites.net/api/values"; //写上自己的url链接即可，我的就不公布了哈~
        String json = loadJsonData(url);  //获得json字符串
        json = "{'array':"+json+"}";
        jsonObject = JSONObject.fromObject(json);
        jsonArray = jsonObject.getJSONArray("array");
        InitialSensorData[] sensors = new InitialSensorData[jsonArray.size()];
        for (int i = 0; i<jsonArray.size() ; ++i)
        {
            String s = jsonArray.getJSONObject(i).getString("pointId");
            boolean deal = false;
            /*
            for (int j = 0; j<InitialSensorData.sensorNum ; ++j)
                if(s == sensors[j].getName())
                {
                    try {
                        Exception e = new Exception("There is a value conflict");
                        if(!sensors[j].changeData(jsonArray.getJSONObject(i).getString("type"), (float) jsonArray.getJSONObject(i).get("value"),true))
                            throw e;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    deal = true;
                    break;
                }*/
            //if(!deal)
                try {
                    sensors[i] = new InitialSensorData(jsonArray.getJSONObject(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                    sensors[i] = new InitialSensorData();
                    sensors[i].setName(e.getLocalizedMessage());
                }
        }
        return sensors;
    }
}
