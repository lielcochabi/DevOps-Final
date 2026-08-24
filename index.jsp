<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DevOps Final Project - Team Portal</title>
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
            margin: 0 0 6px 0;
            font-size: 24px;
        }
        .header p {
            margin: 0;
            font-size: 13px;
            color: #b9c2cf;
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
        }
        h2 {
            font-size: 18px;
            color: #16233a;
            margin-top: 0;
        }
        a.ext-link {
            color: #3b82f6;
            text-decoration: none;
            font-weight: bold;
        }
        a.ext-link:hover {
            text-decoration: underline;
        }
        form {
            margin-top: 20px;
        }
        label {
            display: block;
            font-size: 13px;
            margin-bottom: 6px;
            color: #334155;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 14px;
            box-sizing: border-box;
            margin-bottom: 14px;
        }
        button {
            background: #3b82f6;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
        }
        button:hover {
            background: #2563eb;
        }
        .footer-note {
            margin-top: 20px;
            font-size: 12px;
            color: #64748b;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>DevOps Final Project</h1>
    <p>Student 1 deliverable &mdash; Web Server &amp; Git</p>
</div>
<div class="accent-bar"></div>

<div class="content">
    <h2>Welcome</h2>
    <p>
        This is the base web application for the team's DevOps final project.
        It is deployed via Tomcat and version-controlled with Git / GitHub.
    </p>

    <p>
        <a class="ext-link" href="https://github.com/" target="_blank">
            Visit our GitHub repository &rarr;
        </a>
    </p>

    <form action="welcome.jsp" method="post">
        <label for="username">Enter your name:</label>
        <input type="text" id="username" name="username" placeholder="e.g. Liel" required>
        <button type="submit">Submit</button>
    </form>

    <p class="footer-note">
        Deployed at: <%= application.getServerInfo() %><br>
        Page generated: <%= new java.util.Date() %>
    </p>
</div>

</body>
</html>
