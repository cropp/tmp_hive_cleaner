package com.teamropp;

import org.apache.hadoop.util.ProgramDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Command line driver for running hadoop jars
 * Example Usage
 *  hadoop jar cleaner-job.jar CleanHiveTmp
 * Created by cropp on 1/11/17.
 */
public class CliDriver {
    private static final Logger logger = LoggerFactory.getLogger(CliDriver.class);

    public static void main(String[] args) {
        int exitCode = -1;
        ProgramDriver driver = new ProgramDriver();
        try {
            driver.addClass(
                    "CleanHiveTmp",
                    TmpHiveDirectoryCleaner.class,
                    "Utility for cleaning the /tmp/hive/<user> directories");
            exitCode = driver.run(args);
        } catch (Throwable t) {
            logger.error(t.getMessage());
        }
        System.exit(exitCode);
    }

}
