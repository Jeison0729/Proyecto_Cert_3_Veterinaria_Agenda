import React, { useEffect, useState } from "react";
import { getClientes } from "../../api";
import "../../styles/list-pages.css";

export default function ClientesPage() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const res = await getClientes();
        // api.js unwrap devuelve data o array
        setClientes(Array.isArray(res) ? res : (res?.data || []));
      } catch (err) {
        console.error("Error cargando clientes:", err);
        setClientes([]);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Clientes</h2>
      {loading ? <p>Cargando clientes...</p> : (
        <table className="mw-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombres</th>
              <th>Apellidos</th>
              <th>DNI</th>
              <th>Celular</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map(c => (
              <tr key={c.id}>
                <td>{c.id ?? c.idCliente}</td>
                <td>{c.nombres ?? c.nombre}</td>
                <td>{c.apellidos ?? c.apellido}</td>
                <td>{c.dni ?? c.documento}</td>
                <td>{c.celular ?? c.telefono}</td>
                <td>{c.email ?? c.correo}</td>
              </tr>
            ))}
            {clientes.length === 0 && <tr><td colSpan="6">No se encontraron clientes</td></tr>}
          </tbody>
        </table>
      )}
    </div>
  );
}
