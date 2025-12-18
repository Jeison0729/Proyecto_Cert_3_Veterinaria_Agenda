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

  // Si está editando, cargar datos del cliente
  useEffect(() => {
    if (editData) {
      setFormData(editData);
    }
  }, [editData]);

  // Validar campos
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
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Guardar cliente
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validar()) return;

    setLoading(true);
    try {
      if (editData?.id) {
        // ACTUALIZAR cliente existente
        await updateCliente(editData.id, formData);
        alert("Cliente actualizado correctamente");
      } else {
        // CREAR cliente nuevo
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
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        background: "rgba(0,0,0,0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 1000,
      }}
      onClick={onClose}
    >
      <div
        style={{
          background: "white",
          borderRadius: "8px",
          maxWidth: "500px",
          width: "90%",
          padding: "20px",
          boxShadow: "0 8px 24px rgba(0,0,0,0.15)",
        }}
        onClick={(e) => e.stopPropagation()}
      >
        <h3 style={{ marginTop: 0, color: "#50352c" }}>
          {editData ? "Editar Cliente" : "Nuevo Cliente"}
        </h3>

        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Nombres *
            </label>
            <input
              type="text"
              value={formData.nombres}
              onChange={(e) =>
                setFormData({ ...formData, nombres: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: errors.nombres ? "2px solid red" : "1px solid #ccc",
              }}
            />
            {errors.nombres && (
              <span style={{ color: "red", fontSize: "12px" }}>
                {errors.nombres}
              </span>
            )}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Apellidos *
            </label>
            <input
              type="text"
              value={formData.apellidos}
              onChange={(e) =>
                setFormData({ ...formData, apellidos: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: errors.apellidos ? "2px solid red" : "1px solid #ccc",
              }}
            />
            {errors.apellidos && (
              <span style={{ color: "red", fontSize: "12px" }}>
                {errors.apellidos}
              </span>
            )}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              DNI *
            </label>
            <input
              type="text"
              maxLength="8"
              value={formData.dni}
              onChange={(e) =>
                setFormData({ ...formData, dni: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: errors.dni ? "2px solid red" : "1px solid #ccc",
              }}
            />
            {errors.dni && (
              <span style={{ color: "red", fontSize: "12px" }}>
                {errors.dni}
              </span>
            )}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Teléfono Principal *
            </label>
            <input
              type="text"
              value={formData.celular}
              onChange={(e) =>
                setFormData({ ...formData, celular: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: errors.celular ? "2px solid red" : "1px solid #ccc",
              }}
            />
            {errors.celular && (
              <span style={{ color: "red", fontSize: "12px" }}>
                {errors.celular}
              </span>
            )}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Teléfono Secundario
            </label>
            <input
              type="text"
              value={formData.celular2}
              onChange={(e) =>
                setFormData({ ...formData, celular2: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: "1px solid #ccc",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Email
            </label>
            <input
              type="email"
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: "1px solid #ccc",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Dirección
            </label>
            <input
              type="text"
              value={formData.direccion}
              onChange={(e) =>
                setFormData({ ...formData, direccion: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: "1px solid #ccc",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Distrito
            </label>
            <input
              type="text"
              value={formData.distrito}
              onChange={(e) =>
                setFormData({ ...formData, distrito: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: "1px solid #ccc",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "5px",
                fontWeight: "bold",
              }}
            >
              Referencia
            </label>
            <input
              type="text"
              value={formData.referencia}
              onChange={(e) =>
                setFormData({ ...formData, referencia: e.target.value })
              }
              style={{
                width: "100%",
                padding: "10px",
                borderRadius: "5px",
                border: "1px solid #ccc",
              }}
            />
          </div>

          <div
            style={{ display: "flex", gap: "10px", justifyContent: "flex-end" }}
          >
            <button
              type="button"
              onClick={onClose}
              style={{
                padding: "10px 20px",
                background: "#95a5a6",
                color: "white",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer",
              }}
            >
              Cancelar
            </button>
            <button
              type="submit"
              disabled={loading}
              className="mw-btn-primary"
              style={{
                padding: "10px 20px",
                cursor: loading ? "not-allowed" : "pointer",
              }}
            >
              {loading ? "Guardando..." : "Guardar"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
