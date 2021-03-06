package com.quehacerhoy.servicios;

import com.quehacerhoy.entidades.Zona;
import com.quehacerhoy.repositorios.ZonaRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZonaService {

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Transactional
    public void altaZona(String nombre) throws ErrorService {

        validarZona(nombre);

        Zona zona = new Zona();

        zona.setNombre(nombre);

        zonaRepositorio.save(zona);

    }

    @Transactional
    public void modificarZona(String id, String nombre) throws ErrorService {

        validarZona(nombre);

        Optional<Zona> respuesta = zonaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Zona zona = respuesta.get();
            zona.setId(id);
            zona.setNombre(nombre);

            zonaRepositorio.save(zona);

        } else {
            throw new ErrorService("No se encontró la zona ingresada");
        }
    }

    @Transactional
    public void eliminarZona(String id) throws ErrorService {

        Optional<Zona> zona = zonaRepositorio.findById(id);
        if (zona.isPresent()) {
            Zona z = zona.get();
             Date baja = new Date();
            z.setBaja(baja);
            zonaRepositorio.save(z);

        } else {
            throw new ErrorService("La zona ingresada no es correcta");
        }
    }
    
    public Zona buscarPorId(String id) throws ErrorService{
          Optional<Zona> zona = zonaRepositorio.findById(id);
        if (zona.isPresent()) {
            Zona z = zona.get();
            return z;

        } else {
            throw new ErrorService("no se ha podido encontrar la zona");
        }
    }

    public List<Zona> listarZona() {

        return zonaRepositorio.listar();

    }

    public void validarZona(String nombre) throws ErrorService {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre de la zona no puede ser nulo ni vacio");

        }

    }
}
