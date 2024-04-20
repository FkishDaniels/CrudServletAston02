package ru.aston.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.database.PostgresConnectionManager;
import ru.aston.dto.MAPPER.ProfessorDtoMapper;
import ru.aston.model.Lesson;
import ru.aston.service.ProfessorService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/professor/lessons")
public class ProfessorAddLessonServlet extends HttpServlet {
    private static final String HTML = "text/html";

    private final ProfessorService professorService;

    public ProfessorAddLessonServlet() {
        this.professorService = new ProfessorService(new PostgresConnectionManager(),new ProfessorDtoMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        long professorId = Long.parseLong(req.getParameter("id"));
        List<Lesson> list = professorService.getLessons(professorId);
        if(!list.isEmpty()){
            for (Lesson lesson : list) {
                out.println("Lesson: " + lesson.getName());
            }
        }else {
            out.println("Lessons not founded!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try {
            long professorId = Long.parseLong(req.getParameter("profId"));
            long lessondId = Long.parseLong(req.getParameter("lessId"));

            professorService.addLesson(professorId,lessondId);
            out.println("Lesson added!");
        }catch (NumberFormatException e){
            out.println("Error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try {
            long professorId = Long.parseLong(req.getParameter("profId"));
            long lessondId = Long.parseLong(req.getParameter("lessId"));

            professorService.removeLesson(professorId,lessondId);
            out.println("Lesson removed!");
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
