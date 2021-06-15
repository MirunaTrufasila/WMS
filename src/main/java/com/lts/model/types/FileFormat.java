package com.lts.model.types;

import org.apache.commons.lang3.StringUtils;

public enum FileFormat {

    DLQF,
    DLQP,
    DLAH;

    public static FileFormat of(String format) {
        switch (StringUtils.defaultString(format.toUpperCase())) {
            case "F":
                return FileFormat.DLQF;
            case "P":
                return FileFormat.DLQP;
            case "A":
                return FileFormat.DLAH;
        }

        return null;
    }
}