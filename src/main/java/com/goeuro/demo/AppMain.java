package com.goeuro.demo;

import com.goeuro.demo.common.Constants;
import com.goeuro.demo.model.Location;
import com.goeuro.demo.service.LocationService;
import com.goeuro.demo.tool.CSVCreator;

/**
 *
 * @author Mehmet Burak Aktürk
 * @description Application's main class. All business operations are done here.
 *
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppMain appMain = new AppMain();

        //Get city name from arguments array
        String cityName = appMain.getCityName(args);

        //If there is no city name terminate the application
        if (cityName == null) {
            return;
        }

        LocationService locationService = new LocationService();

        //Fetch loction informations from GoEuro restful services
        Location[] locationList = locationService.getLocationByCity(cityName);

        //If there no list, do nothing. There is a problem on server side
        if (locationList != null) {
            CSVCreator<Location> csvCreator = appMain.getCSVCreator();
            csvCreator.setColumnTitles("_id,name,type,latitude,longtitude\n");
            csvCreator.createCSVFile(locationList);
        }
    }

    //Create proper csv creator for Loction class
    private CSVCreator<Location> getCSVCreator() {
        return new CSVCreator<Location>(Constants.CSV_OUT_DIR + "location.csv") {

            @Override
            protected StringBuilder createRow(Location location) {
                StringBuilder row = new StringBuilder();
                row.append(location.getId()).
                        append(",").
                        append(location.getName()).
                        append(",").
                        append(location.getType()).
                        append(",").
                        append(location.getGeoPosition().getLatitude()).
                        append(",").
                        append(location.getGeoPosition().getLongitude()).
                        append("\n");
                return row;
            }
        };
    }

    private String getCityName(String[] args) {

        String cityName = null;

        if (args[0] == null || !args[0].matches("[a-zA-Z]*")) {
            System.out.println("Please enter a valid city name");
        } else {
            cityName = args[0];
        }

        return cityName;
    }
}
