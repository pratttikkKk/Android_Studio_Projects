package com.example.simple_grocery_app


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simple_grocery_app.ui.theme.Simple_Grocery_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cricketers()
            }
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Cricketers(){
   val CricketrsList= mapOf(
       "Indian" to listOf("rohit sharma","virat kohli","msd","hardik","surya","jassi","shami","yuvraj","sachin"),
       "Mumbai Indians" to listOf("rohit sharma","pollard","malinga","hardik","surya","jassi","yuvraj","sachin","macclenghan","rickt ponting"),
       "RCB" to listOf("virat kohli","rohit sharma","pollard","malinga","hardik","surya","jassi","yuvraj","sachin","macclenghan","rickt ponting")
    )

   LazyColumn {
       CricketrsList.forEach{
           (Teams,Players)->
           stickyHeader {
               MyTeam(title=Teams)
           }
           items(Players) {item->
               Myplayer(itemtitle=item)

           }
       }
   }

}
@Composable
fun MyTeam(title:String){
    Text(text=title,
        fontSize =64.sp,
        fontWeight = FontWeight.Bold,
        //fontStyle= Fonttyle. ,
                modifier=Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(16.dp)
        )
}
@Composable
fun  Myplayer(itemtitle:String){
    Card(modifier = Modifier.padding(8.dp)) {
        Text(
            text = itemtitle,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
                .padding(16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Simple_Grocery_AppTheme {
        Cricketers()
    }
}