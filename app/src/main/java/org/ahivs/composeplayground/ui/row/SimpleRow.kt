package org.ahivs.composeplayground.ui.row

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import java.util.Collections.min

@Preview
@Composable
fun RowWithText() {
    val titleId = remember { "title" }
    val timeId = remember { "time" }

    Layout(
        content = {
            Text(
                text = "Title text is also growing now ",
                modifier = Modifier.layoutId(titleId)
            )
            Text(
                text = "Time text is growing large and will it overlap",
                modifier = Modifier.layoutId(timeId)
            )
        }
    ) { measurables, constraints ->
        val titleTextMeasurable: Measurable? = measurables.find { it.layoutId == titleId }
        val timeTextMeasurable: Measurable? = measurables.find { it.layoutId == timeId }

        var titleTextPlaceable: Placeable? = null
        var timeTextPlaceable: Placeable? = null

        val titleTextWidth = titleTextMeasurable?.measure(constraints.copy(maxWidth = Int.MAX_VALUE))?.also {
            titleTextPlaceable = it
        }?.width ?: 0

        val timeTextWidth = timeTextMeasurable?.measure(constraints.copy(maxWidth = Int.MAX_VALUE))?.also {
            timeTextPlaceable = it
        }?.width ?: 0

        val parentWidth = constraints.maxWidth
        val titleTextMaxWidth = (parentWidth * 0.6).toInt()
        val timeTextMaxWidth = (parentWidth * 0.4).toInt()

        val availableWidth = parentWidth - timeTextWidth
        val updatedTitleTextWidth = min(listOf(titleTextWidth, titleTextMaxWidth, availableWidth))
        val updatedTimeTextWidth = min(listOf(timeTextWidth, timeTextMaxWidth, parentWidth - updatedTitleTextWidth))

        layout(constraints.maxWidth, constraints.maxHeight) {
            titleTextPlaceable?.place(0, 0)
            timeTextPlaceable?.place(constraints.maxWidth - updatedTimeTextWidth, 0)
        }
    }
}

