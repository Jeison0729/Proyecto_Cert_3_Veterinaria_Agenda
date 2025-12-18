import React, { useState } from "react";
import { createServicio } from "../../api";

export default function ServiciosForm({ onClose, onSaved }) {
  const [formData, setFormData] = useState({
    nombre: "",
    duracionMinutos: "",
    precio: "",
    idCategoria: "",
    descripcion: "",
  });

  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const validar = () => {
    const errs = {};
    if (!formData.nombre.trim()) errs.nombre = "El nombre es requerido";
    if (!formData.duracionMinutos || formData.duracionMinutos <= 0)
      errs.duracionMinutos = "Duración válida requerida";
    if (!formData.precio || formData.precio <= 0)
      errs.precio = "Precio válido requerido";
    if (!formData.idCategoria || formData.idCategoria <= 0)
      errs.idCategoria = "Categoría requerida";
    setErrors(errs);
    return Object.keys(errs).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validar()) return;

    setLoading(true);
    try {
      const payload = {
        nombre: formData.nombre.trim(),
        duracionMinutos: Number(formData.duracionMinutos),
        precio: Number(formData.precio),
        idCategoria: Number(formData.idCategoria),
        descripcion: formData.descripcion.trim() || null,
      };

      await createServicio(payload);
      alert("Servicio creado correctamente");
      onSaved();
    } catch (err) {
      alert("Error: " + (err.response?.data?.message || err.message));
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
                <i className="bi bi-plus-circle me-2"></i>Nuevo Servicio
              </h5>
              <button
                type="button"
                className="btn-close btn-close-white"
                onClick={onClose}
              ></button>
            </div>

            <form onSubmit={handleSubmit} className="modal-body">
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Nombre *</label>
                  <input
                    type="text"
                    className={`form-control ${errors.nombre ? "is-invalid" : ""}`}
                    value={formData.nombre}
                    onChange={(e) =>
                      setFormData({ ...formData, nombre: e.target.value })
                    }
                  />
                  {errors.nombre && (
                    <div className="invalid-feedback d-block">
                      {errors.nombre}
                    </div>
                  )}
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Categoría ID *</label>
                  <input
                    type="number"
                    min="1"
                    placeholder="Ej: 1 = Grooming, 2 = Spa"
                    className={`form-control ${errors.idCategoria ? "is-invalid" : ""}`}
                    value={formData.idCategoria}
                    onChange={(e) =>
                      setFormData({ ...formData, idCategoria: e.target.value })
                    }
                  />
                  {errors.idCategoria && (
                    <div className="invalid-feedback d-block">
                      {errors.idCategoria}
                    </div>
                  )}
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">
                    Duración (minutos) *
                  </label>
                  <input
                    type="number"
                    min="10"
                    step="5"
                    className={`form-control ${errors.duracionMinutos ? "is-invalid" : ""}`}
                    value={formData.duracionMinutos}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        duracionMinutos: e.target.value,
                      })
                    }
                  />
                  {errors.duracionMinutos && (
                    <div className="invalid-feedback d-block">
                      {errors.duracionMinutos}
                    </div>
                  )}
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Precio (S/) *</label>
                  <input
                    type="number"
                    min="0"
                    step="0.01"
                    className={`form-control ${errors.precio ? "is-invalid" : ""}`}
                    value={formData.precio}
                    onChange={(e) =>
                      setFormData({ ...formData, precio: e.target.value })
                    }
                  />
                  {errors.precio && (
                    <div className="invalid-feedback d-block">
                      {errors.precio}
                    </div>
                  )}
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label">Descripción</label>
                <textarea
                  className="form-control"
                  rows="3"
                  value={formData.descripcion}
                  onChange={(e) =>
                    setFormData({ ...formData, descripcion: e.target.value })
                  }
                  placeholder="Opcional: detalles del servicio"
                ></textarea>
              </div>
            </form>

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
                    <i className="bi bi-check-circle me-2"></i>Crear Servicio
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
