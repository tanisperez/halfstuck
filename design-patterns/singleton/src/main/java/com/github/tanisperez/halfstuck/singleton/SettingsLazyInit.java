package com.github.tanisperez.halfstuck.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que implementa el patrón Singleton con lazy-init y no es thread-safe.
 * <p>
 * Esta implementanción permite que la aplicación arranque más rápida, ya que el
 * fichero se carga cuando se quiere hacer el primer uso.
 * <p>
 * El problema que tiene es que no garantiza el acceso concurrente. Es decir,
 * varios hilos podrían recuperar una propiedad de configuración y llamar varias
 * veces al constructor.
 *
 * @author Estanislao Pérez Nartallo
 */
public final class SettingsLazyInit {

	/** Instancia de la clase con lazy-init. */
	private static SettingsLazyInit INSTANCE;

	/** Mapa que contiene en memoria las propiedades de configuración */
	private final Properties properties;

	/**
	 * Constructor privado que utiliza un try-with-resources para la carga del
	 * fichero.
	 */
	private SettingsLazyInit() {
		try (final InputStream stream = getClass().getResourceAsStream("/settings.properties")) {
			this.properties = new Properties();
			this.properties.load(stream);
		} catch (final IOException exception) {
			throw new RuntimeException("No se pudo cargar el fichero settings.properties", exception);
		}
	}

	/**
	 * Devuelve la instancia de la clase. Si es la primera vez que se invoca,
	 * instancia el objeto.
	 *
	 * @return Devuelve la instancia de la clase {@code Settings}.
	 */
	public static SettingsLazyInit getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SettingsLazyInit();
		}
		return INSTANCE;
	}

	/**
	 * Devuelve un {@code String} que contiene el valor de una propiedad de
	 * configuración.
	 *
	 * @param key Un {@code String} que representa la clave de una configuración.
	 * @return Devuelve un {@code String} que contiene el valor de una propiedad de
	 *         configuración.
	 */
	public String getProperty(final String key) {
		return this.properties.getProperty(key);
	}

}
