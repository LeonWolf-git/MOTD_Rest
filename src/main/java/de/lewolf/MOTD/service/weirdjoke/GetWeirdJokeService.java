package de.lewolf.MOTD.service.weirdjoke;

import de.lewolf.MOTD.models.Message;
import org.springframework.stereotype.Service;

@Service
public class GetWeirdJokeService {

    private WeirdJokeRestClient restClient;

    public GetWeirdJokeService(WeirdJokeRestClient restClient) {
        this.restClient = restClient;
    }

    public Message getRandomWeirdJoke() {
        return new Message(restClient.getWeirdJoke(), null);
    }

}
