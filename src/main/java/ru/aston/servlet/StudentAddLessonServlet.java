package ru.aston.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.database.PostgresConnectionManager;
import ru.aston.dto.MAPPER.StudentDtoMapper;
import ru.aston.model.Lesson;
import ru.aston.service.StudentService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students/lessons")
public class StudentAddLessonServlet extends HttpServlet {
    private static final String HTML = "text/html";
    private final StudentService studentService;

    public StudentAddLessonServlet() {
        this.studentService = new StudentService(new PostgresConnectionManager(), new StudentDtoMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long studentId = Long.parseLong(req.getParameter("id"));
        List<Lesson> lessons = studentService.getLessons(studentId);
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        if (!lessons.isEmpty()) {
            for (Lesson lesson : lessons) {
                out.println("Lesson: " + lesson.getName());
            }
        } else {
            out.println("Lessons not founded!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try {
            long studentId = Long.parseLong(req.getParameter("studId"));
            long lessondId = Long.parseLong(req.getParameter("lessId"));
            studentService.addLesson(studentId,lessondId);
            out.println("Lesson had added!");
        }catch (NumberFormatException e){
            out.println("Error");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try {
            long studentId = Long.parseLong(req.getParameter("studId"));
            long lessondId = Long.parseLong(req.getParameter("lessId"));
            studentService.removeLesson(studentId,lessondId);
            out.println("Lesson removed");
        }catch (NumberFormatException e){
                e.printStackTrace();
        }
    }
}
