package com.github.tanisperez.halfstuck.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que implementa el patrón Singleton sin lazy-init y thread-safe.
 * <p>
 * Debido a que la instancia ya se crea al arrancar la aplicación, se garantiza
 * el acceso concurrente y es thread-safe.
 *
 * @author Estanislao Pérez Nartallo
 */
public final class Settings {

	/** Instancia de la clase */
	private static final Settings INSTANCE = new Settings();

	/** Mapa que contiene en memoria las propiedades de configuración */
	private final Properties properties;

	/**
	 * Constructor privado que utiliza un try-with-resources para la carga del
	 * fichero.
	 */
	private Settings() {
		try (final InputStream stream = getClass().getResourceAsStream("/settings.properties")) {
			this.properties = new Properties();
			this.properties.load(stream);
		} catch (final IOException exception) {
			throw new RuntimeException("No se pudo cargar el fichero settings.properties", exception);
		}
	}

	/**
	 * Devuelve la instancia de la clase.
	 *
	 * @return Devuelve la instancia de la clase {@code Settings}.
	 */
	public static Settings getInstance() {
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
