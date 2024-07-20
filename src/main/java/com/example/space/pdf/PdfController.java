package com.example.space.pdf;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PrenotazioneDTO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.pdfbox.pdmodel.font.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public static ByteArrayOutputStream generatePdf(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        try{

            PDFont font =   new PDType1Font(Standard14Fonts.FontName.COURIER);
            PDPageContentStream contentStream;
            ByteArrayOutputStream output =new ByteArrayOutputStream();
            PDDocument document =new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(10, 770);
            contentStream.showText("Amount: $1.00");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(200, 880);
            contentStream.showText("Sequence Number: 123456789");
            contentStream.endText();

            contentStream.close();

            document.save(output);
            document.close();
            return output;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

}
