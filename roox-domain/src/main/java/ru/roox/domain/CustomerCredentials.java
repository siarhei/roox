package ru.roox.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@Embeddable
public class CustomerCredentials {
    @Column(length = ConstraintConstants.NAME_LENGTH)
    private String login;
    @Column(name = "PWDHASH")
    private String passwordHash;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
