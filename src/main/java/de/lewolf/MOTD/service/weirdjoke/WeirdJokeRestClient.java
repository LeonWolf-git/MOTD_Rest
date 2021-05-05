package de.lewolf.MOTD.service.weirdjoke;

import de.lewolf.MOTD.exceptions.URLNotResponsiveException;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

@Service
public class WeirdJokeRestClient {

    public String getWeirdJoke() {
        String stringurl = "http://api.icndb.com/jokes/random";
        try {
            URL url = new URL(stringurl); //Todo: Rest Template verwenden
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
}
