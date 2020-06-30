package org.share.odies.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel("PageResult VO")
public class PageResult<E> implements Serializable {
    @ApiModelProperty("Fetch Page [Start From 0]")
    private int page = 0;
    @ApiModelProperty("Fetch Size")
    private int size = 10;

    @ApiModelProperty("Fetch Data")
    private List<E> result;

    @ApiModelProperty("Total Item Number")
    private int total;

    @ApiModelProperty("Total Page Number")
    private int totalPage;


    public PageResult() { this.result = new ArrayList<>();}


    public PageResult(int page, int size, int total, List<E> result) {

        if (result == null)
            result = new ArrayList<>();

        this.page = page;
        this.size = size;
        this.result = result;
        this.total = total;
        this.totalPage = total / size + (total % size == 0 ? 0 : 1);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
