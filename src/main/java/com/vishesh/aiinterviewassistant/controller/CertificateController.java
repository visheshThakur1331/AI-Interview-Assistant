package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.entity.Result;
import com.vishesh.aiinterviewassistant.service.ResultService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.UUID;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Controller
public class CertificateController {

    @Autowired
    private ResultService resultService;

    // ===============================
    // SHOW CERTIFICATE PAGE
    // ===============================

    @GetMapping("/certificate")
    public String showCertificate(Model model,
                                  HttpSession session) {

        String userEmail =
                (String) session.getAttribute("userEmail");

        if(userEmail == null){
            return "redirect:/login";
        }

        Result result =
                resultService.getLatestResult(userEmail);

        if(result == null){
            return "redirect:/dashboard";
        }

        model.addAttribute("userName",
                result.getUserName());

        model.addAttribute("userEmail",
                result.getUserEmail());

        model.addAttribute("category",
                result.getCategory());

        model.addAttribute("score",
                result.getScore());

        model.addAttribute("total",
                result.getTotal());

        model.addAttribute("date",
                result.getInterviewDate());

        model.addAttribute(
                "certificateId",
                UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase());

        model.addAttribute(
                "issueDate",
                LocalDate.now());

        return "certificate";
    }

    // ==========================================
    // DOWNLOAD PDF
    // ==========================================

    @GetMapping("/certificate/download")
    public void downloadCertificate(
            HttpServletResponse response,
            HttpSession session) throws Exception {

        String userEmail =
                (String) session.getAttribute("userEmail");

        if(userEmail == null){
            response.sendRedirect("/login");
            return;
        }

        Result result =
                resultService.getLatestResult(userEmail);

        if(result == null){
            response.sendRedirect("/dashboard");
            return;
        }

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=AI_Interview_Certificate.pdf");

        Document document = new Document(PageSize.A4, 30, 30, 30, 30);

        PdfWriter writer =
                PdfWriter.getInstance(
                        document,
                        response.getOutputStream());

        document.open();

        // =========================
// FONTS
// =========================

        Font titleFont = new Font(
                Font.FontFamily.HELVETICA,
                26,
                Font.BOLD,
                new BaseColor(37,99,235));

        Font headingFont = new Font(
                Font.FontFamily.HELVETICA,
                16,
                Font.BOLD,
                new BaseColor(30,64,175));

        Font nameFont = new Font(
                Font.FontFamily.HELVETICA,
                22,
                Font.BOLD,
                new BaseColor(37,99,235));

        Font normalFont = new Font(
                Font.FontFamily.HELVETICA,
                12,
                Font.NORMAL,
                BaseColor.BLACK);

        Font smallFont = new Font(
                Font.FontFamily.HELVETICA,
                13,
                Font.NORMAL,
                BaseColor.DARK_GRAY);


// =========================
// BLUE BORDER
// =========================

        PdfContentByte canvas = writer.getDirectContent();

        Rectangle rect =
                new Rectangle(
                        20,
                        20,
                        820,
                        575);

        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(8);
        rect.setBorderColor(
                new BaseColor(37,99,235));

        canvas.rectangle(rect);


// =========================
// TITLE
// =========================

        Paragraph p;

        p = new Paragraph(
                "AI INTERVIEW ASSISTANT",
                headingFont);

        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);


        p = new Paragraph(
                "CERTIFICATE OF ACHIEVEMENT",
                titleFont);

        p.setSpacingBefore(20);
        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);


        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));


// =========================
// PRESENTED TO
// =========================

        p = new Paragraph(
                "This Certificate is Proudly Presented To",
                normalFont);

        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);


        document.add(new Paragraph(" "));


// =========================
// USER NAME
// =========================

        p = new Paragraph(
                result.getUserName(),
                nameFont);

        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);


        document.add(new Paragraph(" "));


// =========================
// DETAILS
// =========================

        PdfPTable table =
                new PdfPTable(2);

        table.setWidthPercentage(55);

        table.setSpacingBefore(20);

        table.setWidths(
                new float[]{35,65});

        table.addCell("Email");
        table.addCell(result.getUserEmail());

        table.addCell("Interview Category");
        table.addCell(result.getCategory());

        table.addCell("Score");
        table.addCell(
                result.getScore()
                        + " / "
                        + result.getTotal());

        table.addCell("Interview Date");
        table.addCell(
                String.valueOf(result.getInterviewDate()));

        table.addCell("Issue Date");
        table.addCell(
                String.valueOf(LocalDate.now()));

        document.add(table);


        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // =========================
// CONGRATULATIONS MESSAGE
// =========================

        document.add(new Paragraph(" "));

        Paragraph msg = new Paragraph(
                "Congratulations!\n\n" +
                        "You have successfully completed the AI Technical Interview Assessment.\n\n" +
                        "We appreciate your dedication and wish you great success in your career.",
                normalFont);

        msg.setAlignment(Element.ALIGN_CENTER);
        msg.setSpacingBefore(20);

        document.add(msg);


// =========================
// SIGNATURE TABLE
// =========================

        document.add(new Paragraph(" "));

        PdfPTable signTable = new PdfPTable(2);

        signTable.setWidthPercentage(90);

        PdfPCell left = new PdfPCell();
        left.setBorder(Rectangle.NO_BORDER);
        left.setHorizontalAlignment(Element.ALIGN_CENTER);

        left.addElement(new Paragraph(
                "_______________________",
                normalFont));

        left.addElement(new Paragraph(
                "Candidate",
                smallFont));

        PdfPCell right = new PdfPCell();
        right.setBorder(Rectangle.NO_BORDER);
        right.setHorizontalAlignment(Element.ALIGN_CENTER);

        right.addElement(new Paragraph(
                "_______________________",
                normalFont));

        right.addElement(new Paragraph(
                "AI Interview Assistant",
                smallFont));

        signTable.addCell(left);
        signTable.addCell(right);

        document.add(signTable);


// =========================
// CERTIFICATE ID
// =========================

        document.add(new Paragraph(" "));

        Paragraph id = new Paragraph(
                "Certificate ID : "
                        + UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase(),
                smallFont);

        id.setAlignment(Element.ALIGN_CENTER);

        document.add(id);


// =========================
// CLOSE PDF
// =========================

        document.close();
    }
}