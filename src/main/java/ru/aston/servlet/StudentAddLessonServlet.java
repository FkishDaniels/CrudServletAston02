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
    private final StudentService service;

    public StudentAddLessonServlet() {
        this.service = new StudentService(new PostgresConnectionManager(), new StudentDtoMapper());
    }
    public StudentAddLessonServlet(StudentService studentService){
        this.service = studentService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long studentId = Long.parseLong(req.getParameter("id"));
        List<Lesson> lessons = service.getLessons(studentId);
        if (!lessons.isEmpty()) {
            for (Lesson lesson : lessons) {
            }
        } else {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            long studentId = Long.parseLong(req.getParameter("studId"));
            long lessondId = Long.parseLong(req.getParameter("lessId"));
            service.addLesson(studentId,lessondId);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);

        try {
            long studentId = Long.parseLong(req.getParameter("studId"));
            long lessondId = Long.parseLong(req.getParameter("lessId"));
            service.removeLesson(studentId,lessondId);

        }catch (NumberFormatException e){
                e.printStackTrace();
        }
    }
}
