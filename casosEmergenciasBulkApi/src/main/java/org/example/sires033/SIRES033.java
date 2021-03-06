package org.example.sires033;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.7 2016-09-19T14:07:31.130+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.example.org/SIRES033/SIRES033", name = "SIRES033")
@XmlSeeAlso({ com.endesa.xmlns.commonschemas.ObjectFactory.class, org.example.sires033_schema.ObjectFactory.class,
		com.endesa.xmlns.commonschemasneol.ObjectFactory.class })
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SIRES033 {

	@WebMethod(operationName = "ConsultaDatosSuministro", action = "ConsultaDatosSuministro")
	@WebResult(name = "ConsultaDatosSuministroResponse", targetNamespace = "http://www.example.org/SIRES033_Schema", partName = "response")
	public org.example.sires033_schema.ConsultaDatosSuministroResponseType consultaDatosSuministro(
			@WebParam(partName = "request", name = "ConsultaDatosSuministroRequest", targetNamespace = "http://www.example.org/SIRES033_Schema") org.example.sires033_schema.ConsultaDatosSuministroRequestType request)
			throws ConsultaDatosSuministroFault;
}