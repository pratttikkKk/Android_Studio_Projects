package com.example.courseapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.courseapp.R

@Composable
fun ProfileImg(modifier: Modifier= Modifier){
    Image(painter = painterResource( id=R.drawable.profileimages),
        contentDescription = "profile logo",
    modifier=modifier
        //.padding(start = 16.dp)
        .clip(CircleShape)
        .size(42.dp)

    )


}

@Composable
fun NotificationImg(modifier: Modifier){
    Image(painter = painterResource( id=R.drawable.notification),
        contentDescription = "notification",
        modifier=modifier
            //.padding(start = 16.dp)
            .clip(CircleShape)
            .size(42.dp)

    )

}