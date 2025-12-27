package com.example.navigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.filledTonalShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.text.Placeholder
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationapp.navigation.NavGraph
import com.example.navigationapp.ui.theme.NavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            NavGraph(navController=navController)
        }}}








//            NavHost(
//                navController=navController,
//                startDestination = "first"
//                ){
//                composable(route="first"){Screeni(navController)}
//                composable(
//                    route="second/{username}/{age}",
//                    arguments=listOf(
//                        navArgument("username") {
//                            type= NavType.StringType
//                        },
//                        navArgument( "age" ) {
//                            type= NavType.StringType
//                        }
//
//                    )
//                )
//
//                {backStackEntry->
//                    Screeno(navController,
//                        backStackEntry.arguments?.getString("username").toString(),
//                        backStackEntry.arguments?.getString("age").toString())
//                }
//
//            }
//
//        }
//    }
//}
//
//@Composable
//fun Screeni(navigationController: NavController){
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(40.dp)
//    ) {
//        Text(text = ("This is the input Screen"))
//        var enteredText by remember {
//         mutableStateOf("")
//        }
//        TextField(
//            value=enteredText,
//            onValueChange={enteredText=it},
//            label ={ Text(text="Name")},
//            placeholder = {Text(text="enter here name")}
//
//        )
//        var enteredText2 by remember {
//            mutableStateOf("")
//        }
//        TextField(
//            value=enteredText2,
//            onValueChange={enteredText2=it},
//            label ={ Text(text="age")},
//            placeholder = {Text(text="enter here age")}
//
//        )
//        Button(onClick={
//            navigationController.navigate("second/$enteredText/$enteredText2")
//        }, shape= filledTonalShape) {
//            Text(text="Submit")
//        }
//    }
//}
//@Composable
//fun Screeno(navigationController: NavController,username: String,age: String){
//    Column (modifier = Modifier.padding(40.dp).fillMaxSize()){
//        Text(text="This is the output Screen")
//        Text(text="Hello I am $username")
//        Text(text="my age is $age")
//        Button(onClick = {
//            navigationController.popBackStack()
//        }) {
//            Text(text="go back")
//        }
//    }
//}
//
