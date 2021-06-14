package de.lewolf.MOTD.service.weirdjoke;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.lewolf.MOTD.exceptions.URLNotResponsiveException;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

@Service
public class WeirdJokeRestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeirdJoke() {
        String stringurl = "http://api.icndb.com/jokes/random";
        try {
            URL url = new URL(stringurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try (InputStream is = con.getInputStream()) {
                JSONParser jsonParser = new JSONParser(is);
                JSONObject jsonObjectAll = new JSONObject(jsonParser.parseObject());
                LinkedHashMap<String, Object> linkedHashMapValue = (LinkedHashMap<String, Object>) jsonObjectAll.get("value");
                String message = linkedHashMapValue.get("joke").toString();
                return message;
            }
        } catch (IOException | ParseException | JSONException e) {
            throw new URLNotResponsiveException("Die aufgerufene Website: " + stringurl + " konnte nicht geladen werden!");
        }
    }

    public String getWeirdJokeTemplate() {
        String stringurl = "http://api.icndb.com/jokes/random";
        ResponseEntity<String> response = restTemplate.getForEntity(stringurl, String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode joke = mapper.readTree(response.getBody())
                    .path("value")
                    .path("joke");
            return joke.toString();
        } catch (JsonProcessingException e) {
            throw new URLNotResponsiveException("Die aufgerufene Website: " + stringurl + " konnte nicht geladen werden!");
        }
    }


    public String getWeirdJokeWebClient() {
        String stringurl = "http://api.icndb.com/jokes/random";
        WebClient webClient = WebClient.create(stringurl);
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get();
        String jsonString = requestHeadersSpec.retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode joke = mapper.readTree(jsonString)
                    .path("value")
                    .path("joke");
            return joke.toString();
        } catch (JsonProcessingException e) {
            throw new URLNotResponsiveException("Die aufgerufene Website: " + stringurl + " konnte nicht geladen werden!");
        }
    }
}
