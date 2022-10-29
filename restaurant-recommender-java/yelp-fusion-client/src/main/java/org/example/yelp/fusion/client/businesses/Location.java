package org.example.yelp.fusion.client.businesses;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import org.example.elasticsearch.client.json.*;
import org.example.elasticsearch.client.util.*;

import java.io.*;
import java.util.*;

import static org.example.elasticsearch.client.json.JsonpDeserializer.stringDeserializer;

@JsonDeserialize(using = Location.LocationDeserializer.class)
public class Location {

    String address1, address2, address3, city, county, zip_code, cross_streets, state, country;

<<<<<<< HEAD
    Object[] display_address;
<<<<<<< HEAD:yelp-fusion-client/src/main/java/org/example/yelp/fusion/client/businesses/Location.java
=======

    public Location(){}
>>>>>>> 80cbb9dac9c2996f7c50576fe9668568597878af:src/main/java/com/example/client/yelp_fusion/businesses/Location.java
=======
    List<String> display_address;
>>>>>>> newtemp


    private final String address1() {
        return address1;
    }


    private final String address2() {
        return address2;
    }

    private final String address3() {
        return address3;
    }

    private final String city() {
        return city;
    }

    private final String county() {
        return county;
    }

    private final String zip_code() {
        return zip_code;
    }

    private final String country() {
        return country;
    }

    private final String cross_streets() {
        return cross_streets;
    }

    private final String state() {
        return state;
    }

    private final List<String> display_address() {
        return display_address;
    }


    private Location(Builder builder) {

        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.address3 = builder.address3;
        this.zip_code = builder.zip_code;
        this.country = builder.country;
        this.cross_streets = builder.cross_streets;
        this.state = builder.state;
        this.display_address = builder.display_address;
    }


    public static class Builder extends WithJsonObjectBuilderBase<Builder> implements ObjectBuilder<Location> {
        private String address1, address2, address3, city, county, cross_streets, state, zip_code, country;

        private List<String> display_address;

        public final Builder country(String value) {
            this.country = value;
            return this;
        }

        public final Builder display_address(List<String> value) {
            this.display_address = value;
            return this;
        }

        public final Builder cross_streets(String value) {
            this.cross_streets = value;
            return this;
        }

        public final Builder state(String value) {
            this.state = value;
            return this;
        }

        public final Builder address3(String value) {
            this.address3 = value;
            return this;
        }

        public final Builder address1(String value) {
            this.address1 = value;
            return this;
        }

        public final Builder address2(String value) {
            this.address2 = value;
            return this;
        }

        public final Builder zip_code(String value) {
            this.zip_code = value;
            return this;
        }

        public final Builder city(String value) {
            this.city = value;
            return this;
        }

        public final Builder county(String value) {
            this.county = value;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }


        public Location build() {
            _checkSingleUse();

            return new Location(this);
        }
    }


    public static final JsonpDeserializer<Location> DESERIALIZER = ObjectBuilderDeserializer.lazy(Location.Builder::new,
            Location::setupInnerHitsDeserializer);

    protected static void setupInnerHitsDeserializer(ObjectDeserializer<Location.Builder> op) {

        op.add(Location.Builder::address1, stringDeserializer(), "address1");
        op.add(Location.Builder::display_address, JsonpDeserializer.arrayDeserializer(stringDeserializer()), "display_address");
        op.add(Location.Builder::address3, stringDeserializer(), "address3");
        op.add(Location.Builder::city, stringDeserializer(), "city");
        op.add(Location.Builder::zip_code, stringDeserializer(), "zip_code");
        op.add(Location.Builder::county, stringDeserializer(), "county");
        op.add(Location.Builder::country, stringDeserializer(), "country");
        op.add(Location.Builder::cross_streets, stringDeserializer(), "cross_streets");

    }

    public static class LocationDeserializer extends JsonDeserializer<Location> {

        @Override
        public Location deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return null;
        }
    }
}

