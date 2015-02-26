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
        ${badPage}
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
       <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
          <li>${message}</li>
        </ul>
      </div>
         <div class="form-horizontal">
        <div class="form-actions">
          <a class="btn" href="${rc.contextPath}/courses">Back</a>
        </div>
      </div>
      </div>
      </body>
      </html>