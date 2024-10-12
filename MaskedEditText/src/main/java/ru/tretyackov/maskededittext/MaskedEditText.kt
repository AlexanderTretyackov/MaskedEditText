package ru.tretyackov.maskededittext

import android.content.Context
import android.util.AttributeSet

class MaskedEditText : androidx.appcompat.widget.AppCompatEditText {

    private var maskSymbol: Char? = null
    private var maskPositions: List<Int> = listOf()

    constructor(context: Context) : super(context) {
        initAndApplyMask(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAndApplyMask(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAndApplyMask(attrs, defStyleAttr)
    }

    private fun initAndApplyMask(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MaskedEditText,
            defStyleAttr, 0
        ).apply {
            try {
                getString(R.styleable.MaskedEditText_mask)?.let { updateMask(it) }
            } finally {
                recycle()
            }
        }
        applyMaskToCurrentText()
    }

    private fun updateMask(newMask: String) {
        val (newMaskSymbol, newMaskPositions) = parseMask(newMask)
        maskSymbol = newMaskSymbol
        maskPositions = newMaskPositions
    }

    private fun parseMask(maskString: String): Pair<Char?, List<Int>> {
        var symbol: Char? = null
        if (maskString.isEmpty())
            return Pair(null, listOf())
        val maskSymbolPositions = mutableListOf<Int>()
        for (i in maskString.indices)
            if (maskString[i] != 'X') {
                maskSymbolPositions.add(i)
                symbol = maskString[i]
            }
        return Pair(symbol, maskSymbolPositions)
    }

    private fun applyMaskToString(
        originString: String,
        maskPositions: List<Int>,
        maskSymbol: Char
    ): String {
        var resultStringIndex = 0
        var originStringIndex = 0
        var maskPositionIndex = 0
        val sb = StringBuilder()
        while (originStringIndex < originString.length) {
            if (maskPositionIndex < maskPositions.size && maskPositions[maskPositionIndex] == resultStringIndex) {
                sb.append(maskSymbol)
                resultStringIndex++
                maskPositionIndex++
            } else {
                if (originString[originStringIndex] != maskSymbol) {
                    sb.append(originString[originStringIndex])
                    resultStringIndex++
                }
                originStringIndex++
            }
        }
        return sb.toString()
    }

    private fun applyMaskToCurrentText() {
        if (text.isNullOrEmpty() || maskSymbol == null) return
        val originString = text.toString()
        val maskedString = applyMaskToString(originString, maskPositions, maskSymbol!!)
        if (originString == maskedString)
            return
        val oldSelection = selectionStart
        setText(maskedString)
        val selection =
            if (oldSelection == originString.length) maskedString.length else oldSelection
        setSelection(selection)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        applyMaskToCurrentText()
    }
}