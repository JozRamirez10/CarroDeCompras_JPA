package webapp.service;

import jakarta.enterprise.inject.Alternative;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

// @Alternative
public class LoginServiceCookieImpl implements LoginService{
    @Override
    public Optional<String> getUsername(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0]; // Obtenemos las cookies
        return Arrays.stream(cookies) // Convertimos las cookies en stream
                .filter(c -> "username".equals(c.getName())) // Filtramos la cookie "username"
                .map(Cookie::getValue) // Lo convierte a String
                .findAny();
    }
}
