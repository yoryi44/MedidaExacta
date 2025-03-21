package com.jorgemeza.medidaexacta.quotation.data.pdf

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
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
import com.jorgemeza.medidaexacta.quotation.domain.pdf.IQuotationPdfGenerator
import com.jorgemeza.medidaexacta.ui.theme.Danger
import java.io.File
import java.io.IOException

class QuotationPdfGeneratorImpl(
    private val context: Context
) : IQuotationPdfGenerator {

    override suspend fun pdfGenerator(
        quotation: QuotationModel,
        client: ClientModel,
        detail: List<DetailModel>
    ) {
        try {

            var subtotal : Double = 0.0
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val filePath = File(dir, "example.pdf")

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

            val title = Paragraph("Presupuesto Nº${quotation.quotationNumber}").setTextAlignment(
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
            }

            document.add(table)

            val totals = Table(floatArrayOf(3f, 1f)).useAllAvailableWidth().setMarginTop(20f)

            totals.addCell(Cell().add(Paragraph("TOTAL")).setBorder(Border.NO_BORDER))
            totals.addCell(
                Cell().add(Paragraph(subtotal.toString().toPrice())).setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
            )

            document.add(totals)

            val note =
                Paragraph("Forma de pago: 50% al iniciar, 50% al terminar. Este valor no incluye IVA" +
                        "").setFontSize(20f)
                    .setFontColor(ColorConstants.RED)
                    .setMarginTop(20f)
                    .setBold()
            document.add(note)

            if(!quotation.observation.isNullOrBlank())
            {
                val observations =
                    Paragraph("NOTE: ${quotation.observation}").setFontSize(10f)
                        .setMarginTop(20f)
                document.add(observations)
            }

            val clausulas = Paragraph("Una vez recibido el pago o justificante de ingreso o " +
                    "transferencia del 50% del total, se tramitará la cita para la instalación en un " +
                    "tiempo no superior a 72hrs, siempre y cuando contando con la disponibilidad y preferencias " +
                    "del cliente. - Este contrato tiene validez junto al justificante bancario de pago. - " +
                    "El envío de la factura se realizará una vez recibido el pago final.- " +
                    "El presente recibo tiene una validez de 7 días desde la fecha indicada en la parte superior, " +
                    "salvo que se especifique una fecha de validez diferente en el encabezado del documento.- No se " +
                    "incluye la gestión y pago de los honorarios facultativos, tasas municipales, permiso de obras.- " +
                    "La garantía se inicia en los términos indicados desde el momento de la finalización de la obra y " +
                    "pago total de la misma.- El incumplimiento de pagos significará la suspensión de la garantía  El " +
                    "contratante nos suministrará sin cargo alguno un caudal de agua continuo y luz, en caso de no existir " +
                    "alguno de estos elementos se efectuará una valoración por día de alquiler y será cargada al presupuesto " +
                    "inicial.- Cualquier trabajo que no esté contemplado en este presupuesto se efectuará una valoración que " +
                    "será aceptada o denegada por el contratante.")
                    .setMarginTop(10f).setFontSize(9f).setTextAlignment(TextAlignment.JUSTIFIED).setBold()

            document.add(clausulas)

            val bitmap = BitmapFactory.decodeResource(context.resources,context.resources.getIdentifier("social", "drawable", context.packageName))
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageBytes = stream.toByteArray()

            val imagen = Image(
                ImageDataFactory.create(imageBytes)
            )

            imagen.setWidth(600f)
            imagen.setHeight(50f)
            imagen.setFixedPosition(15f, 0f) // Coordenadas (x, y)

            document.add(imagen)

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

    override suspend fun pdfGeneratorPayment(
        quotation: QuotationModel,
        client: ClientModel,
        detail: List<DetailModel>
    ) {
        try {

            var subtotal : Double = 0.0
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val filePath = File(dir, "example.pdf")

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

            val title = Paragraph("Nota Cobro Nº${quotation.quotationNumber}").setTextAlignment(
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
            }

            document.add(table)

            val totals = Table(floatArrayOf(3f, 1f)).useAllAvailableWidth().setMarginTop(20f)

            totals.addCell(Cell().add(Paragraph("TOTAL")).setBorder(Border.NO_BORDER))
            totals.addCell(
                Cell().add(Paragraph(subtotal.toString().toPrice())).setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
            )

            document.add(totals)

            val note =
                Paragraph("Este valor no incluye IVA" +
                        "").setFontSize(20f)
                    .setFontColor(ColorConstants.RED)
                    .setMarginTop(20f)
                    .setBold()
            document.add(note)

            if(!quotation.observation.isNullOrBlank())
            {
                val observations =
                    Paragraph("NOTE: ${quotation.observation}").setFontSize(10f)
                        .setMarginTop(20f)
                document.add(observations)
            }

            val bitmap = BitmapFactory.decodeResource(context.resources,context.resources.getIdentifier("social", "drawable", context.packageName))
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageBytes = stream.toByteArray()

            val imagen = Image(
                ImageDataFactory.create(imageBytes)
            )

            imagen.setWidth(600f)
            imagen.setHeight(50f)
            imagen.setFixedPosition(15f, 0f) // Coordenadas (x, y)

            document.add(imagen)

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