package rest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import rest.Auth.SecurityToken;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor {
    private static final ServerResponse ACCESS_DENIED = 
    		new ServerResponse("Access denied for this resource", 401, new Headers<Object>());
    private static final ServerResponse ACCESS_FORBIDDEN = 
    		new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());
     
	public ServerResponse preProcess(HttpRequest request, ResourceMethod methodInvoked)
			throws Failure, WebApplicationException {
		
		Class<? extends Object> cls = methodInvoked.getResourceClass();
		Method m = methodInvoked.getMethod();

		if(cls.isAnnotationPresent(DenyAll.class) || m.isAnnotationPresent(DenyAll.class))
			return ACCESS_FORBIDDEN;
		if(cls.isAnnotationPresent(PermitAll.class) || m.isAnnotationPresent(PermitAll.class))
			return null;
		
		final HttpHeaders headers = request.getHttpHeaders();
		final List<String> token = headers.getRequestHeader("EE-TOKEN");
        if(token == null || token.isEmpty())
        {
            return ACCESS_DENIED;
        }
        
        SecurityToken st = null;
        try{
        	byte arr[] = DatatypeConverter.parseBase64Binary(token.get(0));
        	byte decrypted[] = LoginService.decrypt(arr);
        	st = SecurityToken.parseFrom(decrypted);
        	if(st.getExpires() < new Date().getTime())
        		new RuntimeException("Token expired");
        }catch(Exception e){
        	return new ServerResponse("Incorrect EE-TOKEN: "+e.toString(), 403, new Headers<Object>());
        }
        
        String[] roles = new String[]{};
        if(m.isAnnotationPresent(RolesAllowed.class))
        	roles = m.getAnnotation(RolesAllowed.class).value();
        else if(cls.isAnnotationPresent(RolesAllowed.class))
        	roles = cls.getAnnotation(RolesAllowed.class).value();
        
        if(roles.length > 0)
        {
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(roles));
            rolesSet.retainAll(st.getRolesList());
            if( rolesSet.isEmpty() )
            {
                return ACCESS_DENIED;
            }
        }
		
		return null;
	}

}
