import React, { useEffect, useState } from "react";
import { getClientes, deleteCliente } from "../../api";
import ClientesForm from "./ClientesForm";
import "./clientes.css";

export default function ClientesPage() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editData, setEditData] = useState(null);

  const cargarClientes = async () => {
    setLoading(true);
    const res = await getClientes();
    setClientes(res || []);
    setLoading(false);
  };

  useEffect(() => {
    cargarClientes();
  }, []);

  const handleEliminar = async (id) => {
    if (!confirm("¿Eliminar cliente?")) return;
    await deleteCliente(id);
    cargarClientes();
  };

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Clientes</h2>

      <button className="mw-btn-primary" onClick={() => {
        setEditData(null);
        setShowForm(true);
      }}>
        + Nuevo Cliente
      </button>

      {loading ? (
        <p>Cargando...</p>
      ) : (
        <table className="mw-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre completo</th>
              <th>Teléfono</th>
              <th>Correo</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {clientes.map(c => (
              <tr key={c.idCliente}>
                <td>{c.idCliente}</td>
                <td>{c.nombre} {c.apellido}</td>
                <td>{c.telefono}</td>
                <td>{c.correo}</td>
                <td className="mw-actions">
                  <button className="mw-btn-primary"
                    onClick={() => { setEditData(c); setShowForm(true); }}>
                    Editar
                  </button>

                  <button
                    style={{ background: "#bb2d3b", color: "white", padding: "8px 12px", borderRadius: "8px" }}
                    onClick={() => handleEliminar(c.idCliente)}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {showForm && (
        <ClientesForm
          editData={editData}
          onClose={() => setShowForm(false)}
          onSaved={() => { setShowForm(false); cargarClientes(); }}
        />
      )}
    </div>
  );
}
