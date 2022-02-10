package com.airlines.dao;

import com.airlines.model.CrewMember;

public interface CrewMemberDao {

    void save(CrewMember crewMember);

    void update(CrewMember crewMember);

    CrewMember findById(int id);

    void addCrewMemberToCrew(CrewMember crewMember, int crewID);

}
