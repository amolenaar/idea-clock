package ideaclock

import com.intellij.mock.MockProjectEx
import com.intellij.openapi.extensions.Extensions
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.WindowManager
import com.intellij.openapi.wm.impl.TestWindowManager
import com.intellij.testFramework.PlatformLiteFixture

class IdeaClockTest : PlatformLiteFixture() {

    @Throws(Exception::class)
    public override fun setUp() {
        super.setUp()
        initApplication()
        Extensions.registerAreaClass("IDEA_PROJECT", null)
        myProject = MockProjectEx(testRootDisposable)
    }

    fun setUpWindowManager(): WindowManager {
        val windowManager = TestWindowManager()
        PlatformLiteFixture.getApplication().registerService(WindowManager::class.java, windowManager)
        return windowManager
    }

    fun testRegistration() {
        val windowManager = setUpWindowManager()
        val registration = ClockPluginRegistration(myProject)

        registration.projectOpened()

        val ideaClock = windowManager.getStatusBar(myProject)?.getWidget("IdeaClock")
        val presentation = ideaClock?.getPresentation(StatusBarWidget.PlatformType.DEFAULT) as StatusBarWidget.TextPresentation?

        val time = presentation!!.text
        assertTrue("Clock value '$time' does not match '\u25F7 \\d+:\\d+'", time.matches("^\u25F7 \\d+:\\d+( [AP]M)?".toRegex()))
    }
}
