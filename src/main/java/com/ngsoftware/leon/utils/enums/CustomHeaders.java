package com.ngsoftware.leon.utils.enums;

import lombok.Getter;

@Getter
public enum CustomHeaders {
    MESSAGE_HEADER("message");

    private String headerName;

    private CustomHeaders(String headerName) {
        this.headerName = headerName;
    }

}
