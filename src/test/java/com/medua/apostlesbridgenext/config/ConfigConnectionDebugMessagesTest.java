package com.medua.apostlesbridgenext.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigConnectionDebugMessagesTest {
    @Test
    void connectionDebugMessagesCanBeToggled() {
        Config.setConnectionDebugMessagesEnabled(false);
        assertFalse(Config.isConnectionDebugMessagesEnabled());

        Config.setConnectionDebugMessagesEnabled(true);
        assertTrue(Config.isConnectionDebugMessagesEnabled());
    }
}
