package com.example.myapplication.details

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.details.vm.DetailsState
import com.example.myapplication.details.vm.DetailsViewModel
import com.example.myapplication.ui.view.Like
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, vm: DetailsViewModel = koinViewModel()) {
    val state = vm.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = state.value.title
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = false)
                        ) {
                            navController.navigateUp()
                        },
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = ""
                    )
                },
                actions = {
                    if (state.value is DetailsState.Content) {
                        val content = state.value as DetailsState.Content
                        val like = remember {
                            mutableStateOf(content.element.like)
                        }
                        Like(modifier = Modifier, like = like)
                        val isFirstLike = remember { mutableStateOf(true) }
                        LaunchedEffect(like.value) {
                            if (isFirstLike.value) {
                                isFirstLike.value = false
                            } else {
                                vm.like(content.element, like.value)
                            }
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (val st = state.value) {
                is DetailsState.Content -> {
                    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                        AsyncImage(
                            modifier = Modifier.size(300.dp).clip(shape = RoundedCornerShape(28.dp)),
                            model = st.element.image,
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = st.element.subtitle,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = st.element.content
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Progress(vm)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Article ID: ${st.element.id.toString()}")
                    if (st.read) {
                        Text("Read")
                    } else {
                        Text("Not read")
                    }

                }

                is DetailsState.Error -> {
                    Text(text = st.message)
                }

                DetailsState.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun Progress(vm: DetailsViewModel) {
    var progressValue by remember { mutableStateOf(0f) }
    val progress = animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(
            durationMillis = 10_000,
            easing = LinearEasing
        ),
        finishedListener = {
            vm.markAsRead()
        }
    )
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        progress = { progress.value }
    )
    LaunchedEffect(Unit) {
        progressValue = 1f
    }
}