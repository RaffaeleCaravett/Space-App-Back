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
            PDPageContentStream contentStream;
            ByteArrayOutputStream output =new ByteArrayOutputStream();
            PDDocument document =new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(font, 25);
            contentStream.newLineAtOffset(10, 700);
            User user = userRepository.findById(prenotazioneDTO.user_id()).orElseThrow(()->new BadRequestException("User con id "+ prenotazioneDTO.user_id() + " non trovato."));
            Pacchetto pacchetto = pacchettoRepository.findById(prenotazioneDTO.pacchetto_id().get(0)).orElseThrow(()->new BadRequestException("Pacchetto con id "+ prenotazioneDTO.pacchetto_id().get(0) + " non trovato."));

            Pdf pdf = new Pdf();
            pdf.setUser(user);
            pdf.setPacchetto(pacchetto);

            if(pdfRepository.findByUser_IdAndPacchetto_Id(user.getId(),pacchetto.getId()).isPresent()){
                throw new BadRequestException("Hai già scaricato il pdf per questa tua prenotazione.");
            }

            contentStream.showText(
                    "Informazioni sulla persona : " + " " +
                     user.getNome() + " " + user.getCognome() + " " +
                     user.getEmail() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(10, 678);
            contentStream.showText(
                    "Anni : " + user.getEta() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(10, 656);
            contentStream.showText(
                    "Dove andrai? " + " " +
                            "Pianeta : " + pacchetto.getPianetas().get(0).getNome() + " " +
                            "Galassia : " + pacchetto.getPianetas().get(0).getGalassia() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(10, 634);
            contentStream.showText(
                    "Quanto hai speso per questa prenotazione : " + pacchetto.getPrezzo() +";"
            );
            contentStream.endText();

            contentStream.close();

            document.save(output);
            document.close();


            return output.toByteArray();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

}
