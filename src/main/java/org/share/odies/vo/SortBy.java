package org.share.odies.vo;

public class SortBy {

    private static class Direction {
        private String name;

        public Direction(String name) {
            this.name = name;
        }
    }

    public static Direction DESC = new Direction("desc");
    public static Direction AES = new Direction("asc");


    private String sortedSetFiledName;
    private String order = "desc";


    private SortBy() {
    }

    private SortBy(String sortedSetFiledName) {
        this.sortedSetFiledName = sortedSetFiledName;
    }

    private SortBy(String sortedSetFiledName, SortBy.Direction direction) {
        this.sortedSetFiledName = sortedSetFiledName;
        this.order = direction.name;
    }


    /********************************************************************************************************************
     *  APis
     ********************************************************************************************************************/
    public static SortBy by(String sortedSetFiledName) {
        return new SortBy(sortedSetFiledName);

    }

    public static SortBy by(String sortedSetFiledName, SortBy.Direction direction) {
        return new SortBy(sortedSetFiledName, direction);

    }

    public String getSortedSetFiledName() {
        return sortedSetFiledName;
    }

    public void setSortedSetFiledName(String sortedSetFiledName) {
        this.sortedSetFiledName = sortedSetFiledName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
