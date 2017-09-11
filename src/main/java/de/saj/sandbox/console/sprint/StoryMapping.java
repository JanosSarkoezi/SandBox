package de.saj.sandbox.console.sprint;

import java.util.ArrayList;
import java.util.List;

public class StoryMapping {
    private List<BackBone> backbone = new ArrayList<>();

    public void add(BackBone bone) {
        backbone.add(bone);
    }

    public List<BackBone> getBackbone() {
        return backbone;
    }

    public void setBackbone(List<BackBone> backBone) {
        this.backbone = backBone;
    }
}
