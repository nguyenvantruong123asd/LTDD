<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>List Video</title>
    <link rel="stylesheet" href="/admin/css/courses.css"/>
    <link rel="shortcut icon" type="image/png" href="/favicon.ico"/>
</head>

<body>
<p>Tổng số video: ${pageResponse.pagination._totalRows}</p>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>HREF</th>
        <th>THUMBAI</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${pageResponse.data}">
        <tr>
            <td>${item.id}</td>
            <td>${item.urlVideo}</td>
            <td>
                <video width="200" height="200">
                    <source src="${item.urlVideo}" type="video/mp4"/>

                </video>
            </td>
            <td><a
                    href="/admin/video-delete?id=${item.id}&_limit=${pageResponse.pagination._limit}&_page=${pageResponse.pagination._page}">
                <button
                        type="button">Xóa
                </button>
            </a></td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${pageResponse.pagination._page gt '1' }">
    <a
            href="/admin/videos?_page=${pageResponse.pagination._page-1}&_limit=${pageResponse.pagination._limit}">Previous</a>
</c:if>

<c:if
        test="${pageResponse.pagination._page lt pageResponse.pagination._totalRows/pageResponse.pagination._limit }">
    <a
            href="/admin/videos?_page=${pageResponse.pagination._page+1}&_limit=${pageResponse.pagination._limit}">Next</a>
</c:if>
<!-- <br /> -->
<div></div>
Click on this
<strong><a href="/admin">link</a></strong> to visit Admin Page


</body>
</html>
