package com.casosemergencias.util.constants;

public class ConstantesError {
	/** Codigos de error a mostrar en caso de error. */
	public static final String EMERG_ERROR_CODE_001 = "HRK_LOGIN_SF";
	public static final String EMERG_ERROR_CODE_002 = "HRK_CREATE_CASE_SF";
	public static final String EMERG_ERROR_CODE_003 = "HRK_CREATE_CASE_HRK";
	public static final String EMERG_ERROR_CODE_004 = "HRK_CREATE_CASE_GEN";
	public static final String EMERG_ERROR_CODE_005 = "HRK_CREATE_CASE_DUP";
	public static final String EMERG_ERROR_CODE_006 = "HRK_SEARCH_DIR_SF";
	
	/** Mensajes de error a mostrar en caso de error. */
	public static final String SALESFORCE_LOGIN_ERROR = "Error conectando con Salesforce";
	public static final String SALESFORCE_CASE_CREATION_ERROR = "Error creando el caso en SalesForce";
	public static final String HEROKU_CASE_CREATION_ERROR = "Error creando el caso en Heroku";
	public static final String HEROKU_CASE_CREATION_OK_WITH_ERROR = "Se ha insertado el caso con problemas durante el guardado. Espere unos minutos en consultarlo y si no aparece, consulte con el administrador";
	public static final String HEROKU_CASE_CREATION_GENERIC_ERROR = "Se ha producido un error creando el caso";
	public static final String HEROKU_CASE_SF_RECOVER_GENERIC_ERROR = "Se ha producido un error creando el caso en SF";
	public static final String HEROKU_CASE_CREATION_DUPLICATED_CASE = "No se puede crear un nuevo caso. Existen casos abiertos pendientes";
	public static final String SALESFORCE__SEARCH_ADDRESS_ERROR = "Error recuperando la Direccion de Salesforce";
	public static final String SALESFORCE__SEARCH_CASE_COMMENT_ERROR = "Error recuperando el Comentario de Caso de Salesforce";
	public static final String HEROKU_CASECOMMENT_CCREATION_OK_WITH_ERROR = "Se ha insertado el comentario con problemas durante el guardado. Espere unos minutos en consultarlo y si no aparece, consulte con el administrador";
	public static final String SALESFORCE_CASE_COMMENT_CREATION_ERROR = "Error creando el comentario en SalesForce";
	public static final String SALESFORCE__SEARCH_COMMENT_CASE_ERROR = "Error recuperando el Comentario de Salesforce";
	public static final String HEROKU_CASE_COMMENT_CREATION_GENERIC_ERROR = "Se ha producido un error creando el comentario";
	public static final String SALESFORCE__SEARCH_REL_ACTIVO_CONTACTO_ERROR = "Error recuperando el Service Product de Salesforce";
	public static final String HEROKU_SERVICE_PRODUCT_CREATION_OK_WITH_ERROR = "Se ha insertado la relacion Activo Contacto con problemas durante el guardado. Espere unos minutos en consultarlo y si no aparece, consulte con el administrador";
	public static final String SALESFORCE_SERVICE_PRODUCT_CREATION_ERROR = "Error creando la relacion Activo Contacto en SalesForce";
	public static final String HEROKU_SERVICE_PRODUCT_CREATION_GENERIC_ERROR = "Se ha producido un error creando la relacion Activo Contacto";


	



	
	


}