package net.csthings.antreminder.entity;

import java.io.Serializable;

public abstract class CommonDto<ID extends Serializable> implements Serializable {

    private ID id;
}
