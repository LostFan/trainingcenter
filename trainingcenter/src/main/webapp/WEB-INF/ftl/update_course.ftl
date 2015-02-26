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
          Update Course
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
      <form class="form-horizontal" method="post" action="${rc.contextPath}/courses/${currentcourse.id}/update">
        <fieldset>
          <div class="control-group">
            <label class="control-label">Name</label>
            <div class="controls">
             <#if course.name??> 
               		<input id="titleField" class="span5" type="text" name="name" value="${course.name}" />
              <#else>
               		<input id="titleField" class="span5" type="text" value="${currentcourse.name}" name="name"/>
              </#if>       
            </div>
          </div>
           <div class="control-group">
            <label class="control-label">Category</label>
            <div class="controls">
              <select id="categoryField" class="span5" name="categoryId">
                <#if allCategories??>
        			<#list allCategories as category>
        				<#if course.categoryId != 0>
        				 	<#if course.categoryId == category.id>
        					 <option value ="${category.id}" selected> ${category.name}
        					 <#else>
	                			<option value ="${category.id}"> ${category.name}</option>
	                		</#if>
	                	<#else>	
	                		<#if currentcourse.category==category>
	                			 <option value ="${category.id}" selected> ${category.name} 
	                		<#else>
	                			<option value ="${category.id}"> ${category.name}</option>
	                		 </#if>
	                	</#if>
               		</#list>
               	</#if>		
              </select>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Description</label>
            <div class="controls">
            <#if course.description??>
              <textarea id="descriptionField" class="span5" rows="3" name="description">${course.description}</textarea>
            <#else>
              <textarea id="descriptionField" class="span5" rows="3" name="description">${currentcourse.description}</textarea>
            </#if>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Links</label>
            <div class="controls">
             <#if course.links??>
             	<textarea id="linksField" class="span5" rows="3" name="links">${course.links}</textarea>
             <#else>
             	<textarea id="linksField" class="span5" rows="3" name="links">${currentcourse.links}</textarea>
              </#if>	
            </div>
          </div>
           <div class="control-group">
            <label id="minimalSubscribersField" class="control-label">Minimal Subscribers</label>
            <div class="controls">
             <#if course.name??>
             	<input class="span5" type="text" name = "minimalSubscribers" value="${course.minimalSubscribers}">
             <#else>
              	<input class="span5" type="text" name = "minimalSubscribers" value="${currentcourse.minimalSubscribers}">
              </#if>
            </div>
          </div>
          <div class="control-group">
            <label id="minimalAttendeeField" class="control-label">Minimal Attendee</label>
            <div class="controls">
             <#if course.name??>
             	<input class="span5" type="text" name = "minimalAttendee" value="${course.minimalAttendee}">
             <#else>
              	<input class="span5" type="text" name = "minimalAttendee" value="${currentcourse.minimalAttendee}">
              </#if>
            </div>
          </div>
          <div class="form-actions">
         	 <#if currentcourse.state=="Rejected" || currentcourse.state=="Draft">
            <button id="updateButton" class="btn btn-primary" type="submit" name="action" value="update">Update</button>
            <button id="reviewButton" class="btn btn-warning" type="submit" name="action" value="review">Review</button>
            <#else>
            <button id="reviewButton" class="btn btn-primary" disabled="disabled">Update</button>
            <button id="reviewButton" class="btn btn-warning" disabled="disabled">Review</button>
            </#if>	
            <a class="btn" href="${rc.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
