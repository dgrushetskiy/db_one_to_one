package ru.gothmog.ws.db.core.model;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }
}
