
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/aborigym?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private Connection cn;

    public conexion() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("no se encontro el driver de mysql", e);
        }
        this.cn = DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    public Connection getConexion() {
        return cn;
    }

    public void cerrar() throws SQLException {
        if (cn != null && !cn.isClosed()) {
            cn.close();
        }
    }
}
