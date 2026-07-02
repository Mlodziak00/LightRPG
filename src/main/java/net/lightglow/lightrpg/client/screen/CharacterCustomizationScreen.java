package net.lightglow.lightrpg.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.component.entity.PlayerAppearanceComponent;
import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
import net.lightglow.lightrpg.race.Race;
import net.lightglow.lightrpg.network.AppearanceColorSyncPayload;
import net.lightglow.lightrpg.reg.RaceRegistry;
import net.lightglow.lightrpg.network.AppearanceSyncPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharacterCustomizationScreen extends Screen {

    private RightPanelMode rightPanelMode = RightPanelMode.MAIN;
    private BottomPanelMode bottomPanelMode = BottomPanelMode.DEFAULT;
    private CustomizationCategory activeCategory = null;
    private ColorTarget activeColorTarget = null;

    private static final Identifier UI_TEXTURE_BASE =
            LightRPG.id("textures/gui/character_creation_base.png");
    private static final Identifier UI_TEXTURE_GRID =
            LightRPG.id("textures/gui/character_creation_grid.png");
    private Gender selectedGender = Gender.MALE;
    private Identifier selectedHair;
    private Identifier selectedEyes;
    private Identifier selectedClothes;
    private Identifier selectedRace;

    private int colorR = 255;
    private int colorG = 255;
    private int colorB = 255;

    private final List<ClickableWidget> rightPanelButtons = new ArrayList<>();
    private final List<ClickableWidget> bottomPanelWidgets = new ArrayList<>();

    private boolean canChangeRace = true;

    public CharacterCustomizationScreen(boolean canChangeRace) {
        super(Text.literal("CharCustom Screen"));
        this.canChangeRace = canChangeRace;
    }

    private static final int UI_WIDTH = 360;
    private static final int UI_HEIGHT = 220;
    private static final int GAP = 4;
    private static final int LEFT_WIDTH = 120;
    private static final int RIGHT_WIDTH = 120;
    private static final int CENTER_WIDTH =
            UI_WIDTH - LEFT_WIDTH - RIGHT_WIDTH - (GAP * 2);
    private static final int CENTER_TOP_HEIGHT = 140;
    private static final int CENTER_BOTTOM_HEIGHT =
            UI_HEIGHT - CENTER_TOP_HEIGHT - GAP;

    private int currentGridPage = 0;
    private boolean updatingFromHex = false;
    private boolean updatingFromSliders = false;

    private static final int GRID_COLUMNS = 2;
    private static final int GRID_ROWS = 3;
    private static final int ENTRIES_PER_PAGE = GRID_COLUMNS * GRID_ROWS;

    private static final int ATTR_POSITIVE = 0xFF55FF55;
    private static final int ATTR_NEGATIVE = 0xFFFF5555;
    private static final int ATTR_NEUTRAL  = 0xFFCCCCCC;

    private OtherClientPlayerEntity previewPlayer;
    private ClientPlayerEntity sourcePlayer;

    private TextFieldWidget nameField;
    private TextFieldWidget hexColorField;
    private SliderWidget sliderR;
    private SliderWidget sliderG;
    private SliderWidget sliderB;

    @Override
    protected void init() {

        createPreviewPlayer();
        buildRightPanel();
        buildBottomPanel();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        int startX = (this.width - UI_WIDTH) / 2;
        int startY = (this.height - UI_HEIGHT) / 2;

        int leftX = startX;
        int centerX = leftX + LEFT_WIDTH + GAP;
        int rightX = centerX + CENTER_WIDTH + GAP;

        int topY = startY;
        int centerBottomY = topY + CENTER_TOP_HEIGHT + GAP;

        int uiX = (this.width - UI_WIDTH) / 2;
        int uiY = (this.height - UI_HEIGHT) / 2;

        context.drawTexture(
                rightPanelMode == RightPanelMode.MAIN ? UI_TEXTURE_BASE : UI_TEXTURE_GRID,
                uiX,
                uiY,
                0, 0,                 // texture u,v
                UI_WIDTH,
                UI_HEIGHT,
                UI_WIDTH,
                UI_HEIGHT
        );
        drawPreviewPlayer(context, mouseX, mouseY);
        drawLeftPanelContent(context);
    }

    private void createPreviewPlayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }
        this.sourcePlayer = client.player;
        this.previewPlayer = new OtherClientPlayerEntity(
                client.world,
                sourcePlayer.getGameProfile()
        );

    }

    private void drawPreviewPlayer(DrawContext context, int mouseX, int mouseY) {
        if (this.previewPlayer == null) return;
        int centerX = ((this.width) / 2);
        int centerY = ((this.height) / 2) + 10;
        float yaw = (centerX - mouseX) * 0.005f;
        Quaternionf rotation = new Quaternionf()
                .rotateY((yaw + (float) Math.PI)).rotateZ(180 * MathHelper.RADIANS_PER_DEGREE);
        this.previewPlayer.setCustomNameVisible(false);
        InventoryScreen.drawEntity(
                context,
                centerX,
                centerY,
                55,
                new Vector3f(0, 0, 0),
                rotation,
                null,
                this.previewPlayer
        );
    }

    private void clearRightPanelButtons() {
        for (ClickableWidget button : rightPanelButtons) {
            this.remove(button);
        }
        rightPanelButtons.clear();
    }

    private void buildColorBottomPanel() {
        loadColorFromComponent(); // <-- IMPORTANT

        int startX = (this.width - UI_WIDTH) / 2 + LEFT_WIDTH + GAP + 10;
        int startY = (this.height - UI_HEIGHT) / 2 + CENTER_TOP_HEIGHT + GAP + 6;

        sliderR = createColorSlider(startX, startY + 2, "R", 0, colorR);
        sliderG = createColorSlider(startX, startY + 15, "G", 1, colorG);
        sliderB = createColorSlider(startX, startY + 28, "B", 2, colorB);

        hexColorField = new TextFieldWidget(
                this.textRenderer,
                startX + 1,
                startY + 41,
                CENTER_WIDTH - 22,
                11,
                Text.literal("Hex")
        );

        hexColorField.setMaxLength(7); // #RRGGBB
        hexColorField.setText("#"+rgbToHex());
        hexColorField.setChangedListener(text -> {
            if (updatingFromSliders) return;
            tryApplyLiveHex(text);
        });

        addBottom(hexColorField);
        addBottom(sliderR);
        addBottom(sliderG);
        addBottom(sliderB);
    }

    private void buildDefaultBottomPanel() {
        int startX = (this.width - UI_WIDTH) / 2 + LEFT_WIDTH + GAP + 10;
        int startY = (this.height - UI_HEIGHT) / 2 + CENTER_TOP_HEIGHT + GAP + 8;

        nameField = new TextFieldWidget(
                this.textRenderer,
                startX,
                startY,
                CENTER_WIDTH - 20,
                20,
                Text.literal("Character Name")
        );
        nameField.setMaxLength(32);

        ButtonWidget finalize = ButtonWidget.builder(
                Text.literal("Finalize"),
                btn -> {
                    if (previewPlayer == null) return;

                    var comp =
                            PlayerAppearanceComponent.KEY.get(previewPlayer);
                    var compR =
                            PlayerImpactfulComponent.KEY.get(previewPlayer);

                    AppearanceSyncPayload payload = new AppearanceSyncPayload(
                            compR.getRace(),
                            comp.isMale(),
                            comp.getHairStyle(),
                            comp.getEyeType(),
                            comp.getClothingType()
                    );
                    AppearanceColorSyncPayload payloadC = new AppearanceColorSyncPayload(
                            comp.getSkinColor(),
                            comp.getHairColor(),
                            comp.getEyeColor(),
                            comp.getClothingColor()
                    );

                    ClientPlayNetworking.send(payload);
                    ClientPlayNetworking.send(payloadC);
                    close();
                }
        ).dimensions(
                startX + 22,
                startY + 30,
                50,
                18
        ).build();

        addBottom(nameField);
        addBottom(finalize);
    }
    private SliderWidget createColorSlider(
            int x,
            int y,
            String label,
            int channel,
            int initialValue
    ) {
        return new SliderWidget(
                x + 1,
                y,
                CENTER_WIDTH - 22,
                11,
                Text.literal(label+ ": " + (initialValue)),
                initialValue / 255.0
        ) {
            @Override
            public void updateMessage() {
                int value = (int)(this.value * 255);
                this.setMessage(Text.literal(label + ": " + value));
            }

            @Override
            protected void applyValue() {
                if (updatingFromHex) return;

                updatingFromSliders = true;

                int v = (int)(this.value * 255);

                if (channel == 0) colorR = v;
                if (channel == 1) colorG = v;
                if (channel == 2) colorB = v;

                previewColorUpdate();
                updateHexFieldFromRGB();

                updatingFromSliders = false;
            }
        };
    }

    private String rgbToHex() {
        return String.format("%02X%02X%02X", colorR, colorG, colorB);
    }

    private void updateHexFieldFromRGB() {
        if (hexColorField == null) return;

        updatingFromSliders = true;
        hexColorField.setText("#"+rgbToHex());
        updatingFromSliders = false;
    }

    private void tryApplyLiveHex(String text) {

        if (text.startsWith("#")) {
            text = text.substring(1);
        }

        if (text.length() != 6) return;

        try {
            int rgb = Integer.parseInt(text, 16);

            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            updatingFromHex = true;

            colorR = r;
            colorG = g;
            colorB = b;

            if (sliderR != null) sliderR.value = r / 255.0;
            if (sliderG != null) sliderG.value = g / 255.0;
            if (sliderB != null) sliderB.value = b / 255.0;

            // Force slider labels to refresh
            if (sliderR != null) sliderR.updateMessage();
            if (sliderG != null) sliderG.updateMessage();
            if (sliderB != null) sliderB.updateMessage();

            previewColorUpdate();

        } catch (NumberFormatException ignored) {
        } finally {
            updatingFromHex = false;
        }
    }

    private void previewColorUpdate() {
        int rgb = (colorR << 16) | (colorG << 8) | colorB;

        if (activeColorTarget == ColorTarget.RACE) {
            PlayerAppearanceComponent.KEY.get(previewPlayer).setSkinColor(rgb);
        }

        if (activeColorTarget == ColorTarget.HAIR) {
            PlayerAppearanceComponent.KEY.get(previewPlayer).setHairColor(rgb);
        }

        if (activeColorTarget == ColorTarget.EYES) {
            PlayerAppearanceComponent.KEY.get(previewPlayer).setEyeColor(rgb);
        }

        if (activeColorTarget == ColorTarget.CLOTHES) {
            PlayerAppearanceComponent.KEY.get(previewPlayer).setClothingColor(rgb);
        }
    }

    private int getActiveColorFromComponent() {
        if (previewPlayer == null) return 0xFFFFFF;

        PlayerAppearanceComponent appearance =
                PlayerAppearanceComponent.KEY.get(previewPlayer);

        if (appearance == null) return 0xFFFFFF;

        return switch (activeColorTarget) {
            case RACE -> appearance.getSkinColor();
            case HAIR -> appearance.getHairColor();
            case EYES -> appearance.getEyeColor();
            case CLOTHES -> appearance.getClothingColor();
        };
    }

    private void loadColorFromComponent() {
        int color = getActiveColorFromComponent();

        colorR = (color >> 16) & 0xFF;
        colorG = (color >> 8) & 0xFF;
        colorB = color & 0xFF;
    }

    private void addBottom(ClickableWidget widget) {
        this.addDrawableChild(widget);
        this.bottomPanelWidgets.add(widget);
    }

    private void buildRightPanel() {
        clearRightPanelButtons();

        if (rightPanelMode == RightPanelMode.MAIN) {
            buildMainRightPanel();
        } else if (rightPanelMode == RightPanelMode.GRID) {
            currentGridPage = 0;
            buildGridRightPanel();
        }
    }
    private void buildBottomPanel() {
        clearBottomPanel();

        if (bottomPanelMode == BottomPanelMode.DEFAULT) {
            buildDefaultBottomPanel();
        } else {
            buildColorBottomPanel();
        }
    }

    private void drawLeftPanelContent(DrawContext context) {
        if (selectedRace == null) selectedRace = RaceRegistry.HUMAN.getId();

        Race race = RaceRegistry.get(selectedRace);
        if (race == null) return;

        int x = (this.width - UI_WIDTH) / 2 + 8;
        int y = (this.height - UI_HEIGHT) / 2 + 16;

        // Race name
        context.drawText(
                this.textRenderer,
                race.getName(),
                x,
                y,
                0x000000,
                false
        );

        y += 16;

        // Description (wrapped)
        context.drawTextWrapped(
                this.textRenderer,
                race.getDescription(),
                x,
                y,
                LEFT_WIDTH - 16,
                0xDDDDDD
        );

        y += 46;

        // Attributes title
        context.drawText(
                this.textRenderer,
                Text.literal("Attributes"),
                x,
                y,
                0xFFFFFF,
                false
        );

        y += 12;

        // Attribute list
        for (Map.Entry<RegistryEntry<EntityAttribute>, EntityAttributeModifier> entry
                : race.getAttributes().entrySet()) {

            RegistryEntry<EntityAttribute> attributeEntry = entry.getKey();
            EntityAttributeModifier modifier = entry.getValue();

            int color = modifier.value() >= 0 ? ATTR_POSITIVE : ATTR_NEGATIVE;

            Text line = Text.literal("")
                    .append(formatAttributeValue(modifier))
                    .append(" ")
                    .append(Text.translatable(attributeEntry.value().getTranslationKey()));

            context.drawText(
                    this.textRenderer,
                    line,
                    x,
                    y,
                    color,
                    false
            );

            y += 10;
        }
    }


    private void buildMainRightPanel() {
        int startX = (this.width + UI_WIDTH) / 2 - RIGHT_WIDTH + 10;
        int y = (this.height - UI_HEIGHT) / 2 + 10;

        // Male button
        addRightButton(ButtonWidget.builder(
                        Text.literal("Male"),
                        btn -> {
                            selectedGender = Gender.MALE;
                            buildRightPanel();
                            updateScreenPlayerGender(true);
                        }
                ).dimensions(startX - 3, y, 46, 20)
                .build(), selectedGender == Gender.MALE);

        // Female button
        addRightButton(ButtonWidget.builder(
                        Text.literal("Female"),
                        btn -> {
                            selectedGender = Gender.FEMALE;
                            buildRightPanel();
                            updateScreenPlayerGender(false);
                        }
                ).dimensions(startX + 48, y, 55, 20)
                .build(), selectedGender == Gender.FEMALE);

        y += 30;

        // Category buttons
        addCategoryButton("Race", CustomizationCategory.RACE, y);
        y += 24;
        addCategoryButton("Hair", CustomizationCategory.HAIR, y);
        y += 25;
        addCategoryButton("Eyes", CustomizationCategory.EYES, y);
        y += 25;
        addCategoryButton("Clothes", CustomizationCategory.CLOTHES, y);
    }

    private void addCategoryButton(String text, CustomizationCategory category, int y) {
        int startX = (this.width + UI_WIDTH) / 2 - RIGHT_WIDTH + 10;

        ButtonWidget button = ButtonWidget.builder(
                Text.literal(text),
                btn -> {
                    activeCategory = category;
                    rightPanelMode = RightPanelMode.GRID;
                    buildRightPanel();
                }
        ).dimensions(startX, y, RIGHT_WIDTH - 20, 20).build();

        addRightButton(button);
    }

    private void buildGridRightPanel() {
        clearRightPanelButtons();

        int panelX = (this.width + UI_WIDTH) / 2 - RIGHT_WIDTH + 13;
        int panelY = (this.height - UI_HEIGHT) / 2 + 5;

        int columns = 2;
        int buttonSize = 44;
        int gap = 2;

        if (activeCategory == CustomizationCategory.RACE) {
            bottomPanelMode = BottomPanelMode.COLOR;
            activeColorTarget = ColorTarget.RACE;
            loadColorFromComponent();
            buildBottomPanel();
            buildRaceGrid(panelX, panelY, columns, buttonSize, gap);
            return;
        }

        if (activeCategory == CustomizationCategory.HAIR) {
            bottomPanelMode = BottomPanelMode.COLOR;
            activeColorTarget = ColorTarget.HAIR;
            loadColorFromComponent();
            buildBottomPanel();

            Race race = RaceRegistry.get(selectedRace);

            buildCustomizationGrid(
                    panelX, panelY,
                    columns, buttonSize, gap,
                    race.getHairs(),
                    CustomizationCategory.HAIR
            );
            return;
        }
        if (activeCategory == CustomizationCategory.EYES) {
            bottomPanelMode = BottomPanelMode.COLOR;
            activeColorTarget = ColorTarget.EYES;
            loadColorFromComponent();
            buildBottomPanel();

            Race race = RaceRegistry.get(selectedRace);

            buildCustomizationGrid(
                    panelX, panelY,
                    columns, buttonSize, gap,
                    race.getEyes(),
                    CustomizationCategory.EYES
            );
            return;
        }

        if (activeCategory == CustomizationCategory.CLOTHES) {
            bottomPanelMode = BottomPanelMode.COLOR;
            activeColorTarget = ColorTarget.CLOTHES;
            loadColorFromComponent();
            buildBottomPanel();

            Race race = RaceRegistry.get(selectedRace);

            buildCustomizationGrid(
                    panelX, panelY,
                    columns, buttonSize, gap,
                    race.getClothes(),
                    CustomizationCategory.CLOTHES
            );
            return;
        }
    }

    private void buildRaceGrid(
            int startX,
            int startY,
            int columns,
            int size,
            int gap
    ) {
        List<Race> races = List.copyOf(RaceRegistry.getAll());

        int startIndex = currentGridPage * ENTRIES_PER_PAGE;
        int endIndex = Math.min(startIndex + ENTRIES_PER_PAGE, races.size());

        int localIndex = 0;

        for (int i = startIndex; i < endIndex; i++) {
            Race race = races.get(i);
            if (race.isHidden()) continue;

            int col = localIndex % GRID_COLUMNS;
            int row = localIndex / GRID_COLUMNS;

            int x = (startX + col * (size + gap)) + 2;
            int y = startY + row * (size + (gap + 12));

            OtherClientPlayerEntity entity = createPreviewClone();
            copyAppearanceForRacePreview(entity, race.getId());


            EntityPreviewButton button = new EntityPreviewButton(
                    x, y,
                    size, size,
                    entity,
                    20f,
                    false,
                    race.getName(),
                    () -> {
                        selectedRace = race.getId();
                        bottomPanelMode = BottomPanelMode.DEFAULT;
                        activeColorTarget = null;
                        buildBottomPanel();
                        closeGrid();
                        PlayerImpactfulComponent.KEY.get(previewPlayer).setRace(race.getId());

                        Race newRace = RaceRegistry.get(race.getId());
                        validateAppearanceForRace(newRace);
                    }
            );

            addRightButton(button);
            localIndex++;
        }

        addGridPageButtons(startX, startY, races.size());
    }
    private void validateEntry(
            Identifier current,
            Map<Identifier, String> map,
            java.util.function.Consumer<Identifier> setter
    ) {
        if (map == null || map.isEmpty()) {
            setter.accept(LightRPG.id("empty"));
            return;
        }

        if (!map.containsKey(current)) {
            Identifier first = map.keySet().iterator().next();
            setter.accept(first);
        }
    }
    private void validateAppearanceForRace(Race race) {
        PlayerAppearanceComponent comp =
                PlayerAppearanceComponent.KEY.get(previewPlayer);

        // HAIR
        validateEntry(
                comp.getHairStyle(),
                race.getHairs(),
                comp::setHairStyle
        );

        // EYES
        validateEntry(
                comp.getEyeType(),
                race.getEyes(),
                comp::setEyeType
        );

        // CLOTHES
        validateEntry(
                comp.getClothingType(),
                race.getClothes(),
                comp::setClothingType
        );
    }

    private void buildCustomizationGrid(
            int startX,
            int startY,
            int columns,
            int size,
            int gap,
            Map<Identifier, String> entries,
            CustomizationCategory category
    ) {
        if (entries == null || entries.isEmpty()) {
            entries = Map.of(
                    LightRPG.id("empty"),
                    "Empty"
            );
        }
        List<Map.Entry<Identifier, String>> list =
                new ArrayList<>(entries.entrySet());

        int startIndex = currentGridPage * ENTRIES_PER_PAGE;
        int endIndex = Math.min(startIndex + ENTRIES_PER_PAGE, list.size());

        int localIndex = 0;

        for (int i = startIndex; i < endIndex; i++) {
            Map.Entry<Identifier, String> entry = list.get(i);

            Identifier id = entry.getKey();
            String name = entry.getValue();

            int col = localIndex % GRID_COLUMNS;
            int row = localIndex / GRID_COLUMNS;

            int x = (startX + col * (size + gap)) + 2;
            int y = startY + row * (size + (gap + 12));

            OtherClientPlayerEntity entity = createPreviewClone();
            copyAppearanceToClone(entity, true);

            EntityPreviewButton button = null;

            switch (category) {

                case HAIR -> {
                    copyAppearanceToClone(entity, true);
                    PlayerAppearanceComponent.KEY.get(entity).setHairStyle(id);

                    button = new EntityPreviewButton(
                            x, y,
                            size, size,
                            entity,
                            20f,
                            true,
                            Text.literal(name),
                            () -> {
                                selectedHair = id;

                                PlayerAppearanceComponent.KEY
                                        .get(previewPlayer)
                                        .setHairStyle(id);

                                bottomPanelMode = BottomPanelMode.DEFAULT;
                                activeColorTarget = null;
                                buildBottomPanel();
                                closeGrid();
                            }
                    );
                }

                case EYES -> {
                    PlayerAppearanceComponent.KEY.get(entity).setEyeType(id);

                    button = new EntityPreviewButton(
                            x, y,
                            size, size,
                            entity,
                            20f,
                            true,
                            Text.literal(name),
                            () -> {
                                selectedEyes = id;

                                PlayerAppearanceComponent.KEY
                                        .get(previewPlayer)
                                        .setEyeType(id);

                                bottomPanelMode = BottomPanelMode.DEFAULT;
                                activeColorTarget = null;
                                buildBottomPanel();
                                closeGrid();
                            }
                    );
                }

                case CLOTHES -> {
                    PlayerAppearanceComponent.KEY.get(entity).setClothingType(id);

                    button = new EntityPreviewButton(
                            x, y,
                            size, size,
                            entity,
                            20f,
                            true,
                            Text.literal(name),
                            () -> {
                                selectedClothes = id;

                                PlayerAppearanceComponent.KEY
                                        .get(previewPlayer)
                                        .setClothingType(id);

                                bottomPanelMode = BottomPanelMode.DEFAULT;
                                activeColorTarget = null;
                                buildBottomPanel();
                                closeGrid();
                            }
                    );
                }

                default -> {}
            }

            if (button != null) {
                addRightButton(button);
                localIndex++;
            }
        }

        addGridPageButtons(startX, startY, list.size());
    }


    /*private void buildIntGrid(
            int startX,
            int startY,
            int max,
            java.util.function.IntConsumer onSelect,
            int columns,
            int size,
            int gap,
            CustomizationCategory category
    ) {
        int totalEntries = max + 1;

        int startIndex = currentGridPage * ENTRIES_PER_PAGE;
        int endIndex = Math.min(startIndex + ENTRIES_PER_PAGE, totalEntries);

        int localIndex = 0;

        for (int i = startIndex; i < endIndex; i++) {
            int index = i;

            int col = localIndex % GRID_COLUMNS;
            int row = localIndex / GRID_COLUMNS;

            int x = (startX + col * (size + gap)) + 2;
            int y = startY + row * (size + (gap + 12));

            OtherClientPlayerEntity entity = createPreviewClone();
            EntityPreviewButton button;

            if (category == CustomizationCategory.HAIR) {
                PlayerAppearanceComponent.KEY.get(entity).setHairStyle(index);
                button = new EntityPreviewButton(
                        x, y,
                        size, size,
                        entity,
                        20f,
                        true,
                        Text.literal("Hair " + (index + 1)),
                        () -> {
                            selectedHair = index;
                            bottomPanelMode = BottomPanelMode.DEFAULT;
                            activeColorTarget = null;
                            buildBottomPanel();
                            closeGrid();
                            PlayerAppearanceComponent.KEY.get(previewPlayer).setHairStyle(index);
                        }
                );
            } else if (category == CustomizationCategory.EYES) {
                PlayerAppearanceComponent.KEY.get(entity).setEyeType(index);
                button = new EntityPreviewButton(
                        x, y,
                        size, size,
                        entity,
                        20f,
                        true,
                        Text.literal("Eye " + (index + 1)),
                        () -> {
                            selectedEyes = index;
                            bottomPanelMode = BottomPanelMode.DEFAULT;
                            activeColorTarget = null;
                            buildBottomPanel();
                            closeGrid();
                            PlayerAppearanceComponent.KEY.get(previewPlayer).setEyeType(index);
                        }
                );
            } else {
                PlayerAppearanceComponent.KEY.get(entity).setClothingType(index);
                button = new EntityPreviewButton(
                        x, y,
                        size, size,
                        entity,
                        20f,
                        true,
                        Text.literal("Clothes " + (index + 1)),
                        () -> {
                            selectedClothes = index;
                            bottomPanelMode = BottomPanelMode.DEFAULT;
                            activeColorTarget = null;
                            buildBottomPanel();
                            closeGrid();
                            PlayerAppearanceComponent.KEY.get(previewPlayer).setClothingType(index);
                        }
                );
            }

            addRightButton(button);
            localIndex++;
        }

        addGridPageButtons(startX, startY, totalEntries);
    }*/

    private void addGridPageButtons(int panelX, int panelY, int totalEntries) {
        int totalPages = Math.max(1,
                (int) Math.ceil((double) totalEntries / ENTRIES_PER_PAGE)
        );

        if (totalPages <= 1) return;

        int buttonY = panelY + (GRID_ROWS * (44 + 14)) + 2;

        // LEFT
        ButtonWidget left = ButtonWidget.builder(
                Text.literal("<"),
                btn -> {
                    if (currentGridPage > 0) {
                        currentGridPage--;
                        buildGridRightPanel();
                    }
                }
        ).dimensions(panelX + 6, buttonY, 20, 16).build();

        // RIGHT
        ButtonWidget right = ButtonWidget.builder(
                Text.literal(">"),
                btn -> {
                    if (currentGridPage < totalPages - 1) {
                        currentGridPage++;
                        buildGridRightPanel();
                    }
                }
        ).dimensions(panelX + RIGHT_WIDTH - 40, buttonY, 20, 16).build();

        left.active = currentGridPage > 0;
        right.active = currentGridPage < totalPages - 1;

        addRightButton(left);
        addRightButton(right);
    }

    private void closeGrid() {
        activeCategory = null;
        rightPanelMode = RightPanelMode.MAIN;
        buildRightPanel();
    }
    private void clearBottomPanel() {
        for (ClickableWidget w : bottomPanelWidgets) {
            this.remove(w);
        }
        bottomPanelWidgets.clear();
    }

    private void addRightButton(ClickableWidget button, boolean selected) {
        if (selected) {
            button.active = false; // acts like a toggle
        }
        this.addDrawableChild(button);
        rightPanelButtons.add(button);
    }
    private void addRightButton(ClickableWidget button) {
        this.addDrawableChild(button);
        rightPanelButtons.add(button);
    }
    private void updateScreenPlayerGender(boolean gender){
        var comp = PlayerAppearanceComponent.KEY.get(previewPlayer);
        comp.setMale(gender);
    }

    private void updateScreenPlayerHair(Identifier hair){
        var comp = PlayerAppearanceComponent.KEY.get(previewPlayer);
        comp.setHairStyle(hair);
    }
    private void updateScreenPlayerClothes(Identifier hair){
        var comp = PlayerAppearanceComponent.KEY.get(previewPlayer);
        comp.setClothingType(hair);
    }

    private void updateScreenPlayerEye(Identifier eye){
        var comp = PlayerAppearanceComponent.KEY.get(previewPlayer);
        comp.setEyeType(eye);
    }

    private void updateScreenPlayerRace(Identifier race){
        var comp = PlayerImpactfulComponent.KEY.get(previewPlayer);
        comp.setRace(race);
    }

    private void copyAppearanceToClone(OtherClientPlayerEntity clone, boolean copyRace) {
        if (previewPlayer == null) return;

        PlayerImpactfulComponent srcImpact =
                PlayerImpactfulComponent.KEY.get(previewPlayer);
        PlayerImpactfulComponent cloneImpact =
                PlayerImpactfulComponent.KEY.get(clone);

        if (copyRace) cloneImpact.setRace(srcImpact.getRace());

        PlayerAppearanceComponent srcAppearance =
                PlayerAppearanceComponent.KEY.get(previewPlayer);
        PlayerAppearanceComponent cloneAppearance =
                PlayerAppearanceComponent.KEY.get(clone);

        cloneAppearance.setHairStyle(srcAppearance.getHairStyle());
        cloneAppearance.setEyeType(srcAppearance.getEyeType());
        cloneAppearance.setClothingType(srcAppearance.getClothingType());

        cloneAppearance.setHairColor(srcAppearance.getHairColor());
        cloneAppearance.setEyeColor(srcAppearance.getEyeColor());
    }

    private void validateAppearanceForClone(
            PlayerAppearanceComponent comp,
            Race race
    ) {
        // HAIR
        if (race.getHairs().isEmpty()) {
            comp.setHairStyle(LightRPG.id("empty"));
        } else if (!race.getHairs().containsKey(comp.getHairStyle())) {
            comp.setHairStyle(
                    race.getHairs().keySet().iterator().next()
            );
        }

        // EYES
        if (race.getEyes().isEmpty()) {
            comp.setEyeType(LightRPG.id("empty"));
        } else if (!race.getEyes().containsKey(comp.getEyeType())) {
            comp.setEyeType(
                    race.getEyes().keySet().iterator().next()
            );
        }

        // CLOTHES
        if (race.getClothes().isEmpty()) {
            comp.setClothingType(LightRPG.id("empty"));
        } else if (!race.getClothes().containsKey(comp.getClothingType())) {
            comp.setClothingType(
                    race.getClothes().keySet().iterator().next()
            );
        }
    }

    private void copyAppearanceForRacePreview(
            OtherClientPlayerEntity clone,
            Identifier raceId
    ) {
        if (previewPlayer == null) return;

        // Copy race first
        PlayerImpactfulComponent cloneImpact =
                PlayerImpactfulComponent.KEY.get(clone);
        cloneImpact.setRace(raceId);

        PlayerAppearanceComponent src =
                PlayerAppearanceComponent.KEY.get(previewPlayer);
        PlayerAppearanceComponent dst =
                PlayerAppearanceComponent.KEY.get(clone);

        // Copy colors (always safe)
        dst.setHairColor(src.getHairColor());
        dst.setEyeColor(src.getEyeColor());

        // Copy appearance IDs
        dst.setHairStyle(src.getHairStyle());
        dst.setEyeType(src.getEyeType());
        dst.setClothingType(src.getClothingType());

        // Validate against that race
        Race race = RaceRegistry.get(raceId);
        validateAppearanceForClone(dst, race);
    }

    private OtherClientPlayerEntity createPreviewClone() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return null;

        OtherClientPlayerEntity clone =
                new OtherClientPlayerEntity(
                        client.world,
                        client.player.getGameProfile()
                );

        clone.setCustomNameVisible(false);
        return clone;
    }

    private Text getAttributeDisplayName(Identifier id) {
        return Text.literal(id.getPath());
    }

    private Text formatAttributeValue(EntityAttributeModifier modifier) {
        double value = modifier.value();

        if (modifier.operation() == EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                || modifier.operation() == EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {

            int percent = (int) (value * 100);
            return Text.literal((percent > 0 ? "+" : "") + percent + "%");
        }

        return Text.literal((value > 0 ? "+" : "") + (int) value);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public enum RightPanelMode {
        MAIN,
        GRID
    }

    public enum CustomizationCategory {
        RACE,
        HAIR,
        EYES,
        CLOTHES
    }

    public enum Gender {
        MALE,
        FEMALE
    }
    public enum BottomPanelMode {
        DEFAULT,
        COLOR
    }
    public enum ColorTarget {
        RACE,
        HAIR,
        EYES,
        CLOTHES
    }
}

