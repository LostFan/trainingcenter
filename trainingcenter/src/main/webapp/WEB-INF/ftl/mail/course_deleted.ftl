<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
  </head>
<body> 
<br/>Course deleted by ${course.creater.name}
<br/>----------------------------------------------------
<table>
<tr><td>Name:</td><td>${course.name}</td></tr>
<tr><td>Category:</td> <td>${course.category.name}</td></tr>
<tr><td valign="top">Description: </td> <td> ${course.description?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Links:</td><td>${course.links?replace("\n", "<br />")}</td></tr>
</table>
<br/>----------------------------------------------------
<br/>
<br/>Previously course was rejected by the following votes:
<br/>----------------------------------------------------
<table>
<tr><td valign="top">Decision: </td> <td> ${course.decisionKM?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Reason:</td><td>${course.reasonKM?replace("\n", "<br />")}</td></tr>
</table>
<br/>----------------------------------------------------
<table>
<tr><td valign="top">Decision: </td> <td> ${course.decisionDM?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Reason:</td><td>${course.reasonDM?replace("\n", "<br />")}</td></tr>
</table>
<br/>----------------------------------------------------
</body>
</html>
