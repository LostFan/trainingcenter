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
          Course Details
         <#if user??>
          <div class="logout">
            <span id="currentUserLogin">
              ${user.name}
            </span>
            <a href="${rc.contextPath}/logout">
              <i class="icon-off"></i>
            </a>
          </div>
          </#if>
        </h1>
      </header>
      <div class="form-horizontal">
        <div class="control-group">
          <div class="control-label">Name</div>
          <div id="titleField" class="controls text">${currentcourse.name}</div>
        </div>
          <div class="control-group">
          <div class="control-label">Lecturer</div>
          <div id="ownerField" class="controls text">${currentcourse.creater.name}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Category</div>
          <div id="descriptionField" class="controls text">${currentcourse.category.name}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Description</div>
          <div id="descriptionField" class="controls text">${currentcourse.description?replace("\n", "<br />")}</div>
        </div>
        <div class="control-group" >
          <div class="control-label">Links</div>
          <div id="linksField" class="controls text">${currentcourse.links?replace("\n", "<br />")}</div>
        </div>
          <div class="control-group">
          <div class="control-label">Subscribers</div>
          <div id="subscribersField" class="controls text">${currentcourse.coursesSubscribe?size}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Attendee</div>
          <div id="attendeeField" class="controls text">${currentcourse.coursesAttend?size}</div>
        </div> 
         <#if currentcourse.coursesEvaluate?keys?seq_contains(user)>
         <div class="control-group">
          <div class="control-label">Grade</div>
          <div id="attendeeField" class="controls text">
          ${currentcourse.getUserGrade(user)}</div>
        </div>
        </#if>
        <div class="form-actions">
          <a class="btn" href="${rc.contextPath}/courses">Back</a>
        </div>
      </div>
    </div>
  </body>
</html>
