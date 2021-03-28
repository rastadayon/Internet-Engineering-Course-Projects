<%--
  Created by IntelliJ IDEA.
  User: rastadayon
  Date: 3/16/21
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Bolbolestan.Bolbolestan" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Bolbolestan.Offering.Offering" %>
<%@ page import="java.util.List" %>
<%@ page import="Bolbolestan.Student.WeeklySchedule" %>
<%@ page import="Bolbolestan.Student.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Bolbolestan bolbolestan = Bolbolestan.getInstance();
    Student student = bolbolestan.getStudentById(bolbolestan.getLoggedInId());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Courses</title>
    <style>
        .course_table {
            width: 100%;
            text-align: center;
        }
        .search_form {
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/ca3_war_exploded/">Home</a>
<li id="code">Student Id: <%=bolbolestan.getLoggedInId()%></li>
<li id="units">Total Selected Units: <%=student.getTotalSelectedUnits()%></li>

<br>


<table>
    <tr>
        <th>Code</th>
        <th>Class Code</th>
        <th>Name</th>
        <th>Units</th>
        <th></th>
    </tr>
    <%
        List<Offering> selectedOfferings = student.getSelectedOfferings().getOfferings();
        List<Offering> submittedOfferings = student.getSubmittedOfferings().getOfferings();
        List<Offering> waitingOfferings = student.getWaitingOfferings().getOfferings();
    %>
    <%for (Offering submittedOffering : submittedOfferings) { %>
    <tr>
        <td><%=submittedOffering.getCourseCode()%></td>
        <td><%=submittedOffering.getClassCode()%></td>
        <td><%=submittedOffering.getName()%></td>
        <td><%=submittedOffering.getUnits()%></td>
        <td>
            <form action="changeSelection" method="POST" >
                <input id="form_course_code" type="hidden" name="course_code" value=<%=submittedOffering.getCourseCode()%>>
                <input id="form_class_code" type="hidden" name="class_code" value=<%=submittedOffering.getClassCode()%>>
                <button type="submit" name="action" value="remove">Remove</button>
                <button type="submit" name="action" value="wait">Wait</button>
            </form>
        </td>
    </tr>
    <%}%>
    <%for (Offering waitingOffering : waitingOfferings) { %>
    <tr>
        <td><%=waitingOffering.getCourseCode()%></td>
        <td><%=waitingOffering.getClassCode()%></td>
        <td><%=waitingOffering.getName()%></td>
        <td><%=waitingOffering.getUnits()%></td>
        <td>
            <form action="changeSelection" method="POST" >
                <input id="form_course_code" type="hidden" name="course_code" value=<%=waitingOffering.getCourseCode()%>>
                <input id="form_class_code" type="hidden" name="class_code" value=<%=waitingOffering.getClassCode()%>>
                <button type="submit" name="action" value="remove">Remove</button>
                <button type="submit" name="action" value="wait">Wait</button>
            </form>
        </td>
    </tr>
    <%}%>
    <%for (Offering selectedOffering : selectedOfferings) { %>
    <tr>
        <td><%=selectedOffering.getCourseCode()%></td>
        <td><%=selectedOffering.getClassCode()%></td>
        <td><%=selectedOffering.getName()%></td>
        <td><%=selectedOffering.getUnits()%></td>
        <td>
            <form action="changeSelection" method="POST" >
                <input id="form_course_code" type="hidden" name="course_code" value=<%=selectedOffering.getCourseCode()%>>
                <input id="form_class_code" type="hidden" name="class_code" value=<%=selectedOffering.getClassCode()%>>
                <button type="submit" name="action" value="remove">Remove</button>
                <button type="submit" name="action" value="wait">Wait</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>

<br>

<form action="submit" method="POST">
    <button type="submit" name="action" value="submit">Submit Plan</button>
    <button type="submit" name="action" value="reset">Reset</button>
</form>

<br>

<form class="search_form" action="searchCourses" method="POST">
    <label>Search:</label>
    <%
        String searchString = student.getSearchString();
        if (searchString == null)
            searchString = "";
    %>
    <input type="text" name="search" value=<%=searchString%>>
    <button type="submit" name="action" value="search">Search</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>



<br>

<%
    String stdId = bolbolestan.getLoggedInId();
    List<Offering> searchedOfferings = bolbolestan.getSearchedOfferings(stdId);
%>

<table class="course_table">
    <tr>
        <th>Code</th>
        <th>Class Code</th>
        <th>Name</th>
        <th>Units</th>
        <th>Signed Up</th>
        <th>Capacity</th>
        <th>Type</th>
        <th>Days</th>
        <th>Time</th>
        <th>Exam Start</th>
        <th>Exam End</th>
        <th>Prerequisites</th>
        <th>Prerequisites</th>
        <th></th>
    </tr>
    <%for (Offering offering : searchedOfferings) {%>
    <tr>
        <td><%=offering.getCourseCode()%></td>
        <td><%=offering.getClassCode()%></td>
        <td><%=offering.getName()%></td>
        <td><%=offering.getUnits()%></td>
        <td><%=offering.getSignedUp()%></td>
        <td><%=offering.getCapacity()%></td>
        <td><%=offering.getType()%></td>
        <td><%=offering.getClassDayString("|")%></td>
        <td><%=offering.getClassTime().getTime()%></td>
        <td><%=offering.getExamTime().getStart()%></td>
        <td><%=offering.getExamTime().getEnd()%></td>
        <td><%=offering.getPrerequisitesString()%></td>
        <td>
            <form action="changeSelection" method="POST" >
                <input id="form_action" type="hidden" name="action" value="add">
                <input id="form_class_code" type="hidden" name="course_code" value=<%=offering.getCourseCode()%>>
                <input id="form_class_code" type="hidden" name="class_code" value=<%=offering.getClassCode()%>>
                <button type="submit">Add</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>