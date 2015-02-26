<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
  </head>
<body> 
<br/>${manager.name} had made a decision
<br/>----------------------------------------------------
<table>
<tr><td>Name:</td><td>${course.name}</td></tr>
<tr><td>Category:</td> <td>${course.creater.name}</td></tr>
</table>
<br/>----------------------------------------------------
<table>
<tr><td valign="top">Decision: </td> <td> ${decision?replace("\n", "<br />")}</td></tr>
<tr><td valign="top">Reason:</td><td>${reason?replace("\n", "<br />")}</td></tr>
</table>
</body>
</html>