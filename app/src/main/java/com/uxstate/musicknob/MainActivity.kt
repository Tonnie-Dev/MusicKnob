package com.uxstate.musicknob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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

}