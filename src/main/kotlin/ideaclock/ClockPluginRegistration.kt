package ideaclock

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager

class ClockPluginRegistration(private val project: Project) : ProjectComponent {

    override fun getComponentName(): String {
        return "Clock"
    }

    override fun projectOpened() {
        val statusBar = WindowManager.getInstance().getStatusBar(project)
        statusBar?.addWidget(ClockStatusBarWidget(), "before ReadOnlyAttribute")
    }

    override fun projectClosed() {
    }

    override fun initComponent() {
    }

    override fun disposeComponent() {
    }

}
