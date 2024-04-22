package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.dto.LessonDTO;
import ru.aston.dto.StudentDTO;
import ru.aston.service.LessonService;
import ru.aston.service.StudentService;
import ru.aston.servlet.LessonServlet;
import ru.aston.servlet.StudentServlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class LessonServletTest {
    private final LessonService service = Mockito.mock(LessonService.class);
    private final LessonServlet servlet = new LessonServlet(service);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void doGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = servlet.getClass().getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }
    @Test
    void doPost() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = servlet.getClass().getDeclaredMethod("doPost",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("name")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).save(new LessonDTO(0,"1"));
    }
    @Test
    void doDelete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = servlet.getClass().getDeclaredMethod("doDelete",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).delete(1);
    }
    @Test
    void doPut() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = servlet.getClass().getDeclaredMethod("doPut",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("name")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).update(new LessonDTO(1,"1"));
    }
}
