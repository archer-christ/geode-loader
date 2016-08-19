package com.sm.geode_ref.loader;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smanvi on 8/9/16.
 */
public class RemoveHandler {

    ExecutorService removeThreadPool = Executors.newCachedThreadPool();

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
            System.out.println("### Remove size: "+size+", region: "+regionName);

            removeThreadPool.submit(new Runnable() {
                public void run() {
                    ClientCache clientCache = new ClientCacheFactory().set("cache-xml-file", "client-cache.xml").create();
                    Region<Integer,DomainObj> region =  clientCache.getRegion(regionName);
                    if(size.endsWith("MB")){
                        int mbs = Integer.parseInt(size.split("MB")[0]);
                        Set<Integer> removeKeys = new HashSet<Integer>(mbs);
                        int end = Common.CURRENT_MAX_KEY.get();
//                        int end = 600;
                        int start = end - mbs;
                        for (int j = start+1; j <= end; j++) {
                            removeKeys.add(j);
                        }
                        System.out.println("######## Removing "+ removeKeys.size()+ " number of entries");
                        region.removeAll(removeKeys);
                        Common.CURRENT_MAX_KEY.addAndGet(-mbs);
                    }
                    System.out.println(String.format("############# Done removing %d ",size," for region %s",regionName));
                }
            });
        }
    }
}
