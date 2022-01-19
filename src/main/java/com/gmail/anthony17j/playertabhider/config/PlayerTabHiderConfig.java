package com.gmail.anthony17j.playertabhider.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.exception.FiberException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerTabHiderConfig {

    public static final Path path = FabricLoader.getInstance().getConfigDir().resolve("playertabhider.json");

    public static final PropertyMirror<Boolean> hideTabEnabled = PropertyMirror.create(ConfigTypes.BOOLEAN);
    public static final PropertyMirror<Boolean> hideCountEnabled = PropertyMirror.create(ConfigTypes.BOOLEAN);
    public static final PropertyMirror<String[]> playerListList = PropertyMirror.create(ConfigTypes.makeArray(ConfigTypes.STRING));
    public static final PropertyMirror<String[]> playerCountList = PropertyMirror.create(ConfigTypes.makeArray(ConfigTypes.STRING));

    public static final ConfigTree tree = ConfigTree.builder()
            .fork("hide_from_player_list")
            .withMirroredValue("enabled", hideTabEnabled, false)
            .withMirroredValue("value", playerListList, new String[] { "Player1", "Player2" })
            .finishBranch()
            .fork("hide_from_player_count")
            .withMirroredValue("enabled", hideCountEnabled, false)
            .withMirroredValue("value", playerCountList, new String[] { "Player1", "Player2" })
            .finishBranch()
            .build();

    private static final JanksonValueSerializer serializer = new JanksonValueSerializer(false);

    public static void load() {
        if(Files.exists(path)) {
            try {
                FiberSerialization.deserialize(tree, Files.newInputStream(path), serializer);
            } catch (IOException | FiberException e) {
                save();
            }
        } else
            save();
    }

    public static void save() {
        try {
            FiberSerialization.serialize(tree, Files.newOutputStream(path), serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
