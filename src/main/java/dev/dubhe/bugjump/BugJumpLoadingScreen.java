package dev.dubhe.bugjump;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Validate;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BugJumpLoadingScreen {
    public static final String MODID = "bugjump";
    private final MinecraftClient client;
    private final int patchSize;
    private final Identifier texture;

    private final Random random = new Random();
    private final ArrayList<FallingPatch> fallingPatches = new ArrayList<>();

    private float patchTimer = 0f;

    public BugJumpLoadingScreen(MinecraftClient client) {
        this.client = client;
        this.patchSize = 64;
        ModContainer mod = FabricLoader.getInstance().getModContainer("bugjump").get();
        String path = mod.getMetadata().getIconPath(512).get();
        NativeImageBackedTexture texture = getIconTexture(mod, path);
        this.texture = new Identifier(MODID, mod.getMetadata().getId() + "_icon");
        MinecraftClient.getInstance().getTextureManager().registerTexture(this.texture, texture);
        createPatch();
    }



    private static NativeImageBackedTexture getIconTexture(ModContainer iconSource, String iconPath) {
        try {
            Path path = iconSource.getPath(iconPath);
            try (InputStream inputStream = Files.newInputStream(path)) {
                NativeImage image = NativeImage.read(Objects.requireNonNull(inputStream));
                Validate.validState(image.getHeight() == image.getWidth(), "Must be square icon");
                return new NativeImageBackedTexture(image);
            }

        } catch (Throwable t) {
            return null;
        }
    }

    public void createPatch() {
        fallingPatches.add(new FallingPatch(
                random.nextDouble() * this.client.getWindow().getScaledWidth(), -patchSize,
                (random.nextDouble() - 0.5) * 0.6,
                random.nextDouble() * 3.0 + 1.0,
                random.nextDouble() / 2 + 0.5,
                patchSize
        ));
    }

    public void updatePatches(float delta, boolean ending) {
        for (FallingPatch patch : fallingPatches) {
            if (ending)
                patch.fallSpeed *= 1.0 + delta / 3;

            patch.update(delta);
        }

        patchTimer -= delta;

        if (patchTimer < 0f && !ending) {
            createPatch();

            patchTimer = random.nextFloat();
        }
    }

    public void renderPatches(MatrixStack matrices, float delta, boolean ending) {
        if (delta < 2.0f)
            updatePatches(delta, ending);

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (FallingPatch patch : fallingPatches) {
            patch.render(matrices);
        }
    }

    private static class FallingPatch {
        private double x;
        private double y;

        private final double horizontal;
        private final double scale;

        public double fallSpeed;

        private final int patchSize;

        public FallingPatch(double x, double y, double horizontal, double fallSpeed, double scale, int patchSize) {
            this.x = x;
            this.y = y;

            this.horizontal = horizontal;
            this.fallSpeed = fallSpeed;

            this.scale = scale;

            this.patchSize = patchSize;
        }

        public void update(float delta) {
            x += horizontal * delta;
            y += fallSpeed * delta;
        }

        public void render(MatrixStack matrices) {
            matrices.push();
            matrices.translate(x, y, 0);

            matrices.scale((float) scale, (float) scale, (float) scale);

            double x1 = -patchSize / 2d;
            double y1 = -patchSize / 2d;
            double x2 = patchSize / 2d;
            double y2 = patchSize / 2d;

            DrawableHelper.drawTexturedQuad(
                    matrices.peek().getPositionMatrix(),
                    (int) x1, (int) x2, (int) y1, (int) y2, 0,
                    0.0f, 1.0f, 0.0f, 1.0f
            );

            matrices.pop();
        }
    }
}
