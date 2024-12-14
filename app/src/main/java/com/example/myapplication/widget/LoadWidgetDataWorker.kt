package com.example.myapplication.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecase.ElementByIdUseCase
import org.koin.core.context.GlobalContext.get

class LoadWidgetDataWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val useCase: ElementByIdUseCase = get().get()
        runCatching {
            useCase.execute(0)
        }.onSuccess { result ->
            val manager = GlanceAppWidgetManager(applicationContext)
            manager.getGlanceIds(MyWidget::class.java).forEach { id ->

            }
            MyWidget().updateAll(applicationContext)
        }.onFailure {
            return Result.failure()
        }
        return Result.success()
    }
}