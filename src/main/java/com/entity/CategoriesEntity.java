package com.entity;

public class CategoriesEntity {
    private int categoryId;
    private String categoryName;

    public CategoriesEntity(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }



    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoriesEntity{" +
               "categoryId=" + categoryId +
               ", categoryName='" + categoryName + '\'' +
               '}';
    }
}
