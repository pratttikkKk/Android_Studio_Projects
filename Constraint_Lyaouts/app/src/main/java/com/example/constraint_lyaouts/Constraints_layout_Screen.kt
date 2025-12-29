package com.example.constraint_lyaouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayouts(){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (box1,box2,box3,box4,box5,text)= createRefs()
        val guideLine1= createGuidelineFromStart(10.dp)
        val barrier =createEndBarrier(box1,box2)

        Box(modifier = Modifier.background(Color.Red).size(120.dp).constrainAs(box1) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(guideLine1)
        })
        Box(modifier = Modifier.background(Color.Yellow).size(120.dp).constrainAs(box2){
            top.linkTo(box1.bottom, margin=10.dp )
            start.linkTo(guideLine1)
        })
        Box(modifier = Modifier.background(Color.White).size(120.dp).constrainAs(box3){
            top.linkTo(box2.bottom, margin=10.dp )
            start.linkTo(guideLine1)
        })
        Box(modifier = Modifier.background(Color.Blue).size(120.dp).constrainAs(box4){
            top.linkTo(box3.bottom, margin=10.dp )
            start.linkTo(guideLine1)
        })
        Box(modifier = Modifier.background(Color.Green).size(120.dp).constrainAs(box5){
            top.linkTo(box4.bottom, margin=10.dp )
            start.linkTo(guideLine1)
        })
        Text(text="Jainism", modifier = Modifier
            .constrainAs(text)
            {
                top.linkTo(box5.bottom, margin = 16.dp)
                start.linkTo(guideLine1)


        })
        createVerticalChain(box1,box2,box3,box4,box5,text,
            chainStyle = ChainStyle.Packed
            )

    }



}

