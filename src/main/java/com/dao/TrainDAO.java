package com.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 01.10.2018.
 */
public interface TrainDAO {

    List<Integer> getSeatsCountByTrainId(int trainId) throws SQLException;
}
