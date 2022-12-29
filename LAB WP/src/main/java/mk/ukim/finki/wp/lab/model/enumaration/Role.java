package mk.ukim.finki.wp.lab.model.enumaration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_STUDENT, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
