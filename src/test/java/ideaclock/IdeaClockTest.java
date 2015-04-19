package ideaclock;

import com.intellij.mock.MockProjectEx;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.TestWindowManager;
import com.intellij.testFramework.PlatformLiteFixture;

public class IdeaClockTest extends PlatformLiteFixture {

    private TestWindowManager windowManager;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initApplication();
        Extensions.registerAreaClass("IDEA_PROJECT", null);
        myProject = new MockProjectEx(getTestRootDisposable());

        windowManager = new TestWindowManager();
        getApplication().registerService(WindowManager.class, windowManager);
    }

    public void testRegistration() {
        ClockPluginRegistration registration = new ClockPluginRegistration(myProject);

        registration.projectOpened();

        StatusBarWidget ideaClock = windowManager.getStatusBar(myProject).getWidget("IdeaClock");
        StatusBarWidget.TextPresentation presentation = (StatusBarWidget.TextPresentation) ideaClock.getPresentation(StatusBarWidget.PlatformType.DEFAULT);

        assertTrue(presentation.getText().matches("\u25F7 \\d\\d:\\d\\d"));
    }
}
