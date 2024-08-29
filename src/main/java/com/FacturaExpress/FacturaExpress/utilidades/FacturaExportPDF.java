package com.FacturaExpress.FacturaExpress.utilidades;

import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

public class FacturaExportPDF {
    private Factura factura;

    public FacturaExportPDF(Factura factura) {
        this.factura = factura;
    }

    private void setCabeceraTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        Color colorCabecera = new Color(240, 240, 240); // Gris claro
        celda.setBackgroundColor(colorCabecera);
        celda.setPadding(10);

        Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteCabecera.setColor(Color.BLACK);
        fuenteCabecera.setSize(12);

        // Encabezados de columna sin "ID"
        String[] encabezados = {"Nombre Cliente", "Fecha Emisión", "Fecha Vencimiento", "Consumo Kwh", "Número de Medidor", "Total a Pagar"};
        for (String encabezado : encabezados) {
            celda.setPhrase(new Phrase(encabezado, fuenteCabecera));
            tabla.addCell(celda);
        }
    }

    private void setDatosTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setPadding(8);

        // Datos de la factura sin ID
        tabla.addCell(factura.getCliente().getNombre());
        tabla.addCell(factura.getFechaEmision().toString());
        tabla.addCell(factura.getFechaVencimiento().toString());
        tabla.addCell(String.valueOf(factura.getConsumoKwh()));
        tabla.addCell(factura.getNumeroMedidor());
        tabla.addCell(String.format("%.2f", factura.getTotalPagar()));
    }

    public void Exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        // Encabezado con logo y nombre centrados
        PdfPTable encabezadoTabla = new PdfPTable(2);
        encabezadoTabla.setWidthPercentage(100);
        encabezadoTabla.setSpacingBefore(15);
        encabezadoTabla.setWidths(new float[]{1f, 4f});

        // Celda para el logo
        PdfPCell logoCelda = new PdfPCell();
        try (InputStream inputStream = getClass().getResourceAsStream("/static/dist/img/logo.jpg")) {
            if (inputStream != null) {
                Image logo = Image.getInstance(ImageIO.read(inputStream), null);
                logo.scaleToFit(100, 50); // Ajustar tamaño según necesidad
                logo.setAlignment(Image.ALIGN_CENTER);
                logoCelda.addElement(logo);
            }
        }
        logoCelda.setBorder(Rectangle.NO_BORDER);
        logoCelda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        encabezadoTabla.addCell(logoCelda);

        // Celda vacía para el espacio
        PdfPCell espacioCelda = new PdfPCell();
        espacioCelda.setBorder(Rectangle.NO_BORDER);
        encabezadoTabla.addCell(espacioCelda);

        documento.add(encabezadoTabla);

        // Espaciado
        documento.add(new Paragraph(" "));

        // Título en el cuerpo de la página
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteTitulo.setSize(24);
        fuenteTitulo.setColor(new Color(0, 51, 102)); // Azul oscuro
        Paragraph titulo = new Paragraph("Factura Express", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        // Espaciado
        documento.add(new Paragraph(" "));

        // Título detallado
        Font fuenteTituloDetallado = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteTituloDetallado.setSize(18);
        fuenteTituloDetallado.setColor(new Color(0, 51, 102)); // Azul oscuro
        Paragraph tituloDetallado = new Paragraph("Factura Detallada", fuenteTituloDetallado);
        tituloDetallado.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(tituloDetallado);

        // Espaciado
        documento.add(new Paragraph(" "));

        // Tabla de datos
        PdfPTable tabla = new PdfPTable(6); // Cambiado a 6 columnas
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{2f, 2f, 2f, 2f, 2f, 2f}); // Ajustado a 6 columnas

        setCabeceraTabla(tabla);
        setDatosTabla(tabla);
        documento.add(tabla);

        // Espaciado
        documento.add(new Paragraph(" "));

        // Pie de página
        Font fuentePie = FontFactory.getFont(FontFactory.HELVETICA);
        fuentePie.setSize(10);
        fuentePie.setColor(new Color(0, 51, 102)); // Azul oscuro
        Paragraph pie = new Paragraph("Gracias por su compra. Para más información, visite nuestro sitio web: www.facturaexpress.com", fuentePie);
        pie.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(pie);

        documento.close();
    }
}
