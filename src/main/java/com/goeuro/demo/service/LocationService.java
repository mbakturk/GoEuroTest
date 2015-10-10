package com.goeuro.demo.service;

import com.goeuro.demo.common.Constants;
import com.goeuro.demo.model.Location;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 *
 * @author Mehmet Burak Aktürk
 */
public class LocationService {

    private ClientConfig config;
    private Client client;

    public LocationService() {
        config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(config);
    }

    //If there is an exception it returns null
    public Location[] getLocationByCity(String cityName) {
        WebResource service = client.resource(UriBuilder.fromUri(Constants.LOCATION_API_URL).build());

        try {
            return service.path(cityName).
                    accept(MediaType.APPLICATION_JSON).
                    get(Location[].class);
        } catch (UniformInterfaceException | ClientHandlerException e) {
            //Log error
            e.printStackTrace();
            System.out.printf("An error occured please try again.");
            return null;
        }
    }
}
