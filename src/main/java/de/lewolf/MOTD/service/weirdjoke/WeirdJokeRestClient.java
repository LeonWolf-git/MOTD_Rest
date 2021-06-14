package de.lewolf.MOTD.service.weirdjoke;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.lewolf.MOTD.exceptions.URLNotResponsiveException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeirdJokeRestClient {

    private final RestTemplate restTemplate = new RestTemplate();

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
}
