
package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import modelo.Socio;
import persistencia.conexion;
import persistencia.entidadData;

public class ProyectoFinalLaboratorioI {

    public static void main(String[] args) {
        try {
            conexion c = new conexion();
            entidadData socioData = new entidadData(c.getConexion());
            
            Socio nuevoSocio = new Socio();
            nuevoSocio.setDni(20323456);
            nuevoSocio.setNombre("Carlos");
            nuevoSocio.setApellido("Tevez");
            nuevoSocio.setEdad(29);
            nuevoSocio.setCorreo("carlos.tevez4@gmail.com");
            nuevoSocio.setTelefono(44323355);
            nuevoSocio.setEstado(true);

    boolean exito = socioData.alta(nuevoSocio);

    if (exito) {
        System.out.println("¡Socio agregado con éxito! ID asignado: " + nuevoSocio.getIdSocio());
    } else {
        System.out.println("No se pudo registrar el socio.");
    }
            
            List<Socio> listaSocios = socioData.listar();

            if (listaSocios.isEmpty()) {
                System.out.println("No se encontraron socios registrados en la base de datos.");
            } else {
                System.out.println("===== LISTA DE SOCIOS =====");
                for (Socio socio : listaSocios) {
                    System.out.println("ID: " + socio.getIdSocio());
                    System.out.println("DNI: " + socio.getDni());
                    System.out.println("Nombre y Apellido: " + socio.getNombre() + " " + socio.getApellido());
                    System.out.println("Edad: " + socio.getEdad());
                    System.out.println("Correo: " + socio.getCorreo());
                    System.out.println("Telefono: " + socio.getTelefono());
                    System.out.println("Estado: " + (socio.isEstado() ? "Activo" : "Inactivo"));
                    System.out.println("----------------------------------------");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar o consultar la base de datos: " + e.getMessage());
            e.printStackTrace();
            }
        }
}
