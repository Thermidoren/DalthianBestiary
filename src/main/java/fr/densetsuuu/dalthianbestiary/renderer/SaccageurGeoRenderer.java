package fr.densetsuuu.dalthianbestiary.renderer;

import fr.densetsuuu.dalthianbestiary.entities.EntitySaccageur;
import fr.densetsuuu.dalthianbestiary.geo.SaccageurGeoModel;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SaccageurGeoRenderer extends GeoEntityRenderer<EntitySaccageur> {
    public SaccageurGeoRenderer(RenderManager renderManager) {
        super(renderManager, new SaccageurGeoModel());
    }
}
