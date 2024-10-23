package com.example.componentprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.componentprogressbar.ui.theme.ComponentProgressBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComponentProgressBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProgressBar(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Funció ProgressBar que serveix el propòsit de mostrar els components de barres de progrés.
 * @author RIS
 * @since 22/10/2024
 */
@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    var progressStatus by rememberSaveable { mutableStateOf(0.0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth() // Per tal de que el component ocupi tot l'ample de la pantalla
            .fillMaxHeight() // Per tal de que ocupi tota l'alçada de la pantalla
            .padding(40.dp, 100.dp) // (horitzontal, vertical)
            .verticalScroll(
                rememberScrollState()
            )
    )
    {

        Text(text = "Barra de progrés linial...")

        LinearProgressIndicator( // Consultar la classe del component per a veure l'ordre dels paràmetres
            // Si no especifiquem valor de compleció de progrés, la barra es queda carregant infinitament...
            //progress = { progressStatus },
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Aliniem al centre horitzontalment
                .padding(25.dp, 40.dp) // Separació respecte els components anteriors
                .height(25.dp) // Per modificar el gruix (alçada) del component.
                .width(500.dp), // Per modificar la longitud de la barra de progrés horitzontalment.
            color = Color.Black, // Ens permet definir un color per la línia que es va omplint
            trackColor = Color.LightGray, // Ens permet definir un color per la base de fons de la línia
            strokeCap = StrokeCap.Butt // Ens permet modificar la forma del principi i del final de la barra de compleció (Round, Square o Butt)
        )

        Text(text = "Barra de progrés circular [${"%.2f".format(progressStatus)}]")

        CircularProgressIndicator( // Consultar la classe del component per a veure l'ordre dels paràmetres
            progress = { progressStatus }, // Definim el nivell de compleció de la línia (0.0f - 1.0f)
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Aliniem al centre horitzontalment
                .padding(40.dp, 40.dp) // (horitzontal, vertical),
                .size(150.dp), // Mida del component
            color = Color.Black, // Ens permet definir un color per la línia que es va omplint
            strokeWidth = 25.dp, // Definim el gruix de la línia
            trackColor = Color.LightGray, // Ens permet definir un color per la base de fons de la línia
            strokeCap = StrokeCap.Round // Ens permet modificar la forma del principi i del final de la barra de compleció (Round, Square o Butt)
        )

        Text(text = "Component AnimatedVisibility (apareix quan progressStatus > 0.25f)")

        AnimatedVisibility( // Consultar la classe del component per a veure l'ordre dels paràmetres
            visible = progressStatus > 0.25f,
            modifier = Modifier
                .padding(0.dp, 20.dp),
            enter = fadeIn(animationSpec = tween(durationMillis = 500)), // Fade in al mostrar
            exit = fadeOut(animationSpec = tween(durationMillis = 800)), // Fade out al desaparèixer
            label = "Component AnimatedVisibility (apareix quan progressStatus > 0.25f)" // No es mostra el text, només és per accessibilitat
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color.Black)
                    .alpha(progressStatus) // Controla l'opacitat del component basada en el valor de progressStatus
            )
        }

        Row (modifier = Modifier
            .padding(0.dp, 25.dp) // Separació respecte als components anteriors
        ){

            Button(// Consultar la classe del component per a veure l'ordre dels paràmetres
                onClick = { if (progressStatus > 0.0f) progressStatus -= 0.1f },
                enabled =  progressStatus > 0.0f, // Enabled si progressStatus és > 0
                shape = CutCornerShape(2.dp), // Per determinar la forma del Button, tenim disponibles de base: CircleShape, RectangleShape, CutCornerShape.
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White), // Colors de fora i de dins
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 15.dp), // La mida de l'hombra a sota del botó
                border = BorderStroke(2.dp,Color.Black) // Mida de la vora del contorn
                ) {
                Text(text = "Decrementar")
            }

            Button( // Consultar la classe del component per a veure l'ordre dels paràmetres
                onClick = { if (progressStatus < 1.0f) progressStatus += 0.1f },
                modifier = Modifier
                    .padding(20.dp,0.dp), // Separem horitzontalment del botó del costat
                enabled =  progressStatus < 1.0f, // Enabled si progressStatus és > 0
                shape = CircleShape, // Per determinar la forma del Button, tenim disponibles de base: CircleShape, RectangleShape, CutCornerShape.
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White), // Colors de fora i de dins
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp), // La mida de l'hombra a sota del botó
                border = BorderStroke(2.dp,Color.Black) // Mida de la vora del contorn
            ) {
                Text(text = "Incrementar")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProgressBarPreview() {
    ComponentProgressBarTheme {
        ProgressBar()
    }
}