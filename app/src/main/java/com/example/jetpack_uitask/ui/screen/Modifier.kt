package com.example.jetpack_uitask.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_uitask.R
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.datamodels.CartItem
import com.example.jetpack_uitask.datamodels.ModifierGroup
import com.example.jetpack_uitask.datamodels.ModifierItem
import com.example.jetpack_uitask.ui.component.AppTopBar
import com.example.jetpack_uitask.ui.component.common.AddRemoveButton
import com.example.jetpack_uitask.ui.stat.ModifierUiEvents
import com.example.jetpack_uitask.ui.viewmodel.ModifierViewModel

@Composable
fun Modifier(navigateToCheckout: () -> Unit = {},onBackPressed: () -> Unit = {}) {
    val viewModel = hiltViewModel<ModifierViewModel>()
    viewModel.getRequiredModifierGroup()
    val viewState by viewModel.consumableState().collectAsState()
    val requiredModifiers = remember { viewModel.getRequiredModifierGroup() }
    val associateModifiers = remember { derivedStateOf { viewState.associateModifierGroup } }
    Cart.cartItem = CartItem()
    // viewState.totel = viewState.modifierItemList.sumOf { it.price ?: 0 }
    //Log.e("price", viewState.totel.toString())
    // Log.e("price",viewState.totel.toString())


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding()
            .statusBarsPadding(), topBar = {
            AppTopBar(onBackPressed = {onBackPressed()}, title = Cart.item?.name?.get(0) ?: "")
        }, bottomBar = {
            BottomBar(price = { viewState.totel }, addToCart = {
                Cart.cartItem.selscted = viewState.modifierItemList
                Cart.cartItem.price = viewState.totel
                Cart.itemslist?.add(Cart.cartItem)
                navigateToCheckout()

            })

        }) {
        ModifierGroup(
            modifier = Modifier.padding(it),
            requiredModifier = requiredModifiers,
            associateModifier = { associateModifiers.value },
            onItemChangedCheckbox = { item, isselected, isrequired, isRemove ->
                viewModel.onEvent(
                    ModifierUiEvents.OnCheckChange(
                        item,
                        isselected,
                        isrequired,
                        isRemove
                    )
                )
            },
            onItemChanged = { item, isrequired ->
                viewModel.onEvent(ModifierUiEvents.OnRadioChange(item, isrequired))
            })
    }
}


@Composable
fun ModifierGroup(
    modifier: Modifier = Modifier,
    requiredModifier: List<ModifierGroup> = listOf(),
    associateModifier: () -> List<ModifierGroup> = { listOf() },
    onItemChangedCheckbox: (ModifierItem, Boolean, Boolean, Boolean) -> Unit = { modifierItem: ModifierItem, b: Boolean, r: Boolean, isRemove: Boolean -> },
    onItemChanged: (ModifierItem, Boolean) -> Unit = { item: ModifierItem, r: Boolean -> }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Text(Cart.item?.name?.get(0) ?: "", fontSize = 34.sp, fontWeight = FontWeight.SemiBold)
        requiredModifier.forEach {
            if (it.type == 1) {
                RadioButtonList(it) { item ->
                    Log.i("modifier", it.name.toString())
                    onItemChanged(item, true)
                }
            } else {
                CheckBoxList(it) { item, ischecked, isRemove ->
                    onItemChangedCheckbox(item, ischecked, true, isRemove)

                }
            }
            associateModifier().forEach {
                if (it.type == 1) {
                    RadioButtonList(it) { item ->
                        Log.i("modifier", it.name.toString())
                        onItemChanged(item, false)
                    }
                } else {
                    CheckBoxList(it) { item, ischecked, isRemove ->
                        onItemChangedCheckbox(item, ischecked, false, isRemove)

                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun RadioButtonList(
    modifierGroup: ModifierGroup = ModifierGroup(),
    onItemChanged: (ModifierItem) -> Unit = {}
) {

    val options: List<ModifierItem> = modifierGroup.list ?: listOf()
    var selectedOption by remember {
        mutableStateOf(
            if (options.size >= 1 && modifierGroup.isRequired == true) {
                options[0]
            } else {
                ModifierItem()
            }
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifierGroup.name?.get(0) ?: "",
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 35.sp
        )
        Text(
            "30 - 45 mins",
            modifier = Modifier,
            color = Color.LightGray
        )
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (option.id == selectedOption.id),
                        onClick = {
                            selectedOption = option
                            onItemChanged(selectedOption)
                        }, // Update selected option on click
                        role = Role.Checkbox // Set the role as RadioButton for accessibility
                    )
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (option.id == selectedOption.id),
                    onClick = null // null because it's handled by the parent `.selectable`
                )

                Text(
                    text = option.name?.get(0) ?: "",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )
                if (option.price != 0) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    ) {
                        if (modifierGroup.userCanAddSpecificationQuantity == true) {
                            AddRemoveButton()
                        }
                        Text(
                            text = option.price.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp), maxLines = 1
                        )
                    }
                }
            }
        }
    }
    if (selectedOption.id != null) onItemChanged(selectedOption)
}

@Preview(showBackground = true)
@Composable
fun CheckBoxList(
    modifierGroup: ModifierGroup = ModifierGroup(),
    onItemChangedCheckbox: (ModifierItem, Boolean, Boolean) -> Unit = { modifierItem: ModifierItem, ischecked: Boolean, isRemove: Boolean -> }
) {
    val items: List<ModifierItem> = modifierGroup.list ?: listOf()
    // Maintain a list of checked states for each item
    val checkedStates = remember { mutableStateListOf<ModifierItem>() }



    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifierGroup.name?.get(0) ?: "",
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 35.sp
        )
        Text(
            "30 - 45 mins",
            modifier = Modifier,
            color = Color.LightGray
        )
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = checkedStates.contains(item),
                        onValueChange = {
                            if (checkedStates.contains(item)) {
                                checkedStates.remove(item)
                            } else {
                                handleCheckChange(
                                    maxSelection = 2,
                                    checkedStates = checkedStates,
                                    item = item
                                ) {
                                    onItemChangedCheckbox(items[it], false, true)
                                }
                            }

                            onItemChangedCheckbox(item, checkedStates.contains(item), true)
                        },
                        role = Role.Checkbox
                    )
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Checkbox(
                    checked = checkedStates.contains(item),
                    onCheckedChange = { },// null recommended for accessibility with screen readers
                )
                Text(
                    text = item.name?.get(0).toString(),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    maxLines = 2
                )
                if (item.price != 0) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    ) {
                        var stat = remember { mutableStateOf(1) }
                        if (modifierGroup.userCanAddSpecificationQuantity == true && checkedStates.contains(
                                item
                            )
                        ) {
                            AddRemoveButton(
                                onAdd = {
                                    stat.value++
                                    onItemChangedCheckbox(item, true, false)
                                },
                                onRemove = {
                                    if (stat.value > 1) {
                                        stat.value--
                                        onItemChangedCheckbox(item, false, false)
                                    }
                                },
                                count = stat
                            )
                        } else {
                            stat.value = 1
                        }

                        Text(
                            text = (item.price?.times(stat.value)).toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp), maxLines = 1
                        )
                    }
                }
            }
        }
    }
    Log.d("tag", "asdas")
}

@Preview(showBackground = true)
@Composable
fun BottomBar(
    price: () -> Int = { 0 },
    addToCart: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var count = remember { mutableStateOf(1) }
        AddRemoveButton(
            onAdd = {
                count.value++
                Cart.cartItem.qty = count.value
            },
            onRemove = {
                count.value--
                Cart.cartItem.qty = count.value
            },
            count = count
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = {
                addToCart()
            },
            modifier = Modifier.weight(1f),
            colors = ButtonColors(
                containerColor = Color.Cyan,
                contentColor = Color.Black,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(text = "Add to Cart - " + price() * count.value)
        }
    }
}

// Handle checkbox selection logic with a max range
private fun handleCheckChange(

    checkedStates: MutableList<ModifierItem>,
    maxSelection: Int,
    item: ModifierItem,
    onremoveExtra: (Int) -> Unit = {}
) {
    val selectedCount = checkedStates.size

    if (checkedStates.contains(item)) {
        // Uncheck if already checked
        checkedStates.remove(item)
    } else {
        if (selectedCount < maxSelection) {
            // Check if below the max limit
            checkedStates.add(item)
        } else {
            // If max selection is reached, uncheck the first checked item and check the current one
            checkedStates.removeAt(0) // Remove the first item
            checkedStates.add(item)
            onremoveExtra(0)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Tests() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = { },// null recommended for accessibility with screen readers
            modifier = Modifier
        )
        Text(
            text = "sdfsdfsdfaaaaaaaaaaaaa  aaaaa  safdsfsdfsdf",
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        ) {
            AddRemoveButton()
            Text(
                text = "$2332234234324234",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp), maxLines = 1
            )
        }
    }
}

