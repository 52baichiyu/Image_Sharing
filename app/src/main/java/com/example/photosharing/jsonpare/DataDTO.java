package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/8/25
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class DataDTO {
    public void setRecords(List<RecordsDTO> records) {
        this.records = records;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<RecordsDTO> getRecords() {
        return records;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getCurrent() {
        return current;
    }

    public DataDTO(List<RecordsDTO> records, Integer total, Integer size, Integer current) {
        this.records = records;
        this.total = total;
        this.size = size;
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

    @JsonProperty("records")
    private List<RecordsDTO> records;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("current")
    private Integer current;
}
