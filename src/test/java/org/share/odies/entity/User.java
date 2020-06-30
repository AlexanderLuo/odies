package org.share.odies.entity;

import org.share.odies.annotation.Ro;
import org.share.odies.bean.IdRedisEntity;

/**
 * Auth: Alexander Lo
 * Date: 2020-06-29
 * Description:
 */

@Ro(prefix = "tuser")
public class User extends IdRedisEntity<Long> {


    private String name;
    private String addr;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
