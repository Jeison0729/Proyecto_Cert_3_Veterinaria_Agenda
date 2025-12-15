import React, { useEffect, useState } from "react";
import { getServicios } from "../../api";
import "../../styles/list-pages.css";

export default function ServiciosPage() {
  const [servicios, setServicios] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const res = await getServicios();
        setServicios(Array.isArray(res) ? res : (res?.data || []));
      } catch (err) {
        console.error(err);
        setServicios([]);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Servicios</h2>
      {loading ? <p>Cargando servicios...</p> : (
        <table className="mw-table">
          <thead>
            <tr><th>ID</th><th>Nombre</th><th>Duración (min)</th><th>Precio</th><th>Categoria</th></tr>
          </thead>
          <tbody>
            {servicios.map(s => (
              <tr key={s.id}>
                <td>{s.id}</td>
                <td>{s.nombre}</td>
                <td>{s.duracion_minutos ?? s.duracion_min}</td>
                <td>{s.precio ?? s.valor}</td>
                <td>{s.categoriaNombre ?? s.id_categoria}</td>
              </tr>
            ))}
            {servicios.length === 0 && <tr><td colSpan="5">No hay servicios</td></tr>}
          </tbody>
        </table>
      )}
    </div>
  );
}
