<#import "/spring.ftl" as spring/>
<@spring.bind "evaluation.*" />
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
          Evaluation
          <div class="logout">
            <span id="currentUserLogin">
              ${user.name}
            </span>
            <a href="logout.html">
              <i class="icon-off"></i>
            </a>
          </div>
        </h1>
      </header>
       <#if spring.status.error>
       <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
         <#list spring.status.errorMessages as error>
                <li>${error}</li>
            </#list>
        </ul>
      </div>
      </#if>
      <form class="form-horizontal" method="post" action="${rc.contextPath}/courses/${courseId}/evaluate">
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
          <div class="control-group">
            <label id="gradeField" class="control-label">Grade</label>
            <div class="controls">
              <input class="span2" type="text"/ name = "grade">
            </div>
          </div>
          <div class="form-actions">
            <button id="evaluateButton" class="btn btn-success" type="submit">Evaluate</button>
            <a class="btn" href="${rc.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
