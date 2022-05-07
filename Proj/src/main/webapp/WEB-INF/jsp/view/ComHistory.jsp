<!DOCTYPE html>
<html>
    <head><title> Comment History</title></head>
    <body>
        <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <br /><br /><a href="<c:url value="/Lecture/list" />">Return to list </a>
        <h2>Comment Historys</h2>

        <c:choose>
            <c:when test="${fn:length(ComHistorys) == 0}">
                <i>There are no your Comment history in the system.</i>
            </c:when>
            <c:otherwise>
                History of ${ComHistorys[0].username}<br><br>
                <table>
                    <tr>
                        <th>Place</th>  <th>History_Id</th>  <th>Comment</th>
                    </tr>
                    <c:forEach items="${ComHistorys}" var="comment"   >
                        <tr>
                            <td>${comment.place}</td><td>#${comment.id}</td>
                            <td>
                                ${comment.comment}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

    </body>
</html>

