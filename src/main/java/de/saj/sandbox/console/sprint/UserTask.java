package de.saj.sandbox.console.sprint;

import java.util.ArrayList;
import java.util.List;

public class UserTask {
    private String title;
    private List<SubTask> subTasks = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void add(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }
}
