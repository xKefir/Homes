package org.minerail.homes.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.minerail.homes.File.Message.MessageKey;
import org.minerail.homes.File.Message.MessageProviderLoader;

public class TextFormatUtil {

    public static Component format(String input, TagResolver... resolvers) {
        if (MessageProviderLoader.getString(MessageKey.INPUT_TYPE.getPath()).equals("LEGACY")) {
            return LegacyComponentSerializer.legacyAmpersand().deserialize(
                    LegacyComponentSerializer.legacyAmpersand().serialize(
                            MiniMessage.builder().build().deserialize(input, resolvers))
            );
        }
        return MiniMessage.miniMessage().deserialize(input, resolvers);
    }
}
