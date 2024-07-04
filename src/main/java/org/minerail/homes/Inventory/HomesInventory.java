package org.minerail.homes.Inventory;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.papermc.paper.math.FinePosition;
import io.papermc.paper.math.Position;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.minerail.homes.File.Config.Config;
import org.minerail.homes.File.Config.ConfigKeys;
import org.minerail.homes.File.PlayerData.PlayerData;
import org.minerail.homes.Homes;
import org.minerail.homes.Util.PlayerUtil;
import org.minerail.homes.Util.TextFormatUtil;

import java.util.Random;


public class HomesInventory {
    private static Player player;
    private static Gui homesInv;
    private final Random random = new Random();
    private HomesInventory(Player player) {
        this.player = player;
        buildGui();
        buildIcons();
    }

    public static HomesInventory get(Player player) {
        return new HomesInventory(player);
    }

    private void buildGui() {
        homesInv = Gui.gui()
                .title(TextFormatUtil.format(Config.getString(ConfigKeys.GUI_TITLE)))
                .rows(Config.getInt(ConfigKeys.GUI_SIZE))
                .type(GuiType.CHEST)
                .disableAllInteractions()
                .create();
    }

    private void buildIcons() {
        for (String name : PlayerData.get(player).getHomes()) {
            GuiItem item = ItemBuilder.from(
                    getMaterial())
                    .name(TextFormatUtil.format(Config.getString(ConfigKeys.GUI_ITEM_COLOR_OF_NAME),
                            Placeholder.component("name", Component.text(name)))
                    )
                    .flags(ItemFlag.HIDE_ATTRIBUTES)
                    .asGuiItem(e -> {
                        if (!Config.getBoolean(ConfigKeys.TELEPORT_DELAY_IS_ENABLED)) {
                            PlayerUtil.teleportToHome(player, name);
                        } else {
                            FinePosition finePosition = Position.fine(player.getLocation());
                            PlayerUtil.runDelayedTeleport(player, finePosition, name);
                        }
                        Homes.get().getLogger().info(player.getName() + " Teleported to home: " + name);
            });

            homesInv.addItem(item);
        }
    }

    public void open() {
        homesInv.open(this.player);
    }

    private Material getMaterial() {
        if (Config.getBoolean(ConfigKeys.GUI_MATERIALS_RANDOM)) {

            Material[] materials = Material.values();
            Material randomMaterial;

            do {
                int index = random.nextInt(materials.length);
                randomMaterial = materials[index];
            } while (!randomMaterial.isItem() && !randomMaterial.isAir());

            return randomMaterial;
        } else {
            return Config.getMaterial(ConfigKeys.GUI_CUSTOM_MATERIALS_TYPE);
        }
    }

}
