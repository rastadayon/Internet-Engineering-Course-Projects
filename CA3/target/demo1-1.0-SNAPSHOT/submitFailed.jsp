<%--
  Created by IntelliJ IDEA.
  User: ghazal
  Date: 18.03.21
  Time: 01:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Bolbolestan.Bolbolestan" %>
<%@ page import="Bolbolestan.Student.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Submit Failed</title>
    <style>
        h1 {
            color: rgb(207, 3, 3);
        }
    </style>
</head>
<%
    Bolbolestan bolbolestan = Bolbolestan.getInstance();
    Student student = bolbolestan.getStudentById(bolbolestan.getLoggedInId());
%>
<body>
<a href="/ca3_war_exploded/">Home</a>
<h1>
    Error:
</h1>
<br>
<%
    for (String error: student.getSubmissionErrors()) {
%>
<h3>
    <%=error%>
</h3>
<% } %>
</body>
</html>
