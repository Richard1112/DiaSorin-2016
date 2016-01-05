<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tagLib.tld" prefix="wa" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-multiselect.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-datepicker.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>

<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<link href="<c:url value='/css/bootstrapself.css' />" type="text/css" rel="stylesheet" />

<script>

$(document).ready(function() {
	
    $('.multiselect').multiselect({
    	maxHeight: 200,
    });
    
    $("#costCenter").multiselect({
    	maxHeight: 200,
    	onChange: function(option, checked, select) {
    		var costCenter = $("#costCenter").val();
    		if (costCenter == null || costCenter == "") {
    			$("#employee").text("");
    			$("#employee").multiselect("rebuild");
    			$("#employee").multiselect({
    		    	maxHeight: 200,
    		    });
    		} else {
    			$.ajax({
    				type : "GET",
    				contentType:'application/json',
    				url : '${pageContext.request.contextPath}/SE_RC_RE_LS/reloadEmployee?costCenter='+costCenter,
    				dataType : 'json',
    				success : function(data) {
    					if(!data.isException) {
    						// 重新获取员工信息
    						$("#employee").text("");
    						if (data.employeeList != null && data.employeeList.length > 0){
    							var list = data.employeeList;
								for (var k = 0; k < list.length; k++) {
									$("#employee").append("<option value=\""+list[k].employeeNo+"\">"+list[k].employeeNameEn+"</option>");
								}
    						}
    						$("#employee").multiselect("rebuild");
    		    			$("#employee").multiselect({
    		    		    	maxHeight: 200,
    		    		    });
						
    					} else {
    						
    					}
    				},
    				error : function(data) {
    				}
    			});
    		}

        }
    });
    $("#employee").multiselect({
    	maxHeight: 200,
    });
    
    $('.inputDate').datepicker({
    	format: "yyyy/mm/dd",
        clearBtn: true,
        orientation: "top right",
        autoclose: true,
        todayHighlight: true
    }); 

});

function searchDetail(){
	// 获取检索内容
	var occurDateForm = $("#occurDateFrom").val();
	var occurDateTo = $("#occurDateTo").val();
	var finance = $("#finance").val();
	var financeDateFrom = $("#financeDateFrom").val();
	var financeDateTo = $("#financeDateTo").val();
	var travelFlg = "";
	var otherFlg = "";
	
	var employee = $("#employee").val();
	var employeeStr = "";
	if (employee != null) {
		for (var i = 0 ; i < employee.length; i++) {
			if (i == 0) {
				employeeStr += employee[i];
			} else {
				employeeStr += "," + employee[i];
			}
		}
	}

	var expenseType = $("#expenseType").val();
	var expenseTypeStr = "";
	if (expenseType != null) {
		for (var i = 0 ; i < expenseType.length; i++) {
			if (i == 0) {
				expenseTypeStr += expenseType[i];
			} else {
				expenseTypeStr += "," + expenseType[i];
			}
		}
	}
	
	var costCenter = $("#costCenter").val();
	var costCenterStr = "";
	if (costCenter != null) {
		for (var i = 0 ; i < costCenter.length; i++) {
			if (i == 0) {
				costCenterStr += costCenter[i];
			} else {
				costCenterStr += "," + costCenter[i];
			}
		}
	}
	
	// 以目的地集计是否按下
	var travelCheck = document.getElementById("travelFlg").checked;
	if (travelCheck) travelFlg = "1";
	// OTHER 是否被选中
	var otherCheck = document.getElementById("otherFlg").checked;
	if (otherCheck) otherFlg = "1";
	
	var jsonMap = {
			occurDateForm : occurDateForm,
			occurDateTo : occurDateTo,
			finance : finance,
			financeDateFrom : financeDateFrom,
			financeDateTo : financeDateTo,
			employee : employeeStr,
			travelFlg : travelFlg,
			expenseType : expenseTypeStr,
			costCenter : costCenterStr,
			otherFlg : otherFlg
	};
	
	// 发送AJAX
	$.ajax({
		type : "POST",
		contentType:'application/json',
		url : '${pageContext.request.contextPath}/SE_RC_RE_LS/search',
		dataType : "json",
		data : JSON.stringify(jsonMap), 
		success : function(data) {
			if(!data.isException) {
				var authority = data.authority;
				// 首先将PURPOSE内容显示在画面上
				var headList = data.seRcReLsBean.headList;
				$("#listOne").css("display","");
				$("#exportBtn").css("display","none");
				$("#listTwo").css("display","none");
				$("#tbodyOne tr").eq(0).nextAll().remove();
				if (headList.length > 0) {
					$("#exportBtn").css("display","");
					for (var i = 0; i < headList.length; i++) {
						$("#tbodyOne").append("<tr><td style='text-align:left;padding-left:5px'>"
								+headList[i].employee+"</td><td style='text-align:left;padding-left:5px'>"
								+headList[i].purpose+"</td><td style='text-align:right;padding-right:5px'>"
								+fmoney(headList[i].amount,2)+"</td></tr>");
					}
				}
				// 显示下面明细项目的头部数据
				var titleList = data.titleNameList;
				$("#tbodyTwo tr").eq(0).nextAll().remove();
				$("#tbodyTitle").html("");
				if (titleList.length > 0) {
					$("#exportBtn").css("display","");
					$("#listTwo").css("display","");
					var tableWidth = 0;
					$("#tbodyTitle").append("<td width='200px'>Employee</td>");
					tableWidth += 200;
					if (travelCheck) {
						$("#tbodyTitle").append("<td width='150px'>Travel Location</td>");
						tableWidth += 150;
					}
					
					for (var i = 0; i < titleList.length; i++) {
						$("#tbodyTitle").append("<td width='150px'>"+titleList[i]+"</td>");
						tableWidth += 150;
					}
					$("#tbodyTitle").append("<td width='150px'>Total Amount</td>");
					tableWidth += 150;
					$("#tbodyTitle").append("<td width='150px'>&nbsp;</td>");
					tableWidth += 150;
					
					$("#listTable2").width(tableWidth);
				}
				
				var bodyList = data.seRcReLsBean.bodyList;
				if (bodyList.length > 0) {
					for (var i = 0; i < bodyList.length; i++) {
						var addHtml = "";
						var details = "";
						if (travelCheck) {
							addHtml = "<tr><td style='text-align:left;padding-left:5px'>"+bodyList[i].employee+"</td><td>"+bodyList[i].travelLocation+"</td>"
						} else {
							addHtml = "<tr><td style='text-align:left;padding-left:5px'>"+bodyList[i].employee+"</td>"
						}
						
						var amountList = bodyList[i].amountList;
						for (var k = 0 ; k< amountList.length; k++) {
							addHtml += "<td style='text-align:right;padding-right:5px'>"+fmoney(amountList[k],2)+"</td>";
						}
						if(authority.indexOf("SE_RC_RE_LS_Details") > 0) {
							details = "<a href='#' onclick=\"toDetail('"+bodyList[i].employeeNo+"','"+bodyList[i].travelLocation+"'); return false\">Details</a>";
						}
						addHtml += "<td>" + details + "</td>";
						addHtml += "</tr>";
						$("#tbodyTwo").append(addHtml);
							
					}
				}
			} else {
				// 检测不通过，申请操作上限
			}
		},
		error : function(data) {
		}
	});
	
}

function toDetail(employeeNo, travelLocation) {
	
	if (travelLocation == null || travelLocation == "null") {
		travelLocation = "";
	}
	
	// 以JSON的方式传递
	var param = {
			employeeNo : employeeNo,
			travelLocation : travelLocation
	};
	
	$.ajax({
		type : "POST",
		contentType:'application/json',
		url : '${pageContext.request.contextPath}/SE_RC_RE_LS/showDetail',
		dataType : 'json',
		data : JSON.stringify(param), 
		success : function(data) {
			if(!data.isException) {
				// 将明细内容显示在画面上，然后弹出画面
				if (data.detailList.length > 0) {
					var detail = data.detailList;
					$("#detailTable tr").eq(0).nextAll().remove();
					var totalAmount = 0;
					for (var i = 0;i< detail.length;i++ ) {
						var trHtml = "<tr>";
						if (detail[i].timeMethod == "1") {
							trHtml += "<td style='text-align: left;'>"+detail[i].dayTo+"</td>";
						} else if (detail[i].timeMethod == "2"){
							trHtml += "<td style='text-align: left;'>"+detail[i].dayFrom+" ~ "+detail[i].dayTo+"</td>";
						} else if (detail[i].timeMethod == "3") {
							trHtml += "<td style='text-align: left;'>"+detail[i].dayTo.substring(0, 7)+"</td>";
						}
						trHtml += "<td style='text-align: center;'>"+detail[i].location+"</td>";
						trHtml += "<td style='text-align: center;'>"+detail[i].expenseType+"</td>";
						trHtml += "<td style='text-align: right;'>"+detail[i].expenseAmount+"</td>";
						trHtml += "<td style='text-align: left;'>"+detail[i].comments+"</td>";
						trHtml += "<td style='text-align: center;'>"+detail[i].no+"</td>";
						trHtml += "</tr>";
						totalAmount = totalAmount + parseFloat(detail[i].expenseAmount.replace(",",""));
						$("#detailTable").append(trHtml);
					}
					$("#totalAmount").text(fmoney(totalAmount,2))
					$('#myModal').modal('show')
				}
				
			} else {
				// 检测不通过，申请操作上限
			}
		},
		error : function(data) {
		}
	});

}


//导出数据
function exportReport(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_RC_RE_LS/Export";
	targetform.method = "POST";
	targetform.submit();
}
</script>
</head>

<body>
<form:form id="seRcReLsBean" modelAttribute="seRcReLsBean" commandName="seRcReLsBean">
	<div class="position_none_boots"><fmt:message key="se_rc_re_ls_position" /></div>

    <input type="hidden" id="hiddencostCenter" value="${seRcReLsBean.costCenter }"/>
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_rc_re_ls_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font" width="148px"><fmt:message key="se_rc_re_ls_occurdate" /></td>
                    	<td class="td_box" width="240px">
                            <form:input id="occurDateFrom" path="occurDateFrom" cssClass="inputDate input_date_box_little"/>
                            ~
                            <form:input id="occurDateTo" path="occurDateTo" cssClass="inputDate input_date_box_little"/>
                        </td>
                        <td class="td_font" width="148px"><fmt:message key="se_rc_re_ls_finance" /></td>
                        <td class="td_box" width="240px">
                        	<form:select path="finance" cssClass="select_box">
                        		<form:option value=""></form:option>
                                <c:forEach var="bean" items="${ statusSelect }">
                    				<form:option value="${ bean.key }">${ bean.value }</form:option>
                    			</c:forEach>
                          	</form:select>
                      </td>
                      <td class="td_box" colspan="2" width="240px">
                            <form:input id="financeDateFrom" path="financeDateFrom" cssClass="inputDate input_date_box_little"/>
                            ~
                            <form:input id="financeDateTo" path="financeDateTo" cssClass="inputDate input_date_box_little"/>
                      </td>
                    </tr>
                    
                    <tr>
                    	<td class="td_font"><fmt:message key="se_rc_re_ls_costcenter" /></td>
                    	<td class="td_box" colspan="3">
                          	<form:select path="costCenter" cssClass="select_box" multiple="multiple">
                    			<c:forEach var="bean" items="${ costCenterSelect }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                   			</form:select>
                        </td>
                        <td class="td_font_left"><form:checkbox path="travelFlg" value="1" id="travelFlg"/>&nbsp;&nbsp;Travel Location</td>
                        <td >
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_rc_re_ls_employee" /></td>
                    	<td class="td_box" colspan="3">
                          	<form:select path="employee" cssClass="select_box" multiple="multiple">
                    			<c:forEach var="bean" items="${ employeeSelect }">
                    				<form:option value="${ bean.employeeNo }">${ bean.employeeNameEn }</form:option>
                    			</c:forEach>
                   			</form:select>
                        </td>
                        <td class="td_font_left"><form:checkbox path="other" value="1" id="otherFlg"/>&nbsp;&nbsp;Other</td>
                        <td >
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_rc_re_ls_expensetype" /></td>
                        <td class="td_box" colspan="5">
                          	<form:select path="expenseType" cssClass="multiselect select_box" multiple="multiple">
                    			<c:forEach var="bean" items="${ expensesSelect }">
                    				<form:option value="${ bean.expenseCode }">${ bean.expenseName }</form:option>
                    			</c:forEach>
                   			</form:select>
                        </td>
                    </tr>
                    	
                    <tr>
                        <td colspan="7">
                        	<div class="add_details_center_btn">
                        		<wa:showAtag id="SE_RC_RE_LS_Search" onclick="searchDetail()" innerImg="/images/search_btn.png"></wa:showAtag>
                        	</div>
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box_export" id="listOne" style="display:none;max-height:188px;overflow-y:auto;width:767px" >
			<table border="0" cellpadding="0" cellspacing="0" style="width:750px">
				<tbody id="tbodyOne">
                
                	<tr class="td_title">
                    	<td width="200px"><fmt:message key="se_rc_re_ls_detailEmp" /></td>
                        <td width="400px"><fmt:message key="se_rc_re_ls_detailpur" /></td>
                        <td width="150px"><fmt:message key="se_rc_re_ls_detailamount" /></td>
                    </tr>
                </tbody>
			</table>
		</div>
		
		<div class="list_box_export" id="listTwo" style="display:none;max-height:500px;overflow:auto;width:1100px">
            <table border="0" cellpadding="0" cellspacing="0" id="listTable2">
				<tbody id="tbodyTwo">
                
                	<tr class="td_title" id="tbodyTitle">
                    	
                    </tr>
                   
                </tbody>
			</table>
      	</div>
      	
      	<div class="list_box1" id="exportBtn" style="display:none">
      		<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                    <tr>
                   	  <td>
                   	  <div class="add_details_left_btn">
                   	  	<wa:showAtag id="SE_RC_RE_LS_Export" onclick="exportReport(); return false" innerImg="/images/export.png"></wa:showAtag>
                   	  </div>  
                   	  </td>
                   	  
                    </tr>
                </tbody>
            </table>
      	
      	</div>
      
      	
      	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
      		<div class="modal-dialog" id="modelDialog">
      		<div class="modal-content">
	      		<div class='modal-header'>
					<a class='close' data-dismiss='modal'><fmt:message key="common_x" /></a>
				</div>
	      		<div class="list_table3 modal-body">
		      		<table style="width:910px" id="detailTable">
		        	<tbody>
		            	<tr class="tr_title">
		                    <td width="160px"><fmt:message key="se_rc_re_ls_popup_day" /></td>
		                    <td width="100px"><fmt:message key="se_rc_re_ls_popup_location" /></td>
		                    <td width="150px"><fmt:message key="se_rc_re_ls_popup_type" /></td>
		                    <td width="100px"><fmt:message key="se_rc_re_ls_popup_amount" /></td>
		                    <td width="280px"><fmt:message key="se_rc_re_ls_popup_comments" /></td>
		                    <td width="120px"><fmt:message key="se_rc_re_ls_popup_appno" /></td>
		                </tr>	
		            </tbody>
		        	</table>
	      		</div>
	      		<div class="modal-body">
		      		<table style="width:910px" id="totalView">
			        	<tbody>
			            	<tr>
			                    <td width="160px"></td>
			                    <td width="100px"></td>
			                    <td width="150px" style='text-align:right'><fmt:message key="se_rc_re_ls_popup_total" /></td>
			                    <td width="100px" style='text-align:right' id="totalAmount"></td>
			                    <td width="280px"></td>
			                    <td width="120px"></td>
			                </tr>	
			            </tbody>
		        	</table>
	      		</div>
		     </div>
	      	</div>
	      	</div>
    	</div>
</form:form>
</body>
</html>
