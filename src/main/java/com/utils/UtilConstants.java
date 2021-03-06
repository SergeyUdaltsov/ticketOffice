package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The {@code UtilConstants} class is responsible for holding all the text constants of application
 */
public class UtilConstants {

    public static String language;

    //user errors
    public static final String USER_EXISTS = localize("User.already.exists.");
    public static final String USER_CREATED = localize("User.created.");
    public static final String USER_NOT_VALIDATED = localize("User.not.validated.");
    public static final String WRONG_DATA_FROM_CLIENT_USER = localize("Wrong.user.data.received.from.client.side.");

    //station errors
    public static final String STATION_EXISTS = localize("Station.exists.");
    public static final String COULD_NOT_PERSIST_STATION = localize("Could.not.persist.station.");
    public static final String COULD_NOT_DELETE_STATION = localize("Could.not.delete.station");
    public static final String COULD_NOT_LOAD_STATION = localize("Could.not.load.station.from.db.");
    public static final String COULD_NOT_LOAD_STATIONS = localize("Could.not.load.stations.from.db.");
    public static final String COULD_NOT_RECEIVE_INTERMEDIATE_STATION_TIMES =
            localize("Could.not.receive.intermediate.station.times.from.db");
    public static final String COULD_NOT_RECEIVE_INTERMEDIATE_STATIONS =
            localize("Could.not.receive.intermediate.stations.from.db");
    public static final String INTERMEDIATE_STATION_ERROR =
            localize("Intermediate.station.already.exists.or.equals.to.start.or.finish.station");
    public static final String WRONG_DATA_FROM_CLIENT_STATION =
            localize("Wrong.station.data.received.from.client.side.");
    public static final String WRONG_DATA_FROM_CLIENT_ROUTE =
            localize("Wrong.route.data.received.from.client.side.");

    //route errors
    public static final String ROUTE_EXISTS = localize("Route.already.exists.");
    public static final String COULD_NOT_LOAD_ROUTES = localize("Could.not.load.routes.from.db");
    public static final String COULD_NOT_PERSIST_ROUTE = localize("Could.not.persist.route");
    public static final String WRONG_DATE_OR_TIME_INTER_STATION =
            localize("Wrong.date.or.time.of.intermediate.station");

    //train errors
    public static final String TRAIN_EXISTS = localize("Train.already.exists.");
    public static final String TRAIN_ERROR_UPDATE = localize("Could.not.update.train.");
    public static final String COULD_NOT_SET_TRAIN_TO_ROUTE = localize("Could.not.set.train.to.route.");
    public static final String COULD_NOT_DELETE_TRAIN = localize("Could.not.delete.train.");
    public static final String COULD_NOT_LOAD_SEATS = localize("Could.not.set.count.of.seats.by.train.");
    public static final String COULD_NOT_LOAD_TRAINS = localize("Could.not.load.trains.from.db.");
    public static final String COULD_NOT_SET_SEATS =
            localize("Could.not.set.count.of.seats.to.intermediate.stations.");
    public static final String WRONG_DATA_FROM_CLIENT_TRAIN =
            localize("Wrong.train.data.received.from.client.side.");

    //ticket
    public static final String WRONG_DATA_FROM_CLIENT_TICKET =
            localize("Wrong.ticket.data.received.from.client.side.");
    public static final String COULD_NOT_LOAD_TICKETS = localize("Could.not.load.count.of.seats.from.db.");


    //strings
    public static final String STATION = localize("Station");
    public static final String TRAIN = localize("Train");
    public static final String CREATED = localize("created");
    public static final String UPDATED = localize("updated");
    public static final String DELETED = localize("deleted");
    public static final String ROUTE = localize("Route");
    public static final String FROM = localize("from");
    public static final String TO = localize("to");
    public static final String USER = localize("User");
    public static final String BOUGHT_TICKETS = localize("bought.tickets.");
    public static final String NOT_ENOUGH_TICKETS = localize("Not.enough.tickets.");
    public static final String MAIL_SENT = localize("Mail.sent.");
    public static final String INTERMEDIATE_STATION_ADDED = localize("Intermediate.station.added");
    public static final String DEAR = localize("Dear");
    public static final String YOU_BOUGHT_TICKETS = localize("You.have.bought.ticket(s).on.our.site.Now.you.can.travel");
    public static final String OUR_TRAINS = localize("with.comfort.by.our.trains");
    public static final String ECONOMY = localize("Economy");
    public static final String SUM = localize("sum");
    public static final String BUSINESS = localize("Business");
    public static final String COMFORT = localize("Comfort");
    public static final String TOTAL_SUM = localize("Total.sum");
    public static final String MAIL_SUBJECT = localize("Mail.from.ticket.office");


    //Mail
    public static final String MAIL_HOST = "smtp.gmail.com";
    public static final String MAIL_USER_NAME = "sergii.udaltsov@gmail.com";
    public static final int SMTP_PORT = 465;
    public static final String MAIL_PASSWORD = "t883774t";
    public static final String MAIL_FROM = "sergii.udaltsov@gmail.com";
    public static final String EMAIL_REGEX = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";


    //command url patterns:
    public static final String FRONT_CONTROLLER_SERVLET = "/railways";
    public static final String FRONT_CONTROLLER_SERVLET_ALL_VARIATION = "/railways/*";

    //command User urls:
    static final String REGISTER_NEW_USER_COMMAND = "/user/register";
    static final String VALIDATE_USER_PASSWORD_COMMAND = "/user/validate";

    //command Station urls:
    static final String GET_ALL_STATIONS_COMMAND = "/station/get/all";
    static final String GET_STATION_BY_ID_COMMAND = "/station/get/by/id";
    static final String ADD_NEW_STATION_COMMAND = "/station/new";
    static final String UPDATE_STATION_COMMAND = "/station/update";
    static final String DELETE_STATION_COMMAND = "/station/delete";

    //command Route urls:
    static final String ADD_NEW_ROUTE_COMMAND = "/route/new";
    static final String GET_ALL_ROUTES_COMMAND = "/route/get/all";
    static final String ADD_INTERMEDIATE_STATION_COMMAND = "/route/intermediate/add";
    static final String GET_INTERMEDIATE_STATIONS_BY_ROUTE = "/route/intermediate/get/all/by/route";
    static final String DELETE_INTERMEDIATE_STATION_BY_ID_COMMAND = "/route/intermediate/delete";
    static final String DELETE_ROUTE_BY_ID_COMMAND = "/route/delete";
    static final String SET_TRAIN_COMMAND = "/route/train/set";
    static final String GET_ROUTE_BY_ID_COMMAND = "/route/get/by/id";

    //command Train urls:
    static final String ADD_NEW_TRAIN_COMMAND = "/train/new";
    static final String UPDATE_TRAIN_COMMAND = "/train/update";
    static final String DELETE_TRAIN_COMMAND = "/train/delete";
    static final String GET_ALL_TRAINS_COMMAND = "/train/get/all";
    static final String GET_TRAIN_BY_ID_COMMAND = "/train/get/by/id";
    static final String SHOW_TRAINS_COMMAND = "/train/show";

    //command TicketOrder urls:
    static final String GET_TICKETS_COUNT_COMMAND = "/ticket/get/by/route";
    static final String GET_STATIONS_BY_TRIP_COMMAND = "/ticket/get/stations/by/trip";
    static final String BUY_TICKETS_COMMAND = "/ticket/buy";


    //db constants:
    public static final String MYSQL_DATA_BASE = "MySQL";


    //train:
    public static final int DEFAULT_TRAIN_ID = 1;
    public static final int ROUND_DOUBLE = 2;
    public static final double TRIP_PRICE_ECO = 0.22;
    public static final double TRIP_PRICE_BUS = 0.33;
    public static final double TRIP_PRICE_COM = 0.53;


    public static final String CONTENT_TYPE = "application/json";
    public static final String ENCODING = "UTF-8";


    //SQL User
    public static final String SQL_ADD_NEW_USER = "INSERT INTO users(first_name, last_name, email, pswrd, admin)" +
            " VALUES(?, ?, ?, ?, ?);";
    public static final String SQL_VALIDATE_PASSWORD_USER = "SELECT * FROM users WHERE email=(?) AND pswrd=(?);";

    //SQL Station
    public static final String SQL_ADD_NEW_STATION = "INSERT INTO station(name, name_ru) VALUES(?, ?)";
    public static final String SQL_GET_ALL_STATIONS = "SELECT * FROM station";
    public static final String SQL_GET_STATION_BY_ID = "SELECT * FROM station WHERE station_id=(?);";
    public static final String SQL_UPDATE_STATION = "UPDATE station SET name=(?), name_ru=(?) WHERE station_id=(?);";
    public static final String SQL_DELETE_STATION = "DELETE FROM station WHERE station_id=(?);";
    public static final String SQL_GET_STATIONS_TIME = "SELECT arrival_date_time, stopping " +
            "FROM intermediate_station " +
            "WHERE route_id_fk = (?);";


    //SQL route
    public static final String SQL_ADD_NEW_ROUTE = "INSERT INTO route(code, st_start, departure_time, departure_date, " +
            "arrival_date, st_finish, arrival_time) VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";

    public static final String SQL_GET_ALL_ROUTES = "SELECT r.route_id, r.code, st.name AS dep_st, st.name_ru AS dep_st_ru, " +
            " r.departure_date, " +
            "r.departure_time, st1.name AS arr_st, st1.name_ru AS arr_st_ru, r.arrival_date, r.arrival_time " +
            "FROM route AS r " +
            "INNER JOIN station AS st ON st.station_id = st_start " +
            "INNER JOIN station AS st1 ON st1.station_id = st_finish;";

    public static final String SQL_ADD_INTERMEDIATE_STATION = "INSERT INTO intermediate_station(route_id_fk, " +
            "station_id_fk, arrival_date_time, stopping, departure_time) VALUES\n" +
            "(?, ?, ?, ?, ?);";

    public static final String SQL_GET_INTERMEDIATE_STATIONS_BY_ROUTE = "SELECT i.intermediate_id, st.name, st.name_ru, " +
            "i.arrival_date_time, i.stopping, i.departure_time " +
            "FROM intermediate_station AS i " +
            "INNER JOIN station AS st " +
            "ON station_id_fk = st.station_id " +
            "WHERE i.route_id_fk=(?) " +
            "ORDER BY arrival_date_time;";

    public static final String SQL_DELETE_INTERMEDIATE_STATION_BY_ID = "DELETE FROM intermediate_station " +
            "WHERE intermediate_id = (?);";
    public static final String SQL_DELETE_ROUTE_BY_ID = "DELETE FROM route WHERE route_id = (?);";
    public static final String SQL_SET_TRAIN_TO_ROUTE = "UPDATE route set train_fk_id =(?) WHERE route_id = (?);";
    public static final String SQL_SET_SEATS_TO_INTERMEDIATE = "UPDATE intermediate_station SET economy = (?), " +
            "business = (?), comfort = (?) WHERE route_id_fk = (?);";

    public static final String SQL_GET_ROUTE_BY_ID = "SELECT route_id, code, st.name AS dep_st, " +
            "departure_time AS dep_time, departure_date AS dep_date, st1.name AS arr_st, " +
            "arrival_time AS arr_time, arrival_date AS arr_date " +
            "FROM route " +
            "INNER JOIN station AS st ON st.station_id = st_start " +
            "INNER JOIN station AS st1 ON st1.station_id = st_finish " +
            "WHERE route_id = (?);";

    public static final String SQL_GET_STATIONS_IN_TRIP = "SELECT st.name, st.name_ru, i.arrival_date_time, i.departure_time " +
            "FROM intermediate_station AS i " +
            "INNER JOIN station AS st ON st.station_id = station_id_fk " +
            "WHERE i.arrival_date_time BETWEEN (?) AND (?) " +
            "AND route_id_fk = (?) " +
            "ORDER BY i.arrival_date_time;";

    public static final String SQL_CLEAR_ROUTES = "DELETE FROM route WHERE arrival_date < (?);";


    //SQL train
    public static final String SQL_ADD_NEW_TRAIN = "INSERT INTO train(name, name_ru, economy, business, comfort) " +
            "VALUES(?, ?, ?, ?, ?)";
    public static final String SQL_GET_ALL_TRAINS = "SELECT * FROM train";
    public static final String SQL_GET_TRAIN_BY_ID = "SELECT * FROM train WHERE train_id=(?)";
    public static final String SQL_UPDATE_TRAIN = "UPDATE train SET name=(?), name_ru=(?), economy=(?), business=(?), comfort=(?)" +
            " WHERE train_id=(?);";
    public static final String SQL_DELETE_TRAIN_BY_ID = "DELETE FROM train WHERE train_id=(?);";
    public static final String SQL_GET_SEATS_COUNT_BY_TRAIN_ID = "SELECT economy, business, comfort FROM train WHERE train_id = (?);";

    public static final String SQL_SHOW_TRAINS_BY_STATIONS = "SELECT r.route_id, t.name AS train, " +
            "i1.arrival_date_time AS arr_date_from, i1.departure_time AS dep_time, " +
            "st.station_id AS dep_st_id, st.name AS dep_st, st.name_ru AS dep_st_ru, " +
            "i2.arrival_date_time AS arr_date_to, st1.station_id AS arr_st_id, st1.name AS arr_st, st1.name_ru AS arr_st_ru " +
            "FROM intermediate_station AS i " +
            "INNER JOIN station AS st ON st.station_id = (?) " +
            "INNER JOIN station AS st1 ON st1.station_id = (?) " +
            "INNER JOIN intermediate_station AS i1 ON i1.station_id_fk = (?) AND i1.route_id_fk = i.route_id_fk " +
            "INNER JOIN intermediate_station AS i2 ON i2.station_id_fk = (?) AND i2.route_id_fk = i.route_id_fk " +
            "INNER JOIN route AS r ON r.route_id = i.route_id_fk " +
            "INNER JOIN train AS t ON t.train_id = r.train_fk_id " +
            "WHERE i.station_id_fk = (?) OR i.station_id_fk = (?) " +
            "AND i1.arrival_date_time < i2.arrival_date_time " +
            "AND i1.arrival_date_time BETWEEN (?) AND (?) " +
            "GROUP BY i.route_id_fk " +
            "HAVING(count(i.route_id_fk) = 2);";

    public static final String SQL_GET_TRAIN_ID_FIRST_RECORD = "SELECT train_id FROM train ORDER BY train_id LIMIT 1;";

    //SQL ticket
    public static final String SQL_GET_TIME_AND_DATE_OF_STATIONS_ID = "SELECT arrival_date_time " +
            "FROM intermediate_station " +
            "WHERE route_id_fk=(?) AND station_id_fk=(?) " +
            "OR route_id_fk=(?) AND station_id_fk=(?) " +
            "ORDER BY arrival_date_time;";

    public static final String SQL_COUNT_OF_AVAILABLE_SEATS = "SELECT min(economy) AS eco, min(business) AS bus, min(comfort) AS com " +
            "FROM intermediate_station " +
            "WHERE arrival_date_time BETWEEN (?) AND (?) " +
            "AND route_id_fk = (?);";

    public static final String SQL_BUY_TICKETS = "UPDATE intermediate_station " +
            "SET economy = economy - (?), " +
            "business = business - (?), " +
            "comfort = comfort - (?) " +
            "WHERE route_id_fk = (?) AND " +
            "arrival_date_time BETWEEN (?) AND (?);";


    //for test
    public static final String SQL_COUNT_ROWS_ROUTE = "SELECT count(route_id) FROM route;";
    public static final String SQL_DELETE_INTERMEDIATE_TEST = "DELETE FROM intermediate_station WHERE route_id_fk = (?) " +
            "AND station_id_fk = (?);";
    public static final String SQL_COUNT_ROWS_INTERMEDIATE_STATION = "SELECT count(intermediate_id) FROM intermediate_station;";





    private static String localize(String key) {

        Properties properties = new Properties();
        InputStream input = null;
        Locale locale = null;
        ResourceBundle myBundle = null;


        try {
            input = new FileInputStream("B:\\IdeaProjects\\RailwayTicketOffice\\src\\main\\resources\\log4j.properties");

            properties.load(input);

            language = properties.getProperty("lang");

            switch (language) {
                case "ru": {
                    locale = new Locale("ru");
                    break;
                }
                case "en": {
                    locale = new Locale("en");
                    break;
                }
                default: {
                    locale = new Locale("en");
                }
            }

            myBundle = ResourceBundle.getBundle("logs", locale);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return myBundle.getString(key);
    }

}





