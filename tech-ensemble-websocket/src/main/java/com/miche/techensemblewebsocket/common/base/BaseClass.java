package com.miche.techensemblewebsocket.common.base;

import com.google.gson.Gson;
import com.miche.techensemblewebsocket.common.gson.GsonProvider;
import com.miche.techensemblewebsocket.common.send.Sender;

public abstract class BaseClass {
    protected final Gson gson = GsonProvider.getInstance();
    protected final Sender sender = Sender.getInstance();

    protected String getClassName() {
        return this.getClass().getSimpleName();
    }

    protected String getMethodName(Object o) {
        String methodName = o.getClass().getEnclosingMethod().getName();

        if(methodName.length() > 6) {
            if(methodName.substring(0, 7).equalsIgnoreCase("lambda$")) {
                methodName = methodName.substring(7);
            }
        }
        int index = -1;
        for(int i = 0; i < methodName.length(); i++) {
            String c = methodName.charAt(i) + "";
            if(index > -1 && !c.chars().allMatch(Character::isDigit)) {
                index = -1;
            }
            if(c.equals("$")) {
                index = i;
            }
        }
        if(index > -1) {
            methodName = methodName.substring(0, index);
        }

        return methodName;
    }

    protected String getLogPrefix(Object o) {
        return getClassName() + " " + getMethodName(o) + "(): ";
    }
}