<%--
  Created by IntelliJ IDEA.
  User: ghazal
  Date: 13.03.21
  Time: 01:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Bolbolestan.Bolbolestan" %>
<%@ page import="Bolbolestan.Student.Student" %>
<%@ page import="Bolbolestan.Student.Grade" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 10%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/ca3_war_exploded/">Home</a>

<%
    Bolbolestan bolbolestan = Bolbolestan.getInstance();
    Student student = bolbolestan.getStudentById(bolbolestan.getLoggedInId());
%>

<ul>
    <li id="std_id">Student Id: <%=student.getId()%></li>
    <li id="first_name">First Name: <%=student.getName()%></li>
    <li id="last_name">Last Name: <%=student.getSecondName()%></li>
    <li id="birthdate">Birthdate: <%=student.getBirthDate()%></li>
    <li id="gpa">GPA: <%=Float.toString(student.getGPA())%></li>
    <li id="tpu">Total Passed Units: <%=Integer.toString(bolbolestan.getUnitsPassed(student.getId()))%></li>
</ul>
<table>
<tr>
    <th>Code</th>
    <th>Grade</th>
</tr>
<%
    if (student.getGrades() != null)
        for (Grade grade : student.getGrades()) {
%>
<tr>
    <td><%=grade.getCode()%></td>
    <td><%=Integer.toString(grade.getGrade())%></td>
</tr>
<% } %>
</table>
</body>
</html>
