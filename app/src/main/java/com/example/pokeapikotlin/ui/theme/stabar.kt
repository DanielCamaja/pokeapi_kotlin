import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatBar(
    modifier: Modifier = Modifier,
    value: Int,
    maxValue: Int = 100
) {
    val percentage = (value.toFloat() / maxValue).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFB2F2BB)) // Verde muy claro
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(percentage)
                .background(Color(0xFF2F9E44)) // Verde oscuro
        )
    }
}
