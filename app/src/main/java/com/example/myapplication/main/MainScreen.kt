package com.example.myapplication.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.domain.data.entity.ListButton
import com.example.domain.entity.ListElementEntity
import com.example.myapplication.R
import com.example.myapplication.details.DetailsScreenRoute
import com.example.myapplication.details.vm.DetailsState
import com.example.myapplication.main.vm.MainState
import com.example.myapplication.main.vm.MainViewModel
import com.example.myapplication.media.MyMediaService
import com.example.myapplication.services.MyService
import com.example.myapplication.ui.view.Like
import okhttp3.internal.http2.Header
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 36.dp),
                        text = "Articles"
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 18.dp)
                    )
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            when (val st = state) {
                is MainState.Content -> {
                    ContentState(
                        navController = navController,
                        list = st.list
                    ) { element, like ->
                        viewModel.like(element, like)
                    }
                }

                is MainState.Error -> {
                    ErrorState(st.message)
                }

            MainState.Loading -> {
                Text(text = "Загрузка...")
            }
            }
        }
    }
}

@Composable
fun ErrorState(message: String) {
    Text(modifier = Modifier.fillMaxWidth(), text = message)
}

@Composable
fun ContentState(
    navController: NavController,
    list: List<ListElementEntity>,
    onLike: (ListElementEntity, Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        list.forEach { element ->
            val ctx = LocalContext.current
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if(isGranted) {
                    sendNotification(ctx)
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val permission = ContextCompat.checkSelfPermission(
                                ctx,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                            if (permission == PackageManager.PERMISSION_GRANTED) {
                                sendNotification(ctx)
                            } else {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        } else {
                            sendNotification(ctx)
                        }
//                        navController.navigate(DetailsScreenRoute(element.id))
//                        WorkManager
//                            .getInstance(ctx)
//                            .enqueueUniquePeriodicWork(
//                                "some_name",
//                                ExistingPeriodicWorkPolicy.KEEP,
//                                PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.HOURS).build()
//                            )
                    }
            ) {
                AsyncImage(
                    modifier = Modifier.size(136.dp).clip(shape = RoundedCornerShape(28.dp)),
                    model = element.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = element.title,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = element.subtitle!!,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row() {
                        ButtonLink(element.button!!)
                        Spacer(modifier = Modifier.width(16.dp))
                        val like = remember { mutableStateOf(element.like) }
                        Like(modifier = Modifier.height(40.dp), like = like)
                        val isFirstLike = remember { mutableStateOf(true) }
                        LaunchedEffect(like.value) {
                            if (isFirstLike.value) {
                                isFirstLike.value = false
                            } else {
                                onLike.invoke(element, like.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonLink(button: ListButton) {
    val uriHandler = LocalUriHandler.current
    Button(
        onClick = {
            uriHandler.openUri(button.url)
        },
        modifier = Modifier.height(40.dp)) {
        Text(text = button.title)
    }
}

fun sendNotification(context: Context) {
    ContextCompat.startForegroundService(
        context,
        Intent(context, MyMediaService::class.java).apply {
            action = MyMediaService.STARTFOREGROUND_ACTION
        }
    )
//    val notificationManager = context.getSystemService<NotificationManager>() ?: return
//    val notification = NotificationHelper.createNotification(
//        context = context,
//        title = "title",
//        text = "text"
//    )
//    notificationManager.notify(101, notification)
}
