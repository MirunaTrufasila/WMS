package com.lts.util.logging;

import ch.qos.logback.classic.PatternLayout;
import com.lts.util.logging.converter.RemoteHostConverter;
import com.lts.util.logging.converter.UserConverter;

public class CustomPatternLayout extends PatternLayout {
    static {
        PatternLayout.defaultConverterMap.put("host", RemoteHostConverter.class.getName());
        PatternLayout.defaultConverterMap.put("user", UserConverter.class.getName());
    }
}