<%-- 
    Document   : Pagination
    Created on : 9 Apr, 2018, 6:32:20 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   

<sql:setDataSource
    var="myDS"
    driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://10.10.11.28:3306/esign"
    user="vidya"
    password="vidya"
    />

<sql:query var="queryResults"   dataSource="${myDS}">
    select doc_name from activity_report;
</sql:query>

<c:set var="totalCount" scope="session" value="${queryResults.rowCount}"/>
<c:set var="perPage" scope="session" value="5"/>
<c:set var="totalPages" scope="session" value="${totalCount/perPage}"/>
totalCount = <c:out value="${totalCount}"/><br/>
perPage = <c:out value="${perPage}"/><br/>
totalPages = <c:out value="${totalPages}"/><br/>

<c:set var="pageIndex" scope="session" value="${param.start/perPage+1}"/>

<c:if test="${!empty param.start && param.start >(perPage-1) && param.start !=0 }">
    <a href="?start=<c:out value="${param.start - perPage}"/>">Prev </a>

</c:if>

<c:forEach
    items="${queryResults.rows}"
    var="boundaryStart"
    varStatus="status"
    begin="0"
    end="${totalCount - 1}"
    step="${perPage}">
    <c:choose>
        <c:when test="${status.count>0 && status.count != pageIndex}">
            <a href="?start=<c:out value='${boundaryStart}'/>">
                <c:out value="${status.count}"/> |
            </a>
        </c:when>
        <c:otherwise>
            <c:out value="${status.count}"/> |
        </c:otherwise>

    </c:choose>
</c:forEach>

<c:if test="${empty param.start || param.start<(totalCount-perPage)}">
    <a href="?start=<c:out value="${param.start + perPage}"/>">Next </a>
</c:if>
