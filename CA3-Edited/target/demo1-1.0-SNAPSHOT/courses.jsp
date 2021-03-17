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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Bolbolestan bolbolestan = Bolbolestan.getInstance();
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
<li id="units">Total Selected Units: #(we have to change this)16#</li>

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
        List<Offering> selectedOfferings = bolbolestan.getLoggedInStudent().getSelectedOfferings().getOfferings();
        List<Offering> submittedOfferings = bolbolestan.getLoggedInStudent().getSubmittedOfferings().getOfferings();
    %>
    <%for (Offering submittedOffering : submittedOfferings) { %>
    <tr>
        <td><%=submittedOffering.getCourseCode()%></td>
        <td><%=submittedOffering.getClassCode()%></td>
        <td><%=submittedOffering.getName()%></td>
        <td><%=submittedOffering.getUnits()%></td>
        <td>
            <form action="changeSelection" method="POST" >
                <input id="form_action" type="hidden" name="action" value="remove">
                <input id="form_course_code" type="hidden" name="course_code" value=<%=submittedOffering.getCourseCode()%>>
                <input id="form_class_code" type="hidden" name="class_code" value=<%=submittedOffering.getClassCode()%>>
                <button type="submit">Remove</button>
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
                <input id="form_action" type="hidden" name="action" value="remove">
                <input id="form_course_code" type="hidden" name="course_code" value=<%=selectedOffering.getCourseCode()%>>
                <input id="form_class_code" type="hidden" name="class_code" value=<%=selectedOffering.getClassCode()%>>
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>

<br>

<form action="" method="POST">
    <button type="submit" name="action" value="submit">Submit Plan</button>
    <button type="submit" name="action" value="reset">Reset</button>
</form>

<br>

<form class="search_form" action="searchCourses" method="POST">
    <label>Search:</label>
    <%
        String searchString = bolbolestan.getLoggedInStudent().getSearchString();
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