<#import "/spring.ftl" as spring/>
<@spring.bind "user.*" />
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/style.css"/>
  </head>
  <body>
    <div class="container">
      <header>
        <h1>Login</h1>
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
      <form class="form-horizontal" method="post" action="${rc.contextPath}/login">
        <fieldset>
          <div class="control-group">
            <label class="control-label">Login</label>
            <div class="controls">
              <div class="input-prepend">
                <span class="add-on">@</span>
                <#if user.name??> 
               		<input id="loginField" class="span3" type="text" name="name" value="${user.name}" />
               	<#else>
               		<input id="loginField" class="span3" type="text" name="name" />
               	</#if>
                
              </div>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Password</label>
            <div class="controls">
              <input id="passwordField" class="span3" type="password" name="password"/>
            </div>
          </div>
          <div class="form-actions">
            <button id="loginButton" class="btn btn-primary" type="submit">Login</button>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
