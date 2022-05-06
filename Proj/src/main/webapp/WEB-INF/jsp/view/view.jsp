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
<a href="<c:url value="/Lecture" />">Return to list Lectures</a>
</body>
</html>