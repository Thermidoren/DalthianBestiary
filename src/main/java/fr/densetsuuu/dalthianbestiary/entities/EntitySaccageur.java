package fr.densetsuuu.dalthianbestiary.entities;

import fr.densetsuuu.dalthianbestiary.DalthianBestiary;
import fr.densetsuuu.dalthianbestiary.entities.ai.EntityAIDalthianAttack;
import fr.densetsuuu.dalthianbestiary.init.LootTablesRegistry;
import fr.densetsuuu.dalthianbestiary.init.SoundsRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntitySaccageur extends EntityMob implements IAnimatable, IAnimationTickable {

    //#region Attributes
    public static final DataParameter<Boolean> IS_HOWLING = EntityDataManager.createKey(EntitySaccageur.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    //#endregion

    public EntitySaccageur(World worldIn) {
        super(worldIn);
        this.setSize(2F, 3F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_HOWLING, false);
    }

    //#region Getters
    @Override
    protected ResourceLocation getLootTable() {
        return LootTablesRegistry.SACCAGEUR;
    }

    @Override
    protected int getExperiencePoints(@Nonnull EntityPlayer player) {
        return 0;
    }
    //#endregion

    //#region Geckolib
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "howlingController", 0, this::howlingPredicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    //#endregion

    //#region Predicates
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<EntitySaccageur> entitySaccageurAnimationEvent) {
        if (this.isSwingInProgress && entitySaccageurAnimationEvent.getController().getAnimationState().equals(AnimationState.Stopped)) {
            entitySaccageurAnimationEvent.getController().markNeedsReload();
            entitySaccageurAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.isSwingInProgress = false;
        }
        return PlayState.CONTINUE;
    }

    private PlayState howlingPredicate(AnimationEvent<EntitySaccageur> entitySaccageurAnimationEvent) {
        if (this.dataManager.get(IS_HOWLING) && entitySaccageurAnimationEvent.getController().getAnimationState().equals(AnimationState.Stopped)) {
            entitySaccageurAnimationEvent.getController().markNeedsReload();
            entitySaccageurAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.hurle", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.getDataManager().set(IS_HOWLING, false);
        }
        return PlayState.CONTINUE;
    }
    //#endregion

    //#region Attributes & Attack
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.21D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(75.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof net.minecraft.entity.EntityLivingBase) {
                ((net.minecraft.entity.EntityLivingBase) entityIn).knockBack(this, 1.0F, this.posX - entityIn.posX, this.posZ - entityIn.posZ);
            }
            this.playSound(SoundsRegistry.SACCAGEUR_ATTACK, 1.0F, 1.0F);
            return true;
        }
        return false;
    }
    //#endregion

    //#region Tick
    @Override
    public void tick() {
        super.onUpdate();
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }
    //#endregion

    //#region Sounds
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsRegistry.SACCAGEUR_AMBIENT;
    }



    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
    }



//#endregion

    //#region AI
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntitySaccageur.AIAttackPlayer(this, 2.2D, false, 20.0F, 60));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.5D, 32.0F));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntitySaccageur.AIFindPlayer(this));
    }

    static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        private final EntitySaccageur saccageur;
        private final int HOWLING_DELAY = 60;
        private int howlingTime = HOWLING_DELAY;

        public AIFindPlayer(EntitySaccageur p_i45842_1_) {
            super(p_i45842_1_, EntityPlayer.class, false);
            this.saccageur = p_i45842_1_;
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.toggleHowling(true);
            this.saccageur.playSound(SoundsRegistry.SACCAGEUR_HOWLING, 1.5F, 1.0F);
        }

        public void toggleHowling(boolean howling) {
            this.howlingTime = HOWLING_DELAY;
            this.saccageur.getDataManager().set(IS_HOWLING, howling);
        }

        private void applyDebuff() {
            List<EntityPlayer> list = this.saccageur.world.getEntitiesWithinAABB(EntityPlayer.class, this.getTargetableArea(40.0D), this.targetEntitySelector);
            for (EntityPlayer entityplayer : list) {
                entityplayer.addPotionEffect(new PotionEffect(Objects.requireNonNull(Potion.getPotionFromResourceLocation("minecraft:slowness")), 500, 1));
            }
        }

        @Override
        public void updateTask() {
            if (this.saccageur.getDataManager().get(IS_HOWLING)) {
                this.howlingTime--;
                if (this.howlingTime <= 0) {
                    this.toggleHowling(false);
                    applyDebuff();
                }
                return;
            }
            super.updateTask();
        }
    }

    static class AIAttackPlayer extends EntityAIDalthianAttack {

        private final EntitySaccageur saccageur;

        public AIAttackPlayer(EntityCreature creature, double speedIn, boolean useLongMemory, double attackReach, int attackCooldown) {
            super(creature, speedIn, useLongMemory, attackReach, attackCooldown);
            this.saccageur = (EntitySaccageur) creature;
        }

        @Override
        public boolean shouldExecute() {
            return !saccageur.getDataManager().get(IS_HOWLING) && super.shouldExecute();
        }
    }
    //#endregion
}
