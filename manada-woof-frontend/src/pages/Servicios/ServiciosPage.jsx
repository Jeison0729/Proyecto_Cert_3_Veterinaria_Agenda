import React, { useEffect, useState } from "react";
import { getServicios } from "../../api";
import ServiciosForm from "./ServiciosForm";
import "./servicios.css";

export default function ServiciosPage() {
  const [servicios, setServicios] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");

  const cargarServicios = async () => {
    setLoading(true);
    try {
      const res = await getServicios();
      setServicios(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error("Error al cargar servicios:", err);
      setServicios([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarServicios();
  }, []);

  const serviciosFiltrados = servicios.filter(
    (s) =>
      s.nombre?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      s.categoriaNombre?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleSaved = () => {
    setShowForm(false);
    cargarServicios();
  };

  return (
    <div className="mw-page">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h2 fw-bold text-dark">
          <i className="bi bi-scissors me-2"></i>Gestión de Servicios
        </h1>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => setShowForm(true)}
        >
          <i className="bi bi-plus-circle me-2"></i>Nuevo Servicio
        </button>
      </div>

      <div className="mb-4">
        <input
          type="text"
          placeholder="Buscar por nombre o categoría..."
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
          <p className="mt-3 text-muted">Cargando servicios...</p>
        </div>
      ) : serviciosFiltrados.length === 0 ? (
        <div className="alert alert-info text-center py-5">
          <i className="bi bi-inbox me-2"></i>
          <strong>No hay servicios registrados</strong>
        </div>
      ) : (
        <div className="table-responsive shadow-sm rounded">
          <table className="table table-hover table-striped mb-0">
            <thead className="table-dark">
              <tr>
                <th className="fw-bold">#ID</th>
                <th className="fw-bold">Nombre</th>
                <th className="fw-bold">Duración (min)</th>
                <th className="fw-bold">Precio</th>
                <th className="fw-bold">Categoría</th>
                <th className="fw-bold">Descripción</th>
              </tr>
            </thead>
            <tbody>
              {serviciosFiltrados.map((servicio, idx) => (
                <tr key={servicio.id}>
                  <td>
                    <span className="badge bg-secondary">{idx + 1}</span>
                  </td>
                  <td className="fw-500">{servicio.nombre}</td>
                  <td>{servicio.duracionMinutos}</td>
                  <td>
                    <strong>S/ {servicio.precio.toFixed(2)}</strong>
                  </td>
                  <td>{servicio.categoriaNombre}</td>
                  <td>
                    <small className="text-muted">
                      {servicio.descripcion || "-"}
                    </small>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showForm && (
        <ServiciosForm
          onClose={() => setShowForm(false)}
          onSaved={handleSaved}
        />
      )}
    </div>
  );
}
