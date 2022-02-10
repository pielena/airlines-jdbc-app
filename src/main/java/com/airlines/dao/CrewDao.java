package com.airlines.dao;

import com.airlines.model.CrewMember;

import java.util.List;

public interface CrewDao {

    List<CrewMember> getCrewMembersByCrewID(int crewID);

    List<CrewMember> getCrewMembersByCrewName(String crewName);
}
