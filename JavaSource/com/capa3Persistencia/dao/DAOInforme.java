package com.capa3Persistencia.dao;

import java.util.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.capa1Clases.*;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class DAOInforme {

	@PersistenceContext
	private EntityManager em;

	private static final String COSTO_ALIM_POR_TERNERA_1_FECHA = "SELECT t.idTernera AS TERNERA, a.costo AS COSTO, r.cantidad AS CANTIDAD "
			+ "FROM TerneraEntity as t " + "INNER JOIN t.alimentaciones as r " + "INNER JOIN r.alimento as a "
			+ "WHERE t.idTernera = :id AND r.fecha > :fecha " + "GROUP BY t.idTernera, a.costo, r.cantidad "
			+ "ORDER BY t.idTernera";

	private static final String COSTO_ALIM_POR_TERNERA_2_FECHAS = "SELECT t.idTernera AS TERNERA, a.costo AS COSTO, r.cantidad AS CANTIDAD "
			+ "FROM TerneraEntity as t " + "INNER JOIN t.alimentaciones as r " + "INNER JOIN r.alimento as a "
			+ "WHERE t.idTernera = :id AND r.fecha BETWEEN :fechaInicio AND :fechaFin "
			+ "GROUP BY t.idTernera, a.costo, r.cantidad " + "ORDER BY t.idTernera";

	private static final String COSTO_ALIM_POR_TERNERA_GUACHERA_1_FECHA = "SELECT g.idGuachera, t.idTernera, a.costo, ra.cantidad "
			+ "FROM TerneraEntity t " + "INNER JOIN t.alimentaciones ra " + "INNER JOIN ra.alimento a "
			+ "INNER JOIN t.guachera g " + "WHERE g.idGuachera = :idGuachera " + "AND ra.fecha > :fechaInicio "
			+ "GROUP BY g.idGuachera, t.idTernera, a.costo, ra.cantidad " + "ORDER BY g.idGuachera";

	private static final String COSTO_ALIM_POR_TERNERA_GUACHERA_2_FECHAS = "SELECT g.idGuachera, t.idTernera, a.costo, ra.cantidad "
			+ "FROM TerneraEntity t " + "INNER JOIN t.alimentaciones ra " + "INNER JOIN ra.alimento a "
			+ "INNER JOIN t.guachera g " + "WHERE g.idGuachera = :idGuachera "
			+ "AND ra.fecha BETWEEN :fechaInicio AND :fechaFin "
			+ "GROUP BY g.idGuachera, t.idTernera, a.costo, ra.cantidad " + "ORDER BY g.idGuachera";

	private static final String VARIACION_PESO_POR_TERNERA_1FECHA = "SELECT t.idTernera AS TERNERA, rp.peso AS PESO, rp.fecha AS FECHA "
			+ "FROM RegistroPesoEntity rp " + "INNER JOIN rp.ternera t "
			+ "WHERE t.idTernera = :idTernera AND rp.fecha > :fechaInicio " + "ORDER BY rp.fecha";

	private static final String VARIACION_PESO_POR_TERNERA_2FECHAS = "SELECT t.idTernera AS TERNERA, rp.peso AS PESO, rp.fecha AS FECHA "
			+ "FROM RegistroPesoEntity rp " + "INNER JOIN rp.ternera t "
			+ "WHERE t.idTernera = :idTernera AND rp.fecha BETWEEN :fechaInicio AND :fechaFin " + "ORDER BY rp.fecha";

	private static final String VARIACION_PESO_POR_GUACHERA_1FECHAREAL = "SELECT g.idGuachera AS GUACHERA, t.idTernera AS TERNERA, rp.peso AS PESO, rp.fecha AS FECHA "
			+ "FROM RegistroPesoEntity rp " + "INNER JOIN rp.ternera t " + "INNER JOIN t.guachera g "
			+ "WHERE g.idGuachera = :idGuachera AND rp.fecha > :fechaInicio " + "ORDER BY t.idTernera";

	private static final String VARIACION_PESO_POR_GUACHERA_2FECHASREAL = "SELECT g.idGuachera AS GUACHERA, t.idTernera AS TERNERA, rp.peso AS PESO, rp.fecha AS FECHA "
			+ "FROM RegistroPesoEntity rp " + "INNER JOIN rp.ternera t " + "INNER JOIN t.guachera g "
			+ "WHERE g.idGuachera = :idGuachera AND  rp.fecha BETWEEN :fechaInicio AND :fechaFin "
			+ "ORDER BY t.idTernera";

	public Vector<CostoAlimTernera> listaCostoAlimPorTernera1Fecha(Long idTernera, String fechaInicioString) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

		try {
			java.util.Date fechaUtil = formatoFecha.parse(fechaInicioString);
			java.sql.Date fechaInicio = new java.sql.Date(fechaUtil.getTime());

			Query query = em.createQuery(COSTO_ALIM_POR_TERNERA_1_FECHA);
			query.setParameter("id", idTernera);
			query.setParameter("fecha", fechaInicio);
			List<Object[]> resultList = query.getResultList();

			Vector<CostoAlimTernera> listaCostoAlim = new Vector<>();
			for (Object[] result : resultList) {
				Long terneraId = (Long) result[0];
				Integer costo = (int) result[1];
				int cantidad = (int) result[2];
				CostoAlimTernera costoAlimTernera = new CostoAlimTernera(terneraId.intValue(), costo.floatValue(),
						cantidad);
				listaCostoAlim.add(costoAlimTernera);
			}

			return listaCostoAlim;

		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Error en el formato de la fecha. El formato correcto es 'dd-MM-yyyy'.");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Vector<CostoAlimTernera> listaCostoAlimPorTernera2Fechas(Long idTernera, String fechaInicioString,
			String fechaFinalString) throws PersistenciaException {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

		try {
			java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
			java.util.Date fechaFinalUtil = formatoFecha.parse(fechaFinalString);
			java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
			java.sql.Date fechaFinal = new java.sql.Date(fechaFinalUtil.getTime());

			Query query = em.createQuery(COSTO_ALIM_POR_TERNERA_2_FECHAS);
			query.setParameter("id", idTernera);
			query.setParameter("fechaInicio", fechaInicio);
			query.setParameter("fechaFin", fechaFinal);
			List<Object[]> resultList = query.getResultList();

			Vector<CostoAlimTernera> listaCostoAlim = new Vector<>();
			for (Object[] result : resultList) {
				Long terneraId = (Long) result[0];
				Integer costo = (int) result[1];
				int cantidad = (int) result[2];
				CostoAlimTernera costoAlimTernera = new CostoAlimTernera(terneraId.intValue(), costo.floatValue(),
						cantidad);
				listaCostoAlim.add(costoAlimTernera);
			}

			return listaCostoAlim;

		} catch (Exception e) {
			throw new PersistenciaException("Error al crear el informe" + e.getMessage(), e);		
		}
	}

	public Vector<CostoAlimGuachera> listaCostoAlimPorGuachera1Fecha(Long id_Guachera, String fechaInicioString)
			throws ParseException {
		Vector<CostoAlimGuachera> listaCostoGuachera = new Vector<>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		try {
		java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
		java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
		Query query = em.createQuery(COSTO_ALIM_POR_TERNERA_GUACHERA_1_FECHA);
		query.setParameter("idGuachera", id_Guachera);
		query.setParameter("fechaInicio", fechaInicio);

		List<Object[]> results = query.getResultList();

		for (Object[] result : results) {
			Integer intNumber = (int) result[2];
			Float floatNumber = intNumber.floatValue();
			CostoAlimGuachera costoAlimGuachera = new CostoAlimGuachera((Long) result[0], (Long) result[1], floatNumber,
					(int) result[3]);
			listaCostoGuachera.add(costoAlimGuachera);
		}
		return listaCostoGuachera;
		}catch (Exception e) {
			e.printStackTrace();
			return null;}
			}

	public Vector<CostoAlimGuachera> listaCostoAlimPorGuachera2Fechas(Long idGuachera, String fechaInicioString,
			String fechaFinalString) throws ParseException {
		Vector<CostoAlimGuachera> listaCostoGuachera = new Vector<>();

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
try {
		java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
		java.util.Date fechaFinalUtil = formatoFecha.parse(fechaFinalString);
		java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
		java.sql.Date fechaFinal = new java.sql.Date(fechaFinalUtil.getTime());

		Query query = em.createQuery(COSTO_ALIM_POR_TERNERA_GUACHERA_2_FECHAS);
		query.setParameter("idGuachera", idGuachera);
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaFin", fechaFinal);

		List<Object[]> results = query.getResultList();

		for (Object[] result : results) {
			Integer intNumber = (int) result[2];
			Float floatNumber = intNumber.floatValue();
			CostoAlimGuachera costoAlimGuachera = new CostoAlimGuachera((Long) result[0], (Long) result[1], floatNumber,
					(int) result[3]);
			listaCostoGuachera.add(costoAlimGuachera);
		}

		return listaCostoGuachera;
}catch (Exception e) {
	e.printStackTrace();
	return null;}
	}

	public Vector<VariacionPesoTernera> listarVariacionDePeso1Fecha(Long idTernera, String fechaInicioString)
			throws ParseException {
		Vector<VariacionPesoTernera> listaPesoTernera = new Vector<>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		try {
		java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
		java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());

		Query query = em.createQuery(VARIACION_PESO_POR_TERNERA_1FECHA);
		query.setParameter("idTernera", idTernera);
		query.setParameter("fechaInicio", fechaInicio);

		List<Object[]> results = query.getResultList();

		for (Object[] result : results) {
			Integer intNumber = (int) result[1];
			Float floatNumber = intNumber.floatValue();
			VariacionPesoTernera variacionPesoTernera = new VariacionPesoTernera((Long) result[0], floatNumber,
					(Date) result[2]);
			listaPesoTernera.add(variacionPesoTernera);
		}

		return listaPesoTernera;
		}catch (Exception e) {
			e.printStackTrace();
			return null;}
			}

	public Vector<VariacionPesoTernera> listarVariacionDePeso2Fechas(Long idTernera, String fechaInicioString,
			String fechaFinString) throws ParseException {
		Vector<VariacionPesoTernera> listaPesoTernera = new Vector<>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		try {
		java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
		java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
		java.util.Date fechaFinUtil = formatoFecha.parse(fechaFinString);
		java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

		Query query = em.createQuery(VARIACION_PESO_POR_TERNERA_2FECHAS);
		query.setParameter("idTernera", idTernera);
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaFin", fechaFin);

		List<Object[]> results = query.getResultList();

		for (Object[] result : results) {
			Integer intNumber = (int) result[1];
			Float floatNumber = intNumber.floatValue();
			VariacionPesoTernera variacionPesoTernera = new VariacionPesoTernera((Long) result[0], floatNumber,
					(Date) result[2]);
			listaPesoTernera.add(variacionPesoTernera);
		}

		return listaPesoTernera;
		}catch (Exception e) {
			e.printStackTrace();
			return null;}
			}

	public Vector<VariacionPesoGuachera> listarVariacionDePeso1FechaGuachera(Long idGuachera, String fechaInicioString)
			throws ParseException {
		Vector<VariacionPesoGuachera> listaPesoTernera = new Vector<>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		try {
		java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
		java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());

		Query query = em.createQuery(VARIACION_PESO_POR_GUACHERA_1FECHAREAL);
		query.setParameter("idGuachera", idGuachera);
		query.setParameter("fechaInicio", fechaInicio);

		List<Object[]> results = query.getResultList();

		for (Object[] result : results) {
			Integer intNumber = (int) result[2];
			Float floatNumber = intNumber.floatValue();
			VariacionPesoGuachera variacionPesoGuachera = new VariacionPesoGuachera((Long) result[0], (Long) result[1],
					floatNumber, (Date) result[3]);
			listaPesoTernera.add(variacionPesoGuachera);
		}

		return listaPesoTernera;
		}catch (Exception e) {
			e.printStackTrace();
			return null;}
			}

	public Vector<VariacionPesoGuachera> listarVariacionDePeso2FechasGuachera(Long idGuachera, String fechaInicioString,
			String fechaFinString) throws ParseException {
		Vector<VariacionPesoGuachera> listaPesoTernera = new Vector<>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		try {
		java.util.Date fechaInicioUtil = formatoFecha.parse(fechaInicioString);
		java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
		java.util.Date fechaFinUtil = formatoFecha.parse(fechaFinString);
		java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

		Query query = em.createQuery(VARIACION_PESO_POR_GUACHERA_2FECHASREAL);
		query.setParameter("idGuachera", idGuachera);
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaFin", fechaFin);

		List<Object[]> results = query.getResultList();

		for (Object[] result : results) {
			Integer intNumber = (int) result[2];
			Float floatNumber = intNumber.floatValue();
			VariacionPesoGuachera variacionPesoGuachera = new VariacionPesoGuachera((Long) result[0], (Long) result[1],
					floatNumber, (Date) result[3]);
			listaPesoTernera.add(variacionPesoGuachera);
		}

		return listaPesoTernera;
		}catch (Exception e) {
			e.printStackTrace();
			return null;}
			}

	/*
	 * public static Vector<VariacionPesoTernera> listarVariacionDePeso2Fechas(int
	 * id_ternera, Date Fechainicio, Date Fechafin) { Vector<VariacionPesoTernera>
	 * listaPesoTernera = new Vector<>();
	 * 
	 * try { PreparedStatement statement = DatabaseManager.getConnection()
	 * .prepareStatement(VARIACION_PESO_POR_TERNERA_2FECHA); statement.setInt(1,
	 * id_ternera); statement.setDate(2, Fechainicio); statement.setDate(3,
	 * Fechafin);
	 * 
	 * ResultSet resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) {
	 * 
	 * int idternera = resultado.getInt("TERNERA"); Float peso =
	 * resultado.getFloat("PESO KG"); Date fecha = resultado.getDate("FECHA");
	 * 
	 * VariacionPesoTernera VpT = new VariacionPesoTernera(idternera, peso, fecha);
	 * 
	 * listaPesoTernera.add(VpT);
	 * 
	 * } return listaPesoTernera;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Vector<Registro1> informe1() { Vector<Registro1> registros =
	 * new Vector<>();
	 * 
	 * try { PreparedStatement statement =
	 * DatabaseManager.getConnection().prepareStatement(PESO_POR_TERNERA); ResultSet
	 * resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) { int caravana = resultado.getInt("ID_CARAVANA");
	 * Date fecha = resultado.getDate("FECHA"); float peso =
	 * resultado.getFloat("PESO_KG");
	 * 
	 * Registro1 r = new Registro1(caravana, fecha, peso); System.out.println(r);
	 * registros.add(r);
	 * 
	 * } return registros;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Vector<VariacionPesoGuachera>
	 * INFORME_VariacionPesoGuachera_1FECHA(int id_guachera, Date Fechainicio) {
	 * Vector<VariacionPesoGuachera> listaPesoTernera = new Vector<>();
	 * 
	 * try {
	 * 
	 * PreparedStatement statement = DatabaseManager.getConnection()
	 * .prepareStatement(VARIACION_PESO_POR_GUACHERA_INFORME_1_FECHA);
	 * statement.setInt(1, id_guachera); statement.setDate(2, Fechainicio);
	 * 
	 * ResultSet resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) {
	 * 
	 * int idGuachera = resultado.getInt("GUACHERA"); int idTernera =
	 * resultado.getInt("TERNERA"); Float peso = resultado.getFloat("PESO KG"); Date
	 * fecha = resultado.getDate("FECHA");
	 * 
	 * VariacionPesoGuachera VpT = new VariacionPesoGuachera(idGuachera, idTernera,
	 * peso, fecha);
	 * 
	 * listaPesoTernera.add(VpT);
	 * 
	 * } return listaPesoTernera;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Vector<VariacionPesoGuachera>
	 * INFORME_VariacionPesoGuachera_2FECHA(int id_guachera, Date Fechainicio, Date
	 * Fechafin) { Vector<VariacionPesoGuachera> listaPesoTernera = new Vector<>();
	 * 
	 * try { PreparedStatement statement = DatabaseManager.getConnection()
	 * .prepareStatement(VARIACION_PESO_POR_GUACHERA_INFORME_2_FECHA);
	 * statement.setInt(1, id_guachera); statement.setDate(2, Fechainicio);
	 * statement.setDate(3, Fechafin);
	 * 
	 * ResultSet resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) {
	 * 
	 * int idGuachera = resultado.getInt("GUACHERA"); int idTernera =
	 * resultado.getInt("TERNERA"); Float peso = resultado.getFloat("PESO KG"); Date
	 * fecha = resultado.getDate("FECHA");
	 * 
	 * VariacionPesoGuachera VpT = new VariacionPesoGuachera(idGuachera, idTernera,
	 * peso, fecha);
	 * 
	 * listaPesoTernera.add(VpT);
	 * 
	 * } return listaPesoTernera;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Vector<VariacionPesoGuachera>
	 * listarVariacionDePesoGuachera2Fecha(int id_guachera, Date Fechainicio, Date
	 * Fechafin) { Vector<VariacionPesoGuachera> listaPesoTernera = new Vector<>();
	 * 
	 * try { PreparedStatement statement = DatabaseManager.getConnection()
	 * .prepareStatement(VARIACION_PESO_POR_GUACHERA_2FECHAS); statement.setInt(1,
	 * id_guachera); statement.setDate(2, Fechainicio); statement.setDate(3,
	 * Fechafin);
	 * 
	 * ResultSet resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) {
	 * 
	 * // int idGuachera = resultado.getInt("GUACHERA"); int idTernera =
	 * resultado.getInt("TERNERA"); // Float peso = resultado.getFloat("PESO KG");
	 * // Date fecha = resultado.getDate("FECHA");
	 * 
	 * VariacionPesoGuachera VpT = new VariacionPesoGuachera(idTernera);
	 * 
	 * listaPesoTernera.add(VpT);
	 * 
	 * } return listaPesoTernera;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Vector<VariacionPesoGuachera>
	 * listarVariacionDePesoGuachera1Fecha(int id_guachera, Date Fechainicio) {
	 * Vector<VariacionPesoGuachera> listaPesoTernera = new Vector<>();
	 * 
	 * try { PreparedStatement statement = DatabaseManager.getConnection()
	 * .prepareStatement(VARIACION_PESO_POR_GUACHERA_1FECHA); statement.setInt(1,
	 * id_guachera); statement.setDate(2, Fechainicio);
	 * 
	 * ResultSet resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) {
	 * 
	 * // int idGuachera = resultado.getInt("GUACHERA"); int idTernera =
	 * resultado.getInt("TERNERA"); // Float peso = resultado.getFloat("PESO KG");
	 * // Date fecha = resultado.getDate("FECHA");
	 * 
	 * VariacionPesoGuachera VpT = new VariacionPesoGuachera(idTernera);
	 * 
	 * listaPesoTernera.add(VpT);
	 * 
	 * } return listaPesoTernera;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public static Vector<Integer> listarCaravanas() { Vector<Integer> caravanas =
	 * new Vector<>();
	 * 
	 * try { PreparedStatement statement =
	 * DatabaseManager.getConnection().prepareStatement(LISTA_CARAVANA); ResultSet
	 * resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) { int c = resultado.getInt("ID_CARAVANA"); ;
	 * caravanas.add(c);
	 * 
	 * } return caravanas;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Vector<Integer> listarGuacheras() { Vector<Integer> guacheras =
	 * new Vector<>();
	 * 
	 * try { PreparedStatement statement =
	 * DatabaseManager.getConnection().prepareStatement(LISTA_GUACHERA); ResultSet
	 * resultado = statement.executeQuery();
	 * 
	 * while (resultado.next()) { int g = resultado.getInt("ID_GUACHERA"); ;
	 * guacheras.add(g);
	 * 
	 * } return guacheras;
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 */

}
