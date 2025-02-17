package net.ppvr.artery.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.stream.Collectors;

public class IntFieldWidget extends TextFieldWidget {
    private int maxInt;

    public IntFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, int n, int maxInt) {
        super(textRenderer, x, y, width, height, null, Text.of("" + n));
        this.maxInt = maxInt;
        this.setChangedListener(s -> {
            s = s.chars().filter(c -> (c >= '0' && c <= '9')).mapToObj(c -> "" + (char) c).collect(Collectors.joining());
            s = String.valueOf(s.isEmpty() ? 0 : Math.min(Integer.parseInt(s), this.maxInt));
            if (s.equals(this.getText())) return;
            this.setText(s);
        });
        this.setInt(n);
    }

    public IntFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, int n) {
        this(textRenderer, x, y, width, height, n, 0);
    }

    public int getInt() {
        return this.getText().isEmpty() ? 0 : Integer.parseInt(this.getText());
    }

    public void setInt(int n) {
        this.setText(String.valueOf(n));
    }

    public int getMaxInt() {
        return maxInt;
    }

    public void setMaxInt(int maxInt) {
        this.maxInt = maxInt;
        if (this.getInt() > maxInt) {
            this.setInt(maxInt);
        }
    }
}
