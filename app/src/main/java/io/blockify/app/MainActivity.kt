package io.blockify.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.blockify.app.databinding.ActivityMainBinding
import io.blockify.feature.android.ToastUtil
import io.blockify.feature.components.FactorialCalculator
import io.blockify.library.compose.ComposeActivity

/**
 * Created by kingm on 4/26/2023
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCompute.setOnClickListener {
            val message = if (binding.editTextFactorial.text.isNotEmpty()) {
                val input = binding.editTextFactorial.text.toString().toLong()
                val result = try {
                    FactorialCalculator.computeFactorial(input).toString()
                } catch (ex: IllegalStateException) {
                    "Error: ${ex.message}"
                }

                binding.textResult.text = result
                binding.textResult.visibility = View.VISIBLE
                getString(R.string.notification_title, result)
            } else {
                getString(R.string.please_enter_a_number)
            }
            ToastUtil.showToast(this, message)
        }

        binding.buttonAppcompose.setOnClickListener {
            val intent = Intent(it.context, ComposeActivity::class.java)
            startActivity(intent)
        }
    }
}