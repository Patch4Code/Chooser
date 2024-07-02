package com.uravgcode.chooser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.uravgcode.chooser.utilities.Mode
import com.uravgcode.chooser.views.Chooser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val chooserMode = remember { mutableStateOf(Mode.SINGLE) }
            val chooserCount = remember { mutableIntStateOf(1) }
            val isVisible = remember { mutableStateOf(true) }

            AndroidView(
                factory = { context ->
                    Chooser(context, setButtonVisibility = { newVisible ->
                        isVisible.value = newVisible
                    })
                },
                update = { view ->
                    view.mode = chooserMode.value
                    view.count = chooserCount.intValue
                },
                modifier = Modifier
            )

            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> -2 * fullHeight },
                        animationSpec = tween(durationMillis = 400)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> -2 * fullHeight },
                        animationSpec = tween(durationMillis = 400)
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 24.dp, start = 24.dp)
                        .size(56.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            chooserMode.value = chooserMode.value.next()
                        },
                        shape = CircleShape,
                        containerColor = Color(0xFF2B2B2B),
                        contentColor = Color.White
                    ) {
                        Icon(
                            painter = painterResource(id = chooserMode.value.drawable()),
                            contentDescription = "Mode"
                        )
                    }

                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = if (chooserMode.value == Mode.ORDER) {
                        false
                    } else {
                        isVisible.value
                    },
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> -2 * fullHeight },
                        animationSpec = tween(durationMillis = 400)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> -2 * fullHeight },
                        animationSpec = tween(durationMillis = 400)
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 24.dp)
                        .size(56.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            chooserCount.intValue =
                                chooserMode.value.nextCount(chooserCount.intValue)
                        },
                        shape = CircleShape,
                        containerColor = Color(0xFF2B2B2B),
                        contentColor = Color.White
                    ) {
                        Text(
                            text = chooserCount.intValue.toString(),
                            fontSize = 36.sp
                        )
                    }

                }
            }
        }
    }
}
