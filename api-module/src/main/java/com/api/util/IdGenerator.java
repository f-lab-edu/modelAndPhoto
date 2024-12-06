package com.api.util;

/**
 * Id를 부여하는 Generator
 * id 채번 rule : {prefix}_UUID
 * prefix : IdPrefix
 *
 */
public class IdGenerator {

    public static String generateId(IdPrefix prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix is null");
        }

        return prefix + "_" + java.util.UUID.randomUUID().toString();
    }

    public static IdPrefix getPrefixModel() {
        return IdPrefix.MDL;
    }

    public static IdPrefix getPrefixPhotographer() {
        return IdPrefix.PHO;
    }

    public static IdPrefix getPrefixMessage() {
        return IdPrefix.MSG;
    }

    public static IdPrefix getPrefixConversation() {
        return IdPrefix.CON;
    }

    public static IdPrefix getPrefixMatching() {
        return IdPrefix.MAT;
    }

    public static IdPrefix getPrefixFile() {
        return IdPrefix.FIL;
    }

    public enum IdPrefix {
        MDL,    /*model*/
        PHO,    /*photographer*/
        MSG,    /*message*/
        CON,    /*conversation*/
        MAT,    /*matching*/
        FIL     /*file*/
    }
}
