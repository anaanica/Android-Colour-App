package hr.algebra.colourapp.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ColourWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        ColourFetcher(context).fetchItems("json")
        return Result.success()
    }
}