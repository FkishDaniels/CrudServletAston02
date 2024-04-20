package ru.aston.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.DAO.ProfessorDAO;
import ru.aston.database.PostgresConnectionManager;
import ru.aston.dto.MAPPER.ProfessorDtoMapper;
import ru.aston.dto.ProfessorDTO;
import ru.aston.service.ProfessorService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/professor")
public class ProfessorServlet extends HttpServlet {
    private static final String HTML = "text/html";

    private final ProfessorService professorService;

    public ProfessorServlet() {
        this.professorService = new ProfessorService(new PostgresConnectionManager(),new ProfessorDtoMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();

        ProfessorDTO professor = professorService.findById(id);
        if(professor !=null){
            out.println("Professor Found: " + professor.getName());
        }else{
            out.println("Professor not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try{
            Long id = Long.parseLong(req.getParameter("id"));
            ProfessorDTO professor = professorService.findById(id);
            if(professor != null){
                professorService.delete(professor);
                out.println("Professor successfully deleted");
            }else{
                out.println("Professor not found");
            }

        }catch (NumberFormatException e) {
            out.println("Wrong id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try{
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));

            ProfessorDTO professorDTO = new ProfessorDTO();
            professorDTO.setName(name);
            professorDTO.setAge(age);

            professorService.save(professorDTO);
            out.println("Professor saved!");
        }catch (NumberFormatException e){
            out.println("Error: Professor not saved!" );
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try {
            Long professorId = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));

            ProfessorDTO newProfessor = new ProfessorDTO(professorId, name, age);
            professorService.update(newProfessor);

            out.println("Professor updated!");
        }catch (NumberFormatException e){
            out.println("Error: " + e.getMessage());
        }
    }
}
