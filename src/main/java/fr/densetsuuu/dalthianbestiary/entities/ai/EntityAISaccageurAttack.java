package fr.densetsuuu.dalthianbestiary.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityAISaccageurAttack extends EntityAIAttackMelee {

    private final int attackInterval;

    public EntityAISaccageurAttack(EntityCreature creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        this.attackInterval = 60;
    }

    // Set reach to 3.0F

    @Override
    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return 15.0F;
    }

    // Set attack cooldown to 40

    @Override
    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(p_190102_1_);
        if (p_190102_2_ <= d0 && this.attackTick <= 0) {
            this.attackTick = this.attackInterval;
            this.attacker.swingArm(this.attacker.getActiveHand());
            this.attacker.attackEntityAsMob(p_190102_1_);
        }
    }
}
