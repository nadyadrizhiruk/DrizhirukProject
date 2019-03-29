<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enter client id</title>
</head>
<body>
    <form action="/orders" method="post">
        <p><h1>Enter client id</h1></p><br>
        <input type="hidden" name="kindOfMethod" value="get">
        <label>Client id: </label>
        <input type="text" name="clientId"><br />

        <input type="submit" value="print orders">
    </form>
</body>
</html>