package mk.ukim.finki.wp.lab.web.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ActiveSessions implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session created by id " + se.getSession().getId());
        synchronized (this) {
            Integer num = (Integer) se.getSession().getServletContext().getAttribute("num");
            if (num == null) {
                se.getSession().getServletContext().setAttribute("num", 1);
            } else {
                se.getSession().getServletContext().setAttribute("num", num+1);
            }
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session destroyed by id " + se.getSession().getId());
        synchronized (this) {
            Integer num = (Integer) se.getSession().getServletContext().getAttribute("num");
            if (num != 0) {
                se.getSession().getServletContext().setAttribute("num", num-1);
            }
        }
    }
}
