<!DOCTYPE html>
<html>
    <head><title> User Management</title></head>
    <body>
        <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <br /><br /><a href="<c:url value="/Lecture/list" />">Return to Lecture list </a>
        <h2>Users</h2>
        <a href="<c:url value="/user/create" />">Create a User</a><br /><br />
        <c:choose>
            <c:when test="${fn:length(WebsiteUsers) == 0}">
                <i>There are no users in the system.</i>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Username</th><th>Password</th><th>Roles</th><th>Action</th>
                    </tr>
                    <c:forEach items="${WebsiteUsers}" var="user">
                        <tr>
                            <td>${user.username}</td><td>${user.password}</td>
                            <td>
                                <c:forEach items="${user.roles}" var="role" varStatus="status">
                                    <c:if test="${!status.first}">, </c:if>
                                    ${role}
                                </c:forEach>
                            </td>
                            <td>
                                [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                            </td>
                            <td>
                                [<a href="<c:url value="/user/edit/${user.username}" />">Edit</a>]
                            </td>
                            ${user.fullname}
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

    </body>
</html>

