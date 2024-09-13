package com.example.jetpack_uitask.ui.component.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_uitask.R

@Composable
@Preview(showBackground = true)
fun AddRemoveButton(
    modifier: Modifier = Modifier,
    onAdd: () -> Unit = {},
    onRemove: () -> Unit = {},
    count: State<Int> = mutableIntStateOf(0)
) {
    Row {
        Box(
            modifier = modifier
                .border(
                    width = 2.dp,
                    color = Color.Cyan,
                    shape = RoundedCornerShape(topStartPercent = 100, bottomStartPercent = 100)
                )
                .padding(5.dp)
                .height(25.dp)
                .clickable { onRemove() }
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_remove_24),
                contentDescription = "",
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
        Box(
            modifier = modifier
                .border(width = 2.dp, color = Color.Cyan)
                .padding(5.dp)
                .width(25.dp)
                .height(25.dp)
        ) {
            Text(text = count.value.toString(), modifier = Modifier.align(Alignment.Center))
        }
        Box(
            modifier = modifier
                .border(
                    width = 2.dp,
                    color = Color.Cyan,
                    shape = RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)
                )
                .padding(5.dp)
                .height(25.dp)
                .clickable {
                    onAdd()
                }
        ) {

            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "",
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String = "Customize",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Cyan,
                shape = RoundedCornerShape(100)
            )
            .padding(horizontal = 18.dp, vertical = 5.dp)
            .height(25.dp)
            .clickable { onClick() }
    ) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}