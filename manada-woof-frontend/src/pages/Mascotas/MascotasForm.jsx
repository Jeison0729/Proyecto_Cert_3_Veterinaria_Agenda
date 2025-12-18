import React, { useState, useEffect } from "react";
import { getClientes } from "../../api";
import { createMascota, updateMascota } from "../../api";
import "./mascotas.css";

export default function MascotasForm({ editData, onClose, onSaved }) {
  const [formData, setFormData] = useState({
    nombre: "",
    idEspecie: 1,
    raza: "",
    idSexo: 1,
    fechaNacimiento: "",
    color: "",
    esterilizado: false,
    pesoActual: "",
    observaciones: "",
    foto: "",
    idCliente: "",
  });

  const [clientes, setClientes] = useState([]);
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  // Cargar clientes
  useEffect(() => {
    const loadClientes = async () => {
      try {
        const res = await getClientes();
        setClientes(Array.isArray(res) ? res : []);
      } catch (err) {
        console.error("Error al cargar clientes:", err);
      }
    };
    loadClientes();
  }, []);

  // Si es edición, cargar datos
  useEffect(() => {
    if (editData) {
      // Mapeamos los valores de texto a los IDs correspondientes
      const especieMap = {
        Perro: 1,
        Gato: 2,
        Otro: 3,
      };

      const sexoMap = {
        Macho: 1,
        Hembra: 2,
      };

      setFormData({
        ...editData,
        idEspecie: especieMap[editData.especie] || 1,
        idSexo: sexoMap[editData.sexo] || 1,
        // Los demás campos se copian tal cual
      });
    } else {
      // Reset para nueva mascota
      setFormData({
        nombre: "",
        idEspecie: "", // o 1 si quieres Perro por defecto
        raza: "",
        idSexo: "",
        fechaNacimiento: "",
        color: "",
        esterilizado: false,
        pesoActual: "",
        observaciones: "",
        foto: "",
        idCliente: "",
      });
    }
  }, [editData]);

  // Validar formulario
  const validar = () => {
    const newErrors = {};
    if (!formData.nombre.trim()) newErrors.nombre = "El nombre es requerido";
    if (!formData.idEspecie) newErrors.idEspecie = "La especie es requerida";
    if (!formData.idSexo) newErrors.idSexo = "El sexo es requerido";
    if (!formData.idCliente) newErrors.idCliente = "El cliente es requerido";
    if (formData.pesoActual && isNaN(formData.pesoActual)) {
      newErrors.pesoActual = "El peso debe ser un número";
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
        await updateMascota(editData.id, formData);
        alert("Mascota actualizada correctamente");
      } else {
        await createMascota(formData);
        alert("Mascota creada correctamente");
      }
      onSaved();
    } catch (err) {
      alert("Error: " + (err.response?.data?.message || err.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal show d-block" tabIndex="-1" onClick={onClose}>
        <div
          className="modal-dialog modal-dialog-centered modal-lg"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="modal-content">
            {/* HEADER */}
            <div className="modal-header bg-primary text-white border-0">
              <h5 className="modal-title fw-bold">
                <i className="bi bi-paw me-2"></i>
                {editData ? "Editar Mascota" : "Nueva Mascota"}
              </h5>
              <button
                type="button"
                className="btn-close btn-close-white"
                onClick={onClose}
              ></button>
            </div>

            {/* BODY */}
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
                  <label className="form-label fw-bold">Especie *</label>
                  <select
                    className={`form-select ${errors.idEspecie ? "is-invalid" : ""}`}
                    value={formData.idEspecie}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        idEspecie: parseInt(e.target.value),
                      })
                    }
                  >
                    <option value="">-- Seleccionar --</option>
                    <option value="1">Perro</option>
                    <option value="2">Gato</option>
                    <option value="3">Otro</option>
                  </select>
                  {errors.idEspecie && (
                    <div className="invalid-feedback d-block">
                      {errors.idEspecie}
                    </div>
                  )}
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Raza</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.raza}
                    onChange={(e) =>
                      setFormData({ ...formData, raza: e.target.value })
                    }
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Sexo *</label>
                  <select
                    className={`form-select ${errors.idSexo ? "is-invalid" : ""}`}
                    value={formData.idSexo}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        idSexo: parseInt(e.target.value),
                      })
                    }
                  >
                    <option value="">-- Seleccionar --</option>
                    <option value="1">Macho</option>
                    <option value="2">Hembra</option>
                  </select>
                  {errors.idSexo && (
                    <div className="invalid-feedback d-block">
                      {errors.idSexo}
                    </div>
                  )}
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Fecha de Nacimiento</label>
                  <input
                    type="date"
                    className="form-control"
                    value={formData.fechaNacimiento}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        fechaNacimiento: e.target.value,
                      })
                    }
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label">Color</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.color}
                    onChange={(e) =>
                      setFormData({ ...formData, color: e.target.value })
                    }
                  />
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Peso Actual (kg)</label>
                  <input
                    type="number"
                    step="0.1"
                    className={`form-control ${errors.pesoActual ? "is-invalid" : ""}`}
                    value={formData.pesoActual}
                    onChange={(e) =>
                      setFormData({ ...formData, pesoActual: e.target.value })
                    }
                  />
                  {errors.pesoActual && (
                    <div className="invalid-feedback d-block">
                      {errors.pesoActual}
                    </div>
                  )}
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Cliente *</label>
                  <select
                    className={`form-select ${errors.idCliente ? "is-invalid" : ""}`}
                    value={formData.idCliente}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        idCliente: parseInt(e.target.value),
                      })
                    }
                  >
                    <option value="">-- Seleccionar Cliente --</option>
                    {clientes.map((cliente) => (
                      <option key={cliente.id} value={cliente.id}>
                        {cliente.nombres} {cliente.apellidos}
                      </option>
                    ))}
                  </select>
                  {errors.idCliente && (
                    <div className="invalid-feedback d-block">
                      {errors.idCliente}
                    </div>
                  )}
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label">Observaciones</label>
                <textarea
                  className="form-control"
                  rows="3"
                  value={formData.observaciones}
                  onChange={(e) =>
                    setFormData({ ...formData, observaciones: e.target.value })
                  }
                ></textarea>
              </div>

              <div className="mb-3">
                <div className="form-check">
                  <input
                    className="form-check-input"
                    type="checkbox"
                    id="esterilizado"
                    checked={formData.esterilizado}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        esterilizado: e.target.checked,
                      })
                    }
                  />
                  <label className="form-check-label" htmlFor="esterilizado">
                    ✓ Esterilizado
                  </label>
                </div>
              </div>
            </form>

            {/* FOOTER */}
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
