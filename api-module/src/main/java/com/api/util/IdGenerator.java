package com.api.util;

/**
 * Id를 부여하는 Generator
 * id 채번 rule : {prefix}_UUID
 * prefix : IdPrefix
 *
 */
public class IdGenerator {

    public static String getGenerateModelId() {
        return IdPrefix.MDL + "_" + java.util.UUID.randomUUID().toString();
    }

    public static String getGeneratePhotographerId() {
        return IdPrefix.PHO + "_" + java.util.UUID.randomUUID().toString();
    }

    public static String getGenerateMessageId() {
        return IdPrefix.MSG + "_" + java.util.UUID.randomUUID().toString();
    }

    public static String getGenerateConversationId() {
        return IdPrefix.CON + "_" + java.util.UUID.randomUUID().toString();
    }

    public static String getGenerateMatchingId() {
        return IdPrefix.MAT + "_" + java.util.UUID.randomUUID().toString();
    }

    public static String getGenerateFileId() {
        return IdPrefix.FIL + "_" + java.util.UUID.randomUUID().toString();
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
