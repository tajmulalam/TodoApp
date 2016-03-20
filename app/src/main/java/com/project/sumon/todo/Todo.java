package com.project.sumon.todo;

/**
 * Created by Sumon on 3/16/2016.
 */
public class Todo {
    private int todoId,todo_type,status;
    private String title,description,created_at;
    private int categoryId;

    public Todo(int todoId, int todo_type, String title, String description, String created_at,int status,int categoryId) {
        this.todoId = todoId;
        this.todo_type = todo_type;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.status=status;
        this.categoryId=categoryId;
    }

    public Todo() {
    }

    public Todo(int todo_type, String title, String description, String created_at,int status) {
        this.todo_type = todo_type;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.status=status;
    }

    public Todo(int todo_type, String title, String description, String created_at) {
        this.todo_type = todo_type;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getTodoId() {
        return todoId;
    }


    public int getTodo_type() {
        return todo_type;
    }

    public void setTodo_type(int todo_type) {
        this.todo_type = todo_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
