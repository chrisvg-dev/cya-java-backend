package com.cvg.cya.postulacion.models;

import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@ToString
public class UserPrincipal implements UserDetails {
    private Long id;
    private Collection<? extends GrantedAuthority> authorities;
    private Set<UserMenu> menu;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String safetyWord;

    public UserPrincipal(
            Collection<? extends GrantedAuthority> authorities,
            Set<UserMenu> menu,
            String name,
            String lastName,
            String email,
            String password, String safetyWord) {
        this.authorities = authorities;
        this.menu = menu;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.safetyWord = safetyWord;
    }

    public String getSafetyWord() {
        return safetyWord;
    }

    public void setSafetyWord(String safetyWord) {
        this.safetyWord = safetyWord;
    }

    public Set<UserMenu> getMenu() {
        return menu;
    }

    public void setMenu(Set<UserMenu> menu) {
        this.menu = menu;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
