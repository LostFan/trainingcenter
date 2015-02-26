<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
  </head>
<body> 
<br/>State of course ${course.name} was changed to Open
<br/>----------------------------------------------------
<table>
<tr><td>Name:</td><td>${course.name}</td></tr>
<tr><td>Category:</td> <td>${course.category.name}</td></tr>
<tr><td valign="top">Description: </td> <td> ${course.description?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Links:</td><td>${course.links?replace("\n", "<br />")}</td></tr>
</table>
<br/>----------------------------------------------------
<br/>See course details <a href="${courseDetailLink}">there</a>
<br/>Or became a attender <a href="${courseAttendeLink}">there</a>
</body>
</html>
