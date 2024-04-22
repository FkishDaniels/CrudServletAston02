package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.service.ProfessorService;
import ru.aston.servlet.ProfessorAddLessonServlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class ProfessorAddLessonServletTest {
    private final ProfessorService service = Mockito.mock(ProfessorService.class);
    private final ProfessorAddLessonServlet servlet = new ProfessorAddLessonServlet(service);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void doGet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = servlet.getClass().getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("id")).thenReturn("1");

        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).getLessons(1L);
    }
    @Test
    void doPost() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        Method method = servlet.getClass().getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("profId")).thenReturn("1");
        Mockito.when(request.getParameter("lessId")).thenReturn("1");

        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).addLesson(1,1);
    }
    @Test
    void doDelete() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        Method method = servlet.getClass().getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);

        Mockito.when(request.getParameter("profId")).thenReturn("1");
        Mockito.when(request.getParameter("lessId")).thenReturn("1");

        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).removeLesson(1,1);
    }
}
