
package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import modelo.Socio;
import persistencia.conexion;
import persistencia.SocioData;

public class ProyectoFinalLaboratorioI {

    public static void main(String[] args) {
        try {
            conexion c = new conexion();
            SocioData socioData = new SocioData(c.getConexion());

            Socio nuevoSocio = new Socio();
            nuevoSocio.setDni(20323456);
            nuevoSocio.setNombre("Carlos");
            nuevoSocio.setApellido("Tevez");
            nuevoSocio.setEdad(29);
            nuevoSocio.setCorreo("carlos.tevez4@gmail.com");
            nuevoSocio.setTelefono(44323355);
            nuevoSocio.setEstado(true);

            Socio existente= socioData.buscarPorDni(nuevoSocio.getDni());
            if(existente==null){
            boolean exito = socioData.alta(nuevoSocio);
            if (exito) {
                System.out.println("¡Socio agregado con exito! ID asignado: " + nuevoSocio.getIdSocio());
            } else {
                System.out.println("No se pudo registrar el socio.");
            }
            } else {
                System.out.println("el socio ya existe (ID " + existente.getIdSocio() + "), se usa el existente ");
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
            //buscar por dni
            System.out.println("buscar por dni " + nuevoSocio.getDni());
            mostrarSocio(socioData.buscarPorDni(nuevoSocio.getDni()));
            //actualizar
            int idPrueba = nuevoSocio.getIdSocio();
            Socio socioModificar = socioData.buscar(idPrueba);
            socioModificar.setDni(41236457);
            socioModificar.setNombre("Martina");
            socioModificar.setApellido("Lopez");
            socioModificar.setEdad(28);
            socioModificar.setCorreo("martinalopez23@gmail.com");
            socioModificar.setTelefono(32456741);
            
            boolean actualizado = socioData.actualizar(socioModificar);
            System.out.println(actualizado ? "Socio actualizado con exito" : "No se encontro el socio");
            mostrarSocio(socioData.buscar(idPrueba));

        } catch (SQLException e) {
            System.err.println("Error al conectar o consultar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static void mostrarSocio(Socio socio) {
        if (socio == null) {
            System.out.println("no se encontro el socio");
            return;
        }
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
