package com.entity;

import java.time.LocalDate;

public class TasksEntity {
    private int taskId;
    private String taskName;
    private LocalDate dueDate;
    private CategoriesEntity categories;

    public TasksEntity(int taskId ,String taskName, LocalDate dueDate, CategoriesEntity categories) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.categories = categories;
    }

    public TasksEntity(String taskName, LocalDate dueDate, CategoriesEntity categories) {
        this(0, taskName, dueDate, categories);
    }

    public TasksEntity() {}

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public CategoriesEntity getCategories() {
        return categories;
    }

    public void setCategories(CategoriesEntity categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "taskId=" + taskId +
               ", taskName='" + taskName + '\'' +
               ", dueDate=" + dueDate +
               ", categories=" + categories +
               '}';
    }
}
