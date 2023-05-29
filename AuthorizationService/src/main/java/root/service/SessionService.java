package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.database.model.Session;
import root.database.model.User;
import root.database.repo.SessionRepository;
import root.util.JwtUtils;

import java.time.ZoneId;

@Service
public class SessionService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SessionRepository sessionRepository;

    public void registerNewSession(String token, User user) {
        Session session = new Session();
        session.setSessionToken(token);
        session.setUser(user);
        session.setExpiresAt(jwtUtils.getExpirationDateFromToken(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        sessionRepository.save(session);
    }

}
