package webapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import webapp.configs.ServiceStereotype;

import java.util.Optional;

@ApplicationScoped
public class LoginServiceSessionImpl implements LoginService{
    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if(username != null){
            return Optional.of(username); // Lo convertimos a Optional<String>
        }
        return Optional.empty();
    }
}
