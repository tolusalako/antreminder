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
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.csthings.antreminder.websoc.WebSocUtils.Category;


@Service("webSocService")
public interface WebSocService {
	
	public File getFormHtml() throws IOException;
	
	public List<String> getFormData(Category category);
}
