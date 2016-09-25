package ideaclock

import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.util.Consumer
import com.intellij.util.text.DateFormatUtil

import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent

class ClockStatusBarWidget : StatusBarWidget, StatusBarWidget.TextPresentation, ActionListener {
    private val timer = Timer(1000, this)
    private var statusBar: StatusBar? = null

    @Volatile private var lastTime = time

    override fun ID(): String {
        return "IdeaClock"
    }

    override fun getPresentation(platformType: StatusBarWidget.PlatformType): StatusBarWidget.WidgetPresentation? {
        return this
    }


    override fun install(statusBar: StatusBar) {
        this.statusBar = statusBar
        timer.start()

    }

    override fun dispose() {
        timer.stop()
        statusBar = null
    }

    override fun actionPerformed(actionEvent: ActionEvent) {
        val newTime = time
        if (newTime / MINUTES != lastTime / MINUTES) {
            lastTime = newTime
            statusBar?.updateWidget(ID())
        }
        timer.restart()
    }

    override fun getText(): String {
        return "\u25F7 " + DateFormatUtil.formatTime(lastTime)
    }

    override fun getMaxPossibleText(): String {
        return "0000000"
    }

    override fun getAlignment(): Float {
        return 0.5f
    }

    override fun getTooltipText(): String? {
        return "time..."
    }

    override fun getClickConsumer(): Consumer<MouseEvent>? {
        return null
    }

    internal val time: Long
        get() = System.currentTimeMillis()

    companion object {
        private val MINUTES: Long = 60000
    }
}
