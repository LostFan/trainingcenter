<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
  </head>
<body> 
<br/>${course.creater.name} had created a new course
<br/>----------------------------------------------------
<table>
<tr><td>Name:</td><td>${course.name}</td></tr>
<tr><td>Category:</td> <td>${course.category.name}</td></tr>
<tr><td valign="top">Description: </td> <td> ${course.description?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Links:</td><td>${course.links?replace("\n", "<br />")}</td></tr>
</table>
<br/>----------------------------------------------------
<br/>Please, review this course at <a href="${CourseApproveLink}">this link</a>
</body>
</html>
