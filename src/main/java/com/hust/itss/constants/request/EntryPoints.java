package com.hust.itss.constants.request;

public class EntryPoints {
    public final static String[] ACTOR_ENTRYPOINTS = {
            "/admin",
            "/driver",
            "/assistant",
            "/client",
            "/root"
    };

    public final static String[] UNSECURE_ENTRYPOINTS = {
            "/",
            "/login",
            "/signup"
    };
}
