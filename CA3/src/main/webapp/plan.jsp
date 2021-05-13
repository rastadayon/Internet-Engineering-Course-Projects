<%--
  Created by IntelliJ IDEA.
  User: ghazal
  Date: 16.03.21
  Time: 02:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Bolbolestan.Bolbolestan" %>
<%@ page import="Bolbolestan.Student.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Plan</title>
    <style>
        table{
            width: 100%;
            text-align: center;

        }
        table, th, td{
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<%
    Bolbolestan bolbolestan = Bolbolestan.getInstance();
    Student student = bolbolestan.getStudentById(bolbolestan.getLoggedInId());
%>

<%
    final List<String> days = Arrays.asList("Saturday", "Sunday", "Monday",
            "Tuesday", "Wednesday");
    final List<String> startTimes = Arrays.asList("7:30", "9:00", "10:30",
            "14:00", "16:00");
%>

<a href="/ca3_war_exploded/">Home</a>
<li id="code">Student Id: <%=student.getId()%></li>
<br>
<table>
    <tr>
        <th></th>
        <th>7:30-9:00</th>
        <th>9:00-10:30</th>
        <th>10:30-12:00</th>
        <th>14:00-15:30</th>
        <th>16:00-17:30</th>
    </tr>
    <%
        for (String day: days) {
    %>
    <tr>
        <td><%=day%></td>
        <%
            for (String startTime: startTimes) {
        %>
        <td><%=student.getCourseNameByClassTime(day, startTime)%></td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
