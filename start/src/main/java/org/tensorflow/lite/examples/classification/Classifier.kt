package org.tensorflow.lite.examples.classification

import android.graphics.Bitmap

interface Classifier {
    class Recognition(
        /**
         * A unique identifier for what has been recognized. Specific to the class, not the instance of
         * the object.
         */
        val id: String,
        /**
         * Display name for the recognition.
         */
        val title: String?,
        /**
         * A sortable score for how good the recognition is relative to others. Higher should be better.
         */
        val confidence: Float?
    ) {

        override fun toString(): String {
            var resultString = ""
            //            if (id != null) {
//                resultString += "[" + id + "] ";
//            }
            if (title != null) {
                resultString += "$title "
            }
            if (confidence != null) {
                resultString += String.format("(%.1f%%)\n", confidence * 100.0f)
            }
            resultString += "\n"
            return resultString.trim { it <= ' ' }
        }
    }

    fun recognizeImage(bitmap: Bitmap): List<Recognition?>?
    fun close()
}