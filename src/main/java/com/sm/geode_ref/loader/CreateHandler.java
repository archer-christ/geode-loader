package com.sm.geode_ref.loader;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by smanvi on 8/9/16.
 */
public class CreateHandler {

    private static final int BATCH_PERCENTAGE = 5;
    ExecutorService createThreadPool = Executors.newCachedThreadPool();


    public void handle(String command) {
        if(command!=null && command.trim().length()!=0){
            StringTokenizer tokenizer = new StringTokenizer(command);
            String[] arr = new String[3];
            int i=0;
            while(tokenizer.hasMoreTokens()){
                arr[i++] = tokenizer.nextToken();
            }
            final String size= arr[1];
            final String regionName = arr[2];
            System.out.println("### Create size: "+size+", region: "+regionName);

            createThreadPool.submit(new Runnable() {
                public void run() {
                    ClientCache clientCache = new ClientCacheFactory().set("cache-xml-file", "client-cache.xml").create();
                    Region<Integer,byte[]> region =  clientCache.getRegion(regionName);
                    if(size.endsWith("MB")){
                        int mbs = Integer.parseInt(size.split("MB")[0]);
                        int tenPercent = mbs * ( BATCH_PERCENTAGE / 100);
//                        Map<Integer, byte[]> batchMap = new HashMap<Integer, byte[]>(tenPercent);
                        Map<Integer, byte[]> batchMap = new HashMap<Integer, byte[]>(50);
                        for (int j = 1; j <= mbs; j++) {

                            batchMap.put(Common.CURRENT_MAX_KEY.incrementAndGet(), new byte[Common.MB]);
//                            batchMap.put(++MAX_KEY, new byte[Common.MB]);

                            if(j % 50 == 0){
                                region.putAll(batchMap);
                                batchMap.clear();
                            }
                        }
                        region.putAll(batchMap);
                    }
                    System.out.println(String.format("############# Done Creating %d ",size," for region %s",regionName));
                }
            });
        }
    }
}
