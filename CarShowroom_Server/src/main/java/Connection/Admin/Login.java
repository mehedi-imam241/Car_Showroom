package Connection.Admin;

import Connection.JdbcLoginValidator;

import java.sql.SQLException;


public class Login {
    public Login()
    {

    }
    public boolean isSuccessful(String name,String password) throws SQLException {
        String nameTxt = name;
        String passwordTxt = password;
        JdbcLoginValidator jdbcLoginValidator = new JdbcLoginValidator();
        boolean flag = jdbcLoginValidator.validate(nameTxt, passwordTxt,"admin");
        if (flag) {
            return true;
        }
        else {
            return false;
        }
    }
}
