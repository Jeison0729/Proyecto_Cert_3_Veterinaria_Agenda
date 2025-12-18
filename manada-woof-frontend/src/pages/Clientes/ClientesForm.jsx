import React, { useState, useEffect } from "react";
import { createCliente, updateCliente } from "../../api";
import "./clientes.css";

export default function ClientesForm({ editData, onClose, onSaved }) {
  const [formData, setFormData] = useState({
    nombres: "",
    apellidos: "",
    dni: "",
    celular: "",
    celular2: "",
    email: "",
    direccion: "",
    distrito: "",
    referencia: "",
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (editData) {
      setFormData(editData);
    }
  }, [editData]);

  const validar = () => {
    const newErrors = {};
    if (!formData.nombres.trim()) newErrors.nombres = "El nombre es requerido";
    if (!formData.apellidos.trim())
      newErrors.apellidos = "El apellido es requerido";
    if (!formData.dni.trim()) newErrors.dni = "El DNI es requerido";
    if (!/^\d{8}$/.test(formData.dni))
      newErrors.dni = "El DNI debe tener 8 dígitos";
    if (!formData.celular.trim())
      newErrors.celular = "El teléfono es requerido";
    if (formData.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = "Email inválido";
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validar()) return;

    setLoading(true);
    try {
      if (editData?.id) {
        await updateCliente(editData.id, formData);
        alert("✅ Cliente actualizado correctamente");
      } else {
        await createCliente(formData);
        alert("✅ Cliente creado correctamente");
      }
      onSaved();
    } catch (err) {
      alert("❌ Error: " + (err.response?.data?.message || err.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div
        className="modal show d-block"
        tabIndex="-1"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="modal-dialog modal-dialog-centered modal-lg">
          <div className="modal-content">
            {/* ======== HEADER ======== */}
            <div className="modal-header bg-primary text-white border-0">
              <h5 className="modal-title fw-bold">
                <i className="bi bi-person-plus me-2"></i>
                {editData ? "Editar Cliente" : "Nuevo Cliente"}
              </h5>
              <button
                type="button"
                className="btn-close btn-close-white"
                onClick={onClose}
                aria-label="Close"
              ></button>
            </div>

            {/* ======== BODY ======== */}
            <form onSubmit={handleSubmit} className="modal-body">
              {/* FILA 1: Nombres y Apellidos */}
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Nombres *</label>
                  <input
                    type="text"
                    className={`form-control ${errors.nombres ? "is-invalid" : ""}`}
                    value={formData.nombres}
                    onChange={(e) =>
                      setFormData({ ...formData, nombres: e.target.value })
                    }
                    placeholder="Ej: Juan"
                  />
                  {errors.nombres && (
                    <div className="invalid-feedback d-block">
                      <i className="bi bi-exclamation-circle me-1"></i>
                      {errors.nombres}
                    </div>
                  )}
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Apellidos *</label>
                  <input
                    type="text"
                    className={`form-control ${errors.apellidos ? "is-invalid" : ""}`}
                    value={formData.apellidos}
                    onChange={(e) =>
                      setFormData({ ...formData, apellidos: e.target.value })
                    }
                    placeholder="Ej: Pérez"
                  />
                  {errors.apellidos && (
                    <div className="invalid-feedback d-block">
                      <i className="bi bi-exclamation-circle me-1"></i>
                      {errors.apellidos}
                    </div>
                  )}
                </div>
              </div>

              {/* FILA 2: DNI y Teléfono Principal */}
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">DNI *</label>
                  <input
                    type="text"
                    maxLength="8"
                    className={`form-control ${errors.dni ? "is-invalid" : ""}`}
                    value={formData.dni}
                    onChange={(e) =>
                      setFormData({ ...formData, dni: e.target.value })
                    }
                    placeholder="Ej: 71234567"
                  />
                  {errors.dni && (
                    <div className="invalid-feedback d-block">
                      <i className="bi bi-exclamation-circle me-1"></i>
                      {errors.dni}
                    </div>
                  )}
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">
                    Teléfono Principal *
                  </label>
                  <input
                    type="text"
                    className={`form-control ${errors.celular ? "is-invalid" : ""}`}
                    value={formData.celular}
                    onChange={(e) =>
                      setFormData({ ...formData, celular: e.target.value })
                    }
                    placeholder="Ej: 987654321"
                  />
                  {errors.celular && (
                    <div className="invalid-feedback d-block">
                      <i className="bi bi-exclamation-circle me-1"></i>
                      {errors.celular}
                    </div>
                  )}
                </div>
              </div>

              {/* FILA 3: Teléfono Secundario y Email */}
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Teléfono Secundario</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.celular2}
                    onChange={(e) =>
                      setFormData({ ...formData, celular2: e.target.value })
                    }
                    placeholder="Ej: 951234567"
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Email</label>
                  <input
                    type="email"
                    className={`form-control ${errors.email ? "is-invalid" : ""}`}
                    value={formData.email}
                    onChange={(e) =>
                      setFormData({ ...formData, email: e.target.value })
                    }
                    placeholder="Ej: juan@gmail.com"
                  />
                  {errors.email && (
                    <div className="invalid-feedback d-block">
                      <i className="bi bi-exclamation-circle me-1"></i>
                      {errors.email}
                    </div>
                  )}
                </div>
              </div>

              {/* FILA 4: Dirección (full width) */}
              <div className="mb-3">
                <label className="form-label">Dirección</label>
                <input
                  type="text"
                  className="form-control"
                  value={formData.direccion}
                  onChange={(e) =>
                    setFormData({ ...formData, direccion: e.target.value })
                  }
                  placeholder="Ej: Av. Larco 1234"
                />
              </div>

              {/* FILA 5: Distrito y Referencia */}
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Distrito</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.distrito}
                    onChange={(e) =>
                      setFormData({ ...formData, distrito: e.target.value })
                    }
                    placeholder="Ej: Miraflores"
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label">Referencia</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.referencia}
                    onChange={(e) =>
                      setFormData({ ...formData, referencia: e.target.value })
                    }
                    placeholder="Ej: Frente al parque"
                  />
                </div>
              </div>
            </form>

            {/*  FOOTER */}
            <div className="modal-footer border-top bg-light">
              <button
                type="button"
                className="btn btn-secondary"
                onClick={onClose}
              >
                <i className="bi bi-x-circle me-2"></i>Cancelar
              </button>
              <button
                type="submit"
                className="btn btn-primary"
                disabled={loading}
                onClick={handleSubmit}
              >
                {loading ? (
                  <>
                    <span
                      className="spinner-border spinner-border-sm me-2"
                      role="status"
                      aria-hidden="true"
                    ></span>
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
