package nz.ac.waikato.ssc10.map;

import android.location.Address;
import android.location.Location;
import nz.ac.waikato.ssc10.map.interfaces.WalkingDirections;
import nz.ac.waikato.ssc10.map.navigation.NavigationStep;
import nz.ac.waikato.ssc10.map.navigation.WalkingNavigationStep;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 15/07/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleWalkingDirections implements WalkingDirections {
    private static final String API_BASE_URL = "http://maps.googleapis.com/maps/api/directions/json";

    private static final String API_ORIGIN_PARAM = "origin";    // Required
    private static final String API_DEST_PARAM = "destination"; // Required
    private static final String API_SENSOR_PARAM = "sensor";    // Required

    private static final String API_MODE_PARAM = "mode";

    /**
     * @param startLocation
     * @param endpointName
     * @throws NoSuchRouteException
     */
    public GoogleWalkingDirections(Location startLocation, String endpointName) throws NoSuchRouteException {
        GooglePlaceProvider placeProvider = new GooglePlaceProvider();
        placeProvider.setSearchFromLocation(startLocation);

        List<Address> addresses = placeProvider.get(endpointName);
        Address endAddress = addresses.get(0);

        // TODO:
        //  Finish this off and make it so WalkingDirections can update based on
        //  LatLng
        this.startAddress = stringify(startLocation);
        this.endAddress = endAddress;

        this.update();
    }

    public GoogleWalkingDirections(Location startLocation, Address endAddress) throws NoSuchRouteException {
        this.startAddress = stringify(startLocation);
        this.endAddress = endAddress;

        this.update();
    }

    /**
     * Converts a Location object to a string in the format of 'Latitude,Longitude' so that
     * the location can be put into an API string.
     *
     * @param location The location to convert to the specified format.
     * @return The location in the specified format as a String.
     */
    private static String stringify(Location location) {
        return String.format("%f,%f", location.getLatitude(), location.getLongitude());
    }

    private static String stringify(Address address) {
        return String.format("%f,%f", address.getLatitude(), address.getLongitude());
    }

    private static String getDirections(String from, Address to) throws IOException {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair(API_ORIGIN_PARAM, from));
        params.add(new BasicNameValuePair(API_DEST_PARAM, stringify(to)));
        params.add(new BasicNameValuePair(API_SENSOR_PARAM, "true"));
        params.add(new BasicNameValuePair(API_MODE_PARAM, "walking"));

        StringBuilder content = new StringBuilder();
        String url = API_BASE_URL + "?" + URLEncodedUtils.format(params, "UTF-8");
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

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

    /**
     * Routes the current walking directions from the specified location to
     * the already specified end location.
     *
     * @param from The new location to route from
     * @return A NEW GoogleWalkingDirections object from the specified Location
     *         to the already specified end location.
     * @throws NoSuchRouteException When there is no route from the current location
     *                              to the destination.
     */
    @Override
    public WalkingDirections routeFrom(Location from) throws NoSuchRouteException {
        return new GoogleWalkingDirections(from, this.endAddress);
    }

    private void update() throws NoSuchRouteException {
        try {
            setFromJson(getDirections(this.startAddress, this.endAddress));
        } catch (IOException e) {
            // throw e;
            e.printStackTrace();
        } catch (NoSuchRouteException e) {
            throw e;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LatLng getNorthEastBound() {
        return northEastBound;
    }

    public LatLng getSouthWestBound() {
        return southWestBound;
    }

    private LatLng northEastBound;
    private LatLng southWestBound;
    private int distanceValue;
    private int durationValue;
    private Address endAddress;
    private LatLng endLocation;
    private String startAddress;
    private LatLng startLocation;
    private ArrayList<NavigationStep> steps = new ArrayList<NavigationStep>();
    private String copyright = "Map data (c)2013 Google";


    public List<NavigationStep> getSteps() {
        return steps;
    }

    public NavigationStep getStep(int step) {
        return steps.get(step);
    }

    private void setFromJson(String json) throws
            JSONException,
            NoSuchRouteException {
        try {
            JSONObject rootObj = new JSONObject(json);
            JSONArray routes = rootObj.getJSONArray("routes");

            if (routes.length() == 0) {
                throw new NoSuchRouteException("A route between the points does not exist");
            }

            JSONObject route = routes.getJSONObject(0);
            JSONObject bounds = route.getJSONObject("bounds");
            JSONArray legs = route.getJSONArray("legs");
            JSONObject primaryLeg = legs.getJSONObject(0);
            JSONArray steps = primaryLeg.getJSONArray("steps");

            int stepCnt = steps.length();
            ArrayList<NavigationStep> stepArrayList = new ArrayList<NavigationStep>(stepCnt);

            for (int i = 0; i < stepCnt; i++) {
                stepArrayList.add(getMapStepFromJson(steps.getJSONObject(i)));
            }

            this.steps = stepArrayList;
            this.northEastBound = getLatLngFromJson(bounds.getJSONObject("northeast"));
            this.southWestBound = getLatLngFromJson(bounds.getJSONObject("southwest"));
        } catch (JSONException e) {
            throw e;
        }
    }

    private static NavigationStep getMapStepFromJson(JSONObject jsonObject) throws JSONException {
        LatLng startLocation = getLatLngFromJson(jsonObject.getJSONObject("start_location"));
        LatLng endLocation = getLatLngFromJson(jsonObject.getJSONObject("end_location"));

        JSONObject duration = jsonObject.getJSONObject("duration");
        JSONObject distance = jsonObject.getJSONObject("distance");

        String instructions = jsonObject.getString("html_instructions");
        String travelMode = jsonObject.getString("travel_mode");

        return new WalkingNavigationStep(distance.getInt("value"), duration.getInt("value"), endLocation, startLocation, instructions, travelMode);
    }

    private static LatLng getLatLngFromJson(JSONObject jsonObject) throws JSONException {
        return new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
    }
}
