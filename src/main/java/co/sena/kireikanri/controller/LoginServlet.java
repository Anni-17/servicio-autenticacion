package co.sena.kireikanri.controller;

import co.sena.kireikanri.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/api/auth/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            JsonObject jsonReq = new Gson().fromJson(request.getReader(), JsonObject.class);
            String user = jsonReq.get("usuario").getAsString();
            String pass = jsonReq.get("contrasena").getAsString();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT contrasena FROM usuarios WHERE usuario = ?")) {
                
                stmt.setString(1, user);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next() && BCrypt.checkpw(pass, rs.getString("contrasena"))) {
                    response.getWriter().write("{\"mensaje\": \"Login exitoso\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"error\": \"Credenciales invalidas\"}");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error en el servidor\"}");
        }
    }
}