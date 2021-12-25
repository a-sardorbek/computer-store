package com.system.ws.domain.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "systemUser")
public class SystemUser {

    private final static String NUMBER_GENERATOR = "systemUser_generator";
    private final static String SEQUENCE_NAME = "systemUser_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NUMBER_GENERATOR)
    @SequenceGenerator(name=NUMBER_GENERATOR, sequenceName = SEQUENCE_NAME,allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "userId",nullable = false)
    private String userId;

    @Column(name = "fio")
    private String fio;

    private String username;
    private String password;

    @Lob
    private byte[] profileImage;

    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;

    private String role;
    private String[] authorities;


    private boolean isActive;
    private boolean isNotLocked;

    public SystemUser(){}

    public SystemUser(Long id
                    , String userId
                    , String fio
                    , String username
                    , String password
                    , byte[] profileImage
                    , Date lastLoginDate
                    , Date lastLoginDateDisplay
                    , Date joinDate
                    , String role
                    , String[] authorities
                    , boolean isActive
                    , boolean isNotLocked) {
        this.id = id;
        this.userId = userId;
        this.fio = fio;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDateDisplay() {
        return lastLoginDateDisplay;
    }

    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
        this.lastLoginDateDisplay = lastLoginDateDisplay;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }
}
