package com.uxstate.musicknob

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import com.uxstate.musicknob.ui.theme.MusicKnobTheme
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


@OptIn(ExperimentalComposeUiApi::class)
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

                    //Calculate Sweep Angle

                    //minus indicates direction of the angle
                    //atan2 - inverse of tan and returns sweep angle in Degrees
                    val angleInRadians = -atan2(x = centerX - touchX, y = centerY - touchY)
                    val angle = Math.toDegrees(angleInRadians.toDouble())


                    //PERFORM ROTATION
                    //establish if we have the correct touch event depending on action

                    when (motionEvent.action) {

                        //perform calculation on-tap and on-move

                        // MotionEvent.ACTION_DOWN -> {}
                        MotionEvent.ACTION_MOVE -> {


                            if (angle !in -limitingAngle..limitingAngle) {
                                val normalizedAngle = if (angle in -180f..-limitingAngle) {
                                    //add 360 degrees for normal rotation
                                    angle + 360f
                                } else {

                                    //assign angle as it is

                                    angle
                                }


                                rotation = normalizedAngle.toFloat()

                                //map rotation to value btw zero and one
                                val percent =
                                    ((normalizedAngle - limitingAngle) / (360f - (2 * limitingAngle)))

                                //we call onValueChange() with the percentage
                                onValueChange(percent.toFloat())

                                true
                            } else false
                        }

                        else -> false
                    }

                    //rotate image at the very end
                }
                .rotate(rotation)
    )

}

@Composable
fun VolumeBar(modifier: Modifier = Modifier, activeBars:Int = 0, barCount:Int = 0) {

    
}