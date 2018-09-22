package com.utils;

import com.controller.command.impl.station.AddNewStationCommand;
import com.controller.command.impl.user.RegisterNewUserCommand;
import com.controller.command.Command;
import com.controller.command.impl.user.ValidateUserPasswordCommand;

import java.util.HashMap;
import java.util.Map;

import static com.utils.UtilConstants.*;

/**
 * The {@code UtilData} class is responsible for holding all service collections including application data
 */
public class UtilData {

    public static final Map<String, Command> COMMANDS_MAP = new HashMap<>();


    static {

        COMMANDS_MAP.put(REGISTER_NEW_USER_COMMAND, new RegisterNewUserCommand());
        COMMANDS_MAP.put(VALIDATE_USER_PASSWORD_COMMAND, new ValidateUserPasswordCommand());
        COMMANDS_MAP.put(ADD_NEW_STATION_COMMAND, new AddNewStationCommand());
    }

}
