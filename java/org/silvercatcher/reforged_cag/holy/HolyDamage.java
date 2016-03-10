package org.silvercatcher.reforged_cag.holy;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;

public class HolyDamage {

	public static final String sourceName = "holy";
	public static final String modifierName = "Holy Damage Modifier";
	public static final UUID uuid = UUID.fromString("b64c0e41-0082-41fa-a0fb-4a8041777809");
	public static final IAttribute attribute = new IAttribute() {
		
		@Override
		public boolean getShouldWatch() {
			return true;
		}
		
		@Override
		public double getDefaultValue() {
			return 0;
		}
		
		@Override
		public String getAttributeUnlocalizedName() {
			return "holy.attackDamage";
		}
		
		@Override
		public IAttribute func_180372_d() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public double clampValue(double value) {
			return MathHelper.clamp_double(value, 0, 20);
		}
	};
	
	public static final DamageSource causeHolyDamage(EntityLivingBase attacker) {
		return new EntityDamageSource(sourceName, attacker);
	}
}
