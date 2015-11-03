package fr.ujf.m2pgi.database.Service;

import javax.ejb.Local;
import java.io.*;

/**
 * Created by FAURE Adrien on 03/11/15.
 */
@Local
public interface IFileService {

    void saveFile(InputStream uploadedInputStream, String serverLocation);

}
