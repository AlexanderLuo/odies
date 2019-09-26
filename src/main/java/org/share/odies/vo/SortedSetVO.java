package org.share.odies.vo;

public class SortedSetVO {

    private String key;
    private String score;
    public SortedSetVO(String key, String score){
        this.key = key;
        this.score = score;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
