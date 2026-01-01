package com.example.courseapp.screens

import androidx.compose.foundation.Image
import com.example.courseapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutScreen(){

    ConstraintLayout(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)
        .verticalScroll(rememberScrollState())
    ){
        //background
        val (gradientbg,profileImg,notificationImg,welcometext,questiontext,joinnow,courseimg,mycard,textcourses)= createRefs()
        val (androidcourse,javacourse, pythoncourse)=createRefs()
        val hguideline1 = createGuidelineFromTop(0.45f)

        GradientBackground( modifier = Modifier
            .constrainAs(gradientbg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(hguideline1)
                width = Dimension.fillToConstraints
                height= Dimension.fillToConstraints
            })

        //Header
        val topGuideline = createGuidelineFromTop(16.dp)
        val startguideline = createGuidelineFromStart(16.dp)
        val endguideline = createGuidelineFromEnd(16.dp)

        createHorizontalChain(
            profileImg,notificationImg,
            chainStyle = ChainStyle.SpreadInside
        )
        ProfileImg( modifier = Modifier .constrainAs(profileImg) {
            top.linkTo(topGuideline)
        })
        NotificationImg( modifier = Modifier .constrainAs(notificationImg) {
            top.linkTo(profileImg.top)
        })


        //Middle part

        Welcometext(modifier = Modifier.constrainAs(welcometext) {
            top.linkTo(profileImg.bottom, margin=32.dp)
            start.linkTo(startguideline)
        }
        )
        Questiontext(modifier = Modifier.constrainAs(questiontext) {
            top.linkTo(welcometext.bottom,margin=8.dp)
            start.linkTo(welcometext.start)
        })
       JoinNow(modifier = Modifier.constrainAs (joinnow){
           top.linkTo(questiontext.bottom, margin = 32.dp)
           start.linkTo(questiontext.start)
           end.linkTo(questiontext.end)
       })
        CourseImg(modifier = Modifier.constrainAs(courseimg) {
            top.linkTo(joinnow.bottom)
            start.linkTo(joinnow.end)
            end.linkTo(parent.end)
            bottom.linkTo(hguideline1)
             height= Dimension.fillToConstraints
            width= Dimension.fillToConstraints
        })

        //card
Mycard(modifier = Modifier.constrainAs (mycard){
    top.linkTo(hguideline1, margin = (-32).dp)

    start.linkTo(parent.start)
    end.linkTo(parent.end)
    bottom.linkTo(parent.bottom)
    width= Dimension.fillToConstraints
    height= Dimension.fillToConstraints
})

        // courses

        TextourCourses(modifier = Modifier.constrainAs (textcourses){
            top.linkTo(mycard.top, margin = 20.dp)
            start.linkTo(mycard.start, margin = 16.dp)
        })

        createHorizontalChain(
            androidcourse,javacourse,pythoncourse,
            chainStyle = ChainStyle.Spread
        )
        //courses
        AndroidCourse(modifier = Modifier.constrainAs (androidcourse){
            top.linkTo(textcourses.bottom, margin = 16.dp)

        })
        JavaCourse(modifier = Modifier.constrainAs (javacourse){
            top.linkTo(textcourses.bottom, margin = 16.dp)

        })
        PythonCourse(modifier = Modifier.constrainAs (pythoncourse){
            top.linkTo(textcourses.bottom, margin = 16.dp)

        })
    }
}
