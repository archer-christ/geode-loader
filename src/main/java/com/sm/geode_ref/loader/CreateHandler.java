package com.sm.geode_ref.loader;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by smanvi on 8/9/16.
 */
public class CreateHandler {
    static Logger logger = Logger.getLogger(CreateHandler.class.getSimpleName());

    private static final int BATCH_SIZE = 20;
    ExecutorService createThreadPool = Executors.newCachedThreadPool();


    public void handle(CommandHolder holder) {
        //Submit to a pool as we want to handle multiple create commands in parallel
        createThreadPool.submit(new CreateCommandRunner(holder));
    }

    private class CreateCommandRunner implements Runnable {

        CommandHolder holder;

        public CreateCommandRunner(CommandHolder holder) {
            this.holder = holder;
        }

        public void run() {
            String command = holder.getCommand();
            logger.info(String.format("##### Staring to run command : <" + command + ">"));
            if (command != null && command.trim().length() != 0) {
                StringTokenizer tokenizer = new StringTokenizer(command);
                String[] arr = new String[3];
                int i = 0;
                while (tokenizer.hasMoreTokens()) {
                    arr[i++] = tokenizer.nextToken();
                }
                final String size = arr[1];
                final String regionName = arr[2];

                ClientCache clientCache = new ClientCacheFactory().set("cache-xml-file", "client-cache.xml").create();
                Region<Long, byte[]> region = clientCache.getRegion(regionName);
                if (size.endsWith("MB")) {
                    double mbs = Double.parseDouble(size.split("MB")[0]);
                    Map<Long, byte[]> batchMap = new HashMap<Long, byte[]>(BATCH_SIZE);

                    for (long j = 1; j < mbs - 1; j++) {
                        batchMap.put(j, new byte[Common.MB]);
                        if (j % BATCH_SIZE == 0) {
                            region.putAll(batchMap);
                            batchMap.clear();
                            logger.info("Inserted "+j+" Entries for command <"+command+"> ...");
                        }
                    }
                    region.putAll(batchMap);
                }
                logger.info(String.format("##### Completed running command : <" + command + ">"));
            }
        }
    }
}

