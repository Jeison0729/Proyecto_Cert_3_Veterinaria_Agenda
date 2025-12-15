import React, { useEffect, useState } from "react";
import api from "../../api";
import "../../styles/list-pages.css";

export default function MascotasPage() {
  const [mascotas, setMascotas] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        // endpoint general de mascotas (si existe). Si no, puedes obtener list por cliente.
        const res = await api.get("/mascotas").then(r => r.data ?? r);
        setMascotas(Array.isArray(res) ? res : (res?.data || []));
      } catch (err) {
        console.error("Error mascotas:", err);
        setMascotas([]);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Mascotas</h2>
      {loading ? <p>Cargando mascotas...</p> : (
        <table className="mw-table">
          <thead>
            <tr><th>ID</th><th>Nombre</th><th>Especie</th><th>Raza</th><th>Cliente</th></tr>
          </thead>
          <tbody>
            {mascotas.map(m => (
              <tr key={m.id}>
                <td>{m.id}</td>
                <td>{m.nombre}</td>
                <td>{m.especieNombre ?? m.id_especie}</td>
                <td>{m.raza}</td>
                <td>{m.clienteNombre ?? m.id_cliente}</td>
              </tr>
            ))}
            {mascotas.length === 0 && <tr><td colSpan="5">No hay mascotas</td></tr>}
          </tbody>
        </table>
      )}
    </div>
  );
}
