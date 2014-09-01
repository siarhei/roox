package ru.roox.web.ws.model;

import java.io.Serializable;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 25.05.2014
 */
public abstract class Response implements Serializable {
    abstract String getResult();
}
