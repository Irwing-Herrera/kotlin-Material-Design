package com.iherrera.materialdesigncomponents

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.iherrera.materialdesigncomponents.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincular binding con la vista
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this._eventsFab()
        this._eventsBottomAppBar()
        this._eventsButtonSkip()
        this._eventsButtonBuy()
        this._checkboxPassword()
        this._loadImageCover()
        this._changeColorButton()
    }

    private fun _changeColorButton() {
        binding.contentScrolling.buttonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.contentScrolling.buttonSkip.apply {
                setBackgroundColor(
                    when (checkedId) {
                        R.id.buttonBlue -> Color.RED
                        R.id.buttonGreen -> Color.GREEN
                        else -> Color.BLUE
                    }
                )
            }
        }
    }

    private fun _loadImageCover() {
        binding.contentScrolling.editTextUrl.onFocusChangeListener =
            View.OnFocusChangeListener { _, focused ->
                val url: String = binding.contentScrolling.editTextUrl.text.toString()
                var errorStr: String? = null

                if (!focused) {
                    when {
                        url.isEmpty() -> {
                            errorStr = getString(R.string.card_required)
                        }
                        URLUtil.isValidUrl(url) -> {
                            this._loadImageViewWithGlide(url)
                        }
                        else -> {
                            errorStr = getString(R.string.card_invalid_url)
                        }
                    }
                }

                binding.contentScrolling.textInputURL.error = errorStr
            }
    }

    private fun _checkboxPassword() {
        binding.contentScrolling.checkboxEnablePassword.setOnClickListener {
            binding.contentScrolling.textInputPassword.apply {
                isEnabled = !isEnabled
            }
        }
    }

    private fun _loadImageViewWithGlide(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.contentScrolling.imageViewCover)
    }

    private fun _eventsButtonBuy() {
        binding.contentScrolling.btnBuy.setOnClickListener {
            Snackbar.make(binding.root, R.string.card_buying, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    private fun _eventsButtonSkip() {
        binding.contentScrolling.btnSkip.setOnClickListener {
            binding.contentScrolling.cardViewBuy.apply {
                visibility = View.GONE
            }
        }
    }

    private fun _eventsBottomAppBar() {
        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.fab).show()
        }
    }

    private fun _eventsFab() {
        binding.fab.setOnClickListener {
            if (binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            } else {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}