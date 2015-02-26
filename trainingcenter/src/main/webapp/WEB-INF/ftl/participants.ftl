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
          Course Participants
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
          <div class="control-label">Subscribers</div>
          <div id="subscribersList" class="controls text">
            <ul class="unstyled">
            <#if currentcourse.coursesSubscribe??>
	        	<#list currentcourse.coursesSubscribe as courseUser>
	              <li>${courseUser.name}</li>
	            </#list>
			</#if>		
            </ul>
          </div>
        </div>
        <div class="control-group">
          <div class="control-label">Attendee</div>
          <div id="attendeeList" class="controls text">
            <ul class="unstyled">
             <#if currentcourse.coursesAttend??>
	        	<#list currentcourse.coursesAttend as courseUser>
	              <li>${courseUser.name}</li>
	            </#list>
			</#if>
            </ul>
          </div>
        </div>
        <div class="form-actions">
          <a class="btn" href="${rc.contextPath}/courses">Back</a>
        </div>
      </div>
    </div>
  </body>
</html>
