/*
 * MIT License
 *
 * Copyright (c) 2021 Byte Sculptor Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.bytesculptor.guitartones

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import dev.bytesculptor.guitartones.databinding.ActivityMainBinding
import dev.bytesculptor.guitartones.ui.PlayActivity
import dev.bytesculptor.guitartones.utilities.Tuning
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val tuningList = ArrayList<String>()
    private val toneList = ArrayList<String>()
    private val randomList = ArrayList<String>()
    private var dataAdapterTuning: ArrayAdapter<String>? = null
    private var dataAdapterTone: ArrayAdapter<String>? = null
    private var dataAdapterRandomTone: ArrayAdapter<String>? = null
    private var spTuning: Spinner? = null
    private var spTone: Spinner? = null
    private var spRandomTone: Spinner? = null
    var tuning = Tuning()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //show the activity in full screen

        spTuning = binding.spTuning
        initSpinnerTuning()
        spTone = binding.spToneToFind
        initSpinnerToneToFind()
        spRandomTone = binding.spRandomNumberOfTones
        initSpinnerRandomTone()
    }

    fun startPlay(v: View?) {
        val intentPlay = Intent(this, PlayActivity::class.java)
        intentPlay.putExtra("tuning", spTuning!!.selectedItemPosition)
        if (binding.rbOneTone.isChecked) {
            intentPlay.putExtra("tone", spTone!!.selectedItemPosition)
            intentPlay.putExtra("random", 0)
        } else {
            intentPlay.putExtra("tone", 0)
            intentPlay.putExtra("random", (spRandomTone!!.selectedItemPosition - 2))
        }

        startActivity(intentPlay)
    }

    private fun initSpinnerTuning() {
        tuningList.clear()
        for (element in Tuning.TUNING) {
            tuningList.add(element)
        }
        dataAdapterTuning = ArrayAdapter(this, android.R.layout.simple_spinner_item, tuningList)
        dataAdapterTuning!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTuning!!.adapter = dataAdapterTuning
    }

    private fun initSpinnerToneToFind() {
        toneList.clear()
        for (i in 0..11) {
            toneList.add(Tuning.SCALE[i])
        }
        dataAdapterTone = ArrayAdapter(this, android.R.layout.simple_spinner_item, toneList)
        dataAdapterTone!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTone!!.adapter = dataAdapterTone
    }

    private fun initSpinnerRandomTone() {
        randomList.clear()
        for (i in 2..12) {
            randomList.add(i.toString())
        }
        dataAdapterRandomTone = ArrayAdapter(this, android.R.layout.simple_spinner_item, randomList)
        dataAdapterRandomTone!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spRandomTone!!.adapter = dataAdapterRandomTone
    }

    fun startHelp(view: View) {}

    fun startAbout(view: View) {}
}