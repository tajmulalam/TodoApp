package com.project.sumon.todo;

/**
 * Created by Sumon on 3/16/2016.
 */
public class TodoCategory {
    private int categoryId;
    private String categoryName;

    public TodoCategory(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public TodoCategory() {
    }

    public TodoCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
