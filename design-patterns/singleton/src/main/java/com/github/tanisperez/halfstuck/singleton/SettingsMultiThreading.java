package com.github.tanisperez.halfstuck.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que implementa el patrón Singleton con lazy-init y es thread-safe.
 * <p>
 * Esta implementación utiliza la técnica double-check locking para garantizar
 * que nunca van a instanciar la clase varios hilos.
 * <p>
 * Además, tiene la ventaja del lazy-init, permitiendo que la aplicación
 * arranqué antes.
 * <p>
 * El problema que tiene, es que es más compleja de implementar esta técnica y
 * puede producir errores si no se hace bien.
 *
 * @author Estanislao Pérez Nartallo
 */
public final class SettingsMultiThreading {

	/** Objeto lock que se utilizará para la sincronización entre hilos */
	private static final Object lock = new Object();
	/**
	 * Instancia de la clase con lazy-init. Se marca como volatile para que todos
	 * los hilos sean capaces de ver el valor más actualizado de la variable.
	 */
	private static volatile SettingsMultiThreading INSTANCE;

	/** Mapa que contiene en memoria las propiedades de configuración */
	private final Properties properties;

	/**
	 * Constructor privado que utiliza un try-with-resources para la carga del
	 * fichero.
	 */
	private SettingsMultiThreading() {
		try (final InputStream stream = getClass().getResourceAsStream("/settings.properties")) {
			this.properties = new Properties();
			this.properties.load(stream);
		} catch (final IOException exception) {
			throw new RuntimeException("No se pudo cargar el fichero settings.properties", exception);
		}
	}

	/**
	 * Devuelve la instancia de la clase. Si es la primera vez que se invoca,
	 * instancia el objeto utilizando la técnica double-check locking.
	 *
	 * @return Devuelve la instancia de la clase {@code Settings}.
	 */
	public static SettingsMultiThreading getInstance() {
		if (INSTANCE == null) {
			synchronized (lock) {
				if (INSTANCE == null) {
					INSTANCE = new SettingsMultiThreading();
				}
			}
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
