package fr.densetsuuu.dalthianbestiary.entities.ai.saccageur;

import fr.densetsuuu.dalthianbestiary.entities.EntitySaccageur;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAISaccageurNearestAttackablePlayer extends EntityAINearestAttackableTarget {

    private final int HOWLING_TIME = 100;
    private int howlingTick = HOWLING_TIME;

    private EntitySaccageur saccageur;

    public EntityAISaccageurNearestAttackablePlayer(EntitySaccageur creature, Class classTarget, boolean checkSight) {
        super(creature, classTarget, checkSight);
        saccageur = creature;
    }

    @Override
    public void startExecuting() {
        if (this.saccageur.isHowling()) {
            return;
        }
        super.startExecuting();
        this.taskOwner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.taskOwner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue()*1.2);
        this.toggleHowling(true);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.taskOwner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.taskOwner.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue()/1.2);
    }

    public void toggleHowling(boolean howling) {
        this.saccageur.setHowling(howling);
        this.howlingTick = HOWLING_TIME;
    }

    @Override
    public void updateTask() {
        super.updateTask();
        if (this.saccageur.isHowling()) {
            this.howlingTick--;
            if (this.howlingTick <= 0) {
                toggleHowling(false);
            }
        }

    }
}
