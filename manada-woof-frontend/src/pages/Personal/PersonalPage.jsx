import React, { useEffect, useState } from "react";
import { getPersonal, deletePersonal } from "../../api";
import PersonalForm from "./PersonalForm";
import "./personal.css";

export default function PersonalPage() {
  const [personal, setPersonal] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editData, setEditData] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  const cargarPersonal = async () => {
    setLoading(true);
    try {
      const res = await getPersonal();
      setPersonal(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error("Error al cargar personal:", err);
      setPersonal([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarPersonal();
  }, []);

  const handleEliminar = async (id) => {
    if (window.confirm("¿Seguro quieres eliminar este miembro del personal?")) {
      try {
        await deletePersonal(id);
        cargarPersonal();
      } catch (err) {
        alert("Error al eliminar: " + err.message);
      }
    }
  };

  const personalFiltrado = personal.filter(
    (p) =>
      p.nombres?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      p.apellidos?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      p.especialidad?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      p.telefono?.includes(searchTerm)
  );

  return (
    <div className="mw-page">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h2 fw-bold text-dark">
          <i className="bi bi-person-badge-fill me-2"></i>Gestión de Personal /
          Veterinarios
        </h1>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => {
            setEditData(null);
            setShowForm(true);
          }}
        >
          <i className="bi bi-plus-circle me-2"></i>Nuevo Personal
        </button>
      </div>

      <div className="mb-4">
        <input
          type="text"
          placeholder="Buscar por nombre, apellido o especialidad..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="form-control form-control-lg"
        />
      </div>

      {loading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
          <p className="mt-3 text-muted">Cargando personal...</p>
        </div>
      ) : personalFiltrado.length === 0 ? (
        <div className="alert alert-info text-center py-5">
          <i className="bi bi-inbox me-2"></i>
          <strong>No hay personal registrado</strong>
        </div>
      ) : (
        <div className="table-responsive shadow-sm rounded">
          <table className="table table-hover table-striped mb-0">
            <thead className="table-dark">
              <tr>
                <th className="fw-bold">#ID</th>
                <th className="fw-bold">Nombres</th>
                <th className="fw-bold">Apellidos</th>
                <th className="fw-bold">Especialidad</th>
                <th className="fw-bold">Teléfono</th>
                <th className="fw-bold">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {personalFiltrado.map((persona, idx) => (
                <tr key={persona.id}>
                  <td>
                    <span className="badge bg-secondary">{idx + 1}</span>
                  </td>
                  <td className="fw-500">{persona.nombreCompleto}</td>
                  <td>{persona.apellidos}</td>
                  <td>{persona.especialidad || "-"}</td>
                  <td>
                    <a
                      href={`tel:${persona.telefono}`}
                      className="text-primary"
                    >
                      {persona.telefono || "-"}
                    </a>
                  </td>
                  <td className="text-center">
                    <button
                      className="btn btn-sm btn-success me-2"
                      onClick={() => {
                        setEditData(persona);
                        setShowForm(true);
                      }}
                    >
                      <i className="bi bi-pencil-square"></i> Editar
                    </button>
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => handleEliminar(persona.id)}
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

      {showForm && (
        <PersonalForm
          editData={editData}
          onClose={() => setShowForm(false)}
          onSaved={() => {
            setShowForm(false);
            cargarPersonal();
          }}
        />
      )}
    </div>
  );
}
