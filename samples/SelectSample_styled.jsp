<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.sql.*, javax.naming.*, javax.sql.*"%>
<%--
  演習5-1 解答例：デザイン変更版 SelectSample.jsp
  - CSS スタイル適用
  - タイトルを「社員一覧」に変更
  - 現在日時の表示
  - 検索フォーム付き（演習6-1 と連携）
--%>
<html>
<head>
<meta charset="UTF-8">
<title>社員一覧</title>
<style>
    body {
        font-family: 'Yu Gothic', 'メイリオ', sans-serif;
        margin: 20px;
        background-color: #FAFAFA;
    }
    h3 {
        color: #2E5090;
        border-bottom: 2px solid #2E5090;
        padding-bottom: 5px;
    }
    table {
        border-collapse: collapse;
        width: 80%;
        margin-top: 10px;
    }
    th {
        background-color: #D6E4F0;
        padding: 10px 8px;
        text-align: left;
        border: 1px solid #B0C4DE;
    }
    td {
        padding: 8px;
        border: 1px solid #ccc;
    }
    tr:nth-child(even) {
        background-color: #f5f5f5;
    }
    tr:hover {
        background-color: #E8F0FE;
    }
    .footer {
        margin-top: 20px;
        color: #888;
        font-size: 0.9em;
    }
    form {
        margin-bottom: 15px;
    }
</style>
</head>
<body>
<h3>社員一覧 (Docker環境)</h3>

<!-- 検索フォーム（演習6-1 と連携） -->
<form action="Servlet/SearchSample" method="GET">
    名前で検索：<input type="text" name="keyword" />
    <input type="submit" value="検索" />
</form>

<table>
<tr><th>id</th><th>name</th><th>created_at</th><th>updated_at</th></tr>
<%
    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/example");
    Connection con = ds.getConnection();
    PreparedStatement pstmt = con.prepareStatement(
        "select id, name, created_at, updated_at from tb_example order by id");
    ResultSet rs = pstmt.executeQuery();
    int count = 0;
    while(rs.next()) {
        count++;
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
<p><%= count %> 件表示</p>

<div class="footer">
<%
    String now = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());
%>
    表示日時：<%= now %>
</div>
</body>
</html>
