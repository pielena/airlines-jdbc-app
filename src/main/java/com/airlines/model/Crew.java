package com.airlines.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Crew {
    private final Integer id;
    private final String name;
    private final List<CrewMember> crewMembers;

    private Crew(Integer id, String name, List<CrewMember> crewMembers) {
        this.id = id;
        this.name = name;
        this.crewMembers = crewMembers;
    }

    public Integer getId() {
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crew)) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(getId(), crew.getId()) && Objects.equals(getName(), crew.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}