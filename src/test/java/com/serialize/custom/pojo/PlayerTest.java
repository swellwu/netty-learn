package com.serialize.custom.pojo;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by wuxinjian on 2017/5/23.
 */
public class PlayerTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void customSerializeTest(){
        Player player = new Player();
        player.setAge(10);
        player.setPlayerId(11);
        player.setName("jack");
        player.setSkills(Arrays.asList(1,2,3));
        Resource resource = new Resource();
        resource.setGold(100);
        player.setResource(resource);
        byte[] bytes = player.getByteArray();
        System.out.println(Arrays.toString(bytes));
        Player player1 = Player.parseFromBytes(bytes);
        //反序列化后的对象是一个新的对象
        assertFalse(player==player1);
        //两个对象内容相同
        assertTrue(Objects.deepEquals(player,player1));
    }
}