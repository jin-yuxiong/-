<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSON analysis</title>
</head>
<body>
<form action="parsejson.do" method="post" enctype="multipart/form-data">
    <p>JSON文件解析：</p>
    <p>
    <label><input type="file" name="json"></label>
    </p>
    <p>
    <label><input type="submit" value="确定" /></label>
    </p>
</form>
</body>
</html>