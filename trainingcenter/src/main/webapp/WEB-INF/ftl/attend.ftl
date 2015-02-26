<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/style.css""/>
  </head>
  <body>
    <div class="container">
      <header>
        <h1>
          Attend 
          <div class="logout">
            <span id="currentUserLogin">
              ${user.name}
            </span>
            <a href="${rc.contextPath}/logout">
              <i class="icon-off"></i>
            </a>
          </div>
        </h1>
      </header>
      <form class="form-horizontal" method="post" action="${rc.contextPath}/courses/${courseId}/attend">
        <fieldset>
          <div class="control-group">
            <div class="control-label">
              Course
            </div>
            <div id="titleField" class="controls text">
            ${currentcourse.name}
            </div>
          </div>
          <div class="control-group">
            <div class="control-label">
              Lecturer
            </div>
            <div id="ownerField" class="controls text">
              ${currentcourse.creater.name}
            </div>
          </div>
          <div class="form-actions">
            <button id="subscribeButton" class="btn btn-success" type="submit">Attend</button>
            <a class="btn" href="${rc.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
