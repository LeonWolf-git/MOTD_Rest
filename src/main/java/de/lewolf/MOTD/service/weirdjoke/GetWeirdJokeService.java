package de.lewolf.MOTD.service.weirdjoke;

import de.lewolf.MOTD.models.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GetWeirdJokeService {

    private final WeirdJokeRestClient restClient;

    public GetWeirdJokeService(WeirdJokeRestClient restClient) {
        this.restClient = restClient;
    }

    public Message getRandomWeirdJoke(LocalDate dom) {
        return new Message(restClient.getWeirdJokeTemplate(), dom);
    }

}
