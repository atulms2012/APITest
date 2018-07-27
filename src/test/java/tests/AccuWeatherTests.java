package tests;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.Response;
import helper.ConfigReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aatul on 7/26/18.
 */
public class AccuWeatherTests {

    private static String apiKey = "";
    private static String PARAM_API_KEY = "apikey";

    //Weather API
    private static String FORECAST_JSON_KEY = "DailyForecasts";
    private static int NUMBER_OF_DAYS_EXPECTED = 5;

    //Location API
    private static String PARAM_CITY_TEXT = "q";
    private static String EXPECTED_CITY_NAME = "New Delhi";
    private static String ADMIN_EXPECTED_CITY_NAME_1 = "Delhi";
    private static String ADMIN_EXPECTED_CITY_NAME_2 = "Illinois";
    private static int NUMBER_OF_CITIES_EXPECTED = 2;

    @BeforeClass
    public static void setup() {
        apiKey = ConfigReader.getProperty(PARAM_API_KEY);
    }

    @Test
    public void fiveDayeWeather(){
        String locationKey = ConfigReader.getProperty("locationKey");
        String url = ConfigReader.getProperty("baseUrl")+ConfigReader.getProperty("forecastUri")+locationKey;
        Response response = given().param(PARAM_API_KEY,apiKey).get(url);
        int status = response.getStatusCode();
        Assert.assertEquals("Api Failed"+status,status, HttpURLConnection.HTTP_OK);
        JSONObject jsonObject = new JSONObject(response.asString());
        JSONArray dailyForcastArray = jsonObject.getJSONArray(FORECAST_JSON_KEY);
        Assert.assertEquals("More/Less than 5 days of records",NUMBER_OF_DAYS_EXPECTED,dailyForcastArray.length());
    }

    @Test
    public void getLocation(){
        String city = ConfigReader.getProperty(PARAM_CITY_TEXT);
        String url = ConfigReader.getProperty("baseUrl")+ConfigReader.getProperty("locationUri");
        Response response = given().param(PARAM_API_KEY,apiKey).param(PARAM_CITY_TEXT,city).get(url);
        int status = response.getStatusCode();
        Assert.assertEquals("Api Failed"+status,status, HttpURLConnection.HTTP_OK);
        JSONArray cityArray = new JSONArray(response.asString());
        Assert.assertEquals("2 expected",NUMBER_OF_CITIES_EXPECTED,cityArray.length());
        for(int i=0;i<cityArray.length();i++){
            JSONObject eachCity = cityArray.getJSONObject(i);
            String cityName = eachCity.getString("EnglishName");
            Assert.assertEquals("Wrong City",EXPECTED_CITY_NAME,cityName);
            JSONObject administrativeArea = eachCity.getJSONObject("AdministrativeArea");
            String adminEnglishName = administrativeArea.getString("EnglishName");
            if(!adminEnglishName.equals(ADMIN_EXPECTED_CITY_NAME_1) && !adminEnglishName.equals(ADMIN_EXPECTED_CITY_NAME_2))
                Assert.fail("Admin Names not matching");
        }
    }

}
