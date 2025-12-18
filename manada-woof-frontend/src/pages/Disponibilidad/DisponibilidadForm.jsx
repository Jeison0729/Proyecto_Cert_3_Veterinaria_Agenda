import React, { useState } from "react";
import { createDisponibilidadPersonal } from "../../api";

export default function DisponibilidadForm({ idPersonal, onClose, onSaved }) {
  const [formData, setFormData] = useState({
    fecha: "",
    horaInicio: "",
    horaFin: "",
    activo: true,
  });

  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const validar = () => {
    const errs = {};
    if (!formData.fecha) errs.fecha = "Fecha requerida";
    if (!formData.horaInicio) errs.horaInicio = "Hora inicio requerida";
    if (!formData.horaFin) errs.horaFin = "Hora fin requerida";
    if (
      formData.horaInicio &&
      formData.horaFin &&
      formData.horaInicio >= formData.horaFin
    ) {
      errs.horaFin = "La hora fin debe ser posterior a la hora inicio";
    }
    setErrors(errs);
    return Object.keys(errs).length === 0;
  };

  const handleSubmit = async (e) => {
    if (e) e.preventDefault();
    if (!validar()) return;

    setLoading(true);
    try {
      const payload = {
        idPersonal,
        fecha: formData.fecha,
        horaInicio:
          formData.horaInicio.length === 5
            ? formData.horaInicio + ":00"
            : formData.horaInicio,
        horaFin:
          formData.horaFin.length === 5
            ? formData.horaFin + ":00"
            : formData.horaFin,
        activo: formData.activo,
      };

      console.log("📤 Enviando payload:", payload);

      const response = await createDisponibilidadPersonal(payload);

      console.log("✅ Respuesta del servidor:", response);

      alert("✅ Bloque de disponibilidad creado correctamente");
      onSaved();
      onClose();
    } catch (err) {
      console.error("❌ Error completo:", err);
      console.error("❌ Error response:", err.response);
      const errorMsg =
        err.response?.data?.message || err.message || "Error desconocido";
      alert("❌ Error: " + errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal show d-block" onClick={(e) => e.stopPropagation()}>
        <div className="modal-dialog modal-dialog-centered modal-lg">
          <div className="modal-content">
            <div className="modal-header bg-primary text-white">
              <h5 className="modal-title fw-bold">
                <i className="bi bi-calendar-plus me-2"></i>
                Nuevo Bloque de Disponibilidad
              </h5>
              <button
                type="button"
                className="btn-close btn-close-white"
                onClick={onClose}
              ></button>
            </div>

            <div className="modal-body">
              <div className="row">
                <div className="col-md-12 mb-4">
                  <label className="form-label fw-bold">
                    <i className="bi bi-calendar-date me-2"></i>Fecha *
                  </label>
                  <input
                    type="date"
                    className={`form-control form-control-lg ${errors.fecha ? "is-invalid" : ""}`}
                    value={formData.fecha}
                    min={new Date().toISOString().split("T")[0]}
                    onChange={(e) =>
                      setFormData({ ...formData, fecha: e.target.value })
                    }
                  />
                  {errors.fecha && (
                    <div className="invalid-feedback d-block">
                      <i className="bi bi-exclamation-circle me-1"></i>
                      {errors.fecha}
                    </div>
                  )}
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-4">
                  <label className="form-label fw-bold">
                    <i className="bi bi-clock me-2"></i>Hora Inicio *
                  </label>
                  <div className="input-group input-group-lg">
                    <span className="input-group-text">
                      <i className="bi bi-clock-fill text-primary"></i>
                    </span>
                    <input
                      type="time"
                      className={`form-control ${errors.horaInicio ? "is-invalid" : ""}`}
                      value={formData.horaInicio}
                      step="900"
                      onChange={(e) =>
                        setFormData({ ...formData, horaInicio: e.target.value })
                      }
                    />
                    {errors.horaInicio && (
                      <div className="invalid-feedback d-block">
                        <i className="bi bi-exclamation-circle me-1"></i>
                        {errors.horaInicio}
                      </div>
                    )}
                  </div>
                  <small className="text-muted">
                    <i className="bi bi-info-circle me-1"></i>
                    Intervalos de 15 minutos
                  </small>
                </div>

                <div className="col-md-6 mb-4">
                  <label className="form-label fw-bold">
                    <i className="bi bi-clock me-2"></i>Hora Fin *
                  </label>
                  <div className="input-group input-group-lg">
                    <span className="input-group-text">
                      <i className="bi bi-clock-fill text-danger"></i>
                    </span>
                    <input
                      type="time"
                      className={`form-control ${errors.horaFin ? "is-invalid" : ""}`}
                      value={formData.horaFin}
                      step="900"
                      onChange={(e) =>
                        setFormData({ ...formData, horaFin: e.target.value })
                      }
                    />
                    {errors.horaFin && (
                      <div className="invalid-feedback d-block">
                        <i className="bi bi-exclamation-circle me-1"></i>
                        {errors.horaFin}
                      </div>
                    )}
                  </div>
                  <small className="text-muted">
                    <i className="bi bi-info-circle me-1"></i>
                    Intervalos de 15 minutos
                  </small>
                </div>
              </div>

              <div className="card border-0 bg-light mb-4">
                <div className="card-body">
                  <div className="form-check form-switch">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      role="switch"
                      id="activo"
                      checked={formData.activo}
                      style={{ width: "3rem", height: "1.5rem" }}
                      onChange={(e) =>
                        setFormData({ ...formData, activo: e.target.checked })
                      }
                    />
                    <label
                      className="form-check-label fw-bold ms-2"
                      htmlFor="activo"
                    >
                      <i
                        className={`bi ${formData.activo ? "bi-check-circle-fill text-success" : "bi-x-circle-fill text-secondary"} me-2`}
                      ></i>
                      {formData.activo
                        ? "Bloque activo (disponible para agendar citas)"
                        : "Bloque inactivo"}
                    </label>
                  </div>
                </div>
              </div>
            </div>

            <div className="modal-footer bg-light">
              <button
                type="button"
                className="btn btn-secondary"
                onClick={onClose}
              >
                <i className="bi bi-x-circle me-2"></i>Cancelar
              </button>
              <button
                type="button"
                className="btn btn-primary"
                disabled={loading}
                onClick={handleSubmit}
              >
                {loading ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-2"></span>
                    Guardando...
                  </>
                ) : (
                  <>
                    <i className="bi bi-check-circle me-2"></i>Guardar
                  </>
                )}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
