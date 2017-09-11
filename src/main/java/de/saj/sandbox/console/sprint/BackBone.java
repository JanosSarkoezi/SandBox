package de.saj.sandbox.console.sprint;

import java.util.ArrayList;
import java.util.List;

public class BackBone {
    private String title;
    private List<UserTask> userTasks = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void add(UserTask userTask) {
        userTasks.add(userTask);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserTask> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(List<UserTask> userTasks) {
        this.userTasks = userTasks;
    }
}
