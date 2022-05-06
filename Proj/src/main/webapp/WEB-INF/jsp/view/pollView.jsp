<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Poll #${PollId}: <c:out value="${Poll.poll_q}" /> </h2> (Total voted: ${Poll.total})
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="PollSubmitForm">
            <form:input type="text" path="id" value="${PollId}" hidden="yes" />
            <table>
                <tr><td><form:radiobutton  path="answers" value= "A"/><label ><c:out value="${Poll.poll_a_a}" /></label></td><td> (${Poll.number_of_a} Voted)</td></tr><br> 
                <tr><td><form:radiobutton  path="answers" value= "B"/><label ><c:out value="${Poll.poll_a_b}" /></label> </td><td>(${Poll.number_of_b} Voted)</td></tr><br> 
            <tr><td><form:radiobutton  path="answers" value= "C"/><label ><c:out value="${Poll.poll_a_c}" /></label> </td><td>(${Poll.number_of_c} Voted)</td></tr><br>
            <tr><td><form:radiobutton  path="answers" value= "D"/><label ><c:out value="${Poll.poll_a_d}" /></label> </td><td>(${Poll.number_of_d} Voted)</td></tr><br>
            </table>
            <br><BR>
                <input type="submit" value="Submit"/>
                <br><br>
        </form:form>
        <security:authorize access="hasAnyRole('ADMIN','LECTURER')">
            [<a href="<c:url value="/Poll/edit/${PollId}" />">Edit</a>]
        </security:authorize>
        <security:authorize access="hasAnyRole('ADMIN','LECTURER')">
            [<a href="<c:url value="/Poll/delete/${PollId}" />">Delete</a>]
        </security:authorize>
        <br /><br />
        <a href="<c:url value="/Poll" />">Return to list Polls</a>
    </body>
</html>