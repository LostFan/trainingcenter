<#import "/spring.ftl" as spring/>
<@spring.bind "course.*" />
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
          Create Course
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
      <#if user.role != "LECTURER">
       <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
          <li>Action not allowed. Say again please.</li>
        </ul>
      </div>
      <div class="form-horizontal">
        <div class="form-actions">
          <a class="btn" href="${rc.contextPath}/courses">Back</a>
        </div>
      </div>
    </div>
    <#else>
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
      <form class="form-horizontal" method="post" action="${rc.contextPath}/courses/create">
        <fieldset>
          <div class="control-group">
            <label class="control-label">Name</label>
            <div class="controls">
              <#if course.name??> 
               		<input id="titleField" class="span5" type="text" name="name" value="${course.name}" />
              <#else>
               		<input id="titleField" class="span5" type="text" name="name" />
              </#if>     
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Category</label>
            <div class="controls">
              <select id="categoryField" class="span5" name="categoryId">
              <option></option>
                <#if allCategories??>
        			<#list allCategories as category>
        				<#if course.categoryId == category.id>
                			<option value ="${category.id}" selected="selected">${category.name}</option>
                		<#else>
                			<option value ="${category.id}" >${category.name}</option>
                		</#if>	
               		</#list>
               	</#if>		
              </select>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Description</label>
            <div class="controls">
              <textarea id="descriptionField" class="span5" rows="3" name="description"
              ><#if course.description??>${course.description}</#if></textarea>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Links</label>
            <div class="controls">
              <textarea id="linksField" class="span5" rows="3" name="links"
              ><#if course.links??>${course.links}</#if></textarea>
            </div>
          </div>
          <div class="form-actions">
            <button id="createButton" class="btn btn-primary" type="submit">Create</button>
            <a class="btn" href="${rc.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
    </#if>
  </body>
</html>
