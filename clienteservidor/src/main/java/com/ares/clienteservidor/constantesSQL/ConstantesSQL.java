package com.ares.clienteservidor.constantesSQL;

public class ConstantesSQL {
	
	
	
	public static final String SQL_OBTENER_LIBROS_PARA_JSON = 
			"select l.id, l.titulo, l.descripcion, l.precio, c.nombre as nombre_categoria from tabla_libros as l, categoria as c where l.categoria_id = c.id order by l.id desc";
	
	public static final String SQL_OBTENER_PRODUCTOS_CARRITO = 
			"SELECT "
			+ "C.USUARIO_ID , TL.TITULO, TL.ID AS LIBRO_ID, TL.PRECIO, TL.DESCRIPCION, C.CANTIDAD "
			+ "FROM CARRITO AS C, TABLA_LIBROS AS TL "
			+ "WHERE "
			+ "USUARIO_ID = :usuario_id AND TL.ID = C.LIBRO_ID ";
	public static final String SQL_ELIMINAR_PRODUCTO_CARRITO = 
			"DELETE FROM CARRITO WHERE LIBRO_ID = :libro_id AND USUARIO_ID = :usuario_id";
	public static final String SQL_VACIAR_CARRITO = 
			"DELETE FROM CARRITO WHERE USUARIO_ID = :usuario_id";
	
}


