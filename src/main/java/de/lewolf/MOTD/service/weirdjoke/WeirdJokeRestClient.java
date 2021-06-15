package de.lewolf.MOTD.service.weirdjoke;

import de.lewolf.MOTD.exceptions.SomethingWentTerriblyWrongException;
import de.lewolf.MOTD.exceptions.URLNotResponsiveException;
import de.lewolf.MOTD.models.JokeResponseDto;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

@Service
public class WeirdJokeRestClient {

    private final RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://api.icndb.com").build();
    private final String urlString = "http://api.icndb.com/jokes/random";
    private final String urlPath = "/jokes/random";

    public String getWeirdJoke() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try (InputStream is = con.getInputStream()) {
                JSONParser jsonParser = new JSONParser(is);
                JSONObject jsonObjectAll = new JSONObject(jsonParser.parseObject());
                LinkedHashMap<String, Object> linkedHashMapValue = (LinkedHashMap<String, Object>) jsonObjectAll.get("value");
                String message = linkedHashMapValue.get("joke").toString();
                return message;
            }
        } catch (IOException | ParseException | JSONException e) {
            throw new URLNotResponsiveException("Die aufgerufene Website: " + urlString + " konnte nicht geladen werden!");
        }
    }

    public String getWeirdJokeTemplate() {
        try {
            ResponseEntity<JokeResponseDto> jokeResponse = restTemplate.getForEntity(urlPath, JokeResponseDto.class);
            return jokeResponse.getBody().getValue().getJokeText();
        } catch (RestClientException e) {
            throw new SomethingWentTerriblyWrongException("You did it, you broke it!");
        }
    }

    public String getWeirdJokeWebClient() {
        WebClient webClient = WebClient.create(urlString);
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get();
        JokeResponseDto jokeResponse = requestHeadersSpec.retrieve()
                .bodyToMono(JokeResponseDto.class)
                .block();
        try {
            return jokeResponse.getValue().getJokeText();
        } catch (WebClientException e) {
            throw new SomethingWentTerriblyWrongException("You did it, you broke it!");
        }
    }
}
