package com.example.client.yelp_fusion.business;

import co.elastic.clients.elasticsearch.core.*;
import com.fasterxml.jackson.databind.*;
import jakarta.json.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class BusinessSearchRequestWithCategoriesTest extends AbstractYelpRequestTestCase {

    static int limit = 50;

    List<String> restaurantCategories = List.of(
            "sushi", "bbq", "korean", "italian", "steak",
            "seafood", "chinese", "mexican", "pizza", "salad",
            "brunch", "burgers", "french", "indian", "japanese",
            "vietnamese", "breakfast", "vegetarian", "vegan", "thai",
            "steakhouses", "peruvian", "ramen", "udon");

    List<String> districts = List.of("mission", "Marina/Cow Hollow", "old oakland", "inner sunset", "richmond", "japantown");
    ObjectMapper mapper;
    String restaurantCategory = restaurantCategories.get(22);

    @Test
    void prepareRequestWithRestaurantCategoryTest() throws IOException {

        requestParams.put("categories", restaurantCategory);

        requestParams.forEach((k, v) -> {
            if (requestUsingCurl.toString().endsWith("search")) {
                requestUsingCurl.append("?")
                        .append(k)
                        .append("=")
                        .append(v);

            } else {
                requestUsingCurl
                        .append("&")
                        .append(k)
                        .append("=")
                        .append(v);
            }
        }); // search?location=SF&latitude=&longitude=&sort_by=distance&limit=50categories= iterate through list & offset = depends on response parameter: total

        JsonObject jsonObject = performRequest(requestUsingCurl.toString()); // GetBusinessSearchResponse response = this.transport.performRequest(GetBusinessSearchRequest.Builder ...)

        mapper = new ObjectMapper();

        // index results 1-50
        GetBusinessResponse businessResponse = mapper.readValue(jsonObject.toString(), GetBusinessResponse.class);
        IndexResponse response = client.index(i -> i // Creates or updates a document in an index.
                .index("restaurants-index-000001") // The name of the index
                .id(String.format("restaurants-%s1", restaurantCategory)) // Document ID
                .document(businessResponse) // Request body
        );

        System.out.println("doc id = " + response.id());
        System.out.println("index = " + response.index());
        System.out.println("result = " + response.result());
        System.out.println("businessResponse.getTotal= " + businessResponse.getTotal());
        // max results per page = 50
        int total = jsonObject.getInt("total");

        if (total > 50) {
            addOffsetParameter(total);
        }
    }


    private void addOffsetParameter(int total) throws IOException {
        // add request param for offset
        requestParams.put("offset", String.valueOf(limit));
        requestUsingCurl
                .append("&")
                .append("offset")
                .append("=");

        int length = requestUsingCurl.toString().length();

        // if total results > max results, add category filter
        // limit = max results per page, increment offset by limit, iterate until
        for (int offset = limit; offset <= total + 50; offset += limit) {
            String request = requestUsingCurl.toString() + offset;

            JsonObject jsonObj = performRequest(request);

            GetBusinessResponse businessRes = mapper.readValue(jsonObj.toString(), GetBusinessResponse.class);

            String docId = String.format("restaurants-%s%s", restaurantCategory, offset);

            IndexResponse response = client.index(i -> i
                    .index("restaurants-index-000001")
                    .id(docId)
                    .document(businessRes)
            );
            System.out.println("current offset = " + offset);
        }

    }

    private JsonObject performRequest(String request) throws IOException {

        Process process = Runtime.getRuntime().exec(request);

        InputStream inputStream = process.getInputStream();

        String response = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        var jsonReader = Json.createReader(new StringReader(response));
        var messageAsJson = jsonReader.readObject();

        assertThat(messageAsJson.getInt("total")).isLessThanOrEqualTo(1000);

        return messageAsJson;
    }

}