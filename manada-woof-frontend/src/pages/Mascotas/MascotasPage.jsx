import React, { useEffect, useState } from "react";
import { getMascotas, deleteMascota } from "../../api";
import MascotasForm from "./MascotasForm";
import "./mascotas.css";

export default function MascotasPage() {
  const [mascotas, setMascotas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editData, setEditData] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  // Cargar mascotas
  const cargarMascotas = async () => {
    setLoading(true);
    try {
      const res = await getMascotas();
      setMascotas(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error("Error al cargar mascotas:", err);
      setMascotas([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarMascotas();
  }, []);

  // Eliminar mascota
  const handleEliminar = async (id) => {
    if (window.confirm("¿Está seguro de eliminar esta mascota?")) {
      try {
        await deleteMascota(id);
        cargarMascotas();
      } catch (err) {
        alert("Error al eliminar: " + err.message);
      }
    }
  };

  // Filtrar por búsqueda
  const mascotasFiltradas = mascotas.filter(
    (m) =>
      m.nombre?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      m.raza?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      m.clienteNombreCompleto?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="mw-page">
      {/* HEADER */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h2 fw-bold text-dark">
          <i className="bi bi-paw-fill me-2"></i>Gestión de Mascotas
        </h1>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => {
            setEditData(null);
            setShowForm(true);
          }}
        >
          <i className="bi bi-plus-circle me-2"></i>Nueva Mascota
        </button>
      </div>

      {/* BÚSQUEDA */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="Buscar por nombre, raza o cliente..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="form-control form-control-lg"
        />
      </div>

      {/* TABLA */}
      {loading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
          <p className="mt-3 text-muted">Cargando mascotas...</p>
        </div>
      ) : mascotasFiltradas.length === 0 ? (
        <div className="alert alert-info text-center py-5" role="alert">
          <i className="bi bi-inbox me-2"></i>
          <strong>No hay mascotas registradas</strong>
        </div>
      ) : (
        <div className="table-responsive shadow-sm rounded">
          <table className="table table-hover table-striped mb-0">
            <thead className="table-dark">
              <tr>
                <th className="fw-bold">#ID</th>
                <th className="fw-bold">Nombre</th>
                <th className="fw-bold">Especie</th>
                <th className="fw-bold">Raza</th>
                <th className="fw-bold">Sexo</th>
                <th className="fw-bold">Cliente</th>
                <th className="fw-bold">Peso</th>
                <th className="fw-bold text-center">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {mascotasFiltradas.map((mascota, idx) => (
                <tr key={mascota.id}>
                  <td>
                    <span className="badge bg-secondary">{idx + 1}</span>
                  </td>
                  <td className="fw-500">
                    <i className="bi bi-paw me-2"></i>
                    {mascota.nombre}
                  </td>
                  <td>
                    <span className="bi bi-paw me-2">
                      {mascota.especie || "-"}
                    </span>
                  </td>
                  <td>{mascota.raza || "-"}</td>
                  <td>
                    {mascota.sexo === "Macho" ? (
                      <span className="bi bi-paw me-2">Macho</span>
                    ) : (
                      <span className="bi bi-paw me-2">Hembra</span>
                    )}
                  </td>
                  <td>
                    <small className="text-muted">
                      {mascota.clienteNombreCompleto || "-"}
                    </small>
                  </td>
                  <td>
                    <code>
                      {mascota.pesoActual ? `${mascota.pesoActual} kg` : "-"}
                    </code>
                  </td>
                  <td className="text-center">
                    <button
                      className="btn btn-sm btn-success me-2"
                      onClick={() => {
                        setEditData(mascota);
                        setShowForm(true);
                      }}
                      title="Editar"
                    >
                      <i className="bi bi-pencil-square"></i> Editar
                    </button>
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => handleEliminar(mascota.id)}
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

      {/* MODAL FORMULARIO */}
      {showForm && (
        <MascotasForm
          editData={editData}
          onClose={() => setShowForm(false)}
          onSaved={() => {
            setShowForm(false);
            cargarMascotas();
          }}
        />
      )}
    </div>
  );
}
