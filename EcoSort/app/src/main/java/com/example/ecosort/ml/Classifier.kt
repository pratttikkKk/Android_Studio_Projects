package com.example.ecosort.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class Classifier(private val context: Context) {
    private val labels = listOf("Organic", "Recyclable", "NonRecyclable")
    private val inputSize = 224
    private val interpreter: Interpreter

    init {
        val model = loadModelFile("model.tflite")
        interpreter = Interpreter(model)
    }

    private fun loadModelFile(filename: String): MappedByteBuffer {
        val afd = context.assets.openFd(filename)
        val inputStream = FileInputStream(afd.fileDescriptor)
        val channel = inputStream.channel
        val start = afd.startOffset
        val len = afd.declaredLength
        return channel.map(FileChannel.MapMode.READ_ONLY, start, len)
    }

    fun classify(bitmap: Bitmap): Pair<String, Float> {
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0f, 1f)) // if training used rescale=1./255
            .build()
        var tensorImage = TensorImage.fromBitmap(bitmap)
        tensorImage = imageProcessor.process(tensorImage)
        val inputBuffer = tensorImage.buffer

        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), org.tensorflow.lite.DataType.FLOAT32)
        interpreter.run(inputBuffer, outputBuffer.buffer.rewind())

        val scores = outputBuffer.floatArray
        var maxIdx = 0
        for (i in scores.indices) if (scores[i] > scores[maxIdx]) maxIdx = i
        return Pair(labels[maxIdx], scores[maxIdx])
    }

    fun close() {
        interpreter.close()
    }
}
