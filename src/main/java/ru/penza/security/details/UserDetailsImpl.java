package ru.penza.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.penza.models.User;

import java.util.Collection;
import java.util.Collections;


// связываем UserDetails с нашим объектом User, закидывая юзера в details
public class UserDetailsImpl implements UserDetails {

    private User user;

    UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getHashPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public int hashCode() {
        return user.getLogin() != null ? user.getLogin().hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserDetailsImpl) {
            return user.getLogin().equals(((UserDetailsImpl) obj).getUsername());
        }
        return false;
    }
}
