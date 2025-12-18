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

  // Cargar clientes al montar el componente
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

  // Se ejecuta cuando el componente se monta
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

  // Filtrar clientes por búsqueda
  const clientesFiltrados = clientes.filter(
    (c) =>
      c.nombres?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.apellidos?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.dni?.includes(searchTerm)
  );

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Gestión de Clientes</h2>

      {/* Botón para crear nuevo cliente - ABRE EL FORMULARIO MODAL */}
      <button
        className="mw-btn-primary"
        onClick={() => {
          setEditData(null);
          setShowForm(true);
        }}
      >
        + Nuevo Cliente
      </button>

      {/* Campo de búsqueda */}
      <input
        type="text"
        placeholder="Buscar por nombre, apellido o DNI..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{
          marginLeft: "10px",
          padding: "10px",
          borderRadius: "5px",
          border: "1px solid #ccc",
          width: "300px",
        }}
      />

      {/* Tabla de clientes */}
      {loading ? (
        <p>Cargando clientes...</p>
      ) : clientesFiltrados.length === 0 ? (
        <p>No hay clientes registrados</p>
      ) : (
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
                  {/* Botón EDITAR - Abre el formulario con datos */}
                  <button
                    className="mw-btn-primary"
                    onClick={() => {
                      setEditData(cliente);
                      setShowForm(true);
                    }}
                  >
                    Editar
                  </button>

                  {/* Botón ELIMINAR - Solicita confirmación */}
                  <button
                    style={{
                      background: "#e74c3c",
                      color: "white",
                      padding: "10px 16px",
                      borderRadius: "8px",
                      border: "none",
                      cursor: "pointer",
                    }}
                    onClick={() => handleEliminar(cliente.id)}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* Modal FORMULARIO - Se muestra SOLO si showForm es true */}
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
