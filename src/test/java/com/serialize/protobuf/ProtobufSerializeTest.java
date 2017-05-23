package com.serialize.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.serialize.custom.pojo.Resource;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * protobuf的jar版本和生产代码的protoc程序版本必须一样，否则可能出现以下异常：
 * java.lang.UnsupportedOperationException: This is supposed to be overridden by subclasses.
 *
 * Created by wuxinjian on 2017/5/23.
 *
 */
public class ProtobufSerializeTest {

    @Test
    public void protobufTest() throws InvalidProtocolBufferException {
        PlayerModule.PBPlayer player = createPBPlayer(11,10,"jack",Arrays.asList(1,2,3),100);
        byte[] bytes=toBytes(player);
        PlayerModule.PBPlayer playerFromBytes = toPBPlayer(bytes);
        boolean isSame = player == playerFromBytes;
        assertFalse(isSame);
        assertEquals(player,playerFromBytes);
    }

    static PlayerModule.PBPlayer createPBPlayer(int playerId, int age, String name, List<Integer> skills, int gold){

        PlayerModule.PBResource.Builder resourceBuilder = PlayerModule.PBResource.newBuilder();
        resourceBuilder.setGold(gold);

        //获取builder
        PlayerModule.PBPlayer.Builder builder = PlayerModule.PBPlayer.newBuilder();
        //设置数据
        builder.setPlayerId(playerId).setAge(age).setName(name).addAllSkills(skills).setResource(resourceBuilder);
        //通过builder获取对象
        PlayerModule.PBPlayer player = builder.build();
        return player;
    }

    /**
     * 序列化
     * @param player
     * @return
     */
    static byte[] toBytes(PlayerModule.PBPlayer player) {
        //序列化
        byte[] bytes = player.toByteArray();
        System.out.println(Arrays.toString(bytes));
        return bytes;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws InvalidProtocolBufferException
     */
    static PlayerModule.PBPlayer toPBPlayer(byte[] bytes) throws InvalidProtocolBufferException {
        return PlayerModule.PBPlayer.parseFrom(bytes);
    }
}