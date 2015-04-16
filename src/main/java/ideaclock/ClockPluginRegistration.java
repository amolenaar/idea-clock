package ideaclock;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

public class ClockPluginRegistration implements ProjectComponent {

    private final Project project;

    public ClockPluginRegistration(Project project) {
        this.project = project;
    }

    @NotNull
    public String getComponentName() {
        return "Clock";
    }

    @Override
    public void projectOpened() {
        WindowManager wm = WindowManager.getInstance();
        StatusBar statusBar = wm.getStatusBar(project);
        statusBar.addWidget(new ClockStatusBarWidget(), "before ReadOnlyAttribute");
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

}
