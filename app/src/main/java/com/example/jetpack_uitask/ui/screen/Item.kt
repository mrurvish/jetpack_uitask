package com.example.jetpack_uitask.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_uitask.R
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.datamodels.Item
import com.example.jetpack_uitask.ui.component.AppBottomSheetScaffold
import com.example.jetpack_uitask.ui.component.common.AddRemoveButton
import com.example.jetpack_uitask.ui.component.common.RoundedButton
import com.example.jetpack_uitask.ui.viewmodel.ItemViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Item(navigateToModifier: () -> Unit = {}, navigateToCheckout: () -> Unit = {}) {
    val viewModel = hiltViewModel<ItemViewModel>()
    val viewState by viewModel.consumableState().collectAsState()
    viewModel.checkCart()
    val stat = remember { derivedStateOf { viewState.itemCount } }
    val sheetScaffoldState =
        rememberBottomSheetScaffoldState(rememberStandardBottomSheetState(skipHiddenState = false))
    val coroutine = rememberCoroutineScope()
    val discription = remember { mutableStateOf("") }
    AppBottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            BottomSheetContent(onCloseClick = {
                coroutine.launch {
                    sheetScaffoldState.bottomSheetState.hide()
                }
            }, onRepeatClick = {
                val item = Cart.itemslist.get(Cart.itemslist.size - 1)
                item.qty += 1
                Cart.itemslist.set(Cart.itemslist.size - 1, item)
                viewModel.checkCart()
                coroutine.launch {
                    sheetScaffoldState.bottomSheetState.hide()
                }
            }, onCustomizeClick = {
                navigateToModifier()
                coroutine.launch {
                    sheetScaffoldState.bottomSheetState.hide()
                }
            },
                text = discription
            )
        },
        scaffoldState = sheetScaffoldState,
        sheetSwipeEnabled = false,
    ) {


        ItemContent(
            onAddItemClick = {
                navigateToModifier()
            },
            isAddRemoveButton = { viewState.isAddRemoveButton },
            onAddClick = {
                var text = ""
                Cart.itemslist[Cart.itemslist.size - 1].selscted.forEach {
                    if (text.isEmpty()) {
                        text += it.name?.get(0)
                    } else {
                        text += ", " + it.name?.get(0)
                    }
                }
                discription.value = text
                coroutine.launch {
                    sheetScaffoldState.bottomSheetState.expand()
                }
            },
            onRemoveClick = {
                if (Cart.itemslist.get(Cart.itemslist.size - 1).qty > 1) {
                    val item = Cart.itemslist.get(Cart.itemslist.size - 1)
                    item.qty -= 1
                    Cart.itemslist.set(Cart.itemslist.size - 1, item)
                } else {
                    Cart.itemslist.removeAt(Cart.itemslist.size - 1)
                }
                viewModel.checkCart()
            },
            stat = stat,
            navigateToCheckout = { navigateToCheckout() },
            totalCartItem = { viewState.totalCartItem }

        )


    }

}

@Preview(showBackground = true)
@Composable
fun ItemContent(
    onAddItemClick: () -> Unit = {},
    isAddRemoveButton: () -> Boolean = { false },
    onAddClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    stat: State<Int> = mutableStateOf(0),
    navigateToCheckout: () -> Unit = {},
    totalCartItem: () -> Int = { 0 }
) {
    val scrollstate = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollstate)
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(R.drawable.home),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            StoreDescription()

            Cart.item?.let {
                MenuItem(
                    item = it,
                    onAddItemClick = { onAddItemClick() },
                    isAddRemoveButton = { isAddRemoveButton() },
                    onAddClick = { onAddClick() },
                    onRemoveClick = { onRemoveClick() },
                    stat = stat
                )
            }

        }
        TopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .systemBarsPadding()
        )
        if (totalCartItem() > 0) {
            Button(
                onClick = { navigateToCheckout() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 15.dp),
                colors = ButtonColors(
                    containerColor = Color.Cyan,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    BadgedBox(
                        badge = {

                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(totalCartItem().toString())
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_shopping_bag_24),
                            contentDescription = "",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "View cart", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    item: Item,
    onAddItemClick: () -> Unit = {},
    isAddRemoveButton: () -> Boolean = { false },
    onAddClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    stat: State<Int> = mutableStateOf(0)
) {

    var price: Int = 0
    item.modifierGroups?.forEach { specification ->
        specification.list?.forEach {
            if (it.isDefaultSelected == true && it.price != null) {
                price += it.price
            }
        }
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name?.get(0) ?: "",
                modifier = Modifier.weight(1f),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (isAddRemoveButton() && stat.value > 0) {
                AddRemoveButton(
                    onAdd = { onAddClick() },
                    onRemove = { onRemoveClick() },
                    count = stat
                )
            } else {
                RoundedButton(onClick = { onAddItemClick() })
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = price.toString(),
                modifier = Modifier.weight(1f),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter = painterResource(R.drawable.download),
                contentDescription = "",
                modifier = Modifier
                    .size(70.dp)
                    .clip(
                        RoundedCornerShape(percent = 12)
                    )
            )
        }
        HorizontalDivider(modifier = Modifier.padding(5.dp))
    }
}


@Composable
fun StoreDescription(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text("Cleaner", fontSize = 34.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(Color.Cyan)
                    .padding(horizontal = 5.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "")

                Text("4.5", modifier = Modifier.padding(5.dp))
            }
            VerticalDivider(
                modifier = Modifier
                    .height(30.dp)
                    .padding(start = 5.dp)
            )
            Text(
                "30 - 45 mins",
                modifier = Modifier.padding(horizontal = 10.dp),
                color = Color.LightGray
            )
            VerticalDivider(modifier = Modifier.height(30.dp))
            Text("$", modifier = Modifier.padding(horizontal = 10.dp), color = Color.LightGray)

        }
        Spacer(modifier = Modifier.height(15.dp))
        Text("All items are inclusive of text", color = Color.Green)
        Text("Home cleaning", fontSize = 28.sp, fontWeight = FontWeight.Normal)
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = "asd",
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_thumb_up_24),
                    contentDescription = "asd",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_info_24),
                    contentDescription = "asd",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContent(
    onCustomizeClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    text: MutableState<String> = mutableStateOf("")
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .systemBarsPadding()
            .navigationBarsPadding()
            .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(5.dp)
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
            Icon(imageVector = Icons.Filled.Close,
                contentDescription = "",
                tint = Color.Cyan,
                modifier = Modifier.clickable {
                    onCloseClick()
                })
        }

        Text(
            text.value, modifier = Modifier, color = Color.LightGray
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onCustomizeClick() },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                colors = ButtonColors(
                    containerColor = Color.Cyan,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(text = "Customize")
            }
            Button(
                onClick = { onRepeatClick() },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                colors = ButtonColors(
                    containerColor = Color.Cyan,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(text = "Repeat")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BadgeInteractiveExample() {
    var itemCount by remember { mutableStateOf(2) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BadgedBox(
            badge = {
                if (itemCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text("$itemCount")
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Shopping cart",
            )
        }
        Button(onClick = { itemCount++ }) {
            Text("Add item")
        }
    }
}

/*
Column(horizontalAlignment = Alignment.CenterHorizontally) {
    OutlinedButton(onClick = {}, contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)) {
        Text("Add", fontSize = 12.sp)
    }
    Image(painter = painterResource(R.drawable.download), contentDescription = "", modifier = Modifier.size(70.dp).clip(
        RoundedCornerShape(percent = 12)
    ))

}*/
