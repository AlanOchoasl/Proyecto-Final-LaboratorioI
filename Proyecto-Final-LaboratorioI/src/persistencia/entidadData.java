
package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Socio;

public class entidadData {

    private final Connection conexion;
    
    public entidadData (Connection conexion){
        this.conexion = conexion;
    }
    
    public boolean alta (Socio socio) throws SQLException {
        String sql = "INSERT INTO socio (dni, Nombre, Apellido, Edad, Correo, telefono, Estado) VALUES (?, ?, ?, ?. ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, String.valueOf(socio.getDni()));
            ps.setString(2, socio.getNombre());
            ps.setString(3, socio.getApellido());
            ps.setInt(4, socio.getEdad());
            ps.setString(5, socio.getCorreo());
            ps.setString(6, String.valueOf(socio.getTelefono()));
            ps.setBoolean(7, socio.isEstado());
            
            if (ps.executeUpdate() == 0){
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()){
                    socio.setIdSocio(rs.getInt(1));
                }
            }
            return true;
        }
    }
    
    public Socio buscar(int idSocio) throws SQLException {
        String sql = "SELECT id_Socio, dni, Nombre, Apellido, Edad, Correo, telefono, Estado FROM socio WHERE id_Socio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public Socio buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT id_Socio, dni, Nombre, Apellido, Edad, Correo, telefono, Estado FROM socio WHERE dni = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }
    
    public List<Socio> listar() throws SQLException {
        return listarPorEstado(null);
    }
    
    public List<Socio> listarActivos() throws SQLException {
        return listarPorEstado(1);
    }
    
    private List<Socio> listarPorEstado(Integer estado) throws SQLException {
        String sql = "SELECT id_Socio, dni, Nombre, Apellido, Edad, Correo, telefono, Estado FROM socio";
        if (estado != null) {
            sql += " WHERE Estado = ?";
        }
        sql += " ORDER BY Apellido, Nombre";

        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            if (estado != null) {
                ps.setInt(1, estado);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    socios.add(mapear(rs));
                }
            }
        }
        return socios;
    }

    public boolean actualizar(Socio socio) throws SQLException {
        String sql = "UPDATE socio SET dni = ?, Nombre = ?, Apellido = ?, Edad = ?, Correo = ?, telefono = ?, Estado = ? WHERE id_Socio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(socio.getDni()));
            ps.setString(2, socio.getNombre());
            ps.setString(3, socio.getApellido());
            ps.setInt(4, socio.getEdad());
            ps.setString(5, socio.getCorreo());
            ps.setString(6, String.valueOf(socio.getTelefono()));
            ps.setBoolean(7, socio.isEstado());
            ps.setInt(8, socio.getIdSocio());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean bajaLogica(int idSocio) throws SQLException {
        return cambiarEstado(idSocio, false);
    }
    
    public boolean altaLogica(int idSocio) throws SQLException {
        return cambiarEstado(idSocio, true);
    }
    
    private boolean cambiarEstado(int idSocio, boolean estado) throws SQLException {
        String sql = "UPDATE socio SET Estado = ? WHERE id_Socio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setBoolean(1, estado);
            ps.setInt(2, idSocio);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean borrar(int idSocio) throws SQLException {
        String sql = "DELETE FROM socio WHERE id_Socio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            return ps.executeUpdate() > 0;
        }
    }
    
    private Socio mapear(ResultSet rs) throws SQLException {
        Socio socio = new Socio();
        socio.setIdSocio(rs.getInt("id_Socio"));
        socio.setDni(rs.getInt("dni"));
        socio.setNombre(rs.getString("Nombre"));
        socio.setApellido(rs.getString("Apellido"));
        socio.setEdad(rs.getInt("Edad"));
        socio.setCorreo(rs.getString("Correo"));
        socio.setTelefono(rs.getInt("telefono"));
        socio.setEstado(rs.getBoolean("Estado"));
        return socio;
        }
    
}
