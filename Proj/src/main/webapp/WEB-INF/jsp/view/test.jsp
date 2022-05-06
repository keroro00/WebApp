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

<h2>Poll #${PollId}: <c:out value="${Poll.poll_q}" /> </h2>

  You answered ${Poll.answers}

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