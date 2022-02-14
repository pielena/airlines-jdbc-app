package com.airlines.dao.impl;

import com.airlines.dao.CrewMemberDao;
import com.airlines.dao.DbConnector;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Citizenship;
import com.airlines.model.CrewMember;
import com.airlines.model.Position;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CrewMemberDaoImpl implements CrewMemberDao {

    private final static String INSERT_CREW_MEMBER_QUERY = """ 
            INSERT INTO crew_member(id, first_name, last_name, position, birthday, citizenship, crew_id)
            VALUES(?, ?, ?, ?, ?, ?, ?);""";
    private final static String UPDATE_CREW_MEMBER_QUERY = """ 
            UPDATE crew_member SET first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ?
            WHERE id = ?;""";
    private final static String SELECT_CREW_MEMBER_BY_ID_QUERY = "SELECT * FROM crew_member WHERE id = ?;";
    private final static String UPDATE_CREW_ID_QUERY = "UPDATE crew_member SET crew_id = ? WHERE id = ?;";

    private final DbConnector dbConnector;

    public CrewMemberDaoImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void save(CrewMember crewMember) {
        Objects.requireNonNull(crewMember);

        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_CREW_MEMBER_QUERY)) {

            insertStatement.setInt(1, crewMember.getId());
            insertStatement.setString(2, crewMember.getFirstName());
            insertStatement.setString(3, crewMember.getLastName());
            insertStatement.setString(4, crewMember.getPosition().toString());
            insertStatement.setDate(5, Date.valueOf(crewMember.getBirthday()));
            insertStatement.setString(6, crewMember.getCitizenship().toString());
            insertStatement.setInt(7, crewMember.getCrewId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException("Error saving crew member" + crewMember, e);
        }
    }

    @Override
    public void update(CrewMember crewMember) {
        Objects.requireNonNull(crewMember);

        if (crewMember.getId() == null || crewMember.getId() < 1) {
            throw new DaoOperationException("Cannot find a crew member without correct id");
        }

        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_CREW_MEMBER_QUERY)) {

            updateStatement.setString(1, crewMember.getFirstName());
            updateStatement.setString(2, crewMember.getLastName());
            updateStatement.setString(3, crewMember.getPosition().toString());
            updateStatement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            updateStatement.setString(5, crewMember.getCitizenship().toString());
            updateStatement.setInt(6, crewMember.getId());
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DaoOperationException(String.format("Crew member with id = %d does not exist", crewMember.getId()));
            }
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Crew member with id = %d does not exist", crewMember.getId()), e);
        }
    }

    @Override
    public CrewMember findById(Integer id) {
        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_CREW_MEMBER_BY_ID_QUERY)) {

            selectByIdStatement.setInt(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();

            if (!resultSet.next()) {
                throw new DaoOperationException(String.format("Crew member with id = %d does not exist", id));
            }

            return CrewMember.builder()
                    .withId(resultSet.getInt("id"))
                    .withCrewId(resultSet.getInt("crew_id"))
                    .withFirstName(resultSet.getString("first_name"))
                    .withLastName(resultSet.getString("last_name"))
                    .withPosition(Position.valueOf(resultSet.getString("position")))
                    .withBirthday(resultSet.getDate("birthday").toLocalDate())
                    .withCitizenship(Citizenship.valueOf(resultSet.getString("citizenship")))
                    .build();
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error finding crew member by id = %d", id), e);
        }
    }

    @Override
    public void addCrewMemberToCrew(CrewMember crewMember, Integer crewId) {
        Objects.requireNonNull(crewMember);

        if (crewMember.getId() == null || crewMember.getId() < 1) {
            throw new DaoOperationException("Cannot find a crew member without correct id");
        }

        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_CREW_ID_QUERY)) {

            updateStatement.setInt(1, crewId);
            updateStatement.setInt(2, crewMember.getId());
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DaoOperationException(String.format("Crew update for crew member with id = %d was not performed", crewMember.getId()));
            }
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Crew member with id = %d does not exist", crewMember.getId()), e);
        }
    }
}