package com.casosemergencias.logic;

import java.util.List;
import java.util.Map;

import com.casosemergencias.model.Direccion;
import com.casosemergencias.util.datatables.DataTableProperties;

public interface DireccionService {
	public List<Direccion> listOfDireccionesTable();

	public Direccion readDireccionBySfid(String sfid);

	public List<Direccion> readAllDirecciones(DataTableProperties propDatatable);

	public Integer getNumDirecciones(DataTableProperties propDatatable);
	
	public Map<String, Object> getDatosCalleWS(String idComuna, String nombreCalle);
	
	public int insertDireccionSfList(List<Object> addressList, String processId);
	
	public int updateDireccionSfList(List<Object> addressList, String processId);

	public int deleteDireccionSfList(List<Object> addressList, String processId);
	
	/*public void sendDirectionToSF(Direccion direccion);*/
}