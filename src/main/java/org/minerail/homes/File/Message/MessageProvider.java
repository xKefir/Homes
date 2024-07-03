package org.minerail.homes.File.Message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MessageProvider {

    // String <input>, Set<String> <homes>, String <prefix>
    public static Component message(String message, TagResolver... resolvers) {
        if (MessageProviderLoader.getString("input-type").equals("LEGACY")) {
            return LegacyComponentSerializer.legacyAmpersand().deserialize(
                    LegacyComponentSerializer.legacyAmpersand().serialize(
                            MiniMessage.builder().build().deserialize(message, resolvers))
            );
        } else {
            MiniMessage.miniMessage().deserialize(message, resolvers);
        }

        return null;
    }

    public static Component get(MessageKey key, TagResolver... resolvers) {
        return message(MessageProviderLoader.getString(key.getPath()), resolvers);
    }
}
