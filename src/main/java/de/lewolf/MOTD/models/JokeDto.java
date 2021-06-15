package de.lewolf.MOTD.models;

import java.util.List;

public class JokeDto {

    //"value": { "id": 23, "joke": "Time waits for no man. Unless that man is Chuck Norris.", "categories": [] }

    private final int id;
    private final String jokeText;
    private final List<String> categories;

    public JokeDto(int id, String jokeText, List<String> categories) {
        this.id = id;
        this.jokeText = jokeText;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getJokeText() {
        return jokeText;
    }

    public List<String> getCategories() {
        return categories;
    }

}
