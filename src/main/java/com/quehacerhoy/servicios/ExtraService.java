/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quehacerhoy.servicios;

import com.quehacerhoy.entidades.Extra;
import com.quehacerhoy.entidades.Foto;
import com.quehacerhoy.entidades.Usuario;
import com.quehacerhoy.entidades.Zona;
import com.quehacerhoy.repositorios.ExtraRepositorio;
import com.quehacerhoy.repositorios.UsuarioRepositorio;
import com.quehacerhoy.repositorios.ZonaRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExtraService {

    @Autowired
    private ExtraRepositorio repositorio;
    @Autowired
    private ZonaRepositorio zonaR;
    @Autowired
    private UsuarioRepositorio usuarioR;
    @Autowired
    private FotoService fotoS;

    //Alta para los eventos
    @Transactional
    public void altaEvento(String nombre, String descripcion, String direccion, String edad, String hora, String capacidad, Date fecha, MultipartFile archivo, String idZona, String usernameUsuario) throws Exception {

        if (nombre.isEmpty()) {
            throw new Exception("Debe indicar un nombre");
        }

        if (descripcion.isEmpty()) {
            throw new Exception("Debe indicar una descripcion");
        }
        if (edad.isEmpty()) {
            throw new Exception("Debe indicar una edad referencial");
        }
        if (hora.isEmpty()) {
            throw new Exception("Debe indicar la hora del evento");
        }

        if (capacidad.isEmpty()) {
            throw new Exception("Debe indicar la capacidad del evento");
        }

        if (fecha == null) {
            throw new Exception("Debe indicar una fecha valida");
        }
        if (archivo == null) {
            throw new Exception("Debe indicar una foto");
        }
        if (idZona.isEmpty()) {
            throw new Exception("Debe indicar una zona");
        }
        if (usernameUsuario.isEmpty()) {
            throw new Exception("Debe indicar un usuario");
        }

        try {
            Extra evento = new Extra();

            evento.setCapacidad(capacidad);
            evento.setDescripcion(descripcion);
            evento.setDireccion(direccion);
            evento.setEdad(edad);
            evento.setFecha(fecha);
            Foto foto = fotoS.guardar(archivo);
            evento.setFoto(foto);
            evento.setNombre(nombre);
            //buscar zona con id
            Zona zona = zonaR.getOne(idZona);
            evento.setZona(zona);
            evento.setHora(hora);
            Usuario usuario = usuarioR.getOne(usernameUsuario);
            evento.setUsuario(usuario);
            
            Date now = new Date();
            evento.setAlta(now);
            repositorio.save(evento);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void modificarEvento(String id, String nombre, String descripcion, String direccion, String edad, String hora, String capacidad, Date fecha, MultipartFile archivo, String idZona) throws Exception {

        if (id == null) {
            throw new Exception("id nulo");
        }
        if (nombre.isEmpty()) {
            throw new Exception("Debe indicar un nombre");
        }

        if (descripcion.isEmpty()) {
            throw new Exception("Debe indicar una descripcion");
        }
        if (edad.isEmpty()) {
            throw new Exception("Debe indicar una edad referencial");
        }
        if (hora.isEmpty()) {
            throw new Exception("Debe indicar la hora del evento");
        }

        if (capacidad.isEmpty()) {
            throw new Exception("Debe indicar la capacidad del evento");
        }

        if (fecha == null) {
            throw new Exception("Debe indicar una fecha valida");
        }
        if (archivo == null) {
            throw new Exception("Debe indicar una foto");
        }
        if (idZona.isEmpty()) {
            throw new Exception("Debe indicar una zona");
        }
        

        try {
            Extra evento = repositorio.getOne(id);
          

            evento.setCapacidad(capacidad);
            evento.setDescripcion(descripcion);
            evento.setDireccion(direccion);
            evento.setEdad(edad);
            evento.setFecha(fecha);
            //se actualiza la foto
            String idFoto = null;
            if (evento.getFoto().getId() != null) {
                idFoto = evento.getFoto().getId();
            }
            Foto foto = fotoS.actualizar(idFoto, archivo);
            evento.setFoto(foto);

            evento.setNombre(nombre);
            //buscar zona con id
            Zona zona = zonaR.getOne(idZona);
            evento.setZona(zona);

            repositorio.save(evento);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Alta para la publicidad
    @Transactional
    public void altaPublicidad(String nombre, String descripcion,
            MultipartFile archivo, String usernameUsuario) throws Exception {

        if (nombre.isEmpty()) {
            throw new Exception("Debe indicar un nombre");
        }

        if (descripcion.isEmpty()) {
            throw new Exception("Debe indicar una descripcion");
        }
        if (archivo == null) {
            throw new Exception("Debe indicar una foto");
        }
        if (usernameUsuario.isEmpty()) {
            throw new Exception("Debe indicar un usuario");
        }

        try {
            Extra publicidad = new Extra();

            publicidad.setDescripcion(descripcion);
            Foto foto = fotoS.guardar(archivo);
            publicidad.setFoto(foto);
            publicidad.setNombre(nombre);
            Date fechaPublicacion = new Date();
            publicidad.setFecha(fechaPublicacion);
            Usuario usuario = usuarioR.getOne(usernameUsuario);
            publicidad.setUsuario(usuario);
            
            Date now = new Date();
            publicidad.setAlta(now);

            repositorio.save(publicidad);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void modificarPublicidad(String id, String nombre, String descripcion,
            MultipartFile archivo) throws Exception {

        if (id == null) {
            throw new Exception("id nulo");
        }
        if (nombre.isEmpty()) {
            throw new Exception("Debe indicar un nombre");
        }

        if (descripcion.isEmpty()) {
            throw new Exception("Debe indicar una descripcion");
        }
        if (archivo == null) {
            throw new Exception("Debe indicar una foto");
        }
        

        try {
            Extra publicidad = repositorio.getOne(id);


            publicidad.setDescripcion(descripcion);
            String idFoto = null;
            if (publicidad.getFoto().getId() != null) {
                idFoto = publicidad.getFoto().getId();
            }
            Foto foto = fotoS.actualizar(idFoto, archivo);
            publicidad.setFoto(foto);

            publicidad.setNombre(nombre);
            Date fechaPublicacion = new Date();
            publicidad.setFecha(fechaPublicacion);
          
            repositorio.save(publicidad);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
//busca un evento, esto es para la vista para detallar los eventos
    public Extra buscarPorId(String id) throws Exception{
        Optional<Extra> optional = repositorio.findById(id);
        
        if(optional.isPresent()){
            Extra extra = optional.get();
            return extra;
        }else{
            throw new Exception("no se consiguio el extra");
        }
    }
    
    public void baja(String id){
           Optional<Extra> optional = repositorio.findById(id);
        
        if(optional.isPresent()){
            Extra extra = optional.get();
            Date now = new Date();
            extra.setBaja(now);
            repositorio.save(extra);
        }
    }

    //lista de eventos
    public List listar() {
        return repositorio.listarExtras();
    }
    
    public List listarEventos(){
        return repositorio.eventos();
    }
    public List listarEventosPorUsuario(String username){
        return repositorio.eventosUsuario(username);
    }
     public List listarPublicidades(){
        return repositorio.publicidades();
    }
     public List listarPublicidadesPorUsuario(String username){
        return repositorio.publicidadesUsuario(username);
    }

}
