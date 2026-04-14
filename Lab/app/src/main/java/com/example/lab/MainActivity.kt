package com.example.lab

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab.data

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch.setOnClickListener { vm.fetchAndStore() }
        binding.btnNext.setOnClickListener { vm.next() }
        binding.btnPrev.setOnClickListener { vm.prev() }

        vm.state.observe(this) { s ->
            binding.progress.visibility = if (s.loading) View.VISIBLE else View.GONE

            val total = s.total
            val idx = s.index
            binding.tvCounter.text = if (total > 0) "${idx + 1} / $total" else "0 / 0"

            val c = s.comment
            binding.tvPostId.text = "postId: ${c?.postId ?: "-"}"
            binding.tvId.text     = "id: ${c?.id ?: "-"}"
            binding.tvName.text   = "name: ${c?.name ?: "-"}"
            binding.tvEmail.text  = "email: ${c?.email ?: "-"}"
            binding.tvBody.text   = "body:\n${c?.body ?: "-"}"

            binding.btnPrev.isEnabled = !s.loading && total > 0 && idx > 0
            binding.btnNext.isEnabled = !s.loading && total > 0 && idx < total - 1
            binding.btnFetch.isEnabled = !s.loading

            s.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                vm.clearError()
            }
        }
    }
}
