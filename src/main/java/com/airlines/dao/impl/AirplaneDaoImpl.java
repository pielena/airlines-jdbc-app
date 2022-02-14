package com.airlines.dao.impl;

import com.airlines.dao.AirplaneDao;
import com.airlines.dao.DbConnector;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Airplane;
import com.airlines.model.AirplaneModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirplaneDaoImpl implements AirplaneDao {

    private final static String INSERT_AIRPLANE_QUERY =
            "INSERT INTO airplane (id, code_name, model, manufacture_date, capacity, flight_range, crew_id) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?);";
    private final static String SELECT_AIRPLANE_BY_ID_QUERY = "SELECT * FROM airplane WHERE id = ?;";
    private final static String SELECT_ALL_AIRPLANES_QUERY = "SELECT * FROM airplane;";
    private static final String DELETE_AIRPLANE_QUERY = "DELETE FROM airplane WHERE id = ?;";
    private final static String UPDATE_CREW_ID_QUERY = "UPDATE airplane SET crew_id = ? WHERE id = ?;";
    private final static String SELECT_AIRPLANES_BY_CREW_NAME_QUERY = """
            SELECT *
            FROM crew
            INNER JOIN airplane
            ON crew.id = airplane.crew_id
            WHERE name = ?;""";

    private final DbConnector dbConnector;

    public AirplaneDaoImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void save(Airplane airplane) {
        Objects.requireNonNull(airplane);

        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_AIRPLANE_QUERY)) {

            insertStatement.setInt(1, airplane.getId());
            insertStatement.setString(2, airplane.getCodeName());
            insertStatement.setString(3, airplane.getModel().toString());
            insertStatement.setDate(4, Date.valueOf(airplane.getManufactureDate()));
            insertStatement.setInt(5, airplane.getCapacity());
            insertStatement.setInt(6, airplane.getFlightRange());
            insertStatement.setInt(7, airplane.getCrewId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException("Error saving airplane" + airplane, e);
        }
    }

    @Override
    public Airplane findById(int id) {
        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_AIRPLANE_BY_ID_QUERY)) {

            selectByIdStatement.setInt(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();

            if (!resultSet.next()) {
                throw new DaoOperationException(String.format("Airplane with id = %d does not exist", id));
            }

            return Airplane.builder()
                    .withId(resultSet.getInt("id"))
                    .withCodeName(resultSet.getString("code_name"))
                    .withModel(AirplaneModel.valueOf(resultSet.getString("model")))
                    .withManufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                    .withCapacity(resultSet.getInt("capacity"))
                    .withFlightRange(resultSet.getInt("flight_range"))
                    .withCrewId(resultSet.getInt("crew_id"))
                    .build();
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error finding airplane by id = %d", id), e);
        }
    }

    @Override
    public List<Airplane> findAll() {
        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement selectAllAirplanesStatement = connection.prepareStatement(SELECT_ALL_AIRPLANES_QUERY)) {

            ResultSet resultSet = selectAllAirplanesStatement.executeQuery();
            return fillList(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all airplanes", e);
        }
    }

    @Override
    public void delete(Airplane airplane) {
        Objects.requireNonNull(airplane);

        if (airplane.getId() == 0) {
            throw new DaoOperationException("Cannot find airplane without id");
        }

        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_AIRPLANE_QUERY)) {

            deleteStatement.setInt(1, airplane.getId());
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoOperationException(String.format("Airplane with id = %d does not exist", airplane.getId()));
            }
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error removing airplane by id = %d", airplane.getId()), e);
        }
    }

    @Override
    public List<Airplane> getAirplanesByCrewName(String crewName) {
        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement selectByCrewNameStatement = connection.prepareStatement(SELECT_AIRPLANES_BY_CREW_NAME_QUERY)) {

            selectByCrewNameStatement.setString(1, crewName);
            ResultSet resultSet = selectByCrewNameStatement.executeQuery();
            return fillList(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error finding airplanes by crew name = %s", crewName), e);
        }
    }

    @Override
    public void updateAirplaneWithCrew(Airplane airplane, int crewId) {
        Objects.requireNonNull(airplane);

        if (airplane.getId() == 0) {
            throw new DaoOperationException("Cannot update airplane without id");
        }

        try (Connection connection = dbConnector.getDbConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_CREW_ID_QUERY)) {

            updateStatement.setInt(1, crewId);
            updateStatement.setInt(2, airplane.getId());
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoOperationException(String.format("Update for airplane with id = %d was not performed", airplane.getId()));
            }
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Airplane with id = %d does not exist", airplane.getId()), e);
        }
    }

    private List<Airplane> fillList(ResultSet resultSet) {
        try {
            List<Airplane> airplanes = new ArrayList<>();
            while (resultSet.next()) {
                Airplane airplane = Airplane.builder()
                        .withId(resultSet.getInt("id"))
                        .withCodeName(resultSet.getString("code_name"))
                        .withModel(AirplaneModel.valueOf(resultSet.getString("model")))
                        .withManufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flight_range"))
                        .withCrewId(resultSet.getInt("crew_id"))
                        .build();
                airplanes.add(airplane);
            }
            return airplanes;
        } catch (SQLException e) {
            throw new DaoOperationException("Error filling airplanes list", e);
        }
    }
}
