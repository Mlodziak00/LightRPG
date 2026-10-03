package net.lightglow.racialfeats.client.screen;

import net.lightglow.racialfeats.RacialFeats;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EntityPreviewButton extends ClickableWidget {
    private final LivingEntity entity;
    private final float scale;
    private final boolean headOnly;
    private final Runnable onClick;

    private static final Identifier BUTTON_TEXTURE =
            RacialFeats.id("textures/gui/preview_widget.png");

    public EntityPreviewButton(
            int x, int y,
            int width, int height,
            LivingEntity entity,
            float scale,
            boolean headOnly,
            Text tooltip,
            Runnable onClick
    ) {
        super(x, y, width, height, Text.empty());
        this.entity = entity;
        this.scale = scale;
        this.headOnly = headOnly;
        this.onClick = onClick;

        if (tooltip != null) {
            this.setTooltip(Tooltip.of(tooltip));
        }
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // Background
        int bg = this.isHovered()
                ? 44
                : 0;

        context.drawTexture(
                BUTTON_TEXTURE,
                getX(),
                getY(),
                bg, 0,                 // texture u,v
                44,
                44,
                132,
                44
        );



        if (entity == null) return;

        float centerX = getX() + width / 2f;
        float centerY = getY() + height / 2f + 20;

        Quaternionf rotation = new Quaternionf()
                .rotateY((float) (-0.3f + Math.PI))
                .rotateX((float) 0.0f).rotateZ(180 * MathHelper.RADIANS_PER_DEGREE);

        Vector3f translation = new Vector3f(0, -0.0f, 0);

        InventoryScreen.drawEntity(
                context,
                centerX,
                centerY - 1,
                scale,
                translation,
                rotation,
                null,
                entity
        );
        context.drawTexture(
                BUTTON_TEXTURE,
                getX(),
                getY(),
                88, 0,
                44,
                44,
                132,
                44
        );
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        onClick.run();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
