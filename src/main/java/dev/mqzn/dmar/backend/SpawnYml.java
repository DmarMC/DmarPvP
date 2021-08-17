package dev.mqzn.dmar.backend;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.util.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginAwareness;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class SpawnYml {

    private final File file;
    private FileConfiguration config;

    public SpawnYml() {
        file = new File(DmarPvP.getInstance().getDataFolder(), "spawn.yml");

        if(!file.exists()) {
            DmarPvP.getInstance().saveResource("spawn.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public void save() {
        try{
            config.save(file);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isStrictlyUTF8() {
        return DmarPvP.getInstance().getDescription()
                .getAwareness().contains(PluginAwareness.Flags.UTF8);
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
        InputStream defConfigStream = DmarPvP.getInstance().getResource("spawn.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig;
            if (!this.isStrictlyUTF8() && !FileConfiguration.UTF8_OVERRIDE) {
                defConfig = new YamlConfiguration();

                byte[] contents;
                try {
                    contents = ByteStreams.toByteArray(defConfigStream);
                } catch (IOException ex) {
                    Logger.log("Unexpected failure reading config.yml", Logger.LogType.ERROR);
                    return;
                }

                String text = new String(contents, Charset.defaultCharset());
                if (!text.equals(new String(contents, Charsets.UTF_8))) {
                    Logger.log("Default system encoding may have misread config.yml from plugin jar", Logger.LogType.WARN);
                }

                try {
                    defConfig.loadFromString(text);
                } catch (InvalidConfigurationException ex) {
                    Logger.log("Cannot load configuration from jar", Logger.LogType.ERROR);
                }

            } else {
                defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
            }

            this.config.setDefaults(defConfig);
        }
    }


}
