package com.serialize.custom.pojo;

import com.serialize.custom.core.Serializer;

/**
 * Created by wuxinjian on 2017/5/23.
 */
public class Resource extends Serializer {

    private int gold;


    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    protected void read() {
        this.gold = readInt();
    }

    @Override
    protected void write() {
        writeInt(gold);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return gold == resource.gold;

    }

    @Override
    public int hashCode() {
        return gold;
    }
}
