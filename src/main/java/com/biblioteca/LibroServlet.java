package com.biblioteca;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LibroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String categoria = request.getParameter("categoria");

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(
                     "INSERT INTO libros (isbn, titulo, categoria) VALUES (?, ?, ?)")) {

            statement.setString(1, isbn);
            statement.setString(2, titulo);
            statement.setString(3, categoria);
            statement.executeUpdate();

            escribirRespuesta(response, "Libro guardado correctamente.");
        } catch (SQLException e) {
            escribirRespuesta(response, "Error al guardar el libro: " + e.getMessage());
        }
    }

    private void escribirRespuesta(HttpServletResponse response, String mensaje) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Resultado</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>" + mensaje + "</h2>");
            out.println("<a href=\"index.html\">Volver</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
