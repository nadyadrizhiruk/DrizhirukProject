<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Creating new order</title>
</head>
<body>
    <form action="/orders" method="post">
        <p><h1>Creating new order</h1></p><br>
        <input type="hidden" name="kindOfMethod" value="post">

        <label>Client id: </label>
        <input type="text" name="clientId">

        <label>Date:</label>
        <input type="text" name="date"><br />

        <label>Product id:</label>
        <input type="text" name="productId1">

        <label>Amount:</label>
        <input type="text" name="amount1">

        <label>Price: </label>
        <input type="text" name="price1"><br />


        <label>Product id:</label>
        <input type="text" name="productId2">

        <label>Amount:</label>
        <input type="text" name="amount2">

        <label>Price: </label>
        <input type="text" name="price2"><br />


        <label>Product id:</label>
        <input type="text" name="productId3">

        <label>Amount:</label>
        <input type="text" name="amount3">

        <label>Price: </label>
        <input type="text" name="price3"><br />


        <label>Product id:</label>
        <input type="text" name="productId4">

        <label>Amount:</label>
        <input type="text" name="amount4">

        <label>Price: </label>
        <input type="text" name="price4"><br />

        <input type="submit" value="Add new order">
    </form>
</body>
</html>