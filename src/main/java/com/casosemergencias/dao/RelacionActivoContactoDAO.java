package com.casosemergencias.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.casosemergencias.dao.vo.HistoricBatchVO;
import com.casosemergencias.dao.vo.RelacionActivoContactoVO;
import com.casosemergencias.util.constants.ConstantesBatch;

@Repository
public class RelacionActivoContactoDAO {
	
	final static Logger logger = Logger.getLogger(RelacionActivoContactoDAO.class);
		
	@Autowired
	private  SessionFactory sessionFactory;
	
	@Autowired
	HistoricBatchDAO historicBatchDAO;
	
	/**
	 * Inserta un listado de RelacionActivoContactos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int insertRelacionActivoContactoListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- insert Listado RelacionActivoContactos ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoInsertRecord = null;
		RelacionActivoContactoVO relacionActivoContactoToInsert = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
		
		for (Object object : objectList) {
			historicoInsertRecord = new HistoricBatchVO();
			historicoInsertRecord.setStartDate(new Date());
			historicoInsertRecord.setOperation(ConstantesBatch.INSERT_RECORD);
			historicoInsertRecord.setObject(ConstantesBatch.OBJECT_SERVICE_PRODUCT);
			historicoInsertRecord.setProcessId(processId);
			relacionActivoContactoToInsert = new RelacionActivoContactoVO();
			
			try {
				relacionActivoContactoToInsert = (RelacionActivoContactoVO) object;
				historicoInsertRecord.setSfidRecord(relacionActivoContactoToInsert.getSfid());
				session.save(relacionActivoContactoToInsert);
				
				logger.debug("--- Fin -- insertRelacionActivoContacto ---" + relacionActivoContactoToInsert.getSfid());
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en insertRelacionActivoContacto: ---" + relacionActivoContactoToInsert.getSfid(), e);
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_INSERT_RECORD;
			}
			
			historicoInsertRecord.setSuccess(processOk);
			historicoInsertRecord.setEndDate(new Date());
			historicoInsertRecord.setErrorCause(processErrorCause);
			historicBatchDAO.insertHistoric(historicoInsertRecord);
		}
		logger.debug("--- Fin -- insert Listado RelacionActivoContactos ---");
		session.close();
		return processedRecords;
	}

	/**
	 * Actualiza un listado de relacionActivoContactos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int updateRelacionActivoContactoListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- update Listado RelacionActivoContactos ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoUpdateRecord = null;
		RelacionActivoContactoVO relacionActivoContactoToUpdate = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
				
		for (Object object : objectList) {
			historicoUpdateRecord = new HistoricBatchVO();
			historicoUpdateRecord.setStartDate(new Date());
			historicoUpdateRecord.setOperation(ConstantesBatch.UPDATE_RECORD);
			historicoUpdateRecord.setObject(ConstantesBatch.OBJECT_SERVICE_PRODUCT);
			historicoUpdateRecord.setProcessId(processId);
			relacionActivoContactoToUpdate = new RelacionActivoContactoVO();
			
			try {
				relacionActivoContactoToUpdate = (RelacionActivoContactoVO) object;
				historicoUpdateRecord.setSfidRecord(relacionActivoContactoToUpdate.getSfid());
				//1.2-Construimos la query							
				Query sqlUpdateQuery = session.createQuery("UPDATE RelacionActivoContactoVO "
														+ "    SET name = :name"
														+ "		 , createddate = :createddate"
														+ "		 , contact__c = :contact__c"
														+ "		 , principal__c = :principal__c"
														+ "		 , asset__c = :asset__c"
														+ "		 , typeofrelationship__c = :typeofrelationship__c"
														+ "  WHERE sfid = :sfidFiltro");
				
				//1.2-Seteamos los campos			
				sqlUpdateQuery.setString("name", relacionActivoContactoToUpdate.getName());
				sqlUpdateQuery.setTimestamp("createddate", relacionActivoContactoToUpdate.getCreatedDate());
				sqlUpdateQuery.setString("contact__c", relacionActivoContactoToUpdate.getContactoId());
				sqlUpdateQuery.setBoolean("principal__c", relacionActivoContactoToUpdate.getPrincipal());
				sqlUpdateQuery.setString("asset__c", relacionActivoContactoToUpdate.getActivoId());
				sqlUpdateQuery.setString("typeofrelationship__c", relacionActivoContactoToUpdate.getTipoRelacionActivoClave());
				sqlUpdateQuery.setString("sfidFiltro", relacionActivoContactoToUpdate.getSfid());

				//1.3-Ejecutamos la actualizacion
				sqlUpdateQuery.executeUpdate();
				logger.debug("--- Fin -- updateRelacionActivoContacto ---" + relacionActivoContactoToUpdate.getSfid());
				
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en updateRelacionActivoContacto: ---" + relacionActivoContactoToUpdate.getSfid(), e);
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_UPDATE_RECORD;
			} 

			historicoUpdateRecord.setSuccess(processOk);
			historicoUpdateRecord.setEndDate(new Date());
			historicoUpdateRecord.setErrorCause(processErrorCause);
			historicBatchDAO.insertHistoric(historicoUpdateRecord);
		}
		logger.debug("--- Fin -- update Listado RelacionActivoContactos ---");
		session.close();
		return processedRecords;
	}
		
	/**
	 * Borra un listado de relacionActivoContactos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int deleteRelacionActivoContactoListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- delete Listado RelacionActivoContactos ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoDeleteRecord = null;
		RelacionActivoContactoVO relacionActivoContactoToDelete = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
		
		for (Object object : objectList) {
			historicoDeleteRecord = new HistoricBatchVO();
			historicoDeleteRecord.setStartDate(new Date());
			historicoDeleteRecord.setOperation(ConstantesBatch.DELETE_RECORD);
			historicoDeleteRecord.setObject(ConstantesBatch.OBJECT_SERVICE_PRODUCT);
			historicoDeleteRecord.setProcessId(processId);
			relacionActivoContactoToDelete = new RelacionActivoContactoVO();
			
			try {
				relacionActivoContactoToDelete = (RelacionActivoContactoVO) object;
				historicoDeleteRecord.setSfidRecord(relacionActivoContactoToDelete.getSfid());
				Query sqlDeleteQuery = session.createQuery("DELETE RelacionActivoContactoVO  WHERE sfid = :sfidFiltro");
				//Seteamos el campo por el que filtramos el borrado			
				sqlDeleteQuery.setString("sfidFiltro", relacionActivoContactoToDelete.getSfid());				
				//Ejecutamos la actualizacion				
				sqlDeleteQuery.executeUpdate();
				
				logger.debug("--- Fin -- deleteRelacionActivoContacto ---" + relacionActivoContactoToDelete.getSfid());
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en deleteRelacionActivoContacto: ---" + relacionActivoContactoToDelete.getSfid(), e);
				processErrorCause = ConstantesBatch.ERROR_DELETE_RECORD;
			} 

			historicoDeleteRecord.setSuccess(processOk);
			historicoDeleteRecord.setErrorCause(processErrorCause);
			historicoDeleteRecord.setEndDate(new Date());
			historicBatchDAO.insertHistoric(historicoDeleteRecord);	
		}
		logger.debug("--- Fin -- delete Listado RelacionActivoContactos ---");
		session.close();
		return processedRecords;
	}	
}