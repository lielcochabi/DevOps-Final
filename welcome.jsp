<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = request.getParameter("username");
    if (username == null || username.trim().isEmpty()) {
        username = "Guest";
    }
    // Basic sanitization to avoid reflecting raw HTML back to the page
    username = username.replaceAll("[<>&\"']", "");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background: #f1f5f9;
            margin: 0;
            padding: 0;
            color: #1f2937;
        }
        .header {
            background: #1e2a3a;
            color: #ffffff;
            padding: 24px 40px;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .accent-bar {
            height: 5px;
            background: #3b82f6;
        }
        .content {
            max-width: 600px;
            margin: 40px auto;
            background: #ffffff;
            border: 1px solid #dbe1ea;
            border-radius: 8px;
            padding: 28px 32px;
            text-align: center;
        }
        h2 {
            color: #16233a;
        }
        a.back-link {
            display: inline-block;
            margin-top: 16px;
            color: #3b82f6;
            text-decoration: none;
            font-weight: bold;
        }
        a.back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>DevOps Final Project</h1>
</div>
<div class="accent-bar"></div>

<div class="content">
    <h2>Hello, <%= username %>!</h2>
    <p>Your submission was received successfully by the Tomcat server.</p>
    <a class="back-link" href="index.jsp">Back to home</a>
</div>

</body>
</html>
