package com.serialize.protobuf;
import com.google.protobuf.InvalidProtocolBufferException;
import com.serialize.protobuf.PlayerModule.PBPlayer;
import com.serialize.protobuf.PlayerModule.PBPlayer.Builder;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * protobuf的jar版本和生产代码的protoc程序版本必须一样，否则可能出现以下异常：
 * java.lang.UnsupportedOperationException: This is supposed to be overridden by subclasses.
 *
 * Created by wuxinjian on 2017/5/23.
 */
public class ProtobufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        PBPlayer player = createPBPlayer(11,22,"jack",5);
        byte[] bytes=toBytes(player);
        PBPlayer playerFromBytes = toPBPlayer(bytes);
        System.out.println("equals:"+Objects.equals(player,playerFromBytes));
        boolean isSame = player == playerFromBytes;
        System.out.println("isSame:"+isSame);
    }

    static PBPlayer createPBPlayer(int playerId,int age,String name,int skills){
        //获取builder
        Builder builder = PBPlayer.newBuilder();
        //设置数据
        builder.setPlayerId(playerId).setAge(age).setName(name).addSkills(skills);
        //通过builder获取对象
        PBPlayer player = builder.build();
        return player;
    }

    /**
     * 序列化
     * @param player
     * @return
     */
    static byte[] toBytes(PBPlayer player) {
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
    static PBPlayer toPBPlayer(byte[] bytes) throws InvalidProtocolBufferException {
        return PBPlayer.parseFrom(bytes);
    }
}
