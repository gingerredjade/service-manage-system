package com.smp.common.util;

import javax.sql.rowset.serial.SerialClob;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobUtils {

    public String clobToString(Clob clob) {
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Clob stringToClob(String s) {
        try {
            return new SerialClob(s.toCharArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
