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

    public LessonServlet(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);

        Long id = Long.parseLong(req.getParameter("id"));
        LessonDTO lesson = lessonService.findById(id);
        if(lesson != null){

        }else{

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(HTML);

        String name = req.getParameter("name");
        LessonDTO lesson = new LessonDTO();

        lesson.setName(name);
        lessonService.save(lesson);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");

            LessonDTO lesson = new LessonDTO(id,name);
            lessonService.update(lesson);

        }catch (NumberFormatException e){

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));

                lessonService.delete(id);


        }catch (NumberFormatException e){

        }
    }

}
