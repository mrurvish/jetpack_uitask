package com.example.jetpack_uitask.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.datamodels.CartItem
import com.example.jetpack_uitask.ui.component.AppTopBar
import com.example.jetpack_uitask.ui.component.common.AddRemoveButton
import com.example.jetpack_uitask.ui.viewmodel.CheckoutViewModel

@Composable
fun Checkout(onBackPressed: () -> Unit = {}) {
    val viewModel = hiltViewModel<CheckoutViewModel>()
    val viewState by viewModel.consumableState().collectAsState()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            AppTopBar(
                onBackPressed = { onBackPressed() },
                title = "Cart"
            )
        }, bottomBar = {}
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (viewState.isUpdate || !viewState.isUpdate) {

                viewState.itemlist.forEachIndexed { index, it ->
                    CheckoutItem(
                        it,
                        onAddClick = {
                            viewModel.addClick(index)
                        },
                        onRemoveClick = {
                            viewModel.removeClick(index)
                        },
                        onCancelClick = {
                            viewModel.cancelClick(index)
                        }
                    )
                    Log.d("data",index.toString())
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CheckoutItem(
    cartItem: CartItem = CartItem(),
    onAddClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    val qty = remember { mutableStateOf(1) }
    qty.value = cartItem.qty
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Cart.item?.name?.get(0).toString(),
                modifier = Modifier,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "",
                tint = Color.Cyan,
                modifier = Modifier.clickable {
                    onCancelClick()
                })
        }
        var text = ""
        cartItem.selscted.forEach {
            if (text.isEmpty()) {
                text += it.name?.get(0)
            } else {
                text += ", " + it.name?.get(0)
            }
        }
        Text(
            text,
            modifier = Modifier,
            color = Color.LightGray
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AddRemoveButton(count = qty, onAdd = {
                onAddClick()
            }, onRemove = {
                onRemoveClick()
            })
            Text(
                text = (cartItem.price * qty.value).toString(),
                modifier = Modifier,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        HorizontalDivider()
    }
}