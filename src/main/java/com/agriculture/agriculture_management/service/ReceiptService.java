package com.agriculture.agriculture_management.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.agriculture.agriculture_management.entity.Order;
import com.agriculture.agriculture_management.entity.OrderItem;
import com.agriculture.agriculture_management.repository.OrderRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReceiptService {

    private final OrderRepository orderRepository;

    public ReceiptService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public byte[] generateReceipt(Long orderId) throws DocumentException {

        // Find order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // PDF output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, outputStream);

        // =========================
        // FONTS
        // =========================

        Font titleFont = new Font(
                Font.HELVETICA,
                18,
                Font.BOLD
        );

        Font subtitleFont = new Font(
                Font.HELVETICA,
                14,
                Font.BOLD
        );

        Font headerFont = new Font(
                Font.HELVETICA,
                10,
                Font.BOLD
        );

        // Date format
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        document.open();

        // =========================
        // TITLE
        // =========================

        Paragraph title =
                new Paragraph("AGRICULTURE MANAGEMENT", titleFont);

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        // =========================
        // RECEIPT SUBTITLE
        // =========================

        Paragraph subtitle =
                new Paragraph("RECEIPT", subtitleFont);

        subtitle.setAlignment(Element.ALIGN_CENTER);

        document.add(subtitle);

        document.add(new Paragraph(" "));

        // Separator
        document.add(
                new Paragraph("--------------------------------")
        );

        // =========================
        // ORDER DETAILS
        // =========================

        document.add(new Paragraph(
                "Order ID: " + order.getId()
        ));

        document.add(new Paragraph(
                "Customer: " + order.getCustomer().getName()
        ));

        document.add(new Paragraph(
                "Email: " + order.getCustomer().getEmail()
        ));

        document.add(new Paragraph(
                "Order Date: "
                + order.getOrderDate().format(formatter)
        ));

        document.add(new Paragraph(
                "Status: " + order.getStatus()
        ));

        document.add(new Paragraph(" "));

        // =========================
        // PRODUCT TABLE
        // =========================

        PdfPTable table = new PdfPTable(4);

        // Product column is wider
        table.setWidths(new float[] { 3, 1, 1, 1 });

        // =========================
        // TABLE HEADERS
        // =========================

        PdfPCell productHeader =
                new PdfPCell(
                        new Paragraph("Product", headerFont)
                );

        PdfPCell quantityHeader =
                new PdfPCell(
                        new Paragraph("Quantity", headerFont)
                );

        quantityHeader.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        PdfPCell priceHeader =
                new PdfPCell(
                        new Paragraph("Price", headerFont)
                );

        priceHeader.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        PdfPCell subtotalHeader =
                new PdfPCell(
                        new Paragraph("Subtotal", headerFont)
                );

        subtotalHeader.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(productHeader);
        table.addCell(quantityHeader);
        table.addCell(priceHeader);
        table.addCell(subtotalHeader);

        // =========================
        // PRODUCT DATA
        // =========================

        for (OrderItem item : order.getOrderItems()) {

            table.addCell(
                    item.getProduct().getName()
            );

            PdfPCell quantityCell =
                    new PdfPCell(
                            new Paragraph(
                                    String.valueOf(item.getQuantity())
                            )
                    );

            quantityCell.setHorizontalAlignment(
                    Element.ALIGN_CENTER
            );

            table.addCell(quantityCell);

            PdfPCell priceCell =
                    new PdfPCell(
                            new Paragraph(
                                    String.valueOf(item.getPrice())
                            )
                    );

            priceCell.setHorizontalAlignment(
                    Element.ALIGN_CENTER
            );

            table.addCell(priceCell);

            PdfPCell subtotalCell =
                    new PdfPCell(
                            new Paragraph(
                                    String.valueOf(item.getSubtotal())
                            )
                    );

            subtotalCell.setHorizontalAlignment(
                    Element.ALIGN_CENTER
            );

            table.addCell(subtotalCell);
        }

        // Add table BEFORE total
        document.add(table);

        document.add(new Paragraph(" "));

        // =========================
        // TOTAL
        // =========================

        document.add(
                new Paragraph("--------------------------------")
        );

        document.add(new Paragraph(
                "Total Amount: " + order.getTotalAmount()
        ));

        document.add(new Paragraph(" "));

        // =========================
        // THANK YOU
        // =========================

        Paragraph thankYou =
                new Paragraph("Thank you for your purchase!");

        thankYou.setAlignment(
                Element.ALIGN_CENTER
        );

        document.add(thankYou);

        // Close PDF
        document.close();

        return outputStream.toByteArray();
    }
}