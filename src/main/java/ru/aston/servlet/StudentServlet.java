package ru.aston.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.database.PostgresConnectionManager;
import ru.aston.dto.MAPPER.StudentDtoMapper;
import ru.aston.dto.StudentDTO;
import ru.aston.service.StudentService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private static final String HTML = "text/html";
    private final StudentService studentService;

    public StudentServlet() {
        this.studentService = new StudentService(new PostgresConnectionManager(), new StudentDtoMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long studentId = Long.parseLong(req.getParameter("id"));
        StudentDTO student = studentService.findById(studentId);
        resp.setContentType(HTML);
        PrintWriter out = resp.getWriter();
        if (student != null) {
            out.println("Student Found: " + student.getName());
        } else {
            out.println("Student Not Found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Обработка POST запроса для создания нового студента
            String name = req.getParameter("name");
            String ageParam = req.getParameter("age");
            String courseParam = req.getParameter("course");
            int age = Integer.parseInt(ageParam);
            int course = Integer.parseInt(courseParam);

            // Создание объекта StudentDTO из полученных данных
            StudentDTO newStudent = new StudentDTO();
            newStudent.setName(name);
            newStudent.setAge(age);
            newStudent.setCourse(course);

            // Вызов сервиса для создания нового студента
            studentService.save(newStudent);

            resp.setContentType(HTML);
            PrintWriter out = resp.getWriter();
            out.println("New student created successfully!");
        }catch (NumberFormatException e){
            resp.setContentType(HTML);
            PrintWriter out = resp.getWriter();
            out.println(req.getParameter("name"));
            out.println(req.getParameter("age"));
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получение параметров из URL
            long studentId = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));
            int course = Integer.parseInt(req.getParameter("course"));

            // Создание объекта StudentDTO из полученных данных
            StudentDTO updatedStudent = new StudentDTO();
            updatedStudent.setId(studentId);
            updatedStudent.setName(name);
            updatedStudent.setAge(age);
            updatedStudent.setCourse(course);

            // Вызов сервиса для обновления данных студента
            studentService.update(updatedStudent);

            resp.setContentType(HTML);
            PrintWriter out = resp.getWriter();
            out.println("Student updated successfully!");
        } catch (NumberFormatException e) {
            resp.setContentType(HTML);
            PrintWriter out = resp.getWriter();
            out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получение параметра ID из URL
            long studentId = Long.parseLong(req.getParameter("id"));

            // Вызов сервиса для удаления студента
            studentService.delete(studentId);

            resp.setContentType(HTML);
            PrintWriter out = resp.getWriter();
            out.println("Student deleted successfully!");
        } catch (NumberFormatException e) {
            resp.setContentType(HTML);
            PrintWriter out = resp.getWriter();
            out.println("Error: " + e.getMessage());
        }
    }


}
