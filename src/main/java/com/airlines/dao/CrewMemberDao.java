package com.airlines.dao;

import com.airlines.model.CrewMember;

public interface CrewMemberDao {

    void save(CrewMember crewMember);

    void update(CrewMember crewMember);

    CrewMember findById(Integer id);

    void addCrewMemberToCrew(CrewMember crewMember, Integer crewId);

}
