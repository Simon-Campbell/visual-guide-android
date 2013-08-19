package nz.ac.waikato.ssc10.map;

import android.location.Address;
import android.location.Location;
import android.os.Debug;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A place provider which describes places from Google.
 */
public class GooglePlaceProvider implements PlaceProvider {
    public final int DEFAULT_RADIUS = 1000;
    public final int MAX_RADIUS = 50000;
    public final int DEFAULT_RESULTS_LIMIT = 5;

    private String API_KEY = "AIzaSyBRO-57FqQLXm_T70mQwGO_ePtSesJz46c";

    private final String API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private final String API_KEY_PARAM = "key";
    private final String API_LOCATION_PARAM = "location";
    private final String API_RADIUS_PARAM = "radius";
    private final String API_SENSOR_PARAM = "sensor";

    private final String API_NAME_PARAM = "name";
    private final String API_KEYWORD_PARAM = "keyword";

    private int radius = DEFAULT_RADIUS;
    private int resultLimit = DEFAULT_RESULTS_LIMIT;
    private Location location = null;

    public GooglePlaceProvider() {
    }

    @Override
    public void setRadiusLimit(int radius) {
        this.radius = radius;
    }

    @Override
    public void setResultsLimit(int limit) {
        this.resultLimit = limit;
    }

    @Override
    public void setSearchFromLocation(Location location) {
        this.location = location;
    }

    @Override
    public List<Address> get(String description) throws IllegalStateException {
        if (description == null || description.equals("")) {
            throw new IllegalArgumentException("Description is null or the empty string");
        }

        if (location == null) {
            throw new IllegalStateException("This provider has not the from location set via setSearchFromLocation(Location)");
        }

        try {
            getResult(description);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<BasicNameValuePair> getQueryParams(String description) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair(API_RADIUS_PARAM, Integer.toString(radius)));
        params.add(new BasicNameValuePair(API_LOCATION_PARAM, getLocationAsLatLngString(location)));
        params.add(new BasicNameValuePair(API_SENSOR_PARAM, "true"));
        params.add(new BasicNameValuePair(API_KEY_PARAM, API_KEY));
        params.add(new BasicNameValuePair(API_KEYWORD_PARAM, description));
        return params;
    }

    private GooglePlaceResult getResult(String description) throws IOException {
        return (GooglePlaceResult) new Gson().fromJson(downloadStringContent(description), GooglePlaceResult.class);
    }

    private String downloadStringContent(String description) throws IOException {
        StringBuilder content = new StringBuilder();

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(getQueryUrl(description));

        try {
            HttpResponse response = client.execute(request);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent())
            );

            String line = "";

            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw e;
        }

        return content.toString();
    }


    private String getQueryUrl(String description) {
        return (API_BASE_URL + "?" + URLEncodedUtils.format(getQueryParams(description), "UTF-8"));
    }

    private String getLocationAsLatLngString(Location location) {
        return String.format("%f,%f", location.getLatitude(), location.getLongitude());
    }

    @Override
    public Address getNearest(String description) throws IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private class GooglePlaceResult {
        @SerializedName("results") private List<PlaceResult> results;

        public GooglePlaceResult() { }

        private class PlaceResult {
            @SerializedName("formatted_address")
            private String address;

            @SerializedName("geometry")
            private PlacesLocation location;

            @SerializedName("id")
            private String id;

            @SerializedName("name")
            private String name;

            @SerializedName("types")
            private List<String> types;

            public PlaceResult() { }

            private class PlacesLocation {
                @SerializedName("location") LatLng location;

                public PlacesLocation() { }
            }
        }
    }
}
