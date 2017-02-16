package com.teamropp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * This application will go through /tmp/hive/<subdir> and remove any directories
 * that are older than the default 3 days
 * Created by cropp on 2/16/17.
 */
public class TmpHiveDirectoryCleaner extends Configured implements Tool {
    private static final Logger logger = LoggerFactory.getLogger(TmpHiveDirectoryCleaner.class);

    private final int daysBack = 3;
    private final Path tmpHiveRootPath = new Path("/tmp/hive");


    public TmpHiveDirectoryCleaner(Configuration conf) throws IOException {
        super(conf);
        logger.info("Thanks for using my utility. If you like it please follow me on GitHub");
    }

    public static void main(String[] args) throws Exception {
        new TmpHiveDirectoryCleaner(new Configuration()).run(args);
    }

    @Override
    public int run(String[] args) throws Exception {
        // Defaults to 3 days | 72 hours back
        DateTime now = DateTime.now().minusDays(daysBack);
        FileSystem fs = FileSystem.get(getConf());
        Arrays.stream(fs.listStatus(tmpHiveRootPath)).forEach((userDir)-> {
            if (userDir.isDirectory()) {
                try {
                    Arrays.stream(fs.listStatus(userDir.getPath())).forEach(fileStatus1 -> {
                        if (fileStatus1.isDirectory() && new DateTime(fileStatus1.getModificationTime()).isBefore(now)) {
                            System.out.println("Recursively deleting directory: " + fileStatus1.getPath() +
                                    " with modified time: " + new DateTime(fileStatus1.getModificationTime()));
                            try {
                                fs.delete(fileStatus1.getPath(), true);
                            } catch (IOException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    });
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

            } else {
                logger.warn("These should all be directories in /tmp/hive/<userDir> but things happen");
            }
        });
        return 0;
    }
}
