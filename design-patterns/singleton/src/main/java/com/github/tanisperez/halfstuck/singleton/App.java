package com.github.tanisperez.halfstuck.singleton;

/**
 * Aplicación para demostrar el funcionamiento del patrón Singleton.
 *
 * @author Estanislao Pérez Nartallo
 */
public class App {

	public static void main(final String[] args) {
		final Settings settings = Settings.getInstance();
		System.out.println("::Settings Singleton::");
		System.out.println("App name: " + settings.getProperty("app.name"));
		System.out.println("App version: " + settings.getProperty("app.version"));
		System.out.println("App URL: " + settings.getProperty("app.url"));

		final SettingsLazyInit settingsLazy = SettingsLazyInit.getInstance();
		System.out.println("\n::Settings Lazy Init::");
		System.out.println("App name: " + settingsLazy.getProperty("app.name"));
		System.out.println("App version: " + settingsLazy.getProperty("app.version"));
		System.out.println("App URL: " + settingsLazy.getProperty("app.url"));

		final SettingsMultiThreading settingsMultiThreading = SettingsMultiThreading.getInstance();
		System.out.println("\n::Settings MultiThreading::");
		System.out.println("App name: " + settingsMultiThreading.getProperty("app.name"));
		System.out.println("App version: " + settingsMultiThreading.getProperty("app.version"));
		System.out.println("App URL: " + settingsMultiThreading.getProperty("app.url"));
	}

}
