<!DOCTYPE html>
<html>
    <head><title> Vote History</title></head>
    <body>
        <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <br /><br /><a href="<c:url value="/Poll/list" />">Return to Poll list </a>
        <h2>Vote Historys</h2>

        <c:choose>
            <c:when test="${fn:length(VoteHistorys) == 0}">
                <i>There are no your vote history in the system.</i>
            </c:when>
            <c:otherwise>
                History of ${VoteHistorys[0].username}<br><br>
                <table>
                    <tr>
                        <th>Question_Id</th>  <th>History_Id</th>  <th>Answer</th>
                    </tr>
                    <c:forEach items="${VoteHistorys}" var="hist" >
                        <tr>
                            <td>${hist.id}</td><td>#${hist.historyid}</td>
                            <td>
                                ${hist.answer}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

    </body>
</html>

