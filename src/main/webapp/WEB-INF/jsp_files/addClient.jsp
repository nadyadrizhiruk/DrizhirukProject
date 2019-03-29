<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Creating new client</title>
</head>
<body>
    <form action="/clients" method="post">
        <p><h1>Creating new client</h1></p><br>
        <input type="hidden" name="kindOfMethod" value="post">

        <label>Name: </label>
        <input type="text" name="name"><br />

        <label>Surname:</label>
        <input type="text" name="surname"><br />

        <label>Age:</label>
        <input type="text" name="age"><br />

        <label>Email:</label>
        <input type="text" name="email"><br />

        <label>Phone: </label>
        <input type="text" name="phone"><br />

        <input type="submit" value="Add new client">
    </form>
</body>
</html>