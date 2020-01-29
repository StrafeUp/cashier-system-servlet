package cashiersystem;

import cashiersystem.dao.impl.crud.UserCrudDaoImpl;
import cashiersystem.entity.User;

import java.util.List;
import java.util.Optional;

public class CashierSystemApplication {
    public static void main(String[] args) {
        UserCrudDaoImpl userCrudDao = new UserCrudDaoImpl();
        List<User> all = userCrudDao.findAll();
        Optional<User> byEmail = userCrudDao.findByEmail("Hello@gmail.com");
        System.out.println(all.toString());
    }
}

