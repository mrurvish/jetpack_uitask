package com.example.jetpack_uitask.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack_uitask.R
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.datamodels.Item
import com.example.jetpack_uitask.ui.navigation.NavigationGraph
import com.example.jetpack_uitask.ui.theme.Jetpack_uitaskTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStreamReader

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var item: Item? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        item = getItem()
        Cart.item = item
        Log.d("item", item.toString())
        setContent {
            Jetpack_uitaskTheme {
                NavigationGraph()
            }
        }
    }

    private fun getItem(): Item {
        val inputStream =
            resources.openRawResource(R.raw.item_data) // R.raw.data corresponds to data.json

        // Parse the JSON file
        val reader = InputStreamReader(inputStream)
        val itemdata: Item = Gson().fromJson(reader, Item::class.java)
        return itemdata
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Jetpack_uitaskTheme {
        Greeting("Android")
    }
}