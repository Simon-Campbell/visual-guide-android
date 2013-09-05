package nz.ac.waikato.ssc10.map;

import android.location.Address;
import android.location.Location;
import android.util.Log;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import nz.ac.waikato.ssc10.map.interfaces.PlaceProvider;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A place provider which describes places from Google.
 */
public class GooglePlaceProvider implements PlaceProvider {
    public static final String LOCATION_PROVIDER = "google-places";
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
        return get(description, this.resultLimit);
    }

    private List<Address> get(String description, int resultLimit) {
        if (description == null || description.equals("")) {
            throw new IllegalArgumentException("Description is null or the empty string");
        }

        if (location == null) {
            throw new IllegalStateException("This provider has not the from location set via setSearchFromLocation(Location)");
        }

        List<Address> addresses = new ArrayList<Address>(resultLimit);

        try {
            int results = 0;
            GooglePlacesCollection result = getResult(description);

            for (GooglePlacesCollection.GooglePlace res : result.results) {
                Address address = new Address(Locale.getDefault());
                address.setLatitude(res.location.getLatitude());
                address.setLongitude(res.location.getLongitude());
                address.setFeatureName(res.name);
                addresses.add(address);

                if (++results >= resultLimit) {
                    break;
                }

                Log.i("GooglePlaceProvider", "Found address and added it to list " + address);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return addresses;
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

    private GooglePlacesCollection getResult(String description) throws IOException {
        String content = downloadStringContent(description);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationDeserializer())
                .create();

        return gson.fromJson(content, GooglePlacesCollection.class);
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
        return get(description, 1).get(0);
    }

    private class GooglePlacesCollection {
        @SerializedName("results")
        private List<GooglePlace> results;

        public GooglePlacesCollection() {

        }

        /**
         * A class describing a place from the Google Places
         * API
         */
        public class GooglePlace {
            @SerializedName("id")
            private String id;

            @SerializedName("formatted_address")
            private String address;

            @SerializedName("geometry")
            private Location location;

            @SerializedName("name")
            private String name;

            @SerializedName("types")
            private List<String> types;

            public GooglePlace() {

            }
        }
    }

    /**
     * A class that deserializes a JsonObject in the following form to
     * a location object.
     * {
     * "location" : {
     * "lat" : -37.789858,
     * "lng" : 175.325949
     * }
     * }
     */
    public class LocationDeserializer implements JsonDeserializer<Location> {
        @Override
        public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Location l = new Location(LOCATION_PROVIDER);

            JsonObject location = jsonElement
                    .getAsJsonObject()
                    .get("location")
                    .getAsJsonObject();

            double latitude = location.get("lat").getAsDouble();
            double longitude = location.get("lng").getAsDouble();

            l.setLatitude(latitude);
            l.setLongitude(longitude);

            return l;
        }
    }
}
