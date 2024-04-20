package ru.aston.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.database.PostgresConnectionManager;
import ru.aston.dto.LessonDTO;
import ru.aston.dto.MAPPER.LessonDtoMapper;
import ru.aston.service.LessonService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {
    private static final String HTML = "text/html";

    private final LessonService lessonService;

    public LessonServlet() {
        this.lessonService = new LessonService(new PostgresConnectionManager(),new LessonDtoMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        Long id = Long.parseLong(req.getParameter("id"));
        LessonDTO lesson = lessonService.findById(id);
        if(lesson != null){
            out.println("Lesson: " + lesson.getName());
        }else{
            out.println("No lesson founded");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");
        LessonDTO lesson = new LessonDTO();

        lesson.setName(name);
        lessonService.save(lesson);
        out.println("Lesson: " + lesson.getName()+" saved");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");

            LessonDTO lesson = new LessonDTO(id,name);
            lessonService.update(lesson);
            out.println("Lesson: " + lesson.getName()+"updated!");
        }catch (NumberFormatException e){
            out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            LessonDTO lesson = lessonService.findById(id);

            if(lesson!=null){
                lessonService.delete(lesson);
                out.println("Lesson: " + lesson.getName()+" deleted!");
            }else{
                out.println("Error lesson not found");
            }
        }catch (NumberFormatException e){
            out.println("Error " + e.getMessage());
        }
    }

}
