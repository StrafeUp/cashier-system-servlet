package cashiersystem;

import cashiersystem.dao.impl.crud.UserCrudDaoImpl;
import cashiersystem.entity.User;

import java.util.List;

public class CashierSystemApplication {
    public static void main(String[] args) {
        UserCrudDaoImpl userCrudDao = new UserCrudDaoImpl();
        List<User> all = userCrudDao.findAll();
        System.out.println(all.toString());
    }
}

