package com.ultreon.mods.pixelguns.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.ultreon.mods.pixelguns.PixelGuns;
import com.ultreon.mods.pixelguns.item.gun.GunItem;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class GunPropertiesProvider implements DataProvider
{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

	private final DataGenerator generator;
	private final Map<Identifier, GunItem> gunMap = new HashMap<>();

	protected GunPropertiesProvider(DataGenerator generator)
	{
		this.generator = generator;
	}

	protected abstract void registerGuns();

	protected final void addGun(Identifier id, GunItem gun)
	{
		this.gunMap.put(id, gun);
	}

	@Override
	public void run(DataWriter dataWriter)
	{
		this.gunMap.clear();
		this.registerGuns();
		this.gunMap.forEach((id, gun) ->
		{
			Path path = this.generator.getOutput().resolve("data/" + id.getNamespace() + "/guns/" + id.getPath() + ".json");
			try
			{
				JsonObject object = null; //gun.toJsonObject();
				DataProvider.writeToPath(dataWriter, object, path);
			}
			catch(IOException e)
			{
				LOGGER.error("Couldn't save trades to {}", path, e);
			}
		});
	}

	@Override
	public String getName()
	{
		return "Guns: " + PixelGuns.MOD_ID;
	}
}
