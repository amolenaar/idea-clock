package ideaclock;

import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import com.intellij.util.text.DateFormatUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ClockStatusBarWidget implements StatusBarWidget, StatusBarWidget.TextPresentation, ActionListener {

    private final Timer timer = new Timer(1000, this);
    private StatusBar statusBar;

    private volatile String theTime = getTime();

    @NotNull
    @Override
    public String ID() {
        return "IdeaClock";
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation(@NotNull PlatformType platformType) {
        return this;
    }


    @Override
    public void install(@NotNull StatusBar statusBar) {
        this.statusBar = statusBar;
        timer.start();

    }

    @Override
    public void dispose() {
        timer.stop();
        statusBar = null;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String newTime = getTime();
        if (!newTime.equals(theTime)) {
            theTime = newTime;
            statusBar.updateWidget(ID());
        }
        timer.restart();
    }

    @NotNull
    @Override
    public String getText() {
        return theTime;
    }

    @NotNull
    private String getTime() {
        return DateFormatUtil.formatTime(System.currentTimeMillis());
    }

    @NotNull
    @Override
    public String getMaxPossibleText() {
        return null;
    }

    @Override
    public float getAlignment() {
        return 0;
    }

    @Nullable
    @Override
    public String getTooltipText() {
        return "time...";
    }

    @Nullable
    @Override
    public Consumer<MouseEvent> getClickConsumer() {
        return null;
    }
}
