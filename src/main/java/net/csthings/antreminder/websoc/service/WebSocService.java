/** Copyright (c) 2016 SalakoTech.
 * This file and all of its contents belong to SalakoTech and should not be shared.
 * Created on: Jul 2, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 2, 2016
 */
package net.csthings.antreminder.websoc.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import net.csthings.antreminder.websoc.utils.Category;

@Service("webSocService")
public interface WebSocService {

    public File getFormHtml() throws IOException;

    public List<String> getFormData(Category category);

    public File generateNewFormHtml() throws IOException;
    
    public String generateInnerFormHtml();
    
    public String generateInnerSearchHtml(String response);
}
