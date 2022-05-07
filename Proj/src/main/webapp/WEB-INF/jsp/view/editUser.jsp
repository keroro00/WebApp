<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
    </head>
    <body>
                <security:authorize access="hasAnyRole('USER','LECTURER','ADMIN')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <h1 style="text-align: center;">Edit User Account</h1>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="WebsiteUser">
            <c:forEach items="${WebsiteUsers}" var="user">
                <form:label path="username"  hidden="yes" >Username</form:label><br/>
                <form:input type="text" path="username"  value="${user.username}"/><br/><br/>
                <form:label path="password">Password</form:label><br/>
                <form:input type="text" path="password" value=" ${user.password}" /><br/><br/>
                <form:label path="roles">Roles</form:label><br/>
                    <c:choose>
                        <c:when test="${user.roles.contains('ROLE_STUDENT')}">
                            <form:checkbox path="roles" value="ROLE_STUDENT" checked="yes" />ROLE_STUDENT
                        </c:when>
                        <c:otherwise>
                            <form:checkbox path="roles" value="ROLE_STUDENT" />ROLE_STUDENT
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${user.roles.contains('ROLE_LECTURER')}">
                            <form:checkbox path="roles" value="ROLE_LECTURER" checked="yes" />ROLE_LECTURER
                        </c:when>
                        <c:otherwise>
                            <form:checkbox path="roles" value="ROLE_LECTURER" />ROLE_LECTURER
                        </c:otherwise>
                    </c:choose>
                                        <c:choose>
                        <c:when test="${user.roles.contains('ROLE_ADMIN')}">
                            <form:checkbox path="roles" value="ROLE_ADMIN" checked="yes" />ROLE_ADMIN
                        </c:when>
                        <c:otherwise>
                            <form:checkbox path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
                        </c:otherwise>
                    </c:choose>
<br/>
                <form:label path="fullname">fullname</form:label><br/>
                <form:input type="text" path="fullname" value="${user.fullname}"/><br/><br/>
                <form:label path="phone">Phone</form:label><br/>
                <form:input type="text" path="phone" value="${user.phone}" /><br/><br/>
                <form:label path="address">Address</form:label><br/>
                <form:input type="text" path="address" value="${user.address}"/><br/><br/>
                <br /><br />
            </c:forEach>
            <input type="submit" value="Edit User"/>
        </form:form>
    </body>
</html>
