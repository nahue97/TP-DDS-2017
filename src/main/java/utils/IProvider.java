package utils;

import java.util.List;

import dtos.CargaDeCuentasDTO;
import model.Cuenta;

public interface IProvider {

	List<Cuenta> getInformation(CargaDeCuentasDTO datosDeCarga);

}
