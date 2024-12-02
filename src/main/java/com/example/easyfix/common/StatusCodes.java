package com.example.easyfix.common;

public final class StatusCodes {

    private StatusCodes() {

    }

    public static final int LIST_OPERATION_SUCCESS = 1000;
    public static final int LIST_OPERATION_FAILED = 1001;
    public static final int EMPTY_LIST = 1002;
    public static final int DUPLICATE_VALUE = 1003;
    public static final int REGISTER_OPERATION_SUCCESS = 1004;
    public static final int REGISTER_OPERATION_FAILED = 1005;
    public static final int UPDATE_OPERATION_SUCCESS = 1006;
    public static final int UPDATE_OPERATION_FAILED = 1007;
    public static final int LOGIN_OPERATION_SUCCESS = 1008;
    public static final int LOGIN_OPERATION_FAILED = 1009;
    public static final int PURGE_OPERATION_SUCCESS = 1010;
    public static final int PURGE_OPERATION_FAILED = 1011;
    public static final int RESTORE_OPERATION_SUCCESS = 1012;
    public static final int RESTORE_OPERATION_FAILED = 1013;

    public static final int EMAIL_ALREADY_EXISTS = 1014;

}
