package com.airlines.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Crew {
    private int id;
    private String name;
    private List<CrewMember> crewMembers;

    private Crew() {
    }

    private Crew(int id, String name, List<CrewMember> crewMembers) {
        this.id = id;
        this.name = name;
        this.crewMembers = crewMembers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CrewMember> getCrewMembers() {
        return Collections.unmodifiableList(crewMembers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crew crew)) return false;
        return getId() == crew.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
