package com.casosemergencias.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.casosemergencias.dao.vo.GroupVO;

public class GrupoDAO {
	final static Logger logger = Logger.getLogger(AccountDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Inserta un listado de Grupos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
		
	@Transactional
	public void insertGroupListSf(List<Object> objectList) {
		logger.debug("--- Inicio -- insert Listado Grupos ---");

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();		
		for(Object object:objectList){
			GroupVO grupoToInsert = new GroupVO();
			try{
				grupoToInsert=(GroupVO)object;
				session.save(grupoToInsert);
				tx.commit();
				logger.debug("--- Fin -- insertGrupo ---" + grupoToInsert.getSfid());
			} catch (HibernateException e) {
			tx.rollback();
			logger.error("--- Error en insertGrupo: ---" + grupoToInsert.getSfid(), e);
			}						
		}
		logger.debug("--- Fin -- insert Listado Grupos ---");
		session.close();
	}
	

	/**
	 * Actualiza un listado de grupos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
		
	@Transactional
	public void updateGroupListSf(List<Object> objectList) {
		logger.debug("--- Inicio -- update Listado Grupos ---");

		Session session = sessionFactory.openSession();
		for(Object object:objectList){
			GroupVO grupoToUpdate = new GroupVO();
			try{
				grupoToUpdate=(GroupVO)object;
				Query sqlUpdateQuery =session.createQuery("UPDATE GroupVO SET "
				+ "name= :name,createddate= :createddate"				
				+	
				" WHERE sfid = :sfidFiltro");
				
				//Seteamos los campos a actualizar
				
				sqlUpdateQuery.setParameter("name", grupoToUpdate.getName());
				sqlUpdateQuery.setParameter("createddate", grupoToUpdate.getCreateddate());

				//Seteamos el campo por el que filtramos la actualización
				
				sqlUpdateQuery.setParameter("sfidFiltro", grupoToUpdate.getSfid());
				
				//Ejecutamos la actualizacion
				sqlUpdateQuery.executeUpdate();
							
				logger.debug("--- Fin -- updateGrupo ---" + grupoToUpdate.getSfid());
			} catch (HibernateException e) {
			logger.error("--- Error en updateGrupo: ---" + grupoToUpdate.getSfid(), e);
			} 						
		}
		logger.debug("--- Fin -- update Listado Grupos ---");
		session.close();

	}
		
	/**
	 * Borra un listado de grupos venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
		
	@Transactional
	public void deleteGroupListSf(List<Object> objectList) {
		logger.debug("--- Inicio -- delete Listado Grupos ---");

		Session session = sessionFactory.openSession();
		for(Object object:objectList){
			GroupVO grupoToDelete = new GroupVO();
			try{
				grupoToDelete=(GroupVO)object;
				Query sqlDeleteQuery =session.createQuery("DELETE GroupVO  WHERE sfid = :sfidFiltro");
				
				//Seteamos el campo por el que filtramos el borrado			
				sqlDeleteQuery.setParameter("sfidFiltro", grupoToDelete.getSfid());				
				//Ejecutamos la actualizacion				
				sqlDeleteQuery.executeUpdate();
							
				logger.debug("--- Fin -- deleteGrupo ---" + grupoToDelete.getSfid());
			} catch (HibernateException e) {
			logger.error("--- Error en deleteGrupo: ---" + grupoToDelete.getSfid(), e);
			} 					
		}
		logger.debug("--- Fin -- delete Listado Grupos ---");
		session.close();

	}
	
	
	

}
