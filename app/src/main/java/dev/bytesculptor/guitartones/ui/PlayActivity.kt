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
package dev.bytesculptor.guitartones.ui

import androidx.appcompat.app.AppCompatActivity
import dev.bytesculptor.guitartones.dialogs.DialogGameCompleted.GameCompletedListener
import android.widget.TextView
import android.os.Bundle
import dev.bytesculptor.guitartones.R
import android.widget.Toast
import dev.bytesculptor.guitartones.utilities.Tuning
import android.view.WindowManager
import android.annotation.SuppressLint
import android.app.DialogFragment
import android.graphics.Color
import android.view.View
import android.widget.Button
import dev.bytesculptor.guitartones.dialogs.DialogGameCompleted

class PlayActivity : AppCompatActivity(), GameCompletedListener {
    private var tuningChoice = 0
    private var toneChoice = 0
    private var goodCnt = 0
    private var badCnt = 0
    private val allNotesTable = Array(6) { IntArray(12) }
    private var tvGoodCnt: TextView? = null
    private var tvBadCnt: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val extras = intent.extras
        if (extras != null) {
            tuningChoice = extras.getInt("tuning")
            toneChoice = extras.getInt("tone")
        } else {
            Toast.makeText(baseContext, "main bundle failed", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val tvFind = findViewById<TextView>(R.id.tvFind)
        tvFind.text = Tuning.SCALE[toneChoice]
        setTuning()
        setTonesPerString()
        clearTonesOnStrings()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //show the activity in full screen
        tvGoodCnt = findViewById(R.id.tvGoodCnt)
        tvBadCnt = findViewById(R.id.tvBadCnt)
    }

    @SuppressLint("SetTextI18n")
    fun setTuning() {
        for (i in 1..6) {
            val bt =
                findViewById<View>(resources.getIdentifier("string$i", "id", packageName)) as Button
            when (tuningChoice) {
                1 -> bt.text = " " + Tuning.SCALE[Tuning.OFFSET_Eb[i - 1]] + " "
                2 -> bt.text = " " + Tuning.SCALE[Tuning.OFFSET_D[i - 1]] + " "
                3 -> bt.text = " " + Tuning.SCALE[Tuning.OFFSET_DROP_D[i - 1]] + " "
                4 -> bt.text = " " + Tuning.SCALE[Tuning.OFFSET_DROP_C[i - 1]] + " "
                5 -> bt.text = " " + Tuning.SCALE[Tuning.OFFSET_DROP_CIS[i - 1]] + " "
                else -> bt.text = " " + Tuning.SCALE[Tuning.OFFSET_STD[i - 1]] + " "
            }
        }
    }

    fun setTonesPerString() {
        for (k in 0..5) {
            for (i in 1..12) {
                val baseString = "bt" + Tuning.STRING_BUTTONS[k] + i
                val bt = findViewById<View>(
                    resources.getIdentifier(
                        baseString,
                        "id",
                        packageName
                    )
                ) as Button
                //bt.setText(Tuning.scale[(Tuning.stringOffset2[k] + i) % 12]);
                var pos1 = 0
                when (tuningChoice) {
                    0 -> {
                        pos1 = (Tuning.OFFSET_STD[k] + i) % 12
                        bt.text = Tuning.SCALE[pos1]
                        allNotesTable[k][i - 1] = pos1
                    }
                    1 -> {
                        pos1 = (Tuning.OFFSET_Eb[k] + i) % 12
                        bt.text = Tuning.SCALE[pos1]
                        allNotesTable[k][i - 1] = pos1
                    }
                    2 -> {
                        pos1 = (Tuning.OFFSET_D[k] + i) % 12
                        bt.text = Tuning.SCALE[pos1]
                        allNotesTable[k][i - 1] = pos1
                    }
                    3 -> {
                        pos1 = (Tuning.OFFSET_DROP_D[k] + i) % 12
                        bt.text = Tuning.SCALE[pos1]
                        allNotesTable[k][i - 1] = pos1
                    }
                    4 -> {
                        pos1 = (Tuning.OFFSET_DROP_C[k] + i) % 12
                        bt.text = Tuning.SCALE[pos1]
                        allNotesTable[k][i - 1] = pos1
                    }
                    5 -> {
                        pos1 = (Tuning.OFFSET_DROP_CIS[k] + i) % 12
                        bt.text = Tuning.SCALE[pos1]
                        allNotesTable[k][i - 1] = pos1
                    }
                    else -> Toast.makeText(
                        baseContext,
                        "tuning failed, err. 12",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun clearTonesOnStrings() {
        for (k in 0..5) {
            for (i in 1..12) {
                val baseString = "bt" + Tuning.STRING_BUTTONS[k] + i
                val bt =
                    findViewById<Button>(resources.getIdentifier(baseString, "id", packageName))
                bt.text = "   "
            }
        }
    }

    private fun correctButton(bt: Button) {
        bt.setBackgroundColor(Color.GREEN)
        goodCnt++
        tvGoodCnt!!.text = "" + goodCnt
        if (goodCnt >= 6) {
            val dialogClearMemo: DialogFragment = DialogGameCompleted()
            val bundle = Bundle()
            bundle.putFloat(
                "success",
                goodCnt.toFloat() / (goodCnt.toFloat() + badCnt.toFloat()) * 100
            )
            dialogClearMemo.arguments = bundle
            dialogClearMemo.show(fragmentManager, "cm")
        }
    }

    private fun wrongButton(bt: Button, note: Int) {
        bt.setBackgroundColor(Color.RED)
        badCnt++
        tvBadCnt!!.text = "" + badCnt
        bt.text = Tuning.SCALE[note]
    }

    private fun checkTone(bt: Button, string: Int, fret: Int) {
        if (allNotesTable[string][fret] == toneChoice) {
            correctButton(bt)
        } else {
            wrongButton(bt, allNotesTable[string][fret])
        }
    }

    fun onButtonClick(view: View) {
        val bt: Button
        when (view.id) {
            R.id.bte1 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 0)
            }
            R.id.bte2 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 1)
            }
            R.id.bte3 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 2)
            }
            R.id.bte4 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 3)
            }
            R.id.bte5 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 4)
            }
            R.id.bte6 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 5)
            }
            R.id.bte7 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 6)
            }
            R.id.bte8 -> {
                bt = findViewById(view.id)
                checkTone(bt, 0, 7)
            }
            R.id.bte9 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 0, 8)
            }
            R.id.bte10 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 0, 9)
            }
            R.id.bte11 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 0, 10)
            }
            R.id.bte12 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 0, 11)
            }
            R.id.btB1 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 0)
            }
            R.id.btB2 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 1)
            }
            R.id.btB3 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 2)
            }
            R.id.btB4 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 3)
            }
            R.id.btB5 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 4)
            }
            R.id.btB6 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 5)
            }
            R.id.btB7 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 6)
            }
            R.id.btB8 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 7)
            }
            R.id.btB9 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 8)
            }
            R.id.btB10 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 9)
            }
            R.id.btB11 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 10)
            }
            R.id.btB12 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 1, 11)
            }
            R.id.btG1 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 0)
            }
            R.id.btG2 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 1)
            }
            R.id.btG3 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 2)
            }
            R.id.btG4 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 3)
            }
            R.id.btG5 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 4)
            }
            R.id.btG6 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 5)
            }
            R.id.btG7 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 6)
            }
            R.id.btG8 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 7)
            }
            R.id.btG9 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 8)
            }
            R.id.btG10 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 9)
            }
            R.id.btG11 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 10)
            }
            R.id.btG12 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 2, 11)
            }
            R.id.btD1 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 0)
            }
            R.id.btD2 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 1)
            }
            R.id.btD3 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 2)
            }
            R.id.btD4 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 3)
            }
            R.id.btD5 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 4)
            }
            R.id.btD6 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 5)
            }
            R.id.btD7 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 6)
            }
            R.id.btD8 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 7)
            }
            R.id.btD9 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 8)
            }
            R.id.btD10 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 9)
            }
            R.id.btD11 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 10)
            }
            R.id.btD12 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 3, 11)
            }
            R.id.btA1 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 0)
            }
            R.id.btA2 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 1)
            }
            R.id.btA3 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 2)
            }
            R.id.btA4 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 3)
            }
            R.id.btA5 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 4)
            }
            R.id.btA6 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 5)
            }
            R.id.btA7 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 6)
            }
            R.id.btA8 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 7)
            }
            R.id.btA9 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 8)
            }
            R.id.btA10 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 9)
            }
            R.id.btA11 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 10)
            }
            R.id.btA12 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 4, 11)
            }
            R.id.btE1 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 0)
            }
            R.id.btE2 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 1)
            }
            R.id.btE3 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 2)
            }
            R.id.btE4 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 3)
            }
            R.id.btE5 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 4)
            }
            R.id.btE6 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 5)
            }
            R.id.btE7 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 6)
            }
            R.id.btE8 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 7)
            }
            R.id.btE9 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 8)
            }
            R.id.btE10 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 9)
            }
            R.id.btE11 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 10)
            }
            R.id.btE12 -> {
                bt = findViewById<View>(view.id) as Button
                checkTone(bt, 5, 11)
            }
        }
    }

    override fun onSuccessCopied() {
        finish()
    }
}