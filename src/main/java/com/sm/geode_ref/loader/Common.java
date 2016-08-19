package com.sm.geode_ref.loader;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by smanvi on 8/9/16.
 */
public class Common {

    public static final int KB = 1204;
    public static final int MB = KB * 1204;
    public static final int GB = MB * 1204;

    public static final AtomicInteger CURRENT_MAX_KEY = new AtomicInteger();
    public static int MAX_KEY = 0;
}
