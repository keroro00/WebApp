<!DOCTYPE html>
<html>
<head>
    <title>Poll</title>
</head>
<body>
    
        <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>

<h2>Poll #${PollId}: <c:out value="${Poll.poll_q}" /> </h2>

  You answered ${Poll.answers}

<br /><br />
<a href="<c:url value="/Lecture" />">Return to list</a>
</body>
</html>