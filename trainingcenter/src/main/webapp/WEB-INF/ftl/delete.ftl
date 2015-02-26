<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/style.css"/>
  </head>
  <body>
    <div class="container">
      <header>
        <h1>
          Delete Course
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
      <form class="form-horizontal" method="post">
        <fieldset>
          <div class="control-group">
            <div class="control-label">Name</div>
            <div id="titleField" class="controls text">${currentcourse.name}</div>
          </div>
          <div class="control-group">
            <div class="control-label">Category</div>
            <div id="categoryField" class="controls text">${currentcourse.creater.name}t</div>
          </div>
          <div class="form-actions">
            <button id="deleteButton" class="btn btn-danger" type="submit">Delete</button>
            <a class="btn" href="${rc.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
