package com.scheduler;

import com.dbConnector.MySQLConnectorManager;
import org.apache.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.utils.UtilConstants.COULD_NOT_LOAD_ROUTES;
import static com.utils.UtilConstants.SQL_CLEAR_ROUTES;

public class ClearRoutesJob implements Job{

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(ClearRoutesJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CLEAR_ROUTES)) {

            MySQLConnectorManager.startTransaction(connection);

            LocalDate currDate = LocalDate.now().minusDays(1);

            statement.setString(1, currDate.toString());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_ROUTES);

        }
    }
}
