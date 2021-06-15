package de.lewolf.MOTD.models;

public class JokeResponseDto {
    //{ "type": "success", "value": { "id": 23, "joke": "Time waits for no man. Unless that man is Chuck Norris.", "categories": [] } }

    private final String type;
    private final JokeDto value;

    public JokeResponseDto(String type, JokeDto value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public JokeDto getValue() {
        return value;
    }
}
