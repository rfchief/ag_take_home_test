package com.agoda.assessment.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class AgErrorResponse implements Serializable {
    private static final long serialVersionUID = 6880952197469606404L;

    private final int status;
    private final String error;
    private final String path;
    private final String timeStamp;

    public AgErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.path = (String) errorAttributes.get("path");
        this.timeStamp = errorAttributes.get("timestamp").toString();
    }
}
