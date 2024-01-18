package util.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun getTypography(): Typography {

    val regular = FontFamily(
        font(
            "Nunito", "nunito_regular.ttf", FontWeight.Normal, FontStyle.Normal
        )
    )

    val semiBold = FontFamily(
        font(
            "Nunito", "nunito_semibold.ttf", FontWeight.Normal, FontStyle.Normal

        )
    )
    val bold = FontFamily(
        font(
            "Nunito", "nunito_bold.ttf", FontWeight.Normal, FontStyle.Normal
        )
    )
   return Typography(
       h1 = TextStyle(
           fontFamily = bold,
           fontWeight = FontWeight.Bold,
           fontSize = 52.sp,
       ),
       h2 = TextStyle(fontFamily = bold, fontWeight = FontWeight.Bold, fontSize = 24.sp),
       h3 = TextStyle(
           fontFamily = bold,
           fontWeight = FontWeight.Bold,
           fontSize = 18.sp,
       ),
       h4 = TextStyle(
           fontFamily = bold,
           fontWeight = FontWeight.Bold,
           fontSize = 16.sp,
       ),
       h5 = TextStyle(fontFamily = bold, fontWeight = FontWeight.Bold, fontSize = 14.sp),
       h6 = TextStyle(
           fontFamily = semiBold,
           fontWeight = FontWeight.SemiBold,
           fontSize = 12.sp,
       ),
       subtitle1 = TextStyle(
           fontFamily = semiBold,
           fontWeight = FontWeight.SemiBold,
           fontSize = 16.sp,
       ),
       subtitle2 = TextStyle(
           fontFamily = regular,
           fontWeight = FontWeight.Normal,
           fontSize = 14.sp,
       ),
       body1 = TextStyle(
           fontFamily = regular, fontWeight = FontWeight.Normal, fontSize = 14.sp
       ),
       body2 = TextStyle(fontFamily = regular, fontSize = 10.sp),
       button = TextStyle(
           fontFamily = regular,
           fontWeight = FontWeight.Normal,
           fontSize = 15.sp,
           color = primary
       ),
       caption = TextStyle(
           fontFamily = regular, fontWeight = FontWeight.Normal, fontSize = 8.sp
       ),
       overline = TextStyle(
           fontFamily = regular, fontWeight = FontWeight.Normal, fontSize = 12.sp
       )
   )
}

@Composable
expect fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font