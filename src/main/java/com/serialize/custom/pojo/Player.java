package com.serialize.custom.pojo;

import com.serialize.custom.core.Serializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxinjian on 2017/5/23.
 */
public class Player extends Serializer{

    private long playerId;

    private int age;

    String name;

    private List<Integer> skills = new ArrayList<>();

    private Resource resource = new Resource();

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 要保证read 和 write的顺序保持一致
     */
    @Override
    protected void read() {
        this.playerId = readLong();
        this.age = readInt();
        this.skills = readList(Integer.class);
        this.resource = read(Resource.class);
    }

    @Override
    protected void write() {
        writeLong(playerId);
        writeInt(age);
        writeList(skills);
        writeObject(resource);
    }

    public static Player parseFromBytes(byte[] bytes){
        Player player = new Player();
        return (Player)player.readFromBytes(bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playerId != player.playerId) return false;
        if (age != player.age) return false;
        if (skills != null ? !skills.equals(player.skills) : player.skills != null) return false;
        return resource != null ? resource.equals(player.resource) : player.resource == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (playerId ^ (playerId >>> 32));
        result = 31 * result + age;
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        return result;
    }
}
