package org.share.odies.vo;

public class PageOf {
    private int page = 0;
    private int pageSize = 10;

    private PageOf(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }


    /********************************************************************************************************************
     *  APis
     ********************************************************************************************************************/
    public static PageOf of(int page, int pageSize) {
        return new PageOf(page, pageSize);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
