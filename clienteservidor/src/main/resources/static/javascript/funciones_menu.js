//funciones generales:

function checkout_paso_3(json){
	let html = Mustache.render(plantilla_checkout_3, json)
	$("#contenedor").html(html)
	$("#boton_confirmar_pedido").click(function(){
		$.post("pedidosREST/paso3").done(function(res){
			alert(res)
			if(res == "ok"){
				obtenerLibros()
			}
		})
	})
}

function checkout_paso_2(){
	$("#contenedor").html(plantilla_checkout_2)
	$("#aceptar_paso_2").submit(function(e){
		e.preventDefault()
		tipo_tarjeta = $("#tipo_tarjeta").find(":selected").val()
		if(tipo_tarjeta == "0"){
			alert("selecciona un tipo de tarjeta")
			return
		}
		xxx = $("#numero_tarjeta").val()
		titular = $("#titular_tarjeta").val()
		$.post("pedidosREST/paso2",{
			tarjeta : tipo_tarjeta,
			numero: xxx, 
			titular: titular
		}).done(function(res){
			//usar la plantilla de checkout paso 3 y procesarla con el json recibido
			checkout_paso_3(res)			
		})
	})
}

function checkout_paso_1(){
	xxx = $("#campo_nombre").val()
	direccion = $("#campo_direccion").val()
	provincia = $("#campo_provincia").val()
	
	alert("mandar la info insertada a pedidosREST")
	$.post("pedidosREST/paso1",{
		nombre: xxx,
		direccion: direccion,
		provincia: provincia
	}).done(function(res){
		alert(res)
		if(res == "ok"){
			alert("mostrar plantilla checkout2")
			checkout_paso_2()
		}
	})			
}//end checkout_paso_1

function checkout_paso_0(){
	$("#contenedor").html(plantilla_checkout_1)
	$("#aceptar_paso_1").submit(
		function(e){
			e.preventDefault()
			checkout_paso_1()
		}
	)
}//end checkout_paso_0

function mostrarCarrito(){
	if(nombre_login == ""){
			alert("tienes que identificarte para comprar productos")
	}else{
		//$("#contenedor").html(plantilla_carrito)
		$.get("carritoREST/obtener", function(r){
			if(r.length == 0){
				alert("aun no has agregado nada al carrito")
			}else{
				let html = Mustache.render(plantilla_carrito, r )
				$("#contenedor").html(html)	
				$("#realizar_pedido").click(checkout_paso_0)				
				//decir que hay que hacer cuando se haga click 
				//en el enlace de borrar producto
				$(".enlace-borrar-producto-carrito").click(function(){	
					if ( ! confirm("¿estas seguro?")){
						return
					}				
				 	let idLibro = $(this).attr("id-libro")	
					alert("mandar el id de libro: " + idLibro + " a carritoREST para "+
						"que lo borre del carrito")
					$.post("carritoREST/eliminar",{
						id: idLibro
					}).done(function(res){
						alert(res)
						if(res == "ok"){
							mostrarCarrito()
						}
					})//end done
				})//end click
			}//end else							
		})//end get
	}//end else
}//end function

function comprar_producto(){
	if(nombre_login == ""){
		alert("tienes que identificarte para comprar productos")
	}else{
		var id_producto = $(this).attr("data-producto-id")
		alert("añadir producto de id: " + id_producto + " al carrito")	
		//invocar a una operacion de CarritoREST para agregar
		//el producto al carrito
		$.post("carritoREST/agregarProducto",{
			id: id_producto,
			cantidad: 1
		}).done(function(res){
			alert(res)
		})
		
	}
}//end comprar_producto
	
function obtenerLibros(){
	$.get("librosREST/obtener", function(r){
		//codigo a ejecutar cuando reciba la respuesta del recurso indicado
		//alert("recibido: " + r );
		var libros = r //JSON.parse(r)
		console.log(libros)
		var info = Mustache.render( 
				plantilla_libros , { xxx : "hola desde mustache", array_libros: libros } ) 
		$("#contenedor").html(info)
		$(".enlace_comprar_producto").click(comprar_producto)		
	})//end $.get
	$("#contenedor").html("cargando...");
}//end obtenerLibros

function mostrarLogin(){
	$("#contenedor").html(plantilla_login)
	$("#form_login").submit(function(e){
		e.preventDefault()
		var email = $("#email").val()
		var pass = $("#pass").val()
		$.post("usuariosREST/login",{
			email: email,
			pass: pass
		}).done(function(res){
			var parte1 = res.split(",")[0]
			var parte2 = res.split(",")[1]
			if (parte1 == "ok"){
				alert("bienvenido " + parte2 + " ya puedes comprar")
				nombre_login = parte2
				$("#login_usuario").html("hola " + parte2)
			}else{
				alert(res)
			}
		})//end done		
	})//end submit
}//end mostrarLogin

function mostrarRegistro(){
	$("#contenedor").html(plantilla_registro)
	//vamos a interceptar el envio de formulario
	$("#form_registro").submit(function(e){
		e.preventDefault()
		//alert("se intenta enviar form")
		//recoger los datos del form y mandarselos a UsuariosREST
		var nombre = $("#nombre").val()
		var email = $("#email").val()
		var pass = $("#pass").val()
		$.post("usuariosREST/registrar",{
			nombre: nombre, 
			email: email,
			pass: pass
		}).done(function(res){
			alert(res)
		})//end done
	})//end submit form
}//end mostrarRegistro

//atencion a eventos:

$("#enlace_productos").click(obtenerLibros)
$("#enlace_identificarme").click(mostrarLogin)
$("#enlace_registrarme").click(mostrarRegistro)
$("#enlace_carrito").click(mostrarCarrito)


