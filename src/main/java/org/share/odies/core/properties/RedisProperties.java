package org.share.odies.core.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.redis")
public class RedisProperties {
    private String host;
    private int port;
    private int timeout;
    private String password;
    private int minIdle;
    private int maxTotal;
    private boolean testWhileIdle;
    private boolean testOnBorrow;

    private RedisProperties() {
        this.minIdle = 3;
        this.maxTotal = 100;
        this.testWhileIdle = true;
        this.testOnBorrow = true;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public String getPassword() {
        return this.password;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public boolean isTestWhileIdle() {
        return this.testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String toString() {
        return "JedisConfig.RedisProperties(host=" + this.getHost() + ", port=" + this.getPort() + ", timeout=" + this.getTimeout() + ", password=" + this.getPassword() + ", minIdle=" + this.getMinIdle() + ", maxTotal=" + this.getMaxTotal() + ", testWhileIdle=" + this.isTestWhileIdle() + ", testOnBorrow=" + this.isTestOnBorrow() + ")";
    }

}
