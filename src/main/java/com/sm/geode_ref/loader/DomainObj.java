package com.sm.geode_ref.loader;

import java.io.Serializable;

/**
 * Created by smanvi on 8/9/16.
 */
public class DomainObj implements Serializable{

    int id;
    byte[] data;

    public DomainObj(byte[] data) {
        this.data = data;
    }

    public DomainObj(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }
}
