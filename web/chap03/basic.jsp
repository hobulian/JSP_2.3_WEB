<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
    <title>HTML 문서의 제목</title>
</head>
<body>
<%
    String bookTitle = "JSP 프로그래밍";
    String Author = "최범균";
%>
<b><%=bookTitle %>
</b>(<%=Author%>)입니다.
</body>
</html>
