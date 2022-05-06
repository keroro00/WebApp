<!DOCTYPE html>
<html>
<head>
    <title>Lecture</title>
</head>
<body>
        <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>

<h2>Lecture #${LectureId}</h2>
    <form:form method="POST" enctype="multipart/form-data" 
                             modelAttribute="LectureForm">
        <form:label path="LectureName">LectureName</form:label><br/>
        <form:input type="text" path="LectureName" /><br/><br/>
        <c:if test="${Lecture.numberOfAttachments > 0}">
            <b>Attachments:</b><br/>
            <ul>
                <c:forEach items="${Lecture.attachments}" var="attachment">
                    <li>
                        <c:out value="${attachment.name}" />
                        [<a href="<c:url value="/Lecture/${LectureId}/delete/${attachment.name}" />">Delete</a>]
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <b>Add attachments</b><br />
        <input type="file" name="attachments" multiple="multiple"/><br/><br/>
        <input type="submit" value="Save"/>
    </form:form>
</body>
</html> 