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
           ${page}
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
      <div class="courses-top-control">
      <#if page == "Courses">
     	 <a class="btn" href="${rc.contextPath}/mycourses?category=${category}">My Courses</a>
      <#else>
     	 <a class="btn" href="${rc.contextPath}/courses?category=${category}">Courses</a>
      </#if>
      <#if user.role == "LECTURER">
       <a class="btn btn-primary" href="${rc.contextPath}/courses/create">Create</a>
       </#if>
      
       <div class="search-box">
          <form class="form-search" method="get">
            <select id="categoryField" class="span5" name="category">
              <option value="0">All courses</option>
                <#if allCategories??>
        			<#list allCategories as categoryInList>
                		<#if category==categoryInList.id>
                			 <option value ="${categoryInList.id}" selected> ${categoryInList.name} 
                		<#else>
                			<option value ="${categoryInList.id}"> ${categoryInList.name}</option>
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
	        	<#if (category==course.category.id || category==0) 
	        	 && (course.creater == user || course.state != "Draft")
	        	 && (course.creater == user || user.role == "KM" || user.role == "DM" || course.state != "Proposal")
	        	 && (course.creater == user || user.role == "KM" || user.role == "DM" || course.state != "Rejected")>
		        	<tr>
		        	<td>${course.id}</td>     	
		        	 <td>  <a href=${"courses/"+course.id}>${course.name}</a> 
		        	 <#if course.state=="Draft">
		        	 	<span class="label">${course.state}</span></td>
		        	 </#if>
		        	 <#if course.state=="Proposal">
		        	 	<span class="label label-warning">${course.state}</span></td>
		        	 </#if>
		        	 <#if course.state=="Rejected">
		        	 	<span class="label label-important">${course.state}</span></td>
		        	 </#if>
		        	 <#if course.state=="New">
		        	 	<span class="label label-success">${course.state}</span></td>
		        	 </#if>
		        	 <#if course.state=="Open">
		        	 	<span class="label label-success">${course.state}</span></td>
		        	 </#if>
		        	 <#if course.state=="Ready">
		        	 	<span class="label label-success">${course.state}</span></td>
		        	 </#if>
		        	 <#if course.state=="InProgress">
		        	 	<span class="label label-info">In Progress</span></td>
		        	 </#if>
		        	 <#if course.state=="Finished">
		        	 	<span class="label label-inverse">${course.state}</span></td>
		        	 </#if>
		        	  <td><#if course.category??>${course.category.name}</#if></td>
		        	   <td>  <a href="${rc.contextPath}/courses/${course.id}/participants">
		        	   ${course.coursesSubscribe?size}
		        	   <#if course.coursesAttend?size != 0> /${course.coursesAttend?size}</#if>
		        	   </a> </td>
		        	   <td>
		        	   <#if course.getMeanGrade()??>
		        	   ${course.getMeanGrade()}
		        	   </#if>
		        	   </td>
		        	   <td>
		        	   <#if (user.role == "KM" || user.role == "DM") && (course.state=="Rejected" || course.state=="Proposal")>
		        	  
		        	       <a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/approve/"}">Approve</a>
		        	  </#if>
		        	  <#if user.role == "LECTURER" >
		        	  <#if course.creater.id == user.id && (course.state=="Draft" || course.state=="Rejected")>
		        	   <div class="btn-group">
		                <a class="btn btn-mini" href="${rc.contextPath}/courses/${course.id}/update">Update</a>
		                <a class="btn dropdown-toggle btn-mini" data-toggle="dropdown" href="#"><span class="caret"></span></a>
		                <ul class="dropdown-menu">
		                  <li>
		                    <a href="${rc.contextPath}/courses/${course.id}/review">Send to Review</a>
		                    <a href="${rc.contextPath}/courses/${course.id}/delete">Delete</a>
		                  </li>
		                </ul>
		              </div>
		             </#if>
			           	<#if course.creater.id == user.id && (course.state=="Ready")>
			           	 <div class="btn-group">
			           	<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/start/"}">Start</a>
			           	</div>
			           	</#if>
			           	<#if course.creater.id == user.id && (course.state=="InProgress")>
			           	 <div class="btn-group">
			           	<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/finish/"}">Finish</a>
			           	</div>
			           	</#if>
			           		<#if course.creater.id == user.id && (course.state=="Finished")>
			           			<#if course.coursesEvaluate?size != course.coursesAttend?size>
					           	 <div class="btn-group">
					           	<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/notify/"}">Notify</a>
			         		  	</div>
			           			</#if>	
			           		</#if>
		            </#if>
		             <#if user.role == "STUDENT">             
			        	 <#if !course.coursesEvaluate?keys?seq_contains(user)>
			        		<#if !course.coursesAttend?seq_contains(user)>
				        	  	<#if !course.coursesSubscribe?seq_contains(user)> 
				        	  		<#if course.state=="New" || course.state=="Open" || course.state="Ready">
				        				<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/subscribe/"}">Subscribe</a>
				        			</#if>
				        		<#else>
				        			<#if course.state=="Open" || course.state="Ready">
				        				<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/attend/"}">Attend</a> 
					        		</#if>
					        	</#if>
					        <#else>
					        	<#if course.state=="Finished">
					        		<a class="btn btn-mini" href="${rc.contextPath+"/courses/"+course.id+"/evaluate/"}">Evaluate</a>
			        	 		</#if>
			        	 	</#if>
			        	  </#if>
			            
		           
		            </#if>
		            </td>
		        	 </tr>
	         	</#if>
			</#list>
		</#if>		
        </tbody>
      </table>
    </div>
     <script src="${rc.contextPath}/css/jquery-1.8.1.min.js"></script>
    <script src="${rc.contextPath}/css/bootstrap-2.2.2.min.js"></script>
  </body>
</html>
