package com.github.fabriciolfj;

public class ProductDTO {

    private String id;
    private String describe;

    public String getId() {
        return id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id='" + id + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
