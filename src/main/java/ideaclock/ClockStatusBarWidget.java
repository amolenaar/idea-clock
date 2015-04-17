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

    private static final long MINUTES = 60000;
    private final Timer timer = new Timer(1000, this);
    private StatusBar statusBar;

    private volatile long lastTime = getTime();

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
        long newTime = getTime();
        if (newTime / MINUTES != lastTime / MINUTES) {
            lastTime = newTime;
            statusBar.updateWidget(ID());
        }
        timer.restart();
    }

    @NotNull
    @Override
    public String getText() {
        return "It's: " + DateFormatUtil.formatTime(lastTime);
    }

    @NotNull
    @Override
    public String getMaxPossibleText() {
        return null;
    }

    @Override
    public float getAlignment() {
        return 0.5f;
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

    @NotNull
    long getTime() {
        return System.currentTimeMillis();
    }
}
