package com.medua.apostlesbridgenext.handler;

import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

public final class RespectGuildToggleMessages {
    private RespectGuildToggleMessages() { }

    public static void sendSettingMessage(String beforeSetting, String afterSetting) {
        Text message = Text.literal(MessageHandler.getPrefix(true, true))
            .append(Text.literal(beforeSetting))
            .append(settingText())
            .append(Text.literal(afterSetting));
        MessageHandler.sendMessage(message);
    }

    private static Text settingText() {
        return Text.literal("Respect /g toggle").styled(style -> style
            .withColor(0xAA00AA)
            .withHoverEvent(new HoverEvent.ShowText(Text.literal("Pauses the bridge when Hypixel guild chat is disabled via /g toggle.\nYou can change this in /bridge settings."))));
    }
}
