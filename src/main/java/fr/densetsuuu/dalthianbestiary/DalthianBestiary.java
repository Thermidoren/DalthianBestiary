package fr.densetsuuu.dalthianbestiary;

import fr.densetsuuu.dalthianbestiary.entities.EntitySaccageur;
import fr.densetsuuu.dalthianbestiary.init.ModTab;
import fr.densetsuuu.dalthianbestiary.proxy.CommonProxy;
import fr.densetsuuu.dalthianbestiary.renderer.SaccageurGeoRenderer;
import fr.densetsuuu.dalthianbestiary.util.ModConfigManager;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = Resource.MOD_ID, name = Resource.NAME, version = Resource.VERSION)
public class DalthianBestiary {

    public static final CreativeTabs MOD_TAB = new ModTab("tabModTab");
    @Instance
    public static DalthianBestiary instance;
    @SidedProxy(clientSide = Resource.CLIENT_PROXY_CLASS, serverSide = Resource.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    private static Logger logger; // used to print messages to our console output

    /** This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.*/
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
    }

    /** This is the second initialization event. Register custom recipes*/
    @EventHandler
    public static void init(FMLInitializationEvent event) {
        // Initializing all Recipes
        logger.info("saving / loading mod configuration");
        // we need to call our function here, in order to execute the save / load
        ModConfigManager.WorldGenerationConfig(event);
    }

    /** This is the final initialization event. Register actions from other mods here*/
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public static void registerRenderers(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntitySaccageur.class, SaccageurGeoRenderer::new);
    }
}