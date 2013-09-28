package org.glydar.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import org.glydar.api.Backend;
import org.glydar.api.logging.GlydarLogger;
import org.glydar.core.logging.CoreGlydarLogger;
import org.glydar.core.logging.CoreGlydarLoggerFormatter;

public abstract class CoreBackend implements Backend {

    private final Path             baseFolder;
    private final Path             configFolder;
    private final CoreGlydarLogger logger;

    public CoreBackend() {
        this.baseFolder = initBaseFolder();
        this.configFolder = baseFolder.resolve("config");
        this.logger = initLogger();
    }

    private Path initBaseFolder() {
        try {
            URI sourceUri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            Path path = Paths.get(sourceUri).getParent();
            return path;
        }
        catch (URISyntaxException exc) {
            return Paths.get("");
        }
    }

    private CoreGlydarLogger initLogger() {
        CoreGlydarLogger logger = CoreGlydarLogger.of(this, getName());
        logger.getJdkLogger().setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CoreGlydarLoggerFormatter(false));
        consoleHandler.setLevel(Level.ALL);
        logger.getJdkLogger().addHandler(consoleHandler);

        try {
            String folder = getClass().getProtectionDomain().getCodeSource().getLocation().getFile().toString();
            FileHandler fileHandler = new FileHandler(folder + "/../logs");
            fileHandler.setFormatter(new CoreGlydarLoggerFormatter(false));
            fileHandler.setLevel(Level.ALL);
            logger.getJdkLogger().addHandler(fileHandler);
        }
        catch (SecurityException | IOException exc) {
            logger.warning(exc, "Unable to open log file");
        }

        return logger;
    }

    @Override
    public Path getBaseFolder() {
        return baseFolder;
    }

    @Override
    public Path getConfigFolder() {
        return configFolder;
    }

    @Override
    public GlydarLogger getLogger(Class<?> clazz) {
        return logger.getChildLogger(clazz, clazz.getSimpleName());
    }

    @Override
    public GlydarLogger getLogger(Class<?> clazz, String prefix) {
        return logger.getChildLogger(clazz, prefix);
    }
}
