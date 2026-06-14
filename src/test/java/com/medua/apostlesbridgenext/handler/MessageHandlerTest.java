package com.medua.apostlesbridgenext.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlerTest {
    @Test
    void bracketPrefixRequestsUseStandardModPrefix() {
        assertEquals("\u00A75AB\u00A7r > ", MessageHandler.getPrefix(true, true));
    }
}
