import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 演習6-1 解答例：検索機能付き Servlet
 * name カラムで部分一致検索を行う
 */
@WebServlet("/Servlet/SearchSample")
public class SearchServlet extends HttpServlet {
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
            req.setCharacterEncoding("UTF-8");
            res.setContentType("text/html; charset=UTF-8");
            out = res.getWriter();

            String keyword = req.getParameter("keyword");

            out.println("<html><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>検索結果</title>");
            out.println("<style>");
            out.println("body { font-family: 'Yu Gothic', sans-serif; margin: 20px; }");
            out.println("h3 { color: #2E5090; }");
            out.println("table { border-collapse: collapse; width: 80%; }");
            out.println("th { background-color: #D6E4F0; padding: 8px; text-align: left; }");
            out.println("td { padding: 8px; border: 1px solid #ccc; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h3>検索結果</h3>");

            // 検索フォーム
            out.println("<form action=\"/example/Servlet/SearchSample\" method=\"GET\">");
            out.println("名前で検索：<input type=\"text\" name=\"keyword\" value=\""
                + (keyword != null ? keyword : "") + "\" />");
            out.println("<input type=\"submit\" value=\"検索\" />");
            out.println("</form><br/>");

            // DB 接続
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/example");
            con = ds.getConnection();

            // SQL 組み立て（PreparedStatement で安全に）
            String sql = "select id, name, created_at, updated_at from tb_example";
            if (keyword != null && !keyword.isEmpty()) {
                sql += " WHERE name LIKE ? ORDER BY id";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + keyword + "%");
            } else {
                sql += " ORDER BY id";
                pstmt = con.prepareStatement(sql);
            }

            rs = pstmt.executeQuery();

            out.println("<table>");
            out.println("<tr><th>id</th><th>name</th><th>created_at</th><th>updated_at</th></tr>");
            int count = 0;
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("created_at") + "</td>");
                out.println("<td>" + rs.getString("updated_at") + "</td>");
                out.println("</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<p>" + count + " 件表示</p>");
            out.println("<p><a href=\"/example/SelectSample.jsp\">一覧に戻る</a></p>");
            out.println("</body></html>");

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
