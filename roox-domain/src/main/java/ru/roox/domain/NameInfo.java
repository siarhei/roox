package ru.roox.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@Embeddable
public class NameInfo {
    @Column(length = ConstraintConstants.NAME_LENGTH)
    private String firstName;
    @Column(length = ConstraintConstants.NAME_LENGTH)
    private String midName;
    @Column(length = ConstraintConstants.NAME_LENGTH)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s", firstName, midName, lastName);
    }
}
