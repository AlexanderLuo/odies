package org.share.odies.exceptions;

/**
 * Auth: Alexander Lo
 * Date: 2020-06-29
 * Description: 错误的使用
 */
public class OdiesUsageException extends RuntimeException {


    public  OdiesUsageException(){}


    public  OdiesUsageException(String msg){
        super(msg);
    }

}
