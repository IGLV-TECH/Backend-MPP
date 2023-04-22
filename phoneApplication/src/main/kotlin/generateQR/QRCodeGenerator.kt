// package generateQR
// import com.google.zxing.BarcodeFormat
// import com.google.zxing.EncodeHintType
// import com.google.zxing.qrcode.QRCodeWriter
// import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
// import java.awt.Color
// import java.util.EnumMap
// import android.graphics.Bitmap

// class QRCodeGenerator(dataType: DataType) {
//     var dataType: DataType = dataType
//         get(){
//             return field
//         }
//         set(value){
//             field = value
//         }
//     fun generateQRCode(dataType: DataType, width: Int, height: Int): Bitmap {
//         val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
//         hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
//         hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
//         val writer = QRCodeWriter()
//         val bitMatrix = writer.encode(dataType.toString(), BarcodeFormat.QR_CODE, width, height, hints)
//         val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//         for (x in 0 until width) {
//             for (y in 0 until height) {
//                 bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//             }
//         }
//         return bitmap
//     }
// }