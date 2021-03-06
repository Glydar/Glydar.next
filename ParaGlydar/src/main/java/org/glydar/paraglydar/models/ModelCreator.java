package org.glydar.paraglydar.models;

import org.glydar.paraglydar.data.EntityData;

public interface ModelCreator {

	//Creating NPCs
	public NPC createNPC();

	public NPC createNPC(EntityData e);
	
	//Creating Worlds
	public World createWorld(String name, int seed);
}
