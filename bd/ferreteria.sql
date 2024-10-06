DROP DATABASE ferreteria;
CREATE DATABASE IF NOT EXISTS ferreteria;
USE ferreteria;


/*
    Gerente
    Administrador inventario
    Vendedor
    Encargado Obra/Proyecto
*/
CREATE TABLE ROLES
(
    idRol  VARCHAR(255) PRIMARY KEY NOT NULL,
    nombre VARCHAR(100)
);

INSERT INTO ROLES (idRol, nombre)
VALUES ('f8e172b9-3a43-4c89-b80c-5ca5c924e84f', 'Administrador');
INSERT INTO ROLES (idRol, nombre)
VALUES ('3ec2633c-74bf-426d-b999-f2da3d732ca7', 'Gerente');
INSERT INTO ROLES (idRol, nombre)
VALUES ('00d0d324-31a0-415e-b9b5-9f569bf36da1', 'Vendedor');
INSERT INTO ROLES (idRol, nombre)
VALUES ('3a42b356-0ce1-41c0-9260-8b11e0f649de', 'Encargado Proyecto');
INSERT INTO ROLES (idRol, nombre)
VALUES ('0d9e2736-517b-4739-afdc-7c2172fd7966', 'Proveedor');

CREATE TABLE DIRECCION
(
    idDireccion VARCHAR(255) NOT NULL PRIMARY KEY,
    ciudad      VARCHAR(50)  NOT NULL,
    colonia     VARCHAR(50)  NOT NULL,
    calle       VARCHAR(50)  NOT NULL,
    numero      VARCHAR(20)  NOT NULL
);

CREATE TABLE PERSONA
(
    idPersona   VARCHAR(255) NOT NULL PRIMARY KEY,
    idDireccion VARCHAR(255) NOT NULL,
    nombre      VARCHAR(150) NOT NULL,
    telefono    VARCHAR(10)  NOT NULL,
    correo      VARCHAR(150) NOT NULL,
    rfc         VARCHAR(15)  NOT NULL,
    idRol       VARCHAR(255) NOT NULL,
    FOREIGN KEY (idDireccion) REFERENCES DIRECCION (idDireccion),
    FOREIGN KEY (idRol) REFERENCES ROLES (idRol)
);

CREATE TABLE USUARIO
(
    idUsuario  VARCHAR(255) NOT NULL PRIMARY KEY,
    idPersona  VARCHAR(255) NOT NULL,
    usuario    VARCHAR(255) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    FOREIGN KEY (idPersona) REFERENCES PERSONA (idPersona)
);

CREATE TABLE PRODUCTO
(
    idProducto    VARCHAR(255) NOT NULL PRIMARY KEY,
    urlImage      TEXT,
    codigo        varchar(255),
    nombre        VARCHAR(50)  NOT NULL,
    cantidad      FLOAT        NOT NULL,
    stockMinimo   FLOAT        NOT NULL,
    costo         FLOAT        NOT NULL,
    precioMenudeo FLOAT        NOT NULL,
    precioMayoreo FLOAT        NOT NULL,
    descripcion   TEXT         NOT NULL,
    estado        VARCHAR(255) NOT NULL,
    fecha         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE PAQUETE
(
    idPaquete   VARCHAR(255) NOT NULL PRIMARY KEY,
    idProducto  VARCHAR(255) NOT NULL,
    precio      INT          NOT NULL,
    descripcion TEXT         NOT NULL,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto)
);

CREATE TABLE PRODUCTOS_PAQUETE
(
    idProductoPaquete VARCHAR(255) PRIMARY KEY NOT NULL,
    idProducto        VARCHAR(255)             NOT NULL,
    idPaquete         VARCHAR(255)             NOT NULL,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto),
    FOREIGN KEY (idPaquete) REFERENCES PAQUETE (idPaquete)
);

CREATE TABLE VENTA
(
    idVenta   VARCHAR(255) NOT NULL PRIMARY KEY,
    idUsuario VARCHAR(255) NOT NULL,
    monto     FLOAT        NOT NULL,
    fecha     DATE         NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES USUARIO (idUsuario)
);

CREATE TABLE PRODUCTO_VENTA
(
    idProductoVenta VARCHAR(255) NOT NULL PRIMARY KEY,
    idProducto      VARCHAR(255) NOT NULL,
    idVenta         VARCHAR(255) NOT NULL,
    cantidad        FLOAT        NOT NULL,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto),
    FOREIGN KEY (idVenta) REFERENCES VENTA (idVenta)
);

CREATE TABLE PAGO
(
    idPago    VARCHAR(255) NOT NULL PRIMARY KEY,
    idUsuario VARCHAR(255) NOT NULL,
    tipo      VARCHAR(50)  NOT NULL,
    monto     FLOAT        NOT NULL,
    fecha     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado    VARCHAR(100) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES USUARIO (idUsuario)
);

CREATE TABLE PROYECTO
(
    idProyecto  VARCHAR(255) NOT NULL PRIMARY KEY,
    idPersona   VARCHAR(255) NOT NULL,
    idProducto  VARCHAR(255) NOT NULL,
    fecha       DATE         NOT NULL,
    descripcion TEXT         NOT NULL,
    FOREIGN KEY (idPersona) REFERENCES PERSONA (idPersona),
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto)
);

CREATE TABLE PEDIDO_ESPECIAL
(
    idPedido   VARCHAR(255) NOT NULL PRIMARY KEY,
    idPersona  VARCHAR(255) NOT NULL,
    idProducto VARCHAR(255) NOT NULL,
    fecha      DATE         NOT NULL,
    FOREIGN KEY (idPersona) REFERENCES PERSONA (idPersona),
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto)
);

CREATE TABLE REPORTE
(
    idReporte   VARCHAR(255) NOT NULL PRIMARY KEY,
    idProducto  VARCHAR(255) NOT NULL,
    urlImage    TEXT         NOT NULL,
    descripcion TEXT         NOT NULL,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto)
);

CREATE TABLE OFERTA
(
    idOferte    VARCHAR(255) NOT NULL PRIMARY KEY,
    idProducto  VARCHAR(255) NOT NULL,
    fechaInicio DATE         NOT NULL,
    fechaFinal  DATE         NOT NULL,
    detalles    TEXT         NOT NULL,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto)
);