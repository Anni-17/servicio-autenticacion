package co.sena.kireikanri.controller;

import co.sena.kireikanri.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/api/auth/registro")
public class RegistroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            JsonObject jsonReq = new Gson().fromJson(request.getReader(), JsonObject.class);
            String user = jsonReq.get("usuario").getAsString();
            String pass = jsonReq.get("contrasena").getAsString();
            
            // Cifrar la contraseña antes de guardarla en la base de datos
            String hash = BCrypt.hashpw(pass, BCrypt.gensalt());

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)")) {
                
                stmt.setString(1, user);
                stmt.setString(2, hash);
                stmt.executeUpdate();
                
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"mensaje\": \"Usuario registrado exitosamente\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"El usuario ya existe o los datos son invalidos\"}");
        }
    }
}