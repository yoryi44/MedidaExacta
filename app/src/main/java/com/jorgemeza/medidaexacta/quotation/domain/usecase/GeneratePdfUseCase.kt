package com.jorgemeza.medidaexacta.quotation.domain.usecase

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.core.ext.toPrice
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class GeneratePdfUseCase(
    private val context: Context
) {
    suspend operator fun invoke(
        client: ClientModel,
        quotation: QuotationModel,
        detail: List<DetailModel>
    ) = withContext(Dispatchers.Default) {
        try {

            var subtotal : Double = 0.0
            var iva : Double = 0.0
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val filePath = File(dir, "example.pdf")

            val pdfWriter = PdfWriter(filePath)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            val title = Paragraph("PRESUPUESTO Nº${quotation.quotationNumber}").setTextAlignment(
                TextAlignment.CENTER
            ).setFontSize(20f).setBold()
            document.add(title)

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
        FECHA: ${quotation.date}
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
                val value = it.price.toDouble() * it.amount.toInt();
                var cell =
                    Cell().add(Paragraph(it.product)).setTextAlignment(TextAlignment.LEFT)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5f)
                table.addCell(cell)

                cell = Cell().add(Paragraph(it.amount)).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5f)
                table.addCell(cell)

                cell = Cell().add(Paragraph(it.price)).setTextAlignment(TextAlignment.LEFT)
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

            val note =
                Paragraph("Forma de pago: 50% al iniciar, 50% al terminar").setFontSize(10f)
                    .setMarginTop(20f)
            document.add(note)

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