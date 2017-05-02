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

import com.casosemergencias.dao.vo.DireccionVO;
import com.casosemergencias.dao.vo.HistoricBatchVO;
import com.casosemergencias.util.constants.ConstantesBatch;
import com.casosemergencias.util.datatables.DataTableColumnInfo;
import com.casosemergencias.util.datatables.DataTableProperties;

@Repository
public class DireccionDAO {
	final static Logger logger = Logger.getLogger(DireccionDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	HistoricBatchDAO historicBatchDAO;

	/**
	 * Devuelve una lista con todas las Direcciones de BBDD
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DireccionVO> readAllDireccion() {

		logger.debug("--- Inicio -- readAllDireccion ---");

		Session session = sessionFactory.openSession();

		try {
			Query query = session.createQuery("from DireccionVO");

			List<DireccionVO> direccionList = query.list();

			logger.debug("--- Fin -- readAllDireccion ---");

			return direccionList;
		} catch (HibernateException e) {
			logger.error("--- readAllDireccion ", e);
			logger.error("--- Fin -- readAllDireccion ---");
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * Devuelve la Direccion que tiene como id el pasado por parametro al metodo
	 * 
	 * @param id
	 *            - id de un Direcciono
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DireccionVO readDireccionById(Integer id) {

		logger.debug("--- Inicio -- readDireccionById ---");

		Session session = sessionFactory.openSession();

		try {
			Query query = session.createQuery("from DireccionVO as direccion WHERE direccion.id = :id");
			query.setInteger("id", id);

			List<DireccionVO> direccionList = query.list();

			if (direccionList != null && !direccionList.isEmpty()) {
				return direccionList.get(0);
			}

			logger.debug("--- Fin -- readDireccionById ---");
		} catch (HibernateException e) {
			logger.error("--- readDireccionById ", e);
			logger.error("--- Fin -- readDireccionById ---");
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * Devuelve la Direccion que tiene como sfid el pasado por parametro al
	 * metodo
	 * 
	 * @param sfid
	 *            - id de un Direccion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DireccionVO readDireccionBySfid(String sfid) {

		logger.debug("--- Inicio -- readDireccionBySfid ---");

		Session session = sessionFactory.openSession();

		try {
			Query query = session.createQuery("from DireccionVO as direccion WHERE direccion.sfid = :sfid");
			query.setString("sfid", sfid);

			List<DireccionVO> direccionList = query.list();

			if (direccionList != null && !direccionList.isEmpty()) {
				return direccionList.get(0);
			}

			logger.debug("--- Fin -- readDireccionBySfid ---");
		} catch (HibernateException e) {
			logger.error("--- readDireccionBySfid ", e);
			logger.error("--- Fin -- readDireccionBySfid ---");
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * Dado un Direccion, recupera una lista de Direccion con los mismos datos
	 * que Direccion
	 * 
	 * @param Direcciono
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DireccionVO> readDireccion(DireccionVO direccion) {

		logger.debug("--- Inicio -- readDireccion ---");

		Session session = sessionFactory.openSession();
		boolean isFirst = true;

		try {
			// preparamos la query
			StringBuilder query = new StringBuilder("from DireccionVO as direccion");
			if (direccion.getId() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.id = :id");
					isFirst = false;
				} else {
					query.append(" AND direccion.id = :id");
				}
			}

			if (direccion.getName() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.name = :name");
					isFirst = false;
				} else {
					query.append(" AND direccion.name = :name");
				}
			}

			if (direccion.getSfid() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.sfid = :sfid");
					isFirst = false;
				} else {
					query.append(" AND direccion.sfid = :sfid");
				}
			}

			if (direccion.getRegion() != null) {
				if (isFirst) {
					query.append(" WHERE Direccion.region = :region");
					isFirst = false;
				} else {
					query.append(" AND direccion.region = :region");
				}
			}

			if (direccion.getComuna() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.comuna = :comuna");
					isFirst = false;
				} else {
					query.append(" AND direccion.comuna = :comuna");
				}
			}

			if (direccion.getTipoCalle() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.tipoCalle = :tipoCalle");
					isFirst = false;
				} else {
					query.append(" AND direccion.tipoCalle = :tipoCalle");
				}
			}

			if (direccion.getCalle() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.calle = :calle");
					isFirst = false;
				} else {
					query.append(" AND direccion.calle = :calle");
				}
			}

			if (direccion.getNumero() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.numero = :numero");
					isFirst = false;
				} else {
					query.append(" AND direccion.numero = :numero");
				}
			}

			if (direccion.getDepartamento() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.departamento = :departamento");
					isFirst = false;
				} else {
					query.append(" AND direccion.departamento = :departamento");
				}
			}

			if (direccion.getName() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.name = :name");
					isFirst = false;
				} else {
					query.append(" AND direccion.name = :name");
				}
			}

			if (direccion.getDireccionConcatenada() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.direccionConcatenada = :direccionConcatenada");
					isFirst = false;
				} else {
					query.append(" AND direccion.direccionConcatenada = :direccionConcatenada");
				}
			}

			if (direccion.getEsquina() != null) {
				if (isFirst) {
					query.append(" WHERE direccion.esquina = :esquina");
					isFirst = false;
				} else {
					query.append(" AND direccion.esquina = :esquina");
				}
			}

			// Añadimos los valores por los que filtrara la query
			Query result = session.createQuery(query.toString());
			if (direccion.getSfid() != null) {
				result.setString("sfid", direccion.getSfid());
			}

			if (direccion.getHcError() != null) {
				result.setString("hcError", direccion.getHcError());
			}

			if (direccion.getHcLastop() != null) {
				result.setString("hcLastop", direccion.getHcLastop());
			}

			if (direccion.getIsDeleted() != null) {
				result.setBoolean("isDeleted", direccion.getIsDeleted());
			}

			if (direccion.getId() != null) {
				result.setInteger("id", direccion.getId());
			}

			if (direccion.getName() != null) {
				result.setString("name", direccion.getName());
			}

			if (direccion.getRegion() != null) {
				result.setString("region", direccion.getRegion());
			}

			if (direccion.getComuna() != null) {
				result.setString("comuna", direccion.getComuna());
			}

			if (direccion.getTipoCalle() != null) {
				result.setString("tipoCalle", direccion.getTipoCalle());
			}

			if (direccion.getCalle() != null) {
				result.setString("calle", direccion.getCalle());
			}

			if (direccion.getNumero() != null) {
				result.setString("numero", direccion.getNumero());
			}

			if (direccion.getDepartamento() != null) {
				result.setString("departamento", direccion.getDepartamento());
			}

			if (direccion.getName() != null) {
				result.setString("name", direccion.getName());
			}

			if (direccion.getDireccionConcatenada() != null) {
				result.setString("direccionConcatenada", direccion.getDireccionConcatenada());
			}

			if (direccion.getEsquina() != null) {
				result.setString("esquina", direccion.getEsquina());
			}

			List<DireccionVO> direccionsList = result.list();

			logger.debug("--- Fin -- readDireccion ---");

			return direccionsList;

		} catch (HibernateException e) {
			logger.error("--- readDireccion ", e);
			logger.error("--- Fin -- readDireccion ---");
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * Actualiza la fila de Direccion correspondiente al id de Direccion.
	 * Modifica todos los campos con el valor que hay en Direccion.
	 * 
	 * @param Direccion
	 * @return
	 */
	@Transactional
	public int updateDireccion(DireccionVO Direccion) {

		logger.debug("--- Inicio -- updateDireccion ---");

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(Direccion);
			tx.commit();
			logger.debug("--- Fin -- updateDireccion ---");
			return 1;
		} catch (HibernateException e) {
			tx.rollback();
			logger.error("--- updateDireccion ", e);
			logger.error("--- Fin -- updateDireccion ---");
			return 0;
		} finally {
			session.close();
		}
	}

	/**
	 * Inserta un Direccion en BBDD
	 * 
	 * @param Direccion
	 * @return
	 */
	@Transactional
	public Integer insertDireccion(DireccionVO Direccion) {

		logger.debug("--- Inicio -- insert ---");

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(Direccion);
			tx.commit();
			logger.debug("--- Fin -- insert ---");
			return Direccion.getId();
		} catch (HibernateException e) {
			tx.rollback();
			logger.error("--- Error en insertDireccion: ", e);
			logger.error("--- Fin -- updateDireccion---");
			return 0;
		} finally {
			session.close();
		}

	}

	/**
	 * Devuelve una lista de direcciones utilizando los parametros del datatable
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DireccionVO> readDireccionDataTable(DataTableProperties dataTableProperties) {
		logger.debug("--- Inicio -- readDireccionDataTable ---");

		Session session = sessionFactory.openSession();
		String order = null;
		String dirOrder = null;
		if (dataTableProperties.getTableOrdering() != null) {
			order = (String) dataTableProperties.getTableOrdering().get("orderingColumnName");
			dirOrder = (String) dataTableProperties.getTableOrdering().get("orderingDirection");
		}

		int numStart = dataTableProperties.getStart();
		int numLength = dataTableProperties.getLength();
		int searchParamsCounter = 0;

		try {
			StringBuilder query = new StringBuilder("FROM DireccionVO direccion ");

			if (dataTableProperties.getColumsInfo() != null && !dataTableProperties.getColumsInfo().isEmpty()) {
				query.append(" WHERE ");
				for (DataTableColumnInfo columnInfo : dataTableProperties.getColumsInfo()) {
					if ("name".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							query.append("UPPER(direccion." + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							query.append(" AND ");
							searchParamsCounter++;
						}
					}
					if ("direccionConcatenada".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							query.append("UPPER(direccion." + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							query.append(" AND ");
							searchParamsCounter++;
						}
					}
					if ("comuna".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							query.append("UPPER(direccion." + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							query.append(" AND ");
							searchParamsCounter++;
						}
					}

					if ("calle".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							query.append("UPPER(direccion." + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							query.append(" AND ");
							searchParamsCounter++;
						}
					}
				}
			}

			if (searchParamsCounter == 0) {
				query.setLength(query.length() - 7);
			} else {
				query.setLength(query.length() - 5);
			}
			
			if (order != null && !"".equals(order) && dirOrder != null && !"".equals(dirOrder)) {
				if("name".equals(order) || "direccionConcatenada".equals(order)|| "comuna".equals(order)
				|| "calle".equals(order)){
					query.append(" ORDER BY " + order + " " + dirOrder);
				}else{
					query.append(" ORDER BY direccion." + order + " " + dirOrder);
				}
			}else{
				query.append(" ORDER BY direccion.direccionConcatenada desc" );
			}
			Query result = session.createQuery(query.toString()).setFirstResult(numStart).setMaxResults(numLength);
			List<DireccionVO> direccionList = result.list();

			logger.debug("--- Fin -- readDireccionDataTable ---");

			return direccionList;
		} catch (HibernateException e) {
			logger.error("--- readDireccionDataTable ", e);
			logger.error("--- Fin -- readDireccionDataTable ---");
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * Devuelve el numero de direcciones utilizando los parametros del datatable
	 * 
	 * @return
	 */
	public Integer countDireccion(DataTableProperties dataTableProperties) {
		logger.debug("--- Inicio -- countDireccion ---");

		Session session = sessionFactory.openSession();
		int searchParamsCounter = 0;

		try {
			StringBuilder sqlQuery = new StringBuilder("SELECT COUNT(id) FROM DireccionVO ");

			if (dataTableProperties.getColumsInfo() != null && !dataTableProperties.getColumsInfo().isEmpty()) {
				sqlQuery.append(" WHERE ");
				for (DataTableColumnInfo columnInfo : dataTableProperties.getColumsInfo()) {
					if ("name".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							sqlQuery.append(columnInfo.getData() + " LIKE '%" + columnInfo.getSearchValue() + "%'");
							sqlQuery.append(" AND ");
							searchParamsCounter++;
						}
					}

					if ("direccionConcatenada".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							sqlQuery.append("UPPER(" + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							sqlQuery.append(" AND ");
							searchParamsCounter++;
						}
					}
					if ("calle".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							sqlQuery.append("UPPER(" + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							sqlQuery.append(" AND ");
							searchParamsCounter++;
						}
					}
					if ("comuna".equals(columnInfo.getData())) {
						if (columnInfo.getSearchValue() != null && !"".equals(columnInfo.getSearchValue())) {
							sqlQuery.append("UPPER(" + columnInfo.getData() + ") LIKE UPPER('%"
									+ columnInfo.getSearchValue() + "%')");
							sqlQuery.append(" AND ");
							searchParamsCounter++;
						}
					}
				}
			}

			if (searchParamsCounter == 0) {
				sqlQuery.setLength(sqlQuery.length() - 7);
			} else {
				sqlQuery.setLength(sqlQuery.length() - 5);
			}

			Query query = session.createQuery(sqlQuery.toString());
			Long count = (Long) query.uniqueResult();

			logger.debug("--- Fin -- countDireccion ---");

			return count.intValue();
		} catch (HibernateException e) {
			logger.error("--- countDireccion ", e);
			logger.error("--- Fin -- countDireccion ---");
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * Inserta un listado de Direcciones venidos de Salesforce en BBDD de
	 * Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int insertDireccionListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- insert Listado Direcciones ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoInsertRecord = null;
		DireccionVO direccionToInsert = null;

		// Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();

		for (Object object : objectList) {
			historicoInsertRecord = new HistoricBatchVO();
			historicoInsertRecord.setStartDate(new Date());
			historicoInsertRecord.setOperation(ConstantesBatch.INSERT_RECORD);
			historicoInsertRecord.setObject(ConstantesBatch.OBJECT_ADDRESS);
			historicoInsertRecord.setProcessId(processId);
			direccionToInsert = new DireccionVO();

			try {
				direccionToInsert = (DireccionVO) object;
				historicoInsertRecord.setSfidRecord(direccionToInsert.getSfid());
				session.save(direccionToInsert);

				logger.debug("--- Fin -- insertDireccion ---" + direccionToInsert.getSfid());
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en insertDireccion: ---" + direccionToInsert.getSfid(), e);
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_INSERT_RECORD;
			}

			historicoInsertRecord.setSuccess(processOk);
			historicoInsertRecord.setEndDate(new Date());
			historicoInsertRecord.setErrorCause(processErrorCause);
			historicBatchDAO.insertHistoric(historicoInsertRecord);
		}
		logger.debug("--- Fin -- insert Listado Direcciones ---");
		session.close();
		return processedRecords;
	}

	/**
	 * Actualiza un listado de direcciones venidos de Salesforce en BBDD de
	 * Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int updateDireccionListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- update Listado Direcciones ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoUpdateRecord = null;
		DireccionVO direccionToUpdate = null;

		// Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();

		for (Object object : objectList) {
			historicoUpdateRecord = new HistoricBatchVO();
			historicoUpdateRecord.setStartDate(new Date());
			historicoUpdateRecord.setOperation(ConstantesBatch.UPDATE_RECORD);
			historicoUpdateRecord.setObject(ConstantesBatch.OBJECT_ADDRESS);
			historicoUpdateRecord.setProcessId(processId);
			direccionToUpdate = new DireccionVO();

			try {
				direccionToUpdate = (DireccionVO) object;
				historicoUpdateRecord.setSfidRecord(direccionToUpdate.getSfid());
				//1.1-Construimos la query
				Query sqlUpdateQuery = session.createQuery("UPDATE DireccionVO "
														+ "    SET createddate = :createddate"
														+ "		 , region__c = :region__c"
														+ "		 , municipality__c = :municipality__c"
														+ "		 , street_type__c = :street_type__c"
														+ "		 , streetmd__c = :streetmd__c"
														+ "		 , number__c= :number__c"
														+ "		 , department__c= :department__c"
														+ "		 , name = :name"
														+ "	     , concatenatedaddress__c = :concatenatedaddress__c"
														+ "		 , corner__c = :corner__c"
														+ "  WHERE sfid = :sfidFiltro");

				//1.2-Seteamos los campos
				sqlUpdateQuery.setTimestamp("createddate", direccionToUpdate.getCreateddate());
				sqlUpdateQuery.setString("region__c", direccionToUpdate.getRegion());
				sqlUpdateQuery.setString("municipality__c", direccionToUpdate.getComuna());
				sqlUpdateQuery.setString("street_type__c", direccionToUpdate.getTipoCalle());
				sqlUpdateQuery.setString("streetmd__c", direccionToUpdate.getCalle());
				sqlUpdateQuery.setString("number__c", direccionToUpdate.getNumero());
				sqlUpdateQuery.setString("department__c", direccionToUpdate.getDepartamento());
				sqlUpdateQuery.setString("name", direccionToUpdate.getName());
				sqlUpdateQuery.setString("concatenatedaddress__c", direccionToUpdate.getDireccionConcatenada());
				sqlUpdateQuery.setString("corner__c", direccionToUpdate.getEsquina());
				sqlUpdateQuery.setString("sfidFiltro", direccionToUpdate.getSfid());

				// 1.3-Ejecutamos la actualizacion
				sqlUpdateQuery.executeUpdate();
				logger.debug("--- Fin -- updateDireccion ---" + direccionToUpdate.getSfid());

				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en updateDireccion: ---" + direccionToUpdate.getSfid(), e);
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_UPDATE_RECORD;
			} 

			historicoUpdateRecord.setSuccess(processOk);
			historicoUpdateRecord.setEndDate(new Date());
			historicoUpdateRecord.setErrorCause(processErrorCause);
			historicBatchDAO.insertHistoric(historicoUpdateRecord);
		}
		logger.debug("--- Fin -- update Listado Direcciones ---");
		session.close();
		return processedRecords;
	}

	/**
	 * Borra un listado de direcciones venidos de Salesforce en BBDD de Heroku.
	 * 
	 * @param List<Object>
	 * @return
	 */
	@Transactional
	public int deleteDireccionListSf(List<Object> objectList, String processId) {
		logger.debug("--- Inicio -- delete Listado Direcciones ---");
		int processedRecords = 0;
		boolean processOk = false;
		String processErrorCause = null;
		HistoricBatchVO historicoDeleteRecord = null;
		DireccionVO direccionToDelete = null;
		
		//Se crea la sesión y se inica la transaccion
		Session session = sessionFactory.openSession();
		
		for (Object object : objectList) {
			historicoDeleteRecord = new HistoricBatchVO();
			historicoDeleteRecord.setStartDate(new Date());
			historicoDeleteRecord.setOperation(ConstantesBatch.DELETE_RECORD);
			historicoDeleteRecord.setObject(ConstantesBatch.OBJECT_ADDRESS);
			historicoDeleteRecord.setProcessId(processId);
			direccionToDelete = new DireccionVO();
			
			try {
				direccionToDelete = (DireccionVO) object;
				historicoDeleteRecord.setSfidRecord(direccionToDelete.getSfid());
				Query sqlDeleteQuery = session.createQuery("DELETE DireccionVO  WHERE sfid = :sfidFiltro");
				// Seteamos el campo por el que filtramos el borrado
				sqlDeleteQuery.setParameter("sfidFiltro", direccionToDelete.getSfid());
				// Ejecutamos la actualizacion
				sqlDeleteQuery.executeUpdate();
				
				logger.debug("--- Fin -- deleteDireccion ---" + direccionToDelete.getSfid());
				processOk = true;
				processedRecords++;
			} catch (HibernateException e) {
				logger.error("--- Error en deleteDireccion: ---" + direccionToDelete.getSfid(), e);
				processOk = false;
				processErrorCause = ConstantesBatch.ERROR_DELETE_RECORD;
			} 

			historicoDeleteRecord.setSuccess(processOk);
			historicoDeleteRecord.setErrorCause(processErrorCause);
			historicoDeleteRecord.setEndDate(new Date());
			historicBatchDAO.insertHistoric(historicoDeleteRecord);
		}
		logger.debug("--- Fin -- delete Listado Direcciones ---");
		session.close();
		return processedRecords;
	}
}