<!DOCTYPE html>
<html>
    <head>
        <title>Add Lecture</title>
    </head>
    <body>
                <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>

        <h2>Create a Lecture</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="LectureForm">
            <form:label path="LectureName">Lecture Name :</form:label><br />
            <form:input type="text" path="LectureName" /><br /><br />
            <b>Attachments</b><br />
            <input type="file" name="attachments" multiple="multiple" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>
