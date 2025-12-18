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

  // Si es edición, cargar datos
  useEffect(() => {
    if (editData) {
      setFormData(editData);
    }
  }, [editData]);

  // Validar formulario
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

  // Guardar
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validar()) return;

    setLoading(true);
    try {
      if (editData?.id) {
        await updateCliente(editData.id, formData);
        alert("Cliente actualizado correctamente");
      } else {
        await createCliente(formData);
        alert("Cliente creado correctamente");
      }
      onSaved();
    } catch (err) {
      alert("Error: " + (err.response?.data?.message || err.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mw-modal-overlay" onClick={onClose}>
      <div className="mw-modal" onClick={(e) => e.stopPropagation()}>
        <div className="mw-modal-header">
          <h3>{editData ? "Editar Cliente" : "Nuevo Cliente"}</h3>
          <button className="mw-modal-close" onClick={onClose}>
            ✕
          </button>
        </div>

        <form onSubmit={handleSubmit} className="mw-form">
          <div className="mw-form-row">
            <div className="mw-form-group">
              <label>Nombres *</label>
              <input
                type="text"
                value={formData.nombres}
                onChange={(e) =>
                  setFormData({ ...formData, nombres: e.target.value })
                }
                className={errors.nombres ? "mw-input-error" : ""}
              />
              {errors.nombres && (
                <span className="mw-error">{errors.nombres}</span>
              )}
            </div>

            <div className="mw-form-group">
              <label>Apellidos *</label>
              <input
                type="text"
                value={formData.apellidos}
                onChange={(e) =>
                  setFormData({ ...formData, apellidos: e.target.value })
                }
                className={errors.apellidos ? "mw-input-error" : ""}
              />
              {errors.apellidos && (
                <span className="mw-error">{errors.apellidos}</span>
              )}
            </div>
          </div>

          <div className="mw-form-row">
            <div className="mw-form-group">
              <label>DNI *</label>
              <input
                type="text"
                maxLength="8"
                value={formData.dni}
                onChange={(e) =>
                  setFormData({ ...formData, dni: e.target.value })
                }
                className={errors.dni ? "mw-input-error" : ""}
              />
              {errors.dni && <span className="mw-error">{errors.dni}</span>}
            </div>

            <div className="mw-form-group">
              <label>Teléfono Principal *</label>
              <input
                type="text"
                value={formData.celular}
                onChange={(e) =>
                  setFormData({ ...formData, celular: e.target.value })
                }
                className={errors.celular ? "mw-input-error" : ""}
              />
              {errors.celular && (
                <span className="mw-error">{errors.celular}</span>
              )}
            </div>
          </div>

          <div className="mw-form-row">
            <div className="mw-form-group">
              <label>Teléfono Secundario</label>
              <input
                type="text"
                value={formData.celular2}
                onChange={(e) =>
                  setFormData({ ...formData, celular2: e.target.value })
                }
              />
            </div>

            <div className="mw-form-group">
              <label>Email</label>
              <input
                type="email"
                value={formData.email}
                onChange={(e) =>
                  setFormData({ ...formData, email: e.target.value })
                }
                className={errors.email ? "mw-input-error" : ""}
              />
              {errors.email && <span className="mw-error">{errors.email}</span>}
            </div>
          </div>

          <div className="mw-form-group">
            <label>Dirección</label>
            <input
              type="text"
              value={formData.direccion}
              onChange={(e) =>
                setFormData({ ...formData, direccion: e.target.value })
              }
            />
          </div>

          <div className="mw-form-row">
            <div className="mw-form-group">
              <label>Distrito</label>
              <input
                type="text"
                value={formData.distrito}
                onChange={(e) =>
                  setFormData({ ...formData, distrito: e.target.value })
                }
              />
            </div>

            <div className="mw-form-group">
              <label>Referencia</label>
              <input
                type="text"
                value={formData.referencia}
                onChange={(e) =>
                  setFormData({ ...formData, referencia: e.target.value })
                }
              />
            </div>
          </div>

          <div className="mw-modal-footer">
            <button
              type="button"
              className="mw-btn-secondary"
              onClick={onClose}
            >
              Cancelar
            </button>
            <button type="submit" className="mw-btn-primary" disabled={loading}>
              {loading ? "Guardando..." : "Guardar"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
