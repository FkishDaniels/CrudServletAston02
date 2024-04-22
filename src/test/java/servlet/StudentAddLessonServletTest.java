package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.dto.StudentDTO;
import ru.aston.service.StudentService;
import ru.aston.servlet.StudentAddLessonServlet;
import ru.aston.servlet.StudentServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class StudentAddLessonServletTest {
    private final StudentService service = Mockito.mock(StudentService.class);
    private final StudentAddLessonServlet servlet = new StudentAddLessonServlet(service);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void doPost() throws ServletException, IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = servlet.getClass().getDeclaredMethod("doPost",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("studId")).thenReturn("1");
        Mockito.when(request.getParameter("lessId")).thenReturn("1");

        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).addLesson(1L,1L);
    }
    @Test
    void doGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = servlet.getClass().getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet,request, response);
        Mockito.verify(service, Mockito.times(1)).getLessons(1L);
    }

    @Test
    void doDelete()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = servlet.getClass().getDeclaredMethod("doDelete",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("studId")).thenReturn("1");
        Mockito.when(request.getParameter("lessId")).thenReturn("1");
        method.invoke(servlet, request, response);
        Mockito.verify(service,Mockito.times(1)).removeLesson(1L,1L);
    }
}
