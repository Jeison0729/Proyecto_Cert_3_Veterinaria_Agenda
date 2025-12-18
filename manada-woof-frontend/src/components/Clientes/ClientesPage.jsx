import React, { useEffect, useState } from "react";
import { getClientes, deleteCliente } from "../../api";
import ClientesForm from "./ClientesForm";
import "./clientes.css";

export default function ClientesPage() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editData, setEditData] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  // Cargar clientes
  const cargarClientes = async () => {
    setLoading(true);
    try {
      const res = await getClientes();
      setClientes(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error("Error al cargar clientes:", err);
      setClientes([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarClientes();
  }, []);

  // Eliminar cliente
  const handleEliminar = async (id) => {
    if (window.confirm("¿Está seguro de eliminar este cliente?")) {
      try {
        await deleteCliente(id);
        cargarClientes();
      } catch (err) {
        alert("Error al eliminar: " + err.message);
      }
    }
  };

  // Filtrar por búsqueda
  const clientesFiltrados = clientes.filter(
    (c) =>
      c.nombres?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.apellidos?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.dni?.includes(searchTerm)
  );

  return (
    <div className="mw-page">
      <div className="mw-page-header">
        <h2 className="mw-page-title">Gestión de Clientes</h2>
        <button
          className="mw-btn-primary"
          onClick={() => {
            setEditData(null);
            setShowForm(true);
          }}
        >
          + Nuevo Cliente
        </button>
      </div>

      {/* Búsqueda */}
      <div className="mw-search-box">
        <input
          type="text"
          placeholder="Buscar por nombre, apellido o DNI..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="mw-search-input"
        />
      </div>

      {/* Tabla */}
      {loading ? (
        <p className="mw-loading">Cargando clientes...</p>
      ) : clientesFiltrados.length === 0 ? (
        <p className="mw-empty">No hay clientes registrados</p>
      ) : (
        <div className="mw-table-container">
          <table className="mw-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre Completo</th>
                <th>DNI</th>
                <th>Teléfono</th>
                <th>Email</th>
                <th>Distrito</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {clientesFiltrados.map((cliente) => (
                <tr key={cliente.id}>
                  <td>{cliente.id}</td>
                  <td>
                    {cliente.nombres} {cliente.apellidos}
                  </td>
                  <td>{cliente.dni}</td>
                  <td>{cliente.celular}</td>
                  <td>{cliente.email || "-"}</td>
                  <td>{cliente.distrito || "-"}</td>
                  <td className="mw-actions">
                    <button
                      className="mw-btn-edit"
                      onClick={() => {
                        setEditData(cliente);
                        setShowForm(true);
                      }}
                      title="Editar"
                    >
                      ✎ Editar
                    </button>
                    <button
                      className="mw-btn-delete"
                      onClick={() => handleEliminar(cliente.id)}
                      title="Eliminar"
                    >
                      ✕ Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal Formulario */}
      {showForm && (
        <ClientesForm
          editData={editData}
          onClose={() => setShowForm(false)}
          onSaved={() => {
            setShowForm(false);
            cargarClientes();
          }}
        />
      )}
    </div>
  );
}
