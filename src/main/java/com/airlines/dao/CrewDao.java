package com.airlines.dao;

import com.airlines.model.CrewMember;

import java.util.List;

public interface CrewDao {

    List<CrewMember> getCrewMembersByCrewId(int crewId);

    List<CrewMember> getCrewMembersByCrewName(String crewName);
}
