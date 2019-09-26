package org.share.odies.core.utils;

public class ClassTool {

    public static String getSimpleClassName(String qualifiedName) {
        if (null == qualifiedName) {
            return null;
        } else {
            int i = qualifiedName.lastIndexOf(46);
            return i < 0 ? qualifiedName : qualifiedName.substring(i + 1);
        }
    }


    public static String getPackageName(String qualifiedName){
        if (null == qualifiedName) {
            return null;
        } else {
            int i = qualifiedName.lastIndexOf(46);

            return i < 0 ? null : qualifiedName.substring(0,i);
        }

    }



//    public static void main(String[] args){
//            System.err.println(getPackageName("otg.base.ok.Order"));
//    }
}
