/**
 *   Copyright(c) 2009-2015 GIS Department, NCI. All Rights Reserved.
 *
 *     THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF THE
 *                GIS DEPARTMENT, NCI.
 *
 *   The copyright notice above does not evidence any actual or intended
 *   publication of such source code.
 *
 *   Version 1.2 2009.06.18 07:54
 */
package com.smp.common.grid.common;

import java.nio.*;

public class ByteStream {
    private ByteBuffer _buffer = null;

    private int _write_pos = 0;

    private int _read_pos = 0;

    private int _capacity = 0; // Cache for _buffer.capacity()

    // Constructs a empty ByteStream
    public ByteStream() {
    	this(1600);
    }

    // Constructs a empty ByteStream
    public ByteStream(int initCapacity) {
        _buffer = ByteBuffer.allocate(initCapacity);
        _buffer.order(ByteOrder.LITTLE_ENDIAN);
        _capacity = _buffer.capacity();
    }

    // Constructs a ByteStream with an byte array
    public ByteStream(byte[] bytes) {
        _buffer = ByteBuffer.wrap(bytes);
        _buffer.order(ByteOrder.LITTLE_ENDIAN);
        _capacity = _buffer.capacity();
        _write_pos = _capacity;
    }

    // Constructs a ByteStream with an part of byte array
    public ByteStream(byte[] bytes, int offset, int length) {
        _buffer = ByteBuffer.allocate(length);
        _buffer.order(ByteOrder.LITTLE_ENDIAN);
        _buffer.put(bytes, offset, length);
        _capacity = _buffer.capacity();
        _write_pos = length;
    }

    // TODO ������Ż����ڴ濽��
    public static ByteStream wrap(DataChunk chunk) {
    	if (chunk.array != null) {
    		return new ByteStream(chunk.array, chunk.offset, chunk.length);
    	} else {
    		return new ByteStream();
    	}
    }

    // �������ݴ���ʹ�õ��ֽ�˳��
    public void setDataByteOrder(ByteOrder order) {
        _buffer.order(order);
    }

    // ������ݴ���ʹ�õ��ֽ�˳��
    public ByteOrder getDataByteOrder() {
        return _buffer.order();
    }

    private ByteBuffer expand(int size) {
        assert (size >= 0);
        if (_write_pos + size <= _capacity) {
            return _buffer;
        }

        int newCapacity = _capacity + size;
        int cap = _capacity * 2;
        if (newCapacity < cap) {
            newCapacity = cap;
        }

        ByteBuffer old = _buffer;

        _buffer = ByteBuffer.allocate(newCapacity);
        _buffer.order(old.order());
        //_buffer.put(old);
        _buffer.put(old.array(), 0, _write_pos);

        _capacity = _buffer.capacity();
        return _buffer;
    }

    // reset read/write position
    public void reset() {
        _write_pos = 0;
        _read_pos = 0;
    }

    // return the current read position
    public int read_pos() {
        return _read_pos;
    }

    // set the current read position
    public void read_pos(int pos) {
        _read_pos = pos;
    }

    // return the current write position
    public int write_pos() {
        return _write_pos;
    }

    // set the current write position
    public void write_pos(int pos) {
        _write_pos = pos;
    }

    // ������ݻ�����
    public byte[] array() {
        return _buffer.array();
    }

    // ������ݻ������е��ֽڳ���
    public int length() {
        return _write_pos;
    }

    public void writeBool(boolean b) {
        byte v = b ? (byte) 1 : (byte) 0;
        writeByte(v);
    }

    public boolean readBool() throws UnmarshalException {
        return readByte() == (byte) 1;
    }

    public void writeByte(byte b) {
        expand(1);
        _buffer.put(_write_pos, b);
        _write_pos += 1;
    }

    public void writeBytes(byte[] v, int offset, int length) {
        int sz = (v == null) ? 0 : length;
        if (sz > 0) {
            expand(sz);
            _buffer.position(_write_pos);
            _buffer.put(v, offset, length);
            _write_pos += sz;
        }
    }

    public byte readByte() throws UnmarshalException {
        try {
            byte b = _buffer.get(_read_pos);
            _read_pos += 1;
            return b;
        }
        catch (java.nio.BufferUnderflowException ex) {
            throw new UnmarshalException();
        }
    }

    public void readBytes(byte[] to, int offset, int nlen) throws UnmarshalException {
        _buffer.position(_read_pos);
        _buffer.get(to, offset, nlen);
        _read_pos += nlen;
    }

    public void writeShort(short s) {
        expand(2);
        _buffer.putShort(_write_pos, s);
        _write_pos += 2;
    }

    public short readShort() throws UnmarshalException {
        try {
            short s = _buffer.getShort(_read_pos);
            _read_pos += 2;
            return s;
        }
        catch (java.nio.BufferUnderflowException ex) {
            throw new UnmarshalException();
        }
    }

    public void writeInt(int n) {
        expand(4);
        _buffer.putInt(_write_pos, n);
        _write_pos += 4;
    }

    public int readInt() throws UnmarshalException {
        try {
            int n = _buffer.getInt(_read_pos);
            _read_pos += 4;
            return n;
        }
        catch (java.nio.BufferUnderflowException ex) {
            throw new UnmarshalException();
        }
    }

    public void writeLong(long l) {
        expand(8);
        _buffer.putLong(_write_pos, l);
        _write_pos += 8;
    }

    public long readLong() throws UnmarshalException {
        try {
            long n = _buffer.getLong(_read_pos);
            _read_pos += 8;
            return n;
        }
        catch (java.nio.BufferUnderflowException ex) {
            throw new UnmarshalException();
        }
    }

    public void writeFloat(float f) {
        expand(4);
        _buffer.putFloat(_write_pos, f);
        _write_pos += 4;
    }

    public float readFloat() throws UnmarshalException {
        try {
            float n = _buffer.getFloat(_read_pos);
            _read_pos += 4;
            return n;
        }
        catch (java.nio.BufferUnderflowException ex) {
            throw new UnmarshalException();
        }
    }

    public void writeDouble(double d) {
        expand(8);
        _buffer.putDouble(_write_pos, d);
        _write_pos += 8;
    }

    public double readDouble() throws UnmarshalException {
        try {
            double n = _buffer.getDouble(_read_pos);
            _read_pos += 8;
            return n;
        }
        catch (java.nio.BufferUnderflowException ex) {
            throw new UnmarshalException();
        }
    }

    public void writeSize(int size) {
        if (size < 255) {
            writeByte((byte) size);
        }
        else {
            writeByte((byte) -1);
            writeInt(size);
        }
    }

    public int readSize() throws UnmarshalException {
        byte b = readByte();
        if (b != (byte) -1) {
            return (int) (b < 0 ? (b + 256) : b);
        }
        else {
            return readInt();
        }
    }

    public String readString() throws UnmarshalException {
        byte[] bytes_utf8 = readBlob();
        if (bytes_utf8 == null) {
            return null;
        }

        try {
            return new String(bytes_utf8, 0, bytes_utf8.length, "UTF8");
        }
        catch (java.io.UnsupportedEncodingException ex) {
            throw new UnmarshalException("Invalid UTF8 String", ex);
        }
        catch (Exception ex) {
            assert (false);
            return null;
        }
    }

    public void writeString(String str) throws MarshalException {
        byte[] bytes_utf8 = null;

        if (str != null && str.length() > 0) {
            try {
                bytes_utf8 = str.getBytes("UTF8");
            }
            catch (Exception ex) {
                throw new MarshalException("", ex);
            }
        }

        writeBlob(bytes_utf8, 0, bytes_utf8 == null ? 0 : bytes_utf8.length);
    }

    public void writeBlob(byte[] blob, int offset, int length) {
        int sz = (blob == null) ? 0 : length;
        writeSize(sz);
        if (sz > 0) {
            expand(sz);
            _buffer.position(_write_pos);
            _buffer.put(blob, offset, length);
            _write_pos += sz;
        }
    }

    public byte[] readBlob() throws UnmarshalException {
        try {
            byte[] v = null;
            int sz = readSize();
            if (sz > 0) {
                v = new byte[sz];
                _buffer.position(_read_pos);
                _buffer.get(v);
                _read_pos += sz;
            }

            return v;
        }
        catch (Exception ex) {
            throw new UnmarshalException("", ex);
        }
    }

    public void writeShortSeq(short[] v, int offset, int length) {
        int sz = (v == null) ? 0 : length;
        writeSize(sz);
        if (sz > 0) {
            expand(sz * 2);
            _buffer.position(_write_pos);
            ShortBuffer sb = _buffer.asShortBuffer();
            sb.put(v, offset, length);
            _write_pos += (sz * 2);
        }
    }

    public short[] readShortSeq() throws UnmarshalException {
        int sz = readSize();
        if (sz == 0) {
            return null;
        }

        short[] v = new short[sz];

        _buffer.position(_read_pos);
        ShortBuffer sb = _buffer.asShortBuffer();
        sb.get(v);
        _read_pos += (sz * 2);

        return v;
    }

    public void writeIntSeq(int[] v, int offset, int length) {
        int sz = (v == null) ? 0 : length;
        writeSize(sz);
        if (sz > 0) {
            expand(sz * 4);
            _buffer.position(_write_pos);
            IntBuffer sb = _buffer.asIntBuffer();
            sb.put(v, offset, length);
            _write_pos += (sz * 4);
        }
    }

    public int[] readIntSeq() throws UnmarshalException {
        int sz = readSize();
        if (sz == 0) {
            return null;
        }

        int[] v = new int[sz];

        _buffer.position(_read_pos);
        IntBuffer sb = _buffer.asIntBuffer();
        sb.get(v);
        _read_pos += (sz * 4);

        return v;
    }

    public void writeLongSeq(long[] v, int offset, int length) {
        int sz = (v == null) ? 0 : length;
        writeSize(sz);
        if (sz > 0) {
            expand(sz * 8);
            _buffer.position(_write_pos);
            LongBuffer sb = _buffer.asLongBuffer();
            sb.put(v, offset, length);
            _write_pos += (sz * 8);
        }
    }

    public long[] readLongSeq() throws UnmarshalException {
        int sz = readSize();
        if (sz == 0) {
            return null;
        }

        long[] v = new long[sz];

        _buffer.position(_read_pos);
        LongBuffer sb = _buffer.asLongBuffer();
        sb.get(v);
        _read_pos += (sz * 8);

        return v;
    }

    public void writeFloatSeq(float[] v, int offset, int length) {
        int sz = (v == null) ? 0 : length;
        writeSize(sz);
        if (sz > 0) {
            expand(sz * 4);
            _buffer.position(_write_pos);
            FloatBuffer sb = _buffer.asFloatBuffer();
            sb.put(v, offset, length);
            _write_pos += (sz * 4);
        }
    }

    public float[] readFloatSeq() throws UnmarshalException {
        int sz = readSize();
        if (sz == 0) {
            return null;
        }

        float[] v = new float[sz];

        _buffer.position(_read_pos);
        FloatBuffer sb = _buffer.asFloatBuffer();
        sb.get(v);
        _read_pos += (sz * 4);

        return v;
    }

    public void writeDoubleSeq(double[] v, int offset, int length) {
        int sz = (v == null) ? 0 : length;
        writeSize(sz);
        if (sz > 0) {
            expand(sz * 8);
            _buffer.position(_write_pos);
            DoubleBuffer sb = _buffer.asDoubleBuffer();
            sb.put(v, offset, length);
            _write_pos += (sz * 8);
        }
    }

    public double[] readDoubleSeq() throws UnmarshalException {
        int sz = readSize();
        if (sz == 0) {
            return null;
        }

        double[] v = new double[sz];

        _buffer.position(_read_pos);
        DoubleBuffer sb = _buffer.asDoubleBuffer();
        sb.get(v);
        _read_pos += (sz * 8);

        return v;
    }

    public String[] readStringSeq() throws UnmarshalException {
        int sz = readSize();
        if (sz == 0) {
            return null;
        }

        String[] v = new String[sz];

        for (int i = 0; i < sz; ++i) {
            v[i] = readString();
        }

        return v;
    }

    public void writeStringSeq(String[] v) throws MarshalException {
        int sz = (v == null) ? 0 : v.length;
        writeSize(sz);
        for (int i = 0; i < sz; ++i) {
            writeString(v[i]);
        }
    }
}
