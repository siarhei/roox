package ru.roox.domain;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@Embeddable
public class AvatarInfo {
    private String name;
    @Lob
    private byte[] data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
