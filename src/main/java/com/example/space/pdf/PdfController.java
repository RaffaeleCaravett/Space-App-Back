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
import org.apache.pdfbox.pdmodel.font.COURIER;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("")
    @PreAuthorize("hasAuthority('Admin')")
    public void generatePdf(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
            contentStream.beginText();
            contentStream.showText("Hello World");
            contentStream.endText();
            contentStream.close();

            document.save("pdfBoxHelloWorld.pdf");
            document.close();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

}
