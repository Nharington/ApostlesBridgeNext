package com.medua.apostlesbridgenext.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.medua.apostlesbridgenext.client.ApostlesBridgeNextClient;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;

public final class EmojiUtil {
    private static final Pattern SHORTCODE_PATTERN = Pattern.compile(":[a-zA-Z0-9_+\\-]+:");
    private static final String SHORTCODE_RESOURCE = "/assets/apostlesbridgenext/emoji_shortcodes.json";
    private static final Type SHORTCODE_LIST_TYPE = new TypeToken<List<String>>() { }.getType();
    private static final int PUA_START = 0xE000;
    private static final Map<String, String> EMOJIS = new HashMap<>();
    private static final Map<String, String> FONT_GLYPHS = new HashMap<>();
    private static FontDescription emojiFont;

    static {
        loadShortcodes();
        alias(":+1:", ":thumbsup:");
        alias(":-1:", ":thumbsdown:");
        alias(":red_heart:", ":heart:");
        alias(":shit:", ":poop:");
        alias(":facepalm:", ":person_facepalming:");
    }

    private EmojiUtil() { }

    public static int shortcodeCount() {
        return FONT_GLYPHS.size();
    }

    public static String glyphFor(String shortcode) {
        if (shortcode == null || shortcode.isEmpty()) {
            return null;
        }
        return FONT_GLYPHS.get(shortcode.toLowerCase());
    }

    public static String replaceShortcodes(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        Matcher matcher = SHORTCODE_PATTERN.matcher(message);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            String replacement = EMOJIS.get(matcher.group().toLowerCase());
            if (replacement != null) {
                matcher.appendReplacement(builder, Matcher.quoteReplacement(replacement));
            }
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    public static MutableComponent replaceShortcodesWithFont(String message) {
        if (message == null || message.isEmpty()) {
            return Component.literal(message == null ? "" : message);
        }

        Matcher matcher = SHORTCODE_PATTERN.matcher(message);
        MutableComponent text = Component.empty();
        int lastEnd = 0;
        while (matcher.find()) {
            String shortcode = matcher.group().toLowerCase();
            String glyph = FONT_GLYPHS.get(shortcode);
            if (glyph == null) {
                continue;
            }

            if (matcher.start() > lastEnd) {
                text.append(message.substring(lastEnd, matcher.start()));
            }
            text.append(Component.literal(glyph).withStyle(style -> style.withFont(emojiFont())));
            lastEnd = matcher.end();
        }

        if (lastEnd < message.length()) {
            text.append(message.substring(lastEnd));
        }
        return text;
    }

    private static FontDescription emojiFont() {
        if (emojiFont == null) {
            emojiFont = createEmojiFontDescription();
        }
        return emojiFont;
    }

    private static void loadShortcodes() {
        InputStream stream = EmojiUtil.class.getResourceAsStream(SHORTCODE_RESOURCE);
        if (stream == null) {
            return;
        }

        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            List<String> shortcodes = new Gson().fromJson(reader, SHORTCODE_LIST_TYPE);
            if (shortcodes == null) {
                return;
            }
            for (int index = 0; index < shortcodes.size(); index++) {
                String shortcode = shortcodes.get(index);
                if (shortcode == null || shortcode.isEmpty()) {
                    continue;
                }
                String glyph = String.valueOf((char) (PUA_START + index));
                FONT_GLYPHS.put(shortcode.toLowerCase(), glyph);
                EMOJIS.put(shortcode.toLowerCase(), glyph);
            }
        } catch (Exception ignored) {
        }
    }

    private static void alias(String shortcode, String target) {
        String glyph = FONT_GLYPHS.get(target);
        if (glyph != null) {
            FONT_GLYPHS.put(shortcode, glyph);
            EMOJIS.put(shortcode, glyph);
        }
    }

    private static FontDescription createEmojiFontDescription() {
        Object identifier = MinecraftReflectionUtil.createResourceId(ApostlesBridgeNextClient.MODID, "emoji");
        return (FontDescription) MinecraftReflectionUtil.newInstance(
            new String[]{
                "net.minecraft.network.chat.FontDescription$Resource",
                "net.minecraft.class_11719$class_11721"
            },
            identifier
        );
    }
}
