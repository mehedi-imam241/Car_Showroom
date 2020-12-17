package Connection.Manufacturer;
import Connection.JdbcLoginValidator;
import java.sql.SQLException;


public class ManufacturerLogin {
    public ManufacturerLogin() {}

    public boolean isSuccessful(String name, String password) throws SQLException {
        JdbcLoginValidator jdbcLoginValidator = new JdbcLoginValidator();
        boolean flag = jdbcLoginValidator.validate(name, password, "manufacturer");
        if (flag) {
            return true;
        } else {
            return false;
        }
    }
}

