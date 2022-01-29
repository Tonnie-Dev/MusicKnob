package com.uxstate.musicknob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import com.uxstate.musicknob.ui.theme.MusicKnobTheme
import kotlin.math.atan
import kotlin.math.atan2

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


    var rotation by remember { mutableStateOf(limitingAngle) }

    //touch val point
    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }

    //knob centre vals
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "Music Knob",
        modifier = modifier
                .fillMaxSize()

                //establish knob anchor-centre position
                .onGloballyPositioned {

                    //boundary of the image relative to the screen
                    val windowBounds = it.boundsInWindow()

                    centerX = windowBounds.size.width / 2f
                    centerY = windowBounds.size.height / 2f

                }

            //detect touch position

                .pointerInteropFilter {

                    motionEvent ->

                  //set touch coordinates to motion event
                    touchX = motionEvent.x
                    touchY = motionEvent.y

//minus indicates direction of the angle
                    //atan2 - inverse of tan and returns sweep angle in Degrees
                    val angleInRadians = -atan2(x = centerX - touchX, y =centerY - touchX)


                    val angle =Math.toDegrees(angleInRadians)
                }
    )

}