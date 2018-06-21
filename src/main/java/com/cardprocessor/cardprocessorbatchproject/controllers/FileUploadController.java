package com.cardprocessor.cardprocessorbatchproject.controllers;


import com.cardprocessor.cardprocessorbatchproject.configuration.FtpConfig;
import com.cardprocessor.cardprocessorbatchproject.exceptions.StorageFileNotFoundException;
import com.cardprocessor.cardprocessorbatchproject.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    @Autowired
    StorageService storageService;

    @Autowired
    private FtpConfig.UploadGateway gateway;


    @GetMapping("/files/{merchant}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename , @PathVariable String merchant) {

        Resource file = storageService.loadAsResource(filename, merchant);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }



        @PostMapping("/{merchant}/upload")
   public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes  , @PathVariable("merchant") String merchant )
                throws IllegalStateException, IOException {


        java.io.File convFile = new File(file.getOriginalFilename()).getAbsoluteFile();
        file.transferTo(convFile);

        gateway.sendToFtp(convFile,merchant);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/{merchant}/home";
    }




    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
