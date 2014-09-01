package ru.roox.web.ws.model;

import ru.roox.web.constants.RooxHttpConstants;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 25.05.2014
 */
public class SuccessResponse extends Response {
    private static final long serialVersionUID = -1981278400574311234L;

    private SuccessResponse() {
    }

    private static SuccessResponse instance = new SuccessResponse();
    public static SuccessResponse getInstance() {
        return instance;
    }

    @Override
    public String getResult() {
        return RooxHttpConstants.SUCCESS_RESULT;
    }
}
