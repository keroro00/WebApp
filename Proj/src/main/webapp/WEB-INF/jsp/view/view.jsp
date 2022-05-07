<!DOCTYPE html>
<html>
    <head>
        <title>Course Material Page</title>
    </head>
    <body>
                <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>

        <h2>Lecture #${LectureId}: <c:out value="${Lecture.lectureName}" /> </h2>
        <security:authorize access="hasAnyRole('ADMIN','LECTURER')">
            [<a href="<c:url value="/Lecture/edit/${LectureId}" />">Edit</a>]
        </security:authorize>
        <security:authorize access="hasAnyRole('ADMIN','LECTURER')">
            [<a href="<c:url value="/Lecture/delete/${LectureId}" />">Delete</a>]
        </security:authorize>
        <br /><br />
        <c:if test="${Lecture.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${Lecture.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/Lecture/${LectureId}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>

<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="SubmitForm"><br><form:label path="comment">Comment :</form:label><br />
        <form:textarea path="comment" rows="5" cols="30" value=""/><br /><br />
        <input type="submit" value="Submit" />
    </form:form>
    <c:forEach items="${Comment}" var="comment">
        --------------------------------------------------<security:authorize access="hasAnyRole('ADMIN','LECTURER')">[<a href="<c:url value="/Lecture/Comhistory/delete/${LectureId}/${comment.username}/${comment.id}" />">Delete</a>]</security:authorize><br>
        ${comment.comment}<br><br>
        By ${comment.username}<br>  
    </c:forEach>      <br>
        <a href="<c:url value="/Lecture" />">Return to list</a><br><br>
</body>
</html>