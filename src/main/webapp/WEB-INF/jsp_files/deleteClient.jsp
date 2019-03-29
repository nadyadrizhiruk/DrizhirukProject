<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>remove client</title>
</head>
<body>
    <form action="/clients" method="post">
        <p><h1>Removing by id</h1></p><br>
        <input type="hidden" name="kindOfMethod" value="delete">
        <label>Id: </label>
        <input type="text" name="id"><br />

        <input type="submit" value="remove client">
    </form>
</body>
</html>