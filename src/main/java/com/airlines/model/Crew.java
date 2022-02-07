package com.airlines.model;

import java.util.List;

public class Crew {
    private int id;
    private String name;
    private List<CrewMember> crewMembers;

    public Crew() {
    }

    public Crew(int id, String name, List<CrewMember> crewMembers) {
        this.id = id;
        this.name = name;
        this.crewMembers = crewMembers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
