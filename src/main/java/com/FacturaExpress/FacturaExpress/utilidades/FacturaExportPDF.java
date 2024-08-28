package com.FacturaExpress.FacturaExpress.utilidades;

import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FacturaExportPDF {
    private Factura factura;

    public FacturaExportPDF(Factura factura) {
        this.factura = factura;
    }

    private void setCabeceraTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        Color colorPersonalizado = new Color(100, 150, 200);
        celda.setBackgroundColor(colorPersonalizado);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.white);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Nombre Cliente", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Fecha Emisión", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Fecha Vencimiento", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Consumo Kwh", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Número de Medidor", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Total a Pagar", fuente));
        tabla.addCell(celda);
    }

    private void setDatosTabla(PdfPTable tabla) {
        // Añadir una fila con los datos de la factura
        tabla.addCell(String.valueOf(factura.getId()));
        tabla.addCell(factura.getCliente().getNombre()); // Mostrar el nombre del cliente
        tabla.addCell(factura.getFechaEmision().toString());
        tabla.addCell(factura.getFechaVencimiento().toString());
        tabla.addCell(String.valueOf(factura.getConsumoKwh()));
        tabla.addCell(factura.getNumeroMedidor());
        tabla.addCell(String.format("%.2f", factura.getTotalPagar())); // Formatear el total con 2 decimales
    }

    public void Exportar(HttpServletResponse response) throws IOException {
        // Crear un nuevo documento PDF con tamaño A4
        Document documento = new Document(com.lowagie.text.PageSize.A4);

        // Obtener una instancia de PdfWriter para escribir en el documento
        PdfWriter.getInstance(documento, response.getOutputStream());

        // Abrir el documento para agregar contenido
        documento.open();

        try (InputStream inputStream = getClass().getResourceAsStream("/static/dist/img/logo.png")) {
            if (inputStream != null) {
                Image imagen = Image.getInstance(ImageIO.read(inputStream), null);
                imagen.scaleToFit(100, 50);
                imagen.setAbsolutePosition(50, 750);
                documento.add(imagen);
            } else {
                System.out.println("No se pudo cargar la imagen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph(" "));

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLACK);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Factura Detallada", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f, 2f, 2f, 2f, 2f, 2f, 2f});

        setCabeceraTabla(tabla);
        setDatosTabla(tabla);

        documento.add(tabla);

        documento.close();
    }
}
