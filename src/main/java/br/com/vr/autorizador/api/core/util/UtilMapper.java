package br.com.vr.autorizador.api.core.util;

import org.modelmapper.ModelMapper;

public class UtilMapper {

	public static <T> T merge(Object source, Object destination) {
		final ModelMapper mapper = new ModelMapper();
		mapper.map(source, destination);

		return (T) destination;
	}

	public static <T> T merge(Object source, Class<T> clazz) {
		try {
			T obj = clazz.getDeclaredConstructor().newInstance();

			final ModelMapper mapper = new ModelMapper();
			mapper.map(source, obj);

			if (clazz.isInstance(obj))
				return (T) obj;

			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
