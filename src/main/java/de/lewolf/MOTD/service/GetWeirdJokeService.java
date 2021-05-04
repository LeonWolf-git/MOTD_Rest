package de.lewolf.MOTD.service;

import de.lewolf.MOTD.repository.GetWeirdJokeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetWeirdJokeService {

    private GetWeirdJokeDao dao;

    public GetWeirdJokeService(GetWeirdJokeDao dao) {
        this.dao = dao;
    }

    public String getWeirdJoke(){
        return dao.getWeirdJoke();
    }
}
