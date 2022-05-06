<!DOCTYPE html>
<html>
    <head>
        <title>EditPoll</title>
    </head>
    <body>
        <h1 style="text-align: center;">Edit the poll</h1>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="Poll">
            <c:forEach items="${PollDatabase}" var="poll">
                <form:label path="id"  hidden="yes">id</form:label><br/>
                <form:input type="text" path="id"  value="${poll.id}" hidden="yes"/><br/><br/>
                <form:label path="poll_q">Question</form:label><br/>
                <form:input type="text" path="poll_q" value=" ${poll.poll_q}" /><br/><br/>
                <form:label path="poll_a_a">A</form:label>
                <form:input type="text" path="poll_a_a" value="${poll.poll_a_a}"/><br/><br/>
                <form:label path="poll_a_b">B</form:label>
                <form:input type="text" path="poll_a_b" value="${poll.poll_a_b}" /><br/><br/>
                <form:label path="poll_a_c">C</form:label>
                <form:input type="text" path="poll_a_c" value="${poll.poll_a_c}"/><br/><br/>
                <form:label path="poll_a_d">D</form:label>
                <form:input type="text" path="poll_a_d" value="${poll.poll_a_d}"/><br/><br/>
                <br /><br />
            </c:forEach>
            <input type="submit" value="Edit poll"/>
        </form:form>
    </body>
</html>
