package com.example.tracker.model;

import java.io.Serializable;
import java.util.Arrays;

public class Route implements Serializable {
    private final String name;
    private final byte[] data;

    public Route(String name, byte[] data) {
        this.name = name;
        this.data = Arrays.copyOf(data, data.length);
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }
}
