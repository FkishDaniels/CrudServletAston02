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
    public ProfessorServlet (ProfessorService professorService){
        this.professorService = professorService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();

        ProfessorDTO professor = professorService.findById(id);
        if(professor !=null){

        }else{

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Long id = Long.parseLong(req.getParameter("id"));
            professorService.delete(id);
        }catch (NumberFormatException e) {
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

        }catch (NumberFormatException e){

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


        }catch (NumberFormatException e){

        }
    }
}
