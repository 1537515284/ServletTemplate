package com.template.dto;

public class CartBookDTO {
    private Integer id;
    private String title;
    private String author;
    private Double price;
    private Integer nums;

    public CartBookDTO() {
    }

    public CartBookDTO(Integer id, String title, String author, Double price, Integer nums) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.nums = nums;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}
