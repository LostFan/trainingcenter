<#import "/spring.ftl" as spring/>
<@spring.bind "approve.*" />
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
          Approve Course
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
      <form class="form-horizontal" method="post">
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
          <div id="categoryField" class="controls text">${currentcourse.category.name}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Description</div>
          <div id="descriptionField" class="controls text">${currentcourse.description}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Links</div>
          <div id="linksField" class="controls text">${currentcourse.links}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Department Manager</div>
          <div id="dmField" class="controls text">Alex Ivanov</div>
        </div>
        <div class="control-group">
          <label class="control-label">Decision</label>
          <div class="controls">
            <#if user.role ="DM" && currentcourse.decisionDM == "None">
           <select class="span5" name="decision">
           <#else>
            <select class="span5" disabled="disabled">
            </#if>
             <#if currentcourse.decisionDM == "None">
	             <#if approve.decision??>
		              <option value="None">Select one</option>
		              <#if approve.decision == "Approve">
		              	<option value="Approve" selected="selected">Approve</option>
		              <#else>
		              	<option value="Approve">Approve</option>
		              </#if>	
		              <#if approve.decision == "Reject">
		              	<option value="Reject" selected="selected">Reject</option>
		              <#else>
		              	<option value="Reject">Reject</option> 
		               </#if>
	              <#else>
		              <option value="None">Select one</option>
		              <option value="Approve">Approve</option>
		              <option value="Reject">Reject</option> 
	              </#if>
            <#else>
              	  <option value="${currentcourse.decisionDM}">${currentcourse.decisionDM}</option>
            </#if>  
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">Reason</label>
          <div class="controls">
          <#if user.role =="DM" && currentcourse.decisionDM == "None">
          		<#if approve.reason??>
            		<textarea class="span5" rows="3" name="reason">${approve.reason}</textarea>
          		<#else>
          			<textarea class="span5" rows="3" name="reason"></textarea>
          		</#if>
           <#else>
           <#if currentcourse.reasonDM??>
            	<textarea class="span5" rows="3" disabled="disabled">${currentcourse.reasonDM}</textarea>
            <#else>
            	<textarea class="span5" rows="3" disabled="disabled"></textarea>
            </#if>
           </#if>
          </div>
        </div>
        <div class="control-group">
          <div class="control-label">Knowledge Manager</div>
          <div id="kmField" class="controls text">Xela Vonavi</div>
        </div>
        <div class="control-group">
          <label class="control-label">Decision</label>
          <div class="controls">
          <#if user.role ="KM" && currentcourse.decisionKM == "None">
           <select class="span5" name="decision">
           	  
           <#else>
            <select class="span5" disabled="disabled">
            
            
            </#if>
            <#if currentcourse.decisionKM == "None">
            	<#if approve.decision??>
		              <option value="None">Select one</option>
		              <#if approve.decision == "Approve">
		              	<option value="Approve" selected="selected">Approve</option>
		              <#else>
		              	<option value="Approve">Approve</option>
		              </#if>	
		              <#if approve.decision == "Reject">
		              	<option value="Reject" selected="selected">Reject</option>
		              <#else>
		              	<option value="Reject">Reject</option> 
		               </#if>
	              <#else>
		              <option value="None">Select one</option>
		              <option value="Approve">Approve</option>
		              <option value="Reject">Reject</option> 
	              </#if>
            <#else>
              	  <option value="${currentcourse.decisionKM}">${currentcourse.decisionKM}</option>
            </#if>
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">Reason</label>
          <div class="controls">
            <#if user.role =="KM" && currentcourse.decisionKM == "None">
            	<#if approve.reason??>
            		<textarea class="span5" rows="3" name="reason">${approve.reason}</textarea>
          		<#else>
          			<textarea class="span5" rows="3" name="reason"></textarea>
          		</#if>
           <#else>
           	<#if currentcourse.reasonKM??>
            	<textarea class="span5" rows="3" disabled="disabled">${currentcourse.reasonKM}</textarea>
            <#else>
            	<textarea class="span5" rows="3" disabled="disabled"></textarea>
            </#if>
           </#if>
          </div>
        </div>
        <div class="form-actions actions-without-form">
         <#if (user.role ="KM" && currentcourse.decisionKM == "None") 
         || (user.role ="DM" && currentcourse.decisionDM == "None")>
          <button class="btn btn-primary">Save</button>
          <#else>
          <button class="btn btn-primary"  disabled="disabled">Save</button>
          </#if>
          <a class="btn" href="${rc.contextPath}/courses">Back</a>
        </div>
      </form>
    </div>
  </body>
</html>
