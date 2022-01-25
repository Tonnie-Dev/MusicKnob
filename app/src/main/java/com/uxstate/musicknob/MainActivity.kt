package com.uxstate.musicknob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.uxstate.musicknob.ui.theme.MusicKnobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicKnobTheme() {


            }

        }
    }
}


@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = .25f,
    onValueChange: (Float) -> Unit
) {


    var rotation by remember{ mutableStateOf(limitingAngle) }

    //touch val point

    var touchX by remember{ mutableStateOf(0f)}
    var touchY by remember{ mutableStateOf(0f)}

    //knob centre vals
    var centerX by remember{ mutableStateOf(0f)}
    var centerY by remember{ mutableStateOf(0f)}


    
}