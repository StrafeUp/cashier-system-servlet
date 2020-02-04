package cashiersystem.injector;

import cashiersystem.command.Command;
import cashiersystem.command.user.ListAllUsersCommand;
import cashiersystem.command.user.LoginCommand;
import cashiersystem.command.user.LogoutCommand;
import cashiersystem.command.user.RegisterCommand;
import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.UserCrudDao;
import cashiersystem.dao.impl.HikariCPManager;
import cashiersystem.dao.impl.crud.UserPageableCrudDaoImpl;
import cashiersystem.service.UserService;
import cashiersystem.service.encoder.PasswordEncoder;
import cashiersystem.service.impl.UserServiceImpl;
import cashiersystem.service.mapper.UserMapper;
import cashiersystem.service.validator.UserValidator;
import cashiersystem.service.validator.Validator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApplicationInjector {
    private static final String DATABASE_PROPERTIES = "database";

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();
    private static final Validator USER_VALIDATOR = new UserValidator();
    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();
    private static final ConnectionPool CONNECTOR_DB = new HikariCPManager(DATABASE_PROPERTIES);
    private static final UserCrudDao USER_REPOSITORY = new UserPageableCrudDaoImpl(CONNECTOR_DB);
    private static final UserMapper USER_MAPPER = new UserMapper(PASSWORD_ENCODER);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_REPOSITORY, USER_MAPPER, USER_VALIDATOR, PASSWORD_ENCODER);

    private static final Command LOGIN_COMMAND = new LoginCommand(USER_SERVICE);
    private static final Command LOGOUT_COMMAND = new LogoutCommand();
    private static final Command REGISTER_COMMAND = new RegisterCommand(USER_SERVICE);
    private static final Command LIST_ALL_USERS_COMMAND = new ListAllUsersCommand(USER_SERVICE);


    private static final Map<String, Command> URL_TO_COMMAND = initUserCommand();

    private ApplicationInjector() {
    }

    private static Map<String, Command> initUserCommand() {
        Map<String, Command> userCommandNameToCommand = new HashMap<>();
        userCommandNameToCommand.put("/user/login", LOGIN_COMMAND);
        userCommandNameToCommand.put("/user/logout", LOGOUT_COMMAND);
        userCommandNameToCommand.put("/user/register", REGISTER_COMMAND);
        userCommandNameToCommand.put("/user/listAllUsers", LIST_ALL_USERS_COMMAND);

        return Collections.unmodifiableMap(userCommandNameToCommand);
    }

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public Map<String, Command> getUserCommandNameToCommand() {
        return URL_TO_COMMAND;
    }
}
