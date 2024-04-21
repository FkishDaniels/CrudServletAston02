package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.postgresql.Driver;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.StudentDTO;
import ru.aston.service.StudentService;
import ru.aston.servlet.StudentServlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
@author Marukha
created on 21.04.2024 inside
the package - servlet
Have a good coding time inside this  class
*/
@ExtendWith(MockitoExtension.class)

public class StudentServletTest{


    private final StudentService service = Mockito.mock(StudentService.class);
    private final StudentServlet servlet = new StudentServlet(service);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);



    @Test
    void doPost() throws ServletException, IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = servlet.getClass().getDeclaredMethod("doPost",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("name")).thenReturn("1");
        Mockito.when(request.getParameter("age")).thenReturn("1");
        Mockito.when(request.getParameter("course")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).save(new StudentDTO("1",1,1));
    }
    @Test
    void doGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = servlet.getClass().getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet,request, response);
        Mockito.verify(service, Mockito.times(1)).findById(1);
    }

    @Test
    void doDelete()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = servlet.getClass().getDeclaredMethod("doDelete",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet, request, response);
        Mockito.verify(service,Mockito.times(1)).delete(1);
    }
    @Test
    void doPut()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = servlet.getClass().getDeclaredMethod("doPut",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("name")).thenReturn("1");
        Mockito.when(request.getParameter("age")).thenReturn("1");
        Mockito.when(request.getParameter("course")).thenReturn("1");

        method.invoke(servlet,request,response);
        Mockito.verify(service,Mockito.times(1)).update(new StudentDTO(1L,"1",1,1));
    }


}
