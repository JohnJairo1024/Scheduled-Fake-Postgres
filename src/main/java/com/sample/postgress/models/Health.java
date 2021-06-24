package com.sample.postgress.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Health {

    private final String health;

    @JsonCreator
    private Health(@JsonProperty final String health) {
        this.health = health;
    }

    public static Health green() {
        return new Health("green");
    }

    public String getHealth() {
        return health;
    }
}
