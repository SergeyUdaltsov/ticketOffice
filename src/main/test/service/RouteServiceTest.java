package service;

import com.dao.impl.JDBCRouteDAO;
import com.dao.impl.JDBCStationDAO;
import com.dao.impl.JDBCTrainDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Route;
import com.entity.Station;
import com.entity.builder.RouteBuilder;
import com.entity.builder.StationBuilder;
import com.service.RouteService;
import com.service.StationService;
import com.service.mysqlimpl.MySQLRouteService;
import com.service.mysqlimpl.MySQLStationService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.utils.UtilConstants.*;

public class RouteServiceTest {

    private static final StationService STATION_SERVICE = new MySQLStationService(new JDBCStationDAO(), new JDBCRouteDAO());
    private static final RouteService SERVICE = new MySQLRouteService(new JDBCRouteDAO(), new JDBCTrainDAO(),
            new JDBCStationDAO(), STATION_SERVICE);

    @BeforeClass
    public static void doBefore() throws SQLException {

        RouteServiceTest service = new RouteServiceTest();
        service.createTestStation("test1", "тест1");
        service.createTestStation("test2", "тест2");
        service.createTestStation("test3", "тест3");
    }

    @Test
    public void shouldCreateNewRoute() {
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            int countOfRowsBefore = getCountOfRows(connection, SQL_COUNT_ROWS_ROUTE);

            SERVICE.addNewRoute(getTestRoute());

            int countOfRowsAfter = getCountOfRows(connection, SQL_COUNT_ROWS_ROUTE);

            Assert.assertTrue(countOfRowsBefore == countOfRowsAfter - 1);

            deleteTestRoute(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAllRoutes() {
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            int countOfRows = getCountOfRows(connection, SQL_COUNT_ROWS_ROUTE);

            List<Route> routes = SERVICE.getAllRoutes();

            Assert.assertTrue(countOfRows == routes.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void shouldAddIntermediateStation() {
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            int countOfRowsBefore = getCountOfRows(connection, SQL_COUNT_ROWS_INTERMEDIATE_STATION);

            int routeId = getRouteIdByCode("test", connection);

            Station station = new StationBuilder()
                    .buildRouteId(routeId)
                    .buildArrTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
                    .buildDepTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(10))
                    .buildId(3)
                    .buildArrDate(LocalDate.now().plusDays(1))
                    .buildEndStation(false)
                    .build();


            SERVICE.addIntermediateStation(station);

            int countOfRowsAfter = getCountOfRows(connection, SQL_COUNT_ROWS_INTERMEDIATE_STATION);

            Assert.assertTrue(countOfRowsBefore == countOfRowsAfter - 1);

            deleteIntermediateStation(connection, routeId, 3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteIntermediateStation(Connection connection, int routeId, int stationId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INTERMEDIATE_TEST);

        statement.setInt(1, routeId);
        statement.setInt(2, stationId);

        statement.executeUpdate();
    }

    private void createTestStation(String nameEn, String nameRu) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO station (name, name_ru) VALUES (?, ?);");

            statement.setString(1, nameEn);
            statement.setString(2, nameRu);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getRouteIdByCode(String code, Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT route_id FROM route WHERE code = 'test'");

        ResultSet resultSet = statement.executeQuery();

        int routeId = 0;

        while (resultSet.next()) {

            routeId = resultSet.getInt(1);

        }

        return routeId;
    }

    private Route getTestRoute() {

        return new RouteBuilder()
                .buildCode("test")
                .buildStartStation(1)
                .buildFinishStation(2)
                .buildDepartureDate(LocalDate.now())
                .buildDepartureTime(LocalTime.now())
                .buildArrivalDate(LocalDate.now().plusDays(2))
                .buildArrivalTime(LocalTime.now().plusHours(1))
                .build();

    }

    private void deleteTestRoute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "DELETE FROM route WHERE code = 'test'";

        statement.executeUpdate(query);

    }

    private int getCountOfRows(Connection connection, String query) throws SQLException {

        Statement statement = connection.createStatement();

        int count = 0;

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {

            count = resultSet.getInt(1);

        }
        return count;
    }
}
