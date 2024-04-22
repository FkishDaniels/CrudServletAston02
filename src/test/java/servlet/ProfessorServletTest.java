package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.dto.ProfessorDTO;
import ru.aston.dto.StudentDTO;
import ru.aston.service.ProfessorService;
import ru.aston.servlet.ProfessorServlet;
import ru.aston.servlet.StudentServlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class ProfessorServletTest {
    private final ProfessorService service = Mockito.mock(ProfessorService.class);
    private final ProfessorServlet servlet = new ProfessorServlet(service);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void doPost() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = servlet.getClass().getDeclaredMethod("doPost",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("name")).thenReturn("1");
        Mockito.when(request.getParameter("age")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).save(new ProfessorDTO("1",1));
    }
    @Test
    void doGet() throws InvocationTargetException, IllegalAccessException,NoSuchMethodException{
        Method method = servlet.getClass().getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }
    @Test
    void doDelete() throws InvocationTargetException, IllegalAccessException,NoSuchMethodException{
        Method method = servlet.getClass().getDeclaredMethod("doDelete",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service, Mockito.times(1)).delete(1);
    }
    @Test
    void doPut() throws InvocationTargetException, IllegalAccessException,NoSuchMethodException{
        Method method = servlet.getClass().getDeclaredMethod("doPut",HttpServletRequest.class,HttpServletResponse.class);
        method.setAccessible(true);


        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("name")).thenReturn("1");
        Mockito.when(request.getParameter("age")).thenReturn("1");
        method.invoke(servlet,request,response);
        Mockito.verify(service,Mockito.times(1)).update(new ProfessorDTO(1L,"1",1));
    }
}
