package com.medua.apostlesbridgenext.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessageHandlerTest {
    @Test
    void bracketPrefixRequestsUseStandardModPrefix() {
        assertEquals("\u00A75AB\u00A7r > ", MessageHandler.getPrefix(true, true));
    }
}
