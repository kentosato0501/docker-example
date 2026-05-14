import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import javax.sql.*;

@WebServlet("/Servlet/SelectSample")
public class SelectSample extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter out = null;
        Context ctx = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            res.setContentType("text/html; charset=UTF-8");
            out = res.getWriter();
            out.println("<html><head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<title>Servlet上でDBレコードを取得</title>");
            out.println("</head><body>");
            out.println("<h3>Servlet上でDBレコードを取得するサンプル (Docker環境)</h3>");
            out.println("<table border=\"1\">");
            out.println("<tr><th>id</th><th>name</th><th>created_at</th><th>updated_at</th></tr>");

            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/example");
            con = ds.getConnection();
            pstmt = con.prepareStatement(
                "select id, name, created_at, updated_at from tb_example order by id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("created_at") + "</td>");
                out.println("<td>" + rs.getString("updated_at") + "</td>");
                out.println("</tr>");
            }
            out.println("</table></body></html>");

        } catch (Exception e) {
            e.printStackTrace(out);
            throw new ServletException(e);
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) {} rs = null; }
            if (pstmt != null) { try { pstmt.close(); } catch (Exception e) {} pstmt = null; }
            if (con != null) { try { con.close(); } catch (Exception e) {} con = null; }
            if (ctx != null) { try { ctx.close(); } catch (Exception e) {} ctx = null; }
            if (out != null) { out.close(); out = null; }
        }
    }
}
