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
          My Courses
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
      <div class="courses-top-control">
        <a class="btn" href="${rc.contextPath}/courses">Courses</a>
       <div class="search-box">
          <form class="form-search" method="post" action="${rc.contextPath}/mycourses">
            <select id="categoryField" class="span5" name="categoryId">
              <option value="0">All courses</option>
                <#if allCategories??>
        			<#list allCategories as category>
                		<#if categoryId==category.id>
                			 <option value ="${category.id}" selected> ${category.name} 
                		<#else>
                			<option value ="${category.id}"> ${category.name}</option>
                		 </#if>
               		</#list>
               	</#if>		
              </select>
            <button class="btn" type="submit">Apply</button>
          </form>
        </div>
      </div>
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th class="span1">Id</th>
            <th>Course</th>
            <th>Category</th>
            <th class="span1">S/A</th>
            <th class="span1">Grade</th>
            <th class="span2">Action</th>
          </tr>
        </thead>
        <tbody>
        <#if allcourses??>
        	<#list allcourses as course>
        	<#if categoryId==course.category.id || categoryId==0>
        	<tr>
        	<td>${course.id}</td>
        	 <td>  <a href=${"courses/"+course.id}>${course.name}</a></td>
        	  <td><#if course.category??>${course.category.name}</#if></td>
        	   <td>  <a href="${rc.contextPath}/courses/${course.id}/participants">
        	   ${course.coursesSubscribe?size}/${course.coursesAttend?size}</td>
        	   </a>
        	   <td>
        	   <#if course.getMeanGrade()??>
        	   ${course.getMeanGrade()}
        	   </#if>
        	   </td>
        	  <#if user.role == "LECTURER">
        	 <td>
        	  <#if course.creater.id == user.id>
        	  <a class="btn btn-mini" href="${rc.contextPath}/courses/${course.id}/update">Update</a>
             </#if>
            </td>
           
            </#if>
             <#if user.role == "STUDENT">             
	        	 <td>
	        	 <#if !course.coursesEvaluate?keys?seq_contains(user)>
	        		<#if !course.coursesAttend?seq_contains(user)>
		        	  	<#if !course.coursesSubscribe?seq_contains(user)>
		        			<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/subscribe/"}">Subscribe</a>
		        		<#else>
		        			<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/attend/"}">Attend</a> 
			        	</#if>
			        <#else>
			        	<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/evaluate/"}">Evaluate</a>
	        	  </#if>
	        	   </#if>
	            </td>
           
            </#if>
        	 </tr>
         	</#if>
			</#list>
		</#if>		
        </tbody>
      </table>
    </div>
  </body>
</html>
