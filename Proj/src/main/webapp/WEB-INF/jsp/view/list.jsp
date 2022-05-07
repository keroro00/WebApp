<%@ page import="hkmu.comps380f.model.WebsiteUser.*" %>
<%@ page import="hkmu.comps380f.dao.WebsiteUserService.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support Login</title>
    </head>
    <body>
        <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <security:authorize access="!hasAnyRole('STUDENT','ADMIN','LECTURER')">
            <a href="<c:url value="/cslogin" />"><button>Sign In</button></a><br /><br />
            <a href="<c:url value="/user/signup" />"><button>Sign Up</button></a><br /><br />
        </security:authorize>
        <h1 style="text-align: center;">Web Application S380F</h1>
        <security:authorize access="hasAnyRole('ADMIN','LECTURER')">
            <a href="<c:url value="/Poll/votehistory" />">My Vote History</a><br /><br />
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
        <a href="<c:url value="/Lecture/create" />">Create a Lecture</a><br /><br />
        <a href="<c:url value="/Poll/create" />">Create a Poll</a><br /><br />
        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(LectureDatabase) == 0}">
                <i>There are no Lectures in the system.</i>
            </c:when>
            <c:otherwise>
                <ul>
                    <c:forEach items="${LectureDatabase}" var="entry">
                        <li>
                            Lecture ${entry.key}:
                            <a href="<c:url value="/Lecture/view/${entry.key}" />">
                                <c:out value="${entry.value.lectureName}" /></a>


                            <br />
                        </li>
                    </c:forEach>
                </ul> 
            </c:otherwise>
        </c:choose>
                
                        <c:choose>
            <c:when test="${fn:length(PollDatabases) == 0}">
                <i>123$321There are no Poll in the system.</i>
            </c:when>
            <c:otherwise>
                <ul>
                    <c:forEach items="${PollDatabases}" var="Q">
                        <li>
                            Poll ${Q.id}:
                            <a href="<c:url value="/Poll/view/${Q.id}" />">
                                <c:out value="${Q.poll_q}" /></a>


                            <br />
                        </li>
                    </c:forEach>
                </ul> 
            </c:otherwise>
        </c:choose>


</body>
</html>