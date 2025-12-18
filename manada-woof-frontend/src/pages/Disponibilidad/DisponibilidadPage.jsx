import React, { useEffect, useState } from "react";
import {
  getPersonal,
  getDisponibilidadPersonal,
  deleteDisponibilidadPersonal,
} from "../../api";
import DisponibilidadForm from "./DisponibilidadForm";
import "./disponibilidad.css";

export default function DisponibilidadPage() {
  const [personalList, setPersonalList] = useState([]);
  const [selectedPersonal, setSelectedPersonal] = useState(null);
  const [disponibilidades, setDisponibilidades] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    const cargarPersonal = async () => {
      try {
        const res = await getPersonal();
        const lista = Array.isArray(res) ? res : res.data || [];
        setPersonalList(lista);
        if (lista.length > 0) {
          setSelectedPersonal(lista[0].id);
        }
      } catch (err) {
        console.error("Error al cargar personal:", err);
      }
    };
    cargarPersonal();
  }, []);

  useEffect(() => {
    cargarDisponibilidades();
  }, [selectedPersonal]);

  const cargarDisponibilidades = async () => {
    if (!selectedPersonal) {
      setDisponibilidades([]);
      setLoading(false);
      return;
    }

    setLoading(true);
    try {
      const hoy = new Date();
      const inicio = new Date(hoy);
      inicio.setMonth(hoy.getMonth() - 3);
      const fin = new Date(hoy);
      fin.setMonth(hoy.getMonth() + 6);

      const fechaInicio = inicio.toISOString().split("T")[0];
      const fechaFin = fin.toISOString().split("T")[0];

      const res = await getDisponibilidadPersonal(
        selectedPersonal,
        fechaInicio,
        fechaFin
      );

      console.log("Respuesta API:", res);
      setDisponibilidades(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error("Error cargando disponibilidad:", err);
      setDisponibilidades([]);
    } finally {
      setLoading(false);
    }
  };

  const handleEliminar = async (id) => {
    if (window.confirm("¿Eliminar este bloque de disponibilidad?")) {
      try {
        await deleteDisponibilidadPersonal(id);
        setDisponibilidades((prev) => prev.filter((d) => d.id !== id));
        alert("Bloque eliminado");
      } catch (err) {
        alert(
          "Error al eliminar: " + (err.response?.data?.message || err.message)
        );
      }
    }
  };

  const recargarTrasGuardar = () => {
    setShowForm(false);
    cargarDisponibilidades();
  };

  const personalActual = personalList.find((p) => p.id === selectedPersonal);

  return (
    <div className="mw-page">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h2 fw-bold text-dark">
          <i className="bi bi-calendar-week-fill me-2"></i>
          Disponibilidad del Personal
        </h1>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => setShowForm(true)}
          disabled={!selectedPersonal}
        >
          <i className="bi bi-plus-circle me-2"></i>Nuevo Bloque
        </button>
      </div>

      <div className="card shadow-sm mb-4">
        <div className="card-body">
          <label className="form-label fw-bold">Seleccionar Personal</label>
          <select
            className="form-select form-select-lg"
            value={selectedPersonal || ""}
            onChange={(e) => setSelectedPersonal(Number(e.target.value))}
          >
            <option value="">-- Seleccionar personal --</option>
            {personalList.map((p) => (
              <option key={p.id} value={p.id}>
                {p.nombreCompleto || `${p.nombres} ${p.apellidos}`} -{" "}
                {p.especialidad}
              </option>
            ))}
          </select>
        </div>
      </div>

      {loading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
          <p className="mt-3 text-muted">Cargando disponibilidad...</p>
        </div>
      ) : !selectedPersonal ? (
        <div className="alert alert-info text-center py-5">
          <i className="bi bi-info-circle me-2"></i>
          Selecciona un miembro del personal para ver su disponibilidad
        </div>
      ) : disponibilidades.length === 0 ? (
        <div className="alert alert-warning text-center py-5">
          <i className="bi bi-clock-history me-2"></i>
          <strong>
            {personalActual?.nombreCompleto || "Este personal"}
          </strong>{" "}
          no tiene bloques de disponibilidad registrados
        </div>
      ) : (
        <div className="table-responsive shadow-sm rounded">
          <table className="table table-hover table-striped mb-0">
            <thead className="table-dark">
              <tr>
                <th>#</th>
                <th>Fecha</th>
                <th>Hora Inicio</th>
                <th>Hora Fin</th>
                <th>Estado</th>
                <th className="text-center">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {disponibilidades.map((disp, idx) => (
                <tr key={disp.id}>
                  <td>
                    <span className="badge bg-secondary">{idx + 1}</span>
                  </td>
                  <td>
                    <strong>
                      {new Date(disp.fecha + "T00:00:00").toLocaleDateString(
                        "es-PE",
                        {
                          weekday: "long",
                          year: "numeric",
                          month: "long",
                          day: "numeric",
                        }
                      )}
                    </strong>
                  </td>
                  <td>{disp.horaInicio.substring(0, 5)}</td>
                  <td>{disp.horaFin.substring(0, 5)}</td>
                  <td>
                    <span
                      className={`badge ${disp.activo ? "bg-success" : "bg-secondary"}`}
                    >
                      {disp.activo ? "Disponible" : "Inactivo"}
                    </span>
                  </td>
                  <td className="text-center">
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => handleEliminar(disp.id)}
                    >
                      <i className="bi bi-trash"></i>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showForm && selectedPersonal && (
        <DisponibilidadForm
          idPersonal={selectedPersonal}
          onClose={() => setShowForm(false)}
          onSaved={recargarTrasGuardar}
        />
      )}
    </div>
  );
}
