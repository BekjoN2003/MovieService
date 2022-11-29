package com.example.test.config;

import com.example.test.entity.Role;
import com.example.test.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter

public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String userName;
    private String password;
    private Boolean enabled;
    private Role role;

    private final List<GrantedAuthority> authorityList;

    public CustomUserDetails(User profile) {
        this.id = profile.getId();
        this.userName = profile.getEmail();
        this.password = profile.getPassword();
        this.enabled = profile.getStatus();
        this.role = profile.getRole();

        this.authorityList = Arrays.asList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    @Override
    public String getPassword() {
        System.out.println("CustomUserDetails: getPassword()");
        return password;
    }

    @Override
    public String getUsername() {
        System.out.println("CustomUserDetails: getUsername()");
        return userName;
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
        return enabled;
    }


    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", role='" + role + '\'' +
                ", authorityList=" + authorityList +
                '}';
    }
}
