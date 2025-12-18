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
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h2 fw-bold text-dark">
          <i className="bi bi-people-fill me-2"></i>Gestión de Clientes
        </h1>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => {
            setEditData(null);
            setShowForm(true);
          }}
        >
          + Nuevo Cliente
        </button>
      </div>

      {/* Búsqueda */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="Buscar por: Nombre - Apellido - DNI"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="form-control form-control-lg"
        />
      </div>

      {/* Tabla */}
      {loading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
          <p className="mt-3 text-muted">Cargando clientes...</p>
        </div>
      ) : clientesFiltrados.length === 0 ? (
        <div className="alert alert-info text-center py-5" role="alert">
          <i className="bi bi-inbox me-2"></i>
          <strong>No hay clientes registrados</strong>
        </div>
      ) : (
        <div className="table-responsive shadow-sm rounded">
          <table className="table table-hover table-striped mb-0">
            <thead className="table-dark">
              <tr>
                <th className="fw-bold">#ID</th>
                <th className="fw-bold">Nombre Completo</th>
                <th className="fw-bold">DNI</th>
                <th className="fw-bold">Teléfono</th>
                <th className="fw-bold">Email</th>
                <th className="fw-bold">Distrito</th>
                <th className="fw-bold">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {clientesFiltrados.map((cliente, idx) => (
                <tr key={cliente.id}>
                  <td>
                    <span className="badge bg-secondary">{idx + 1}</span>
                  </td>
                  <td className="fw-500">
                    {cliente.nombres} {cliente.apellidos}
                  </td>
                  <td>
                    <code>{cliente.dni}</code>
                  </td>
                  <td>
                    <a
                      href={`tel:${cliente.celular}`}
                      className="bi bi-paw me-2"
                    >
                      {cliente.celular}
                    </a>
                  </td>
                  <td>
                    {cliente.email ? (
                      <a
                        href={`mailto:${cliente.email}`}
                        className="bi bi-paw me-2"
                      >
                        {cliente.email}
                      </a>
                    ) : (
                      <span className="bi bi-paw me-2">-</span>
                    )}
                  </td>
                  <td>
                    <span className="bi bi-paw me-2">
                      {cliente.distrito || "-"}
                    </span>
                  </td>
                  <td className="text-center">
                    <button
                      className="btn btn-sm btn-success me-2"
                      onClick={() => {
                        setEditData(cliente);
                        setShowForm(true);
                      }}
                      title="Editar"
                    >
                      <i className="bi bi-pencil-square"></i> Editar
                    </button>
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => handleEliminar(cliente.id)}
                      title="Eliminar"
                    >
                      <i className="bi bi-trash"></i> Eliminar
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
