package net.artyrian.insufferable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vertisoft.vectorlib.VectorLib;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

public class InsRemarksConfig
{
    private static final File CONFIG = new File(VectorLib.PLATFORM.getConfigDirectory().toFile(), "insufferable-remarks.json");

    private final Client client;

    public InsRemarksConfig()
    {
        this.client = new Client();
    }

    public static InsRemarksConfig load_config()
    {
        InsRemarksConfig config = new InsRemarksConfig();
        if (!CONFIG.exists()) save_config(config);

        Reader reader;
        try
        {
            reader = Files.newBufferedReader(CONFIG.toPath());
            Gson xnl = new GsonBuilder().setPrettyPrinting().create();
            config = xnl.fromJson(reader, InsRemarksConfig.class);
            reader.close();

            InsRemarks.LOGGER.info("Insufferable Remarks config file loaded");
        }
        catch (IOException error)
        {
            InsRemarks.LOGGER.error("Could not load/read Insufferable Remarks config file! Resorting to default configs - please check for config file's existence and try again.", error);
        }

        return config;
    }

    public static void save_config(InsRemarksConfig file)
    {
        try
        {
            Writer writer = Files.newBufferedWriter(CONFIG.toPath());
            Gson xni = new GsonBuilder().setPrettyPrinting().create();
            xni.toJson(file, writer);
            writer.close();

            InsRemarks.LOGGER.info("Insufferable Remarks config file saved");
        }
        catch (IOException error)
        {
            InsRemarks.LOGGER.error("A critical error occured while saving the Insufferable Remarks config file. Please make sure the file isn't damaged and try again.", error);
        }
    }

    private static class Client
    {
        private boolean enabled = true;                    // Setting to false disables the mod entirely. For some reason.
        private boolean doBobbing = false;                 // If true, the text will bob like splash texts.
    }

    // Client
    public boolean isEnabled() { return this.client.enabled; }
    public boolean shouldBob() { return this.client.doBobbing; }
}
