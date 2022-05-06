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

        <h2>Create a MC Poll</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="PollForm">
            <form:label path="poll_q">Question</form:label><br />
            <form:input type="text" path="poll_q" /><br /><br />
                        <form:label path="poll_a_a">A</form:label>
            <form:input type="text" path="poll_a_a" /><br /><br />
                                    <form:label path="poll_a_b">A</form:label>
            <form:input type="text" path="poll_a_b" /><br /><br />
                                    <form:label path="poll_a_c">A</form:label>
            <form:input type="text" path="poll_a_c" /><br /><br />
                                    <form:label path="poll_a_d">A</form:label>
            <form:input type="text" path="poll_a_d" /><br /><br />

            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>
