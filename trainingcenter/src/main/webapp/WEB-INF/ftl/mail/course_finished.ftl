<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
  </head>
<body> 
<br/>Course ${course.name} has been finished
<br/>----------------------------------------------------
<table>
<tr><td>Name:</td><td>${course.name}</td></tr>
<tr><td>Category:</td> <td>${course.category.name}</td></tr>
<tr><td valign="top">Description: </td> <td> ${course.description?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Links:</td><td>${course.links?replace("\n", "<br />")}</td></tr>
</table>
<br/>----------------------------------------------------
<br/>See course details <a href="${courseDetailLink}">this link</a>
</body>
</html>
