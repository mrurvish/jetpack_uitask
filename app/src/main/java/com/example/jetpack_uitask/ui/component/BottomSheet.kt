package com.example.jetpack_uitask.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheetScaffold(
    modifier: Modifier = Modifier,
    sheetContent: @Composable ColumnScope.() -> Unit,
    scaffoldState: BottomSheetScaffoldState,
    sheetSwipeEnabled: Boolean = false,
    content: @Composable () -> Unit,


    ) {
    BottomSheetScaffold(
        modifier = modifier.systemBarsPadding().navigationBarsPadding().statusBarsPadding(),
        scaffoldState = scaffoldState,
        sheetContent = {sheetContent()},
        sheetSwipeEnabled = sheetSwipeEnabled,
        sheetDragHandle = {},
        sheetPeekHeight = 0.dp,

    ) {
        content()
    }
}