package com.airlines.dao;

import com.airlines.model.Airplane;

import java.util.List;

public interface AirplaneDao {

    void save(Airplane airplane);

    Airplane findById(Integer id);

    List<Airplane> findAll();

    void delete(Airplane airplane);

    List<Airplane> getAirplanesByCrewName(String crewName);

    void updateAirplaneWithCrew(Airplane airplane, Integer crewId);
}
