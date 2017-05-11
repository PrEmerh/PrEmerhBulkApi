package com.casosemergencias.batch;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.casosemergencias.util.Utils;
import com.casosemergencias.util.constants.ConstantesBulkApi;

@Resource
public class AccountUpdaterBatch {
	final static Logger LOGGER = Logger.getLogger(AccountUpdaterBatch.class);
	
	@Autowired
	SalesforceRestApiInvokerBatch restApiInvokerBatch;
	
	public void updateAccountInfo() {
		LOGGER.trace("Comienzo del proceso de actualizacion de Account de SalesForce a la base de datos de Heroku");
		Date yesterday = Utils.substractDaysToDate(new Date(), 1);
		Date processStartDate = Utils.setHourInDate(yesterday, 0, 0, 0, 0);
		Date processEndDate = Utils.setHourInDate(yesterday, 23, 59, 59, 999);
		Date processStartDate = cal.getTime();
		//FIXME: Comprobar zona horaria.
		restApiInvokerBatch.setProcessStartDate(processStartDate);
		restApiInvokerBatch.setProcessEndDate(processEndDate);
		restApiInvokerBatch.setObjectName(ConstantesBulkApi.ENTITY_ACCOUNT);
		restApiInvokerBatch.updateObjectsWithRestApiInfo();
		LOGGER.trace("Proceso de actualizacion  de Account de SalesForce a la base de datos de Heroku completado");
	}
}
