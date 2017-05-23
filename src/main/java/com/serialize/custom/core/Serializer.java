package com.serialize.custom.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.*;

/**
 * 自定义序列化接口
 * Created by wuxinjian on 2017/5/23.
 */
public abstract class Serializer {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    ByteBuf byteBuf = Unpooled.buffer();

    /**
     * 反序列化具体实现
     */
    protected abstract void read();

    /**
     * 序列化具体实现
     */
    protected abstract void write();

    /**
     * 从byte数组获取数据
     */
    public Serializer readFromBytes() {
        read();
        return this;
    }

    public Serializer readFromBytes(byte[] bytes){
        byteBuf=Unpooled.copiedBuffer(bytes);
        readFromBytes();
        return this;
    }

    private void readFromBuffer(ByteBuf byteBuf){
        this.byteBuf = byteBuf;
        read();
    }

    private void writeToBuffer(ByteBuf byteBuf){
        byteBuf.writeBytes(getByteArray());
    }

    /**
     * 获取序列化数组
     * @return
     */
    public byte[] getByteArray(){
        byteBuf.clear();
        write();
        int length = byteBuf.readableBytes();
        byte[] array=new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),array);
        return array;
    }

    public byte readByte(){
        return byteBuf.readByte();
    }

    public short readShort() {
        return byteBuf.readShort();
    }

    public int readInt() {
        return byteBuf.readInt();
    }

    public long readLong() {
        return byteBuf.readLong();
    }

    public float readFloat() {
        return byteBuf.readFloat();
    }

    public double readDouble() {
        return byteBuf.readDouble();
    }

    /**
     * 读取字符串，第一个short表示字符串长度，紧接着是内容
     * @return
     */
    public String readString() {
        int size = byteBuf.readShort();
        if (size <= 0) {
            return "";
        }
        byte[] bytes = new byte[size];
        byteBuf.readBytes(bytes);
        return new String(bytes, CHARSET);
    }

    public <T> List<T> readList(Class<T> clz) {
        List<T> list = new ArrayList<>();
        int size = byteBuf.readShort();
        for (int i = 0; i < size; i++) {
            list.add(read(clz));
        }
        return list;
    }

    public <K,V> Map<K,V> readMap(Class<K> keyClz, Class<V> valueClz) {
        Map<K,V> map = new HashMap<>();
        int size = byteBuf.readShort();
        for (int i = 0; i < size; i++) {
            K key = read(keyClz);
            V value = read(valueClz);
            map.put(key, value);
        }
        return map;
    }

    public <I> I read(Class<I> clz) {
        Object t = null;
        if ( clz == int.class || clz == Integer.class) {
            t = this.readInt();
        } else if (clz == byte.class || clz == Byte.class){
            t = this.readByte();
        } else if (clz == short.class || clz == Short.class){
            t = this.readShort();
        } else if (clz == long.class || clz == Long.class){
            t = this.readLong();
        } else if (clz == float.class || clz == Float.class){
            t = readFloat();
        } else if (clz == double.class || clz == Double.class){
            t = readDouble();
        } else if (clz == String.class ){
            t = readString();
        } else if (Serializer.class.isAssignableFrom(clz)){
            try {
                byte hasObject = this.byteBuf.readByte();
                if(hasObject == 1){
                    Serializer temp = (Serializer)clz.newInstance();
                    temp.readFromBuffer(this.byteBuf);
                    t = temp;
                }else{
                    t = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new RuntimeException(String.format("不支持类型:[%s]", clz));
        }
        return (I) t;
    }

    public Serializer writeByte(Byte value) {
        byteBuf.writeByte(value);
        return this;
    }

    public Serializer writeShort(Short value) {
        byteBuf.writeShort(value);
        return this;
    }

    public Serializer writeInt(Integer value) {
        byteBuf.writeInt(value);
        return this;
    }

    public Serializer writeLong(Long value) {
        byteBuf.writeLong(value);
        return this;
    }

    public Serializer writeFloat(Float value) {
        byteBuf.writeFloat(value);
        return this;
    }

    public Serializer writeDouble(Double value) {
        byteBuf.writeDouble(value);
        return this;
    }

    public <T> Serializer writeList(List<T> list) {
        if (isEmpty(list)) {
            byteBuf.writeShort((short) 0);
            return this;
        }
        byteBuf.writeShort((short) list.size());
        for (T item : list) {
            writeObject(item);
        }
        return this;
    }

    public <K,V> Serializer writeMap(Map<K, V> map) {
        if (isEmpty(map)) {
            byteBuf.writeShort((short) 0);
            return this;
        }
        byteBuf.writeShort((short) map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }

    public Serializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeShort((short) 0);
            return this;
        }
        byte data[] = value.getBytes(CHARSET);
        short len = (short) data.length;
        byteBuf.writeShort(len);
        byteBuf.writeBytes(data);
        return this;
    }

    public Serializer writeObject(Object object) {
        if(object == null){
            writeByte((byte)0);
        }else{
            if (object instanceof Integer) {
                writeInt((int) object);
                return this;
            }
            if (object instanceof Long) {
                writeLong((long) object);
                return this;
            }
            if (object instanceof Short) {
                writeShort((short) object);
                return this;
            }
            if (object instanceof Byte) {
                writeByte((byte) object);
                return this;
            }
            if (object instanceof String) {
                String value = (String) object;
                writeString(value);
                return this;
            }
            if (object instanceof Serializer) {
                writeByte((byte)1);
                Serializer value = (Serializer) object;
                value.writeToBuffer(byteBuf);
                return this;
            }
            throw new RuntimeException("不可序列化的类型:" + object.getClass());
        }
        return this;
    }

    private <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }

    public <K,V> boolean isEmpty(Map<K,V> c) {
        return c == null || c.size() == 0;
    }
}
