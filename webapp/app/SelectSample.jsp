<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.sql.*, javax.naming.*, javax.sql.*"%>
<html>
<head>
	<style>
	table{border-collapse:collapse;
		  width:80%;}
	th{background-color:#D6E4F0;
	   padding:8px;}
    td{padding:8px;
       border:1px solid #ccc;}
	</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員の一覧</title>
</head>
<body>
<h3>JSP上でDBレコードを取得するサンプル (Docker環境)</h3>

<form action="Servlet/SearchSample" method="GET">
    名前で検索：<input type="text" name="keyword" />
    <input type="submit" value="検索!" />
</form>

<table border="1">
<tr><th>id</th><th>name</th><th>created_at</th><th>updated_at</th></tr>
<%
    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/example");
    Connection con = ds.getConnection();
    PreparedStatement pstmt = con.prepareStatement(
        "select id, name, created_at, updated_at from tb_example order by id");
    ResultSet rs = pstmt.executeQuery();
    while(rs.next()) {
%>
    <tr>
        <td><%= rs.getString("id") %></td>
        <td><%= rs.getString("name") %></td>
        <td><%= rs.getString("created_at") %></td>
        <td><%= rs.getString("updated_at") %></td>
    </tr>
<%
    }
    rs.close(); rs = null;
    pstmt.close(); pstmt = null;
    con.close(); con = null;
    ctx.close(); ctx = null;
%>
</table>
<% String now = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date()); %>
<p>表示日時：<%= now %></p>

</body>
</html>
