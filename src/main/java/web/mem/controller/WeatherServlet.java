package web.mem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import redis.clients.jedis.Jedis;


public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String WEATHER_URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-061?"
			+ "Authorization=CWB-5C1ADB17-6D5F-4A9D-BDBA-4869653D1148&"
			+ "format=JSON&"
			+ "locationName=大安區&"
			+ "elementName=Wx,PoP6h,T";
	
	Timer timer;
	public void init() throws ServletException{
		TimerTask task = new TimerTask() {
			public void run(){
				Jedis jedis = null;
				InputStream is = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try {
					URL url = new URL(WEATHER_URL);
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					con.setRequestMethod("GET");
					if (con.getResponseCode() == 200) {
						jedis = new Jedis("localhost", 6379);
						is = con.getInputStream();
						isr = new InputStreamReader(is);
						br = new BufferedReader(isr);
						StringBuffer buffer = new StringBuffer();
						
						String data;
						while ((data = br.readLine()) != null) {
							/* 此做法參考於：https://blog.csdn.net/Jay_1989/article/details/70154068
							 * 的 3、Reader –>String
							 */
							buffer.append(data);
							System.out.println(data);
						}
						//System.out.println(buffer.toString());
						String str = buffer.toString();
						Gson gson = new Gson();
						JsonObject jObj = gson.fromJson(str, JsonObject.class);
						JsonObject records = jObj.get("records").getAsJsonObject();
						JsonArray locations = records.get("locations").getAsJsonArray();
						JsonObject jObj2 = locations.get(0).getAsJsonObject();
						JsonArray location = jObj2.get("location").getAsJsonArray();
						JsonObject jObj3 = location.get(0).getAsJsonObject();
						JsonArray weatherElement = jObj3.get("weatherElement").getAsJsonArray();
						
						//天氣現象
						JsonObject wxObj = weatherElement.get(0).getAsJsonObject();
						JsonArray wxTimeJsAry = wxObj.get("time").getAsJsonArray();
						JsonObject wxTimeObj = wxTimeJsAry.get(0).getAsJsonObject();
						jedis.set("weather:wx", wxTimeObj.toString());
						//System.out.println(wxTimeObj.toString());
						
						//溫度
						JsonObject tObj = weatherElement.get(1).getAsJsonObject();
						JsonArray tTimeJsAry = tObj.get("time").getAsJsonArray();
						JsonObject tTimeObj = tTimeJsAry.get(0).getAsJsonObject();
						jedis.set("weather:t", tTimeObj.toString());
						//System.out.println(tTimeObj.toString());
						
						//6小時降雨機率
						JsonObject popObj = weatherElement.get(2).getAsJsonObject();
						JsonArray popTimeJsAry = popObj.get("time").getAsJsonArray();
						JsonObject popTimeObj = popTimeJsAry.get(0).getAsJsonObject();
						jedis.set("weather:pop", popTimeObj.toString());
						//System.out.println(popTimeObj.toString());
						
					}else {
						System.out.println("error!");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if(br != null) {
							br.close();
						}
						if(isr != null) {
							isr.close();
						}
						if(is != null) {
							is.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					if(jedis != null) {
						jedis.close();
					}
				}
			}
		};
		timer = new Timer();
		Calendar cal = new GregorianCalendar(2021, Calendar.DECEMBER, 19, 0, 1, 0); //2021/12/19 00:01:00 時開始
		timer.schedule(task, cal.getTime(), 3*60*60*1000); // 每3小時更新一次天氣資料
		System.out.println("天氣已建立排程!");  
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	}

	public void destroy() {
		timer.cancel();
		System.out.println("天氣已移除排程!");  
	}

}
