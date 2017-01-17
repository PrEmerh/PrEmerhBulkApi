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

import com.casosemergencias.dao.vo.HistoricBatchVO;
import com.casosemergencias.dao.vo.UserVO;
import com.casosemergencias.util.constants.ConstantesBatch;

@Repository
public class UserDAO {

final static Logger logger = Logger.getLogger(UserDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	HistoricBatchDAO historicBatchDAO;

	/**
	 * Devuelve una lista con todas los User de BBDD
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserVO> readAllUser() {
		logger.debug("--- Inicio -- readAllUser ---");
		Session session = sessionFactory.openSession();
				
		try {
			Query query = session.createQuery("from UserVO");
			List<UserVO> userList = query.list(); 

			logger.debug("--- Fin -- readAllUser ---");
			
			return userList;
			
	    } catch (HibernateException e) {
	    	logger.error("--- readAllUser ", e); 
	    	logger.error("--- Fin -- readAllUser ---");
	    } finally {
	    	session.close(); 
	    }
	      return null;
	}
	
	/**
	 * Devuelve el User que tiene como id el pasado por parametro al metodo
	 * 
	 * @param id - id de un User
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserVO readUserById(Integer id) {
		
		logger.debug("--- Inicio -- readUserById ---");
		
		Session session = sessionFactory.openSession();
			
		try {
			Query query = session.createQuery("from UserVO as user WHERE user.id = :id");
			query.setInteger("id", id);
			
			List<UserVO> userList = query.list(); 

			if(userList != null && !userList.isEmpty()){
				return userList.get(0);
			}			
			
			logger.debug("--- Fin -- readUserById ---");
			
	    } catch (HibernateException e) {
	    	logger.error("--- readUserById ", e); 
	    	logger.error("--- Fin -- readUserById ---");
	    } finally {
	    	session.close(); 
	    }
	    return null;
	}
	
	/**
	 * Devuelve una user que tiene como id. el pasado por parámetro al método.
	 * 
	 * @param sfid Id. del user.
	 * @return UserVO Datos de la cuenta.
	 */
	@SuppressWarnings("unchecked")
	public UserVO readUserBySfid(String sfid) {
		logger.debug("--- Inicio -- readUserBySfid ---");
		Session session = sessionFactory.openSession();
		
		try {
			Query query = session.createQuery("from UserVO account WHERE account.sfid = :sfid ");
			query.setString("sfid", sfid);
			List<UserVO> userList = query.list(); 
			
			if (userList != null && !userList.isEmpty()) {
				return userList.get(0);
			}


			logger.debug("--- Fin -- readUserBySfid ---");
	    } catch (HibernateException e) {
	    	logger.error("--- readUserBySfid ", e); 
	    	logger.error("--- Fin -- readUserBySfid ---");
	    } finally {
	    	session.close(); 
	    }
	    return null;
	}
	
	
	
	/**
	 * Inserta un listado de Usuarios venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
		
	@Transactional
	public void insertUserListSf(List<Object> objectList) {
		logger.debug("--- Inicio -- insert Listado Usuarios ---");

		Integer cont = 0;
		
		HistoricBatchVO historicoProcessInsert = new HistoricBatchVO();
		historicoProcessInsert.setStartDate(new Date());
		historicoProcessInsert.setOperation(ConstantesBatch.INSERT_PROCESS);
		historicoProcessInsert.setTotalRecords(objectList.size());
		historicoProcessInsert.setObject(ConstantesBatch.OBJECT_USER);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();		
		for(Object object:objectList){
			
			HistoricBatchVO historicoInsertRecord = new HistoricBatchVO();
			historicoInsertRecord.setOperation(ConstantesBatch.INSERT_RECORD);
			historicoInsertRecord.setObject(ConstantesBatch.OBJECT_USER);
			
			UserVO usuarioToInsert = new UserVO();
			try{
				usuarioToInsert=(UserVO)object;
				
				historicoInsertRecord.setSfidRecord(usuarioToInsert.getSfid());
				
				session.save(usuarioToInsert);
				tx.commit();
				logger.debug("--- Fin -- insertUsuario ---" + usuarioToInsert.getSfid());
				
				historicoInsertRecord.setSuccess(true);
				historicBatchDAO.insertHistoric(historicoInsertRecord);
				cont++;
				
			} catch (HibernateException e) {
			tx.rollback();
			logger.error("--- Error en insertUsuario: ---" + usuarioToInsert.getSfid(), e);
			historicoInsertRecord.setSuccess(false);
			historicoInsertRecord.setErrorCause(ConstantesBatch.ERROR_INSERT_RECORD);
			historicBatchDAO.insertHistoric(historicoInsertRecord);
			}						
		}
		logger.debug("--- Fin -- insert Listado Usuarios ---");
		session.close();
		if(cont == objectList.size()){
			historicoProcessInsert.setEndDate(new Date());
			historicoProcessInsert.setSuccess(true);
			historicoProcessInsert.setProcessedRecords(cont);
			historicBatchDAO.insertHistoric(historicoProcessInsert);
		} else {
			historicoProcessInsert.setEndDate(new Date());
			historicoProcessInsert.setSuccess(false);
			historicoProcessInsert.setErrorCause(ConstantesBatch.ERROR_INSERT_RECORD);
			historicoProcessInsert.setProcessedRecords(cont);
			historicBatchDAO.insertHistoric(historicoProcessInsert);
		}
	}
	

	/**
	 * Actualiza un listado de usuarios venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
		
	@Transactional
	public void updateUserListSf(List<Object> objectList) {
		logger.debug("--- Inicio -- update Listado Usuarios ---");

		Integer cont = 0;
		
		HistoricBatchVO historicoProcessUpdate = new HistoricBatchVO();
		historicoProcessUpdate.setStartDate(new Date());
		historicoProcessUpdate.setOperation(ConstantesBatch.UPDATE_PROCESS);
		historicoProcessUpdate.setTotalRecords(objectList.size());
		historicoProcessUpdate.setObject(ConstantesBatch.OBJECT_USER);
		
		Session session = sessionFactory.openSession();
		for(Object object:objectList){
			
			HistoricBatchVO historicoUpdateRecord = new HistoricBatchVO();
			historicoUpdateRecord.setOperation(ConstantesBatch.UPDATE_RECORD);
			historicoUpdateRecord.setObject(ConstantesBatch.OBJECT_USER);
			
			UserVO usuarioToUpdate = new UserVO();
			try{
				usuarioToUpdate=(UserVO)object;
				
				historicoUpdateRecord.setSfidRecord(usuarioToUpdate.getSfid());
				
				//1.1-Definimos los parámetros que no sean de tipo String				
				
				//1.2-Construimos la query							
				Query sqlUpdateQuery =session.createQuery("UPDATE UserVO SET "
				+ "name= :name"			
				+	
				" WHERE sfid = :sfidFiltro");
				
				//1.3-Seteamos los campos a actualizar de tipo String	
			    
					//1.3.1-Seteamos los campos que no filtren la query						
					sqlUpdateQuery.setParameter("name", usuarioToUpdate.getName());
	
					//1.3.2-Seteamos el sfid,campo por el que filtramos la query								
					sqlUpdateQuery.setParameter("sfidFiltro", usuarioToUpdate.getSfid());
				
				//1.4- Seteamos los campos a actualizar distintos de String				

				
				//1.5-Ejecutamos la actualizacion
				sqlUpdateQuery.executeUpdate();
							
				logger.debug("--- Fin -- updateUsuario ---" + usuarioToUpdate.getSfid());
				
				historicoUpdateRecord.setSuccess(true);
				historicBatchDAO.insertHistoric(historicoUpdateRecord);
				cont++;
				
			} catch (HibernateException e) {
			logger.error("--- Error en updateUsuario: ---" + usuarioToUpdate.getSfid(), e);
			historicoUpdateRecord.setSuccess(false);
			historicoUpdateRecord.setErrorCause(ConstantesBatch.ERROR_UPDATE_RECORD);
			historicBatchDAO.insertHistoric(historicoUpdateRecord);
			} 						
		}
		logger.debug("--- Fin -- update Listado Usuarios ---");
		session.close();
		if(cont == objectList.size()){
			historicoProcessUpdate.setEndDate(new Date());
			historicoProcessUpdate.setSuccess(true);
			historicoProcessUpdate.setProcessedRecords(cont);
			historicBatchDAO.insertHistoric(historicoProcessUpdate);
		} else {
			historicoProcessUpdate.setEndDate(new Date());
			historicoProcessUpdate.setSuccess(false);
			historicoProcessUpdate.setErrorCause(ConstantesBatch.ERROR_UPDATE_RECORD);
			historicoProcessUpdate.setProcessedRecords(cont);
			historicBatchDAO.insertHistoric(historicoProcessUpdate);
		}

	}
		
	/**
	 * Borra un listado de usuarios venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
		
	@Transactional
	public void deleteUserListSf(List<Object> objectList) {
		logger.debug("--- Inicio -- delete Listado Usuarios ---");
		
		Integer cont = 0;
		
		HistoricBatchVO historicoProcessDelete = new HistoricBatchVO();
		historicoProcessDelete.setStartDate(new Date());
		historicoProcessDelete.setOperation(ConstantesBatch.DELETE_PROCESS);
		historicoProcessDelete.setTotalRecords(objectList.size());
		historicoProcessDelete.setObject(ConstantesBatch.OBJECT_USER);

		Session session = sessionFactory.openSession();
		for(Object object:objectList){
			
			HistoricBatchVO historicoDeleteRecord = new HistoricBatchVO();
			historicoDeleteRecord.setOperation(ConstantesBatch.DELETE_RECORD);
			historicoDeleteRecord.setObject(ConstantesBatch.OBJECT_USER);
			
			UserVO usuarioToDelete = new UserVO();
			try{
				usuarioToDelete=(UserVO)object;
				
				historicoDeleteRecord.setSfidRecord(usuarioToDelete.getSfid());
				
				Query sqlDeleteQuery =session.createQuery("DELETE UserVO  WHERE sfid = :sfidFiltro");
				
				//Seteamos el campo por el que filtramos el borrado			
				sqlDeleteQuery.setParameter("sfidFiltro", usuarioToDelete.getSfid());				
				//Ejecutamos la actualizacion				
				sqlDeleteQuery.executeUpdate();
							
				logger.debug("--- Fin -- deleteUsuario ---" + usuarioToDelete.getSfid());
				
				historicoDeleteRecord.setSuccess(true);
				historicBatchDAO.insertHistoric(historicoDeleteRecord);
				cont++;
				
			} catch (HibernateException e) {
			logger.error("--- Error en deleteUsuario: ---" + usuarioToDelete.getSfid(), e);
			historicoDeleteRecord.setSuccess(false);
			historicoDeleteRecord.setErrorCause(ConstantesBatch.ERROR_DELETE_RECORD);
			historicBatchDAO.insertHistoric(historicoDeleteRecord);
			} 					
		}
		logger.debug("--- Fin -- delete Listado Usuarios ---");
		session.close();
		if(cont == objectList.size()){
			historicoProcessDelete.setEndDate(new Date());
			historicoProcessDelete.setSuccess(true);
			historicoProcessDelete.setProcessedRecords(cont);
			historicBatchDAO.insertHistoric(historicoProcessDelete);
		} else {
			historicoProcessDelete.setEndDate(new Date());
			historicoProcessDelete.setSuccess(false);
			historicoProcessDelete.setErrorCause(ConstantesBatch.ERROR_DELETE_RECORD);
			historicoProcessDelete.setProcessedRecords(cont);
			historicBatchDAO.insertHistoric(historicoProcessDelete);
		}

	}
	
	
	
	
	

}
