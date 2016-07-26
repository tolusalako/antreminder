/** Copyright (c) 2016 SalakoTech.
 * This file and all of its contents belong to SalakoTech and should not be shared.
 * Created on: Jul 2, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 2, 2016
 */
package net.csthings.antreminder.websoc;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service("webSocService")
public interface WebSocService {
	
	public File getFormHtml() throws IOException;
	
	public String getFormData(WebSocUtils.Categories category);
}
