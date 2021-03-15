<%@ page import="Bolbolestan.Bolbolestan" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Courses</title>
</head>
<body>
<%
String loggedInStudent = Bolbolestan.getInstance().getLoggedInId();
%>
<ul>
    <li id="std_id">Student Id: <%=loggedInStudent%></li>
    <li>
        <a href="/ca3_war_exploded/courses">Select Courses</a>
    </li>
    <li>
        <a href="/ca3_war_exploded/plan">Submited plan</a>
    </li>
    <li>
        <a href="/ca3_war_exploded/profile">Profile</a>
    </li>
    <li>
        <a href="/ca3_war_exploded/logout">Log Out</a>
    </li>
</ul>
</body>
</html>