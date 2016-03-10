package org.silvercatcher.reforged_cag.holy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HolyEvents {

	@SubscribeEvent
	public void createHoly(EntityConstructing constructing) {
		if(constructing.entity instanceof EntityLivingBase) {
			((EntityLivingBase) constructing.entity).getAttributeMap()
				.registerAttribute(HolyDamage.attribute);
		}
	}
	
	@SubscribeEvent
	public void onUndeadHit(LivingAttackEvent event) {
		
		if(event.isCanceled()) return;		
		if(event.entityLiving.isEntityUndead()) {
			// help preventing the infinite loop of doom
			if(!event.source.damageType.equals(HolyDamage.sourceName) &&
					event.source.getEntity() instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();
				IAttributeInstance holyDamageAttribute = attacker
						.getAttributeMap().getAttributeInstanceByName(
								HolyDamage.attribute.getAttributeUnlocalizedName());
				if(holyDamageAttribute == null) return;
				event.setCanceled(true);
				event.entityLiving.attackEntityFrom(
						HolyDamage.causeHolyDamage(attacker),
						(float) holyDamageAttribute.getAttributeValue()
						+ event.ammount);
			}
		}
	}
}
