<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
  </head>
<body>
<br/>Course was rejected by ${number}
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
<br/>Update your course <a href="${CourseUpdateLink}">there</a>
</body>
</html>
