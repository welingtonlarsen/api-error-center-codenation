package com.api.error.center.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Source implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private SourceProfile sourceProfile;

    public Source() {

    }

    public Source(String username, String password, SourceProfile sourceProfile) {
        this.username = username;
        this.password = password;
        this.sourceProfile = sourceProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SourceProfile getSourceProfile() {
        return this.sourceProfile;
    }

    public void setSourceProfile(SourceProfile sourceProfile) {
        this.sourceProfile = sourceProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SourceProfile> sourceProfiles = new ArrayList<>();
        sourceProfiles.add(sourceProfile);
        return sourceProfiles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
