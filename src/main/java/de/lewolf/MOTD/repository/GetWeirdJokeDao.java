package de.lewolf.MOTD.repository;

import de.lewolf.MOTD.exceptions.URLNotResponsiveException;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

@Component
public class GetWeirdJokeDao {

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
                return "Wir haben leider kein Motto des Tages gefunden, daher habe doch diesen Witz: " + message;
            }
        } catch (IOException | ParseException | JSONException e) {
            throw new URLNotResponsiveException("Die aufgerufene Website: " + stringurl + " konnte nicht geladen werden!");
        }
    }
}
