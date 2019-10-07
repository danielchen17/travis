package com.daniel.demotest;

import java.util.ArrayList;
import java.util.List;

public class JavaServer {
    private static JavaServer mInstance = null;
    public synchronized static JavaServer getInstance() {
        if (mInstance == null) {
            mInstance = new JavaServer();
        }
        return mInstance;
    }

    public List<String> requestUserNameList() {
        ArrayList<String> list = new ArrayList();
        list.add("User A");
        list.add("User B");
        return list;
    }
}
