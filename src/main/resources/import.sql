/*clientes*/
insert into clientes (nombre,apellido,email,create_at,foto) values('Diego','Guzman','diegogz@gmail.com','2023-10-30','');
insert into clientes (nombre,apellido,email,create_at,foto) values('juan','perez','juan345@hotmail.com','2023-10-30','');
/*productos*/
insert into productos (nombre,precio,create_at) values('panasonic tv',250000,NOW());
insert into productos (nombre,precio,create_at) values('samsung tv oled',1000000,NOW());
insert into productos (nombre,precio,create_at) values('LG tv',500000,NOW());
insert into productos (nombre,precio,create_at) values('phone samnsun',1200000,NOW());
insert into productos (nombre,precio,create_at) values('laptop mac',5000000,NOW());
insert into productos (nombre,precio,create_at) values('auriculares',40000,NOW());
/*facturas*/
insert into facturas(descripcion,observacion,cliente_id,create_at)values('factura equipos de oficina',null,1,NOW());
insert into facturas_items(cantidad,factura_id,producto_id) values(1,1,1);
insert into facturas_items(cantidad,factura_id,producto_id) values(2,1,3);
insert into facturas_items(cantidad,factura_id,producto_id) values(1,1,4);
insert into facturas_items(cantidad,factura_id,producto_id) values(1,1,2);

insert into facturas(descripcion,observacion,cliente_id,create_at)values('factura bicicleta','alguna nota importante',1,NOW());
insert into facturas_items(cantidad,factura_id,producto_id) values(3,2,5);
