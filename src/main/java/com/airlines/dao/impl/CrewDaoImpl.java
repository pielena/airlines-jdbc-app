package com.airlines.dao.impl;

import com.airlines.dao.CrewDao;
import com.airlines.dao.DbConnector;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Citizenship;
import com.airlines.model.CrewMember;
import com.airlines.model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewDaoImpl implements CrewDao {

    private final static String SELECT_CREW_MEMBERS_BY_CREW_ID_QUERY = "SELECT * FROM crew_member WHERE crew_id = ?;";
    private final static String SELECT_CREW_MEMBERS_BY_CREW_NAME_QUERY = """
            SELECT *
            FROM crew
            INNER JOIN crew_member
            ON crew.id = crew_member.crew_id
            WHERE name = ?;""";

    private final DbConnector dbConnector;

    public CrewDaoImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public List<CrewMember> getCrewMembersByCrewId(int crewId) {
        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement selectByCrewIdStatement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_CREW_ID_QUERY)) {

            selectByCrewIdStatement.setInt(1, crewId);
            ResultSet resultSet = selectByCrewIdStatement.executeQuery();
            return fillList(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error finding crew members by crew id = %d", crewId), e);
        }
    }

    @Override
    public List<CrewMember> getCrewMembersByCrewName(String crewName) {
        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement selectByCrewNameStatement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_CREW_NAME_QUERY)) {

            selectByCrewNameStatement.setString(1, crewName);
            ResultSet resultSet = selectByCrewNameStatement.executeQuery();
            return fillList(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error finding crew members by crew name = %s", crewName), e);
        }
    }

    private List<CrewMember> fillList(ResultSet resultSet) {
        try {
            List<CrewMember> crewMembers = new ArrayList<>();
            while (resultSet.next()) {
                CrewMember crewMember = CrewMember.builder()
                        .withId(resultSet.getInt("id"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withPosition(Position.valueOf(resultSet.getString("position")))
                        .withBirthday(resultSet.getDate("birthday").toLocalDate())
                        .withCitizenship(Citizenship.valueOf(resultSet.getString("citizenship")))
                        .withCrewId(resultSet.getInt("crew_id"))
                        .build();
                crewMembers.add(crewMember);
            }
            return crewMembers;
        } catch (SQLException e) {
            throw new DaoOperationException("Error filling crew members list", e);
        }
    }
}
