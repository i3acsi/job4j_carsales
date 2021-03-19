package ru.job4j.carsales.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhotoService {
    public static String getPhoto(Part part, String absDirPath, String head) throws Exception {
        String result = "";
        String filename, ext;
        LocalDate now = LocalDate.now();
        File directory, download;
        filename = part.getSubmittedFileName();
        ext = filename.substring(filename.lastIndexOf("."));
        directory = new File(absDirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        filename = head + "_" + now + "_" + ext;
        download = new File(directory + File.separator
                + filename);
        try (FileOutputStream out = new FileOutputStream(download)) {
            out.write(part.getInputStream().readAllBytes());
            return filename;
        }
    }

    public static List<String> savePhotos(String absPath, String path, HttpServletRequest req, String name, Logger log) {
        List<String> result = null;
        try {
            File directory = new File(absPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(directory);
            ServletFileUpload loadFile = new ServletFileUpload(factory);
            List<FileItem> fileList = loadFile.parseRequest(req);
            int counter = 0;
            LocalDate now = LocalDate.now();
            result = new ArrayList<>(fileList.size());
            for (var e : fileList) {
                String filename = e.getName();
                if (filename != null) {
                    String ext = filename.substring(filename.lastIndexOf("."));
                    if (!e.isFormField()) {
                        filename = name + "_" + now + "_" + counter++ + ext;
                        File download = new File(directory + File.separator
                                + filename);
                        try (FileOutputStream out = new FileOutputStream(download)) {
                            out.write(e.getInputStream().readAllBytes());
                        } catch (IOException ex) {
                            log.error("Unable to load file ", ex);
                        }
                        result.add(path + filename);
                    }
                }
            }
        } catch (FileUploadException e) {
            log.error("Unable to load file ", e);
        }
        return result;
    }
}