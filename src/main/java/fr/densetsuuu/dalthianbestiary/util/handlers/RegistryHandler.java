package fr.densetsuuu.dalthianbestiary.util.handlers;

import fr.densetsuuu.dalthianbestiary.entities.EntitySaccageur;
import fr.densetsuuu.dalthianbestiary.init.SoundsRegistry;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = Resource.MOD_ID)
public class RegistryHandler {


    private static final Logger LOGGER = LogManager.getLogger(Resource.MOD_ID + " Event Subscriber");

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onEntitiesRegister(RegistryEvent.Register<EntityEntry> event) {

        event.getRegistry().registerAll(
                EntityEntryBuilder.create()
                        .entity(EntitySaccageur.class)
                        .id(new ResourceLocation(Resource.MOD_ID, "saccageur"), 0)
                        .name("saccageur")
                        .tracker(80, 3, true)
                        .build()
        );
        LOGGER.debug("Registered entities");
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                SoundsRegistry.SACCAGEUR_HOWLING
        );
        LOGGER.debug("Registered sounds events");
    }
}