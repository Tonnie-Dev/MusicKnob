package com.uxstate.musicknob

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.uxstate.musicknob.ui.theme.MusicKnobTheme
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicKnobTheme {


                Box(
                    modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF101010)),
                    contentAlignment = Alignment.Center
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.Green,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(30.dp)
                    ) {

                        var volume by remember {
                            mutableStateOf(0f)
                        }

                        val barCount = 20

                        MusicKnob(
                            onValueChange = { volume = it },
                            modifier = Modifier.size(100.dp)
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        VolumeBar(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp),
                            activeBars = (barCount * volume).roundToInt(),
                            barCount = barCount
                        )
                    }


                }

            }

        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
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
                    val angleInRadians =
                        atan2(x = centerX - touchX, y = centerY - touchY) * (180f / PI).toFloat()
                    // val angle = Math.toDegrees(angleInRadians.toDouble())


                    //PERFORM ROTATION
                    //establish if we have the correct touch event depending on action

                    when (motionEvent.action) {

                        //perform calculation on-tap and on-move

                  MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE -> {


                            if (angleInRadians !in -limitingAngle..limitingAngle) {
                                val normalizedAngle = if (angleInRadians in -180f..-limitingAngle) {
                                    //add 360 degrees for normal rotation
                                    angleInRadians  + 360f
                                } else {

                                    //assign angle as it is

                                    angleInRadians
                                }


                                rotation = normalizedAngle //.toFloat()

                                //map rotation to value btw zero and one
                                val percent =
                                    ((normalizedAngle - limitingAngle) / (360f - (2 * limitingAngle)))

                                //we call onValueChange() with the percentage
                                onValueChange(percent.toFloat())

                                //pointerInteropFilter expects a boolean whether we handled the touch or not
                                true


                            }
                            //return false indicating a touch wasn't handled
                            else false
                        }

                        //return false  for when expression
                        else -> false
                    }

                    //rotate image at the very end
                }
                .rotate(rotation)
    )

}

@Composable
fun VolumeBar(modifier: Modifier = Modifier, activeBars: Int = 0, barCount: Int = 0) {

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {


        //calculate bar width

        val barWidth = remember {

            constraints.maxWidth / (barCount * 2f)
        }


        //draw bars
        Canvas(modifier = modifier, onDraw = {

            for (i in 0 until barCount) {

                drawRoundRect(
                    color = if (i in 0..activeBars) Color.Green else Color.DarkGray,
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(width = barWidth, constraints.maxHeight.toFloat())
                )
            }


        })
    }
}