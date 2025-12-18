import React, { useState, useEffect } from "react";
import { createPersonal, updatePersonal } from "../../api";
import "./personal.css";

export default function PersonalForm({ editData, onClose, onSaved }) {
  const [formData, setFormData] = useState({
    nombres: "",
    apellidos: "",
    telefono: "",
    especialidad: "",
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (editData) {
      setFormData(editData); // ya funciona perfecto como en clientes
    }
  }, [editData]);

  const validar = () => {
    const newErrors = {};
    if (!formData.nombres.trim()) newErrors.nombres = "Nombres requeridos";
    if (!formData.apellidos.trim())
      newErrors.apellidos = "Apellidos requeridos";
    if (!formData.dni.trim()) newErrors.dni = "DNI requerido";
    if (!/^\d{8}$/.test(formData.dni))
      newErrors.dni = "DNI debe tener 8 dígitos";
    if (!formData.celular.trim()) newErrors.celular = "Teléfono requerido";
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
        await updatePersonal(editData.id, formData);
        alert("Personal actualizado correctamente");
      } else {
        await createPersonal(formData);
        alert("Personal creado correctamente");
      }
      onSaved();
    } catch (err) {
      alert("Error: " + (err.response?.data?.message || err.message));
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
            <div className="modal-header bg-primary text-white border-0">
              <h5 className="modal-title fw-bold">
                <i className="bi bi-person-badge me-2"></i>
                {editData ? "Editar Personal" : "Nuevo Personal"}
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
                  <label className="form-label fw-bold">Nombres *</label>
                  <input
                    type="text"
                    className={`form-control ${errors.nombres ? "is-invalid" : ""}`}
                    value={formData.nombres}
                    onChange={(e) =>
                      setFormData({ ...formData, nombres: e.target.value })
                    }
                  />
                  {errors.nombres && (
                    <div className="invalid-feedback d-block">
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
                  />
                  {errors.apellidos && (
                    <div className="invalid-feedback d-block">
                      {errors.apellidos}
                    </div>
                  )}
                </div>
              </div>

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
                  />
                  {errors.dni && (
                    <div className="invalid-feedback d-block">{errors.dni}</div>
                  )}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Teléfono *</label>
                  <input
                    type="text"
                    className={`form-control ${errors.telefono ? "is-invalid" : ""}`}
                    value={formData.celular}
                    onChange={(e) =>
                      setFormData({ ...formData, telefono: e.target.value })
                    }
                  />
                  {errors.celular && (
                    <div className="invalid-feedback d-block">
                      {errors.celular}
                    </div>
                  )}
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Email</label>
                  <input
                    type="email"
                    className={`form-control ${errors.email ? "is-invalid" : ""}`}
                    value={formData.email}
                    onChange={(e) =>
                      setFormData({ ...formData, email: e.target.value })
                    }
                  />
                  {errors.email && (
                    <div className="invalid-feedback d-block">
                      {errors.email}
                    </div>
                  )}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Especialidad *</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.especialidad}
                    onChange={(e) =>
                      setFormData({ ...formData, especialidad: e.target.value })
                    }
                    placeholder="Ej: Cirugía, Odontología"
                  />
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Colegio Profesional</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.colegio}
                    onChange={(e) =>
                      setFormData({ ...formData, colegio: e.target.value })
                    }
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label">CMP / Colegiatura</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.cmp}
                    onChange={(e) =>
                      setFormData({ ...formData, cmp: e.target.value })
                    }
                  />
                </div>
              </div>
            </form>

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
