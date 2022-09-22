package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/16
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Save_Image_Records {
    @JsonProperty("records")
    private List<Save_Inage_DTO> records;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("current")
    private Integer current;

    public Save_Image_Records() {

    }

    public List<Save_Inage_DTO> getRecords() {
        return records;
    }

    public void setRecords(List<Save_Inage_DTO> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "DataDTO{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                '}';
    }
}
