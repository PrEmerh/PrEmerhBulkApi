<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Emergencias App</title>		
		
		<link rel="icon" type="image/png" href="../resources/images/favicon.png">
		
		<link href="../resources/css/cabecera.css" rel="stylesheet" />
		<link href="../resources/css/body.css" rel="stylesheet" />
		<link href="../resources/css/jQueryDatatable.css" rel="stylesheet" />	
		

		<script src="../resources/js/jquery-1.12.3.js" lang=""></script>
		<script src="../resources/js/jQueryDatatables.js"></script>
		<script src="../resources/js/header.js"></script>
		<script src="../resources/js/utils.js"></script>
		<script src="../resources/js/historicBatchDataTable.js"></script>
	</head>
	<body onload="initHeader();limpiarCamposBuscadorHistoricBatchs()">
		<script type="text/javascript">var objetoSeleccionado='<s:message code="cabeceraPage_list_historicBatch"/>';</script>
		<jsp:include page="cabeceraPage.jsp"/>
		 
		<form name="formListadoHistoricBatchs" action="homeHistoricBatchsAction" method="POST">
			<br><br>
			<div class="formBusqueda">
				<table class="tablaBusqueda">
					<tr>
						<td class="tablaBusquedaTitulo" colspan="2">
							<s:message code="search_form_title_historicBatch"/>
						</td>
					</tr>
					<tr >
						<td class="tablaBusquedaEtiquetas" >
							<label for="filtroObjetoHistoricBatch"><s:message code="search_form_input_historicBatch_objeto"/></label>
						</td>
						<td class="tablaBusquedaTextos">
							<input type="text" id="filtroObjetoHistoricBatch" name="filtroObjetoHistoricBatch" class="column_filter" value=""  maxlength="50">
						</td>
						<td class="tablaBusquedaEtiquetas">
							<label for="filtroComienzoOperacionHistoricBatch"><s:message code="search_form_input_historicBatch_comienzo_operacion"/></label>
						</td>
						<td >						
							<select name="filtroComienzoOperacionHistoricBatchPickListFilter"  id="filtroComienzoOperacionHistoricBatchPickListFilter">
								<option value="" ></option>
							    <option value="equal">&#61;</option>
							    <option value="greaterOrEqual">&ge;</option>
							    <option value="lessOrEqual">&le;</option>
							    <option value="greater">&gt;</option>
							    <option value="less">&lt;</option>
							</select>
						</td>												
						<td >
							<input type="datetime-local" step="1"  id="filtroComienzoOperacionHistoricBatch" name="filtroComienzoOperacionHistoricBatch" class="dateTimeInputField" value="">
						</td>
					</tr>
					<tr>
						<td class="tablaBusquedaEtiquetas">
							<label for="filtroOperacionHistoricBatch"><s:message code="search_form_input_historicBatch_operacion"/></label>
						</td>
						<td class="tablaBusquedaTextos">
							<input type="text" id="filtroOperacionHistoricBatch" name="filtroOperacionHistoricBatch" class="column_filter" value=""  maxlength="50">
						</td>
						<td class="tablaBusquedaEtiquetas">
							<label for="filtroFinalOperacionHistoricBatch"><s:message code="search_form_input_historicBatch_final_operacion"/></label>
						</td>
						<td>
							<select name="filtroFinalOperacionHistoricBatchPickListFilter" id="filtroFinalOperacionHistoricBatchPickListFilter">
								<option value="" ></option>
							    <option value="equal">&#61;</option>
							    <option value="greaterOrEqual">&ge;</option>
							    <option value="lessOrEqual">&le;</option>
							    <option value="greater">&gt;</option>
							    <option value="less">&lt;</option>
							</select>	
						</td>
						<td >
							<input type="datetime-local" step="1" id="filtroFinalOperacionHistoricBatch" name="filtroFinalOperacionHistoricBatch" class="dateTimeInputField" value="">					
						</td>
					</tr>
					<tr>
						<td class="tablaBusquedaEtiquetas">
							<label for="filtroSfidRegistroHistoricBatch"><s:message code="search_form_input_historicBatch_sfid_registro"/></label>
						</td>
						<td class="tablaBusquedaTextos">
							<input type="text" id="filtroSfidRegistroHistoricBatch" name="filtroSfidRegistroHistoricBatch" class="column_filter" value="">
						</td>
						<td class="tablaBusquedaEtiquetas">
							<label for="filtroSuccessHistoricBatch"><s:message code="search_form_input_historicBatch_success"/></label>
						</td>
						<td >
							<select name="filtroSuccessHistoricBatch"  id="filtroSuccessHistoricBatch">
							    <option></option>
							    <option value="true">TRUE</option>
							    <option value="false">FALSE</option>
							</select>	
						</td>
					</tr>		
					<tr>
					<tr>
						<td class="tablaBusquedaBotonera" colspan="2">
							<input type="button" id="search" name="searchButton" class="column_filter" value="Buscar">	
						</td>
						
					</tr>
				</table>
			</div>			
			<div>
				<table id="tablaHistoricBatchs" class="display" style="width: 100%;"> 
					<thead>
			            <tr>
			                <th width="20%"><s:message code="homeHistoricBatchs_table_head_objeto"/></th>
			                <th width="16%"><s:message code="homeHistoricBatchs_table_head_operacion"/></th>
			                <th width="16%"><s:message code="homeHistoricBatchs_table_head_comienzo_operacion"/></th>
			                <th width="16%"><s:message code="homeHistoricBatchs_table_head_sfidRegistro"/></th>
			                <th width="16%"><s:message code="homeHistoricBatchs_table_head_success"/></th>
			                <th width="16%"><s:message code="homeHistoricBatchs_table_head_error_cause"/></th>
			                <th width="16%"><s:message code="homeHistoricBatchs_table_head_final_operacion"/></th>			                
			            </tr>
		        	</thead>
				</table>
			</div>
			<div>			
				<iframe id="txtArea1" style="display:none"></iframe>
				<button type="button" id="btnExport" > Export to Excel </button>
			</div>	
		</form>
	</body>
</html>