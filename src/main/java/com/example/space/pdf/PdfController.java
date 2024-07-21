package com.example.space.pdf;

import com.example.space.exceptions.BadRequestException;
import com.example.space.pacchetti.Pacchetto;
import com.example.space.pacchetti.PacchettoRepository;
import com.example.space.payloads.entities.PrenotazioneDTO;

import com.example.space.user.User;
import com.example.space.user.UserRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.pdfbox.pdmodel.font.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {
@Autowired
UserRepository userRepository;
@Autowired
    PacchettoRepository pacchettoRepository;

@Autowired
PdfRepository pdfRepository;
    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public byte[] generatePdf(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        try{

            PDFont font =   new PDType1Font(Standard14Fonts.FontName.COURIER);
            PDFont fontBold =   new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);
            PDPageContentStream contentStream;
            ByteArrayOutputStream output =new ByteArrayOutputStream();
            PDDocument document =new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile("C:/Users/Utente/Pictures/451987055_3624459951153481_7494526139017243318_n.jpg", document);

            contentStream = new PDPageContentStream(document, page);

            contentStream.drawImage( pdImage, 10, 750, 100, 100 );

            contentStream.beginText();
            contentStream.setFont(font, 30);
            contentStream.newLineAtOffset(150, 750);
            contentStream.showText(
                    "La tua prenotazione"
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(50, 634);
            User user = userRepository.findById(prenotazioneDTO.user_id()).orElseThrow(()->new BadRequestException("User con id "+ prenotazioneDTO.user_id() + " non trovato."));
            Pacchetto pacchetto = pacchettoRepository.findById(prenotazioneDTO.pacchetto_id().get(0)).orElseThrow(()->new BadRequestException("Pacchetto con id "+ prenotazioneDTO.pacchetto_id().get(0) + " non trovato."));


            contentStream.showText(
                    "Informazioni sulla persona : " + " " +
                     user.getNome() + " " + user.getCognome() + " " +
                     user.getEmail() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(300, 618);
            contentStream.showText(
                     user.getEta() + " anni."
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(50, 590);
            contentStream.showText(
                    "Dove andrai? " + " "
            );
            contentStream.endText();


            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(250, 590);
            contentStream.showText(
                    "Pianeta : " + pacchetto.getPianetas().get(0).getNome() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(250, 560);
            contentStream.showText(
                            "Galassia : " + pacchetto.getPianetas().get(0).getGalassia() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(50, 524);
            contentStream.showText(
                    "Hai speso " + pacchetto.getPrezzo() + " per questa prenotazione."
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(fontBold, 16);
            contentStream.newLineAtOffset(290, 150);
            contentStream.showText(
                    "P.P.V. Data, luogo e firma."
            );
            contentStream.endText();


            contentStream.moveTo(290, 100);
            contentStream.lineTo(450, 100);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(150, 50);
            contentStream.setFont(fontBold, 30);
            contentStream.showText(
                    "SpaceAgency A.p.s."
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 30);
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(200, 36);
            contentStream.showText(
                    "Tutti i diritti sono riservati"
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(200, 28);
            contentStream.showText(
                    "L'attuale documento non rappresenta "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(200, 20);
            contentStream.showText(
                    "Una reale prenotazione."
            );
            contentStream.endText();

            contentStream.close();


            document.save(output);
            document.close();

/*
            Pdf pdf = new Pdf();
            pdf.setUser(user);
            pdf.setPacchetto(pacchetto);


          if(pdfRepository.findByUser_IdAndPacchetto_Id(user.getId(),pacchetto.getId()).isPresent()){
                throw  new BadRequestException("Hai già scaricato il pdf per questa tua prenotazione.");
            }

            pdfRepository.save(pdf);
        */
            return output.toByteArray();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

}
