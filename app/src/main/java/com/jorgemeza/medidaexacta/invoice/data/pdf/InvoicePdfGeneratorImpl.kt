package com.jorgemeza.medidaexacta.invoice.data.pdf

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.core.content.FileProvider
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.core.ext.toPrice
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.invoice.domain.pdf.IInvoicePdfGenerator
import java.io.File
import java.io.IOException
import java.time.LocalDate

class InvoicePdfGeneratorImpl(
    private val context: Context
) : IInvoicePdfGenerator {
    override suspend fun pdfGenerator(
        quotation: QuotationModel,
        invoiceNumber: String,
        client: ClientModel,
        detail: List<DetailModel>
    ) {
        try {

            var subtotal : Double = 0.0
            var iva : Double = 0.0
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val filePath = File(dir, "factura_$invoiceNumber.pdf")

            val pdfWriter = PdfWriter(filePath)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

//            val bitmap = BitmapFactory.decodeResource(context.resources,context.resources.getIdentifier("ic_launcher_background_png", "drawable", context.packageName))
//            val stream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val imageBytes = stream.toByteArray()
//
//            val imagen = Image(
//                ImageDataFactory.create(imageBytes)
//            )
//
//            imagen.setWidth(200f)
//            imagen.setHeight(200f)
//            imagen.setFixedPosition(20f, 750f) // Coordenadas (x, y)
//
//            document.add(imagen)

            val title = Paragraph("Factura Nº${invoiceNumber}").setTextAlignment(
                TextAlignment.CENTER
            ).setFontSize(20f).setBold()
            document.add(title)

            val legalRepresentative = Paragraph("Cristian Camilo Vanegas Beltrán").setTextAlignment(
                TextAlignment.LEFT
            ).setFontSize(20f).setBold()
            document.add(legalRepresentative)

            val LegalRepresentativeInfo = Paragraph(
            """
                ${LocalDate.now()} 
                Calle Bario 17-6a
                Madrid - 28021
                Cif: Y9754076C
                """.trimIndent()
            ).setFontSize(12f).setTextAlignment(
                TextAlignment.LEFT
            )
            document.add(LegalRepresentativeInfo)

            var table = Table(floatArrayOf(1f))
                .useAllAvailableWidth().setMarginTop(20f)
            var cell =
                Cell().add(Paragraph("INSTALACION Y SUMINISTRO")).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER).setBold()
            table.addHeaderCell(cell)
            document.add(table)

            val clientInfo = Paragraph(
                """
        CLIENTE: ${client.name}
        DIRECCIÓN: ${client.address}
        CP: ${client.postalCode}
        TELEFONO: ${client.phone}
        CIF: ${client.cif}
        """.trimIndent()
            ).setMarginTop(10f).setFontSize(12f)
            document.add(clientInfo)

            table = Table(floatArrayOf(3f, 1f, 1f, 1f))
                .useAllAvailableWidth().setMarginTop(20f)

            val headers = listOf("CONCEPTO", "CANTIDAD", "P. UNITARIO", "TOTAL")
            for (header in headers) {
                val cell =
                    Cell().add(Paragraph(header)).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER).setBold()
                table.addHeaderCell(cell)
            }

            detail.forEach {
                val value = it.price.toDouble() * it.amount.toInt()
                var cell =
                    Cell().add(Paragraph(it.product)).setTextAlignment(TextAlignment.LEFT)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5f)
                table.addCell(cell)

                cell = Cell().add(Paragraph(it.amount)).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5f)
                table.addCell(cell)

                cell = Cell().add(Paragraph(it.price.toPrice())).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5f)
                table.addCell(cell)

                cell = Cell().add(
                    Paragraph(
                        value.toString().toPrice()
                    )
                ).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5f)
                table.addCell(cell)

                subtotal += value
                iva += subtotal * 0.21
            }

            document.add(table)

            val totals = Table(floatArrayOf(3f, 1f)).useAllAvailableWidth().setMarginTop(20f)

            totals.addCell(Cell().add(Paragraph("SUBTOTAL")).setBorder(Border.NO_BORDER))
            totals.addCell(
                Cell().add(Paragraph(subtotal.toString().toPrice())).setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
            )

            totals.addCell(Cell().add(Paragraph("IVA (21%)")).setBorder(Border.NO_BORDER))
            totals.addCell(
                Cell().add(Paragraph(iva.toString().toPrice())).setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
            )

            totals.addCell(Cell().add(Paragraph("TOTAL")).setBold().setBorder(Border.NO_BORDER))
            totals.addCell(
                Cell().add(Paragraph((subtotal + iva).toString().toPrice())).setBold()
                    .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)
            )

            document.add(totals)

            val paymentInfo = Paragraph(
                """
                FORMA PAGO:
                Transferencia bancaria al número de cuenta: ES3400493125702414307535
                """.trimIndent()
            ).setMarginTop(10f).setFontSize(12f).setBold()

            document.add(paymentInfo)

            document.close()

            println("PDF creado exitosamente en: ${filePath.absolutePath}")

            val uri =
                FileProvider.getUriForFile(context, "${context.packageName}.provider", filePath)

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                println("No se encontró una aplicación para abrir el PDF.")

            }
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error al crear el PDF: ${e.message}")
        }
    }
}