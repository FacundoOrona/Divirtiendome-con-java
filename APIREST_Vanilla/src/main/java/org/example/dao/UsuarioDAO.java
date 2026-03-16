package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConection();
             PreparedStatement statement = conn.prepareStatement(sql))
        {
            //Encriptar contraseña
            String hashPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());

            statement.setString(1, usuario.getUsername());
            statement.setString(2, hashPassword);

            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario registrado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar ususario: " + e.getMessage());;
        }
    }

}