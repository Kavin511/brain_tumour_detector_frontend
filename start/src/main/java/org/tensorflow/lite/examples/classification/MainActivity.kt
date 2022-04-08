package org.tensorflow.lite.examples.classification

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.examples.classification.ml.Model
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var select: Button? = null
    private var predict: Button? = null
    private var imageView: ImageView? = null
    private var output: TextView? = null
    private var img: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        select = findViewById(R.id.select)
        predict = findViewById(R.id.predict)
        imageView = findViewById(R.id.brainImg)
        output = findViewById(R.id.output)
        select?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        })
        predict?.setOnClickListener(View.OnClickListener {
            if (img != null) {
                img = Bitmap.createScaledBitmap(img!!, 64, 64, true)
                try {
                    val model: Model = Model.newInstance(applicationContext)

                    // Creates inputs for reference.

                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 64, 64, 3), DataType.FLOAT32)
                    val tensorImage = TensorImage(DataType.FLOAT32)
                    tensorImage.load(img)
                    val byteBuffer = tensorImage.buffer

                    inputFeature0.loadBuffer(byteBuffer)

                    // Runs model inference and gets result.
                    val outputs: Model.Outputs = model.process(inputFeature0)
                    val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
                    if (outputFeature0.floatArray[0] == 1.0F) {
                        output?.setText("Yes Brain Tumor")
                    } else {
                        output?.setText("No Brain Tumor")
                    }
                    //                    output.setText(outputFeature0.getFloatArray()[0]+ "\n"+ outputFeature0.getFloatArray()[0]);

                    // Releases model resources if no longer used.
                    model.close()
                } catch (e: IOException) {
                    // TODO Handle the exception
                }
            } else {
                Toast.makeText(this@MainActivity, "Please Select Image First", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            imageView!!.setImageURI(data!!.data)
            val uri = data.data
            try {
                img = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}