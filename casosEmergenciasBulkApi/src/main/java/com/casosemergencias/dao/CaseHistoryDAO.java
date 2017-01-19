package com.casosemergencias.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.casosemergencias.dao.vo.CaseHistoryVO;
import com.casosemergencias.dao.vo.HistoricBatchVO;
import com.casosemergencias.util.constants.ConstantesBatch;

@Repository
public class CaseHistoryDAO {
	
	final static Logger logger = Logger.getLogger(CaseHistoryDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	HistoricBatchDAO historicBatchDAO;

	/**
	 * Devuelve una lista con todas los CaseHistory de un caseId. No se recuperan los registros que en newvalue tiene longitud 18 y no tienen espacios ni puntos (suponemos que 
	 * es un sfid y no lo tenemos que tener en cuenta)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CaseHistoryVO> readCaseHistoryByCaseId(String caseId, Integer limiteRegistros) {
		logger.debug("--- Inicio -- readCaseHistoryByCaseId ---");
		Session session = sessionFactory.openSession();
				
		try {
			Query query = session.createQuery("from CaseHistoryVO as caseHistory "
					+ " left join fetch caseHistory.fieldPickList fieldPick "
					+ " left join fetch caseHistory.newValuePickList newValuePick  "
					+ " left join fetch caseHistory.oldValuePickList oldValuePick  "
					+ " left join fetch caseHistory.fieldLabel fielLabelDesc  "					
					+ " WHERE caseHistory.caseid = :caseId and "
					+ " caseHistory.sfid not in(SELECT sfid FROM CaseHistoryVO as subhistorial "
					+ " WHERE subhistorial.caseid = :caseId AND (LENGTH(subhistorial.newvalue) = 18 and LOCATE(subhistorial.newvalue, ' ') = 0 and  LOCATE(subhistorial.newvalue, '.') = 0))"
					+ " ORDER BY caseHistory.caseid, caseHistory.createddate DESC, fieldPick.valor DESC, caseHistory.field DESC");
			query.setString("caseId", caseId);
			
			
			if(limiteRegistros != null){
				query = query.setMaxResults(limiteRegistros);
			}
			
			List<CaseHistoryVO> caseHistoryList = query.list(); 

			logger.debug("--- Fin -- readCaseHistoryByCaseId ---");
			
			return caseHistoryList;
			
	    } catch (HibernateException e) {
	    	logger.error("--- readCaseHistoryByCaseId ", e); 
	    	logger.error("--- Fin -- readCaseHistoryByCaseId ---");
	    } finally {
	    	session.close(); 
	    }
	      return null;
	}
	
	
	/**
	 * Devuelve una lista con todas los CaseHistory de BBDD
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CaseHistoryVO> readAllCaseHistory() {
		logger.debug("--- Inicio -- readAllCaseHistory ---");
		Session session = sessionFactory.openSession();
				
		try {
			Query query = session.createQuery("from CaseHistoryVO");
			List<CaseHistoryVO> caseHistoryList = query.list(); 

			logger.debug("--- Fin -- readAllCaseHistory ---");
			
			return caseHistoryList;
			
	    } catch (HibernateException e) {
	    	logger.error("--- readAllCaseHistory ", e); 
	    	logger.error("--- Fin -- readAllCaseHistory ---");
	    } finally {
	    	session.close(); 
	    }
	      return null;
	}
	
	/**
	 * Devuelve el CaseHistory que tiene como id el pasado por parametro al metodo
	 * 
	 * @param id - id de un CaseHistory
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CaseHistoryVO readCaseHistoryById(Integer id) {
		
		logger.debug("--- Inicio -- readCaseHistoryById ---");
		
		Session session = sessionFactory.openSession();
			
		try {
			Query query = session.createQuery("from CaseHistoryVO as caseHistory WHERE caseHistory.id = :id");
			query.setInteger("id", id);
			
			List<CaseHistoryVO> caseHistoryList = query.list(); 

			if(caseHistoryList != null && !caseHistoryList.isEmpty()){
				return caseHistoryList.get(0);
			}			
			
			logger.debug("--- Fin -- readCaseHistoryById ---");
			
	    } catch (HibernateException e) {
	    	logger.error("--- readCaseHistoryById ", e); 
	    	logger.error("--- Fin -- readCaseHistoryById ---");
	    } finally {
	    	session.close(); 
	    }
	    return null;
	}
	
	/**
	 * Devuelve una CaseHistory que tiene como id. el pasado por parámetro al método.
	 * 
	 * @param sfid Id. del CaseHistory.
	 * @return CaseHistoryVO
	 */
	@SuppressWarnings("unchecked")
	public CaseHistoryVO readCaseHistoryBySfid(String sfid) {
		logger.debug("--- Inicio -- readCaseHistoryBySfid ---");
		Session session = sessionFactory.openSession();
		
		try {
			Query query = session.createQuery("from CaseHistoryVO caseHistory WHERE caseHistory.sfid = :sfid ");
			query.setString("sfid", sfid);
			List<CaseHistoryVO> caseHistoryList = query.list(); 
			
			if (caseHistoryList != null && !caseHistoryList.isEmpty()) {
				return caseHistoryList.get(0);
			}
			
			logger.debug("--- Fin -- readCaseHistoryBySfid ---");
	    } catch (HibernateException e) {
	    	logger.error("--- readCaseHistoryBySfid ", e); 
	    	logger.error("--- Fin -- readCaseHistoryBySfid ---");
	    } finally {
	    	session.close(); 
	    }
	    return null;
	}
	
	/**
	 * Devuelve el número de entradas en el historia de un caso. Tenemos que tener cuidado con los casos en los que se modifica el user, en estos casoso
	 * se guardan dos registors, una con el sfid y otra con el nombre, no contamos los campos que tienen longitud 18 y no tienen ni espacios ni puntos
	 * 
	 * @return
	 */
	public Integer countHistoriaDeCaso(String sfidCaso) {
		logger.debug("--- Inicio -- countHistoriaDeCaso ---");
		
		Session session = sessionFactory.openSession();
		
		try {
			/*
			 select field, casehistory.newvalue, createdbyid, createddate
			from salesforce.casehistory
			where caseid = '5005B000000zEo3QAE' 
					and sfid not in(SELECT sfid
					FROM casehistory
					WHERE caseid = '5005B000000zEo3QAE' 
							AND (LENGTH(newvalue) = 18 and POSITION(' ' IN newvalue) = 0 and  POSITION('.' IN newvalue) = 0));
			*/

			StringBuilder sqlQuery = new StringBuilder("SELECT count(sfid) FROM CaseHistoryVO as historial ");
			sqlQuery.append(" WHERE historial.caseid = :sfidCaso "
					+ " and historial.sfid not in(SELECT sfid FROM CaseHistoryVO as subhistorial "
					+ " WHERE caseid = :sfidCaso AND (LENGTH(subhistorial.newvalue) = 18 and LOCATE(subhistorial.newvalue, ' ') = 0 and  LOCATE(subhistorial.newvalue, '.') = 0))");
					
			Query query = session.createQuery(sqlQuery.toString());
			query.setString("sfidCaso", sfidCaso);
			
			Long count = (Long) query.uniqueResult();
			
//			StringBuilder sqlQuery = new StringBuilder("SELECT count(distinct(historial.field || historial.createdbyid || historial.createddate)) FROM salesforce.casehistory as historial ");
//			sqlQuery.append(" WHERE historial.caseid = '" + sfidCaso + "'");
//			
//			Query query = session.createSQLQuery(sqlQuery.toString());
//						
//			BigInteger count = (BigInteger) query.uniqueResult();
			
			logger.debug("--- Fin -- countHistoriaDeCaso ---");
			
			return count.intValue();
			
	    } catch (HibernateException e) {
	    	logger.error("--- countHistoriaDeCaso ", e); 
	    	logger.error("--- Fin -- countHistoriaDeCaso ---");
	    } finally {
	    	session.close(); 
	    }
		return null;
	}
	
	/**
	 * Inserta un listado de HistorialCasos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int insertCaseHistoryListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- insert Listado HistorialCasos ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoInsertRecord = null;
		CaseHistoryVO historialCasoToInsert = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		for (Object object : objectList) {
			historicoInsertRecord = new HistoricBatchVO();
			historicoInsertRecord.setStartDate(new Date());
			historicoInsertRecord.setOperation(ConstantesBatch.INSERT_RECORD);
			historicoInsertRecord.setObject(ConstantesBatch.OBJECT_CASE_HISTORY);
			historicoInsertRecord.setProcessId(processId);
			historialCasoToInsert = new CaseHistoryVO();
			 
			try {
				historialCasoToInsert = (CaseHistoryVO) object;
				historicoInsertRecord.setSfidRecord(historialCasoToInsert.getSfid());
				session.save(historialCasoToInsert);
				tx.commit();

				logger.debug("--- Fin -- insertHistorialCaso ---" + historialCasoToInsert.getSfid());
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				tx.rollback();
				logger.error("--- Error en insertHistorialCaso: ---" + historialCasoToInsert.getSfid(), e);
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_INSERT_RECORD;
			}

			historicoInsertRecord.setSuccess(processOk);
			historicoInsertRecord.setEndDate(new Date());
			historicoInsertRecord.setErrorCause(processErrorCause);
			historicBatchDAO.insertHistoric(historicoInsertRecord);
		}
		logger.debug("--- Fin -- insert Listado HistorialCasos ---");
		session.close();
		return processedRecords;
	}

	/**
	 * Actualiza un listado de historialCasos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int updateCaseHistoryListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- update Listado HistorialCasos ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoUpdateRecord = null;
		CaseHistoryVO historialCasoToUpdate = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
				
		for (Object object : objectList) {
			historicoUpdateRecord = new HistoricBatchVO();
			historicoUpdateRecord.setStartDate(new Date());
			historicoUpdateRecord.setOperation(ConstantesBatch.UPDATE_RECORD);
			historicoUpdateRecord.setObject(ConstantesBatch.OBJECT_CASE_HISTORY);
			historicoUpdateRecord.setProcessId(processId);
			historialCasoToUpdate = new CaseHistoryVO();
			
			try {
				historialCasoToUpdate = (CaseHistoryVO) object;
				historicoUpdateRecord.setSfidRecord(historialCasoToUpdate.getSfid());
				//1.1-Construimos la query							
				Query sqlUpdateQuery = session.createQuery("UPDATE CaseHistoryVO "
														+ "    SET createdbyid = :createdbyid"
														+ "		 , createddate = :createddate"
														+ "		 , newvalue = :newvalue"
														+ "		 , oldvalue = :oldvalue"
														+ "		 , field = :field"
														+ "		 , caseid = :caseid"
														+ "  WHERE sfid = :sfidFiltro");
				
				//1.2-Seteamos los campos
				sqlUpdateQuery.setParameter("createdbyid", historialCasoToUpdate.getCreatedbyid());
				sqlUpdateQuery.setTimestamp("createddate", historialCasoToUpdate.getCreateddate());
				sqlUpdateQuery.setParameter("newvalue", historialCasoToUpdate.getNewvalue());
				sqlUpdateQuery.setParameter("oldvalue", historialCasoToUpdate.getOldvalue());
				sqlUpdateQuery.setParameter("field", historialCasoToUpdate.getField());
				sqlUpdateQuery.setParameter("caseid", historialCasoToUpdate.getCaseid());
				sqlUpdateQuery.setParameter("sfidFiltro", historialCasoToUpdate.getSfid());					
				
				//1.5-Ejecutamos la actualizacion
				sqlUpdateQuery.executeUpdate();
				tx.commit();		
				logger.debug("--- Fin -- updateHistorialCaso ---" + historialCasoToUpdate.getSfid());
				
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en updateHistorialCaso: ---" + historialCasoToUpdate.getSfid(), e);
				tx.rollback();
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_UPDATE_RECORD;
			} 

			historicoUpdateRecord.setSuccess(processOk);
			historicoUpdateRecord.setEndDate(new Date());
			historicoUpdateRecord.setErrorCause(processErrorCause);
			historicBatchDAO.insertHistoric(historicoUpdateRecord);
		}
		logger.debug("--- Fin -- update Listado HistorialCasos ---");
		session.close();
		return processedRecords;
	}
		
	/**
	 * Borra un listado de historialCasos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int deleteCaseHistoryListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- delete Listado HistorialCasos ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoDeleteRecord = null;
		CaseHistoryVO historialCasoToDelete = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		for (Object object : objectList) {
			historicoDeleteRecord = new HistoricBatchVO();
			historicoDeleteRecord.setStartDate(new Date());
			historicoDeleteRecord.setOperation(ConstantesBatch.DELETE_RECORD);
			historicoDeleteRecord.setObject(ConstantesBatch.OBJECT_CASE_HISTORY);
			historicoDeleteRecord.setProcessId(processId);
			historialCasoToDelete = new CaseHistoryVO();
			
			try {
				historialCasoToDelete = (CaseHistoryVO) object;
				historicoDeleteRecord.setSfidRecord(historialCasoToDelete.getSfid());
				Query sqlDeleteQuery = session.createQuery("DELETE CaseHistoryVO  WHERE sfid = :sfidFiltro");
				//Seteamos el campo por el que filtramos el borrado			
				sqlDeleteQuery.setParameter("sfidFiltro", historialCasoToDelete.getSfid());				
				//Ejecutamos la actualizacion				
				sqlDeleteQuery.executeUpdate();
				tx.commit();
				
				logger.debug("--- Fin -- deleteHistorialCaso ---" + historialCasoToDelete.getSfid());
				
				processOk = true;
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en deleteHistorialCaso: ---" + historialCasoToDelete.getSfid(), e);
				tx.rollback();
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_DELETE_RECORD;
			} 

			historicoDeleteRecord.setSuccess(processOk);
			historicoDeleteRecord.setErrorCause(processErrorCause);
			historicoDeleteRecord.setEndDate(new Date());
			historicBatchDAO.insertHistoric(historicoDeleteRecord);
		}
		logger.debug("--- Fin -- delete Listado HistorialCasos ---");
		session.close();
		return processedRecords;
	}
}