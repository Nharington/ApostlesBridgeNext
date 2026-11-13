package com.medua.apostlesbridgenext.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmojiUtilTest {
    @Test
    void loadsDiscordShortcodeCatalog() {
        assertTrue(EmojiUtil.shortcodeCount() >= 1556);
        assertNotNull(EmojiUtil.glyphFor(":fire:"));
        assertNotNull(EmojiUtil.glyphFor(":rocket:"));
        assertNull(EmojiUtil.glyphFor(":apostles:"));
        assertNull(EmojiUtil.glyphFor(":pregant_man:"));
        assertNull(EmojiUtil.glyphFor(":not_a_real_emoji:"));
    }

    @Test
    void matchesShortcodesCaseInsensitively() {
        assertEquals(EmojiUtil.glyphFor(":fire:"), EmojiUtil.glyphFor(":FIRE:"));
        assertEquals(EmojiUtil.glyphFor(":skull:"), EmojiUtil.glyphFor(":SkUlL:"));
    }

    @Test
    void keepsCommonAliases() {
        assertEquals(EmojiUtil.glyphFor(":thumbsup:"), EmojiUtil.glyphFor(":+1:"));
        assertEquals(EmojiUtil.glyphFor(":thumbsdown:"), EmojiUtil.glyphFor(":-1:"));
        assertEquals(EmojiUtil.glyphFor(":heart:"), EmojiUtil.glyphFor(":red_heart:"));
        assertEquals(EmojiUtil.glyphFor(":poop:"), EmojiUtil.glyphFor(":shit:"));
        assertEquals(EmojiUtil.glyphFor(":person_facepalming:"), EmojiUtil.glyphFor(":facepalm:"));
    }

    @Test
    void replaceShortcodesLeavesUnknownTextAlone() {
        assertEquals("hello :not_real:", EmojiUtil.replaceShortcodes("hello :not_real:"));
        assertEquals("plain chat", EmojiUtil.replaceShortcodes("plain chat"));
    }

    @Test
    void replaceShortcodesUsesSingleGlyphs() {
        String fire = EmojiUtil.glyphFor(":fire:");
        String rocket = EmojiUtil.glyphFor(":rocket:");
        assertEquals("emoji test " + fire + " " + rocket, EmojiUtil.replaceShortcodes("emoji test :fire: :rocket:"));
        assertEquals(1, fire.length());
    }
}
