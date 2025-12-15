// src/pages/Appointments/AppointmentForm.jsx
import React, { useEffect, useMemo, useState } from "react";
import { getMascotasByCliente, createAgenda } from "../../api";
import ServicesTable from "./ServicesTable";
import { useAuth } from "../../context/AuthContext";

export default function AppointmentForm({
  clientes = [],
  servicios = [],
  personal = [],
  onSaved,
}) {
  const { user } = useAuth();

  const [form, setForm] = useState({
    clienteId: "",
    mascotaId: "",
    fecha: new Date().toISOString().slice(0, 10),
    hora: "10:00",
    idEstado: 2,
    idMedioSolicitud: 4,
    abonoInicial: 0,
    idMedioPago: 1,
    observaciones: "",
  });

  const [mascotas, setMascotas] = useState([]);
  const [detalleServicios, setDetalleServicios] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!form.clienteId) return setMascotas([]);
    (async () => {
      try {
        const res = await getMascotasByCliente(form.clienteId);
        const data = Array.isArray(res) ? res : (res?.data ?? res);
        setMascotas(data || []);
      } catch (err) {
        console.error(err);
        setMascotas([]);
      }
    })();
  }, [form.clienteId]);

  const totalDuracion = useMemo(
    () =>
      detalleServicios.reduce(
        (acc, s) =>
          acc + (Number(s.duracion_min) || 0) * (Number(s.cantidad) || 1),
        0
      ),
    [detalleServicios]
  );

  const totalCosto = useMemo(
    () =>
      detalleServicios.reduce(
        (acc, s) =>
          acc + (Number(s.valor_servicio) || 0) * (Number(s.cantidad) || 1),
        0
      ),
    [detalleServicios]
  );

  function handleChange(e) {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  }

  async function handleSave(e) {
    e.preventDefault();
    if (!form.clienteId || !form.mascotaId) {
      alert("Seleccione cliente y mascota");
      return;
    }
    if (detalleServicios.length === 0) {
      alert("Agregue servicios");
      return;
    }

    const payload = {
      idCliente: Number(form.clienteId),
      idMascota: Number(form.mascotaId),
      fecha: form.fecha,
      hora: form.hora.length === 5 ? `${form.hora}:00` : form.hora,
      abonoInicial: Number(form.abonoInicial || 0),
      idEstado: Number(form.idEstado),
      idMedioSolicitud: Number(form.idMedioSolicitud),
      observaciones: form.observaciones || "",
      idUsuarioRegistro: Number(user?.id || user?.usuario || 1),
      servicios: detalleServicios.map((s) => ({
        idServicio: Number(s.id_servicio),
        idPersonal: Number(s.id_personal),
        cantidad: Number(s.cantidad || 1),
        valorServicio: Number(s.valor_servicio || 0),
        adicionales: s.adicionales || "",
      })),
    };

    if (Number(form.abonoInicial) > 0) {
      payload.idMedioPago = Number(form.idMedioPago);
    }

    try {
      setLoading(true);
      console.log("PAYLOAD:", JSON.stringify(payload, null, 2));
      await createAgenda(payload);
      alert("Cita registrada");
      setDetalleServicios([]);
      setForm({
        clienteId: "",
        mascotaId: "",
        fecha: new Date().toISOString().slice(0, 10),
        hora: "10:00",
        idEstado: 2,
        idMedioSolicitud: 4,
        abonoInicial: 0,
        idMedioPago: 1,
        observaciones: "",
      });
      if (typeof onSaved === "function") onSaved();
    } catch (err) {
      console.error("Error crear agenda", err);
      console.log("ERROR RESPONSE:", err?.response?.data);
      alert(err?.response?.data?.message || "Error registrando cita");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form className="card p-3" onSubmit={handleSave}>
      <h5>Datos de la Cita</h5>
      <div className="mb-2">
        <label>Cliente</label>
        <select
          className="form-select"
          name="clienteId"
          value={form.clienteId}
          onChange={handleChange}
        >
          <option value="">Seleccionar cliente</option>
          {Array.isArray(clientes) &&
            clientes.map((c) => (
              <option key={c.id} value={c.id}>
                {c.nombres} {c.apellidos} (ID {c.id})
              </option>
            ))}
        </select>
      </div>

      <div className="mb-2">
        <label>Mascota</label>
        <select
          className="form-select"
          name="mascotaId"
          value={form.mascotaId}
          onChange={handleChange}
        >
          <option value="">Seleccionar mascota</option>
          {mascotas.map((m) => (
            <option key={m.id} value={m.id}>
              {m.nombre}
            </option>
          ))}
        </select>
      </div>

      <div className="row">
        <div className="col">
          <label>Fecha</label>
          <input
            type="date"
            className="form-control"
            name="fecha"
            value={form.fecha}
            onChange={handleChange}
          />
        </div>
        <div className="col">
          <label>Hora</label>
          <input
            type="time"
            className="form-control"
            name="hora"
            value={form.hora}
            onChange={handleChange}
          />
        </div>
      </div>

      <div className="mt-3">
        <ServicesTable
          servicios={servicios}
          personal={personal}
          detalleServicios={detalleServicios}
          setDetalleServicios={setDetalleServicios}
        />
      </div>

      <div className="mt-2 d-flex justify-content-between align-items-center">
        <div>
          Total Duración: <strong>{totalDuracion} min</strong>
        </div>
        <div>
          Costo Total: <strong>S/ {totalCosto.toFixed(2)}</strong>
        </div>
      </div>

      <div className="mt-3 d-flex gap-2">
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => {
            setDetalleServicios([]);
          }}
        >
          Cancelar
        </button>
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? "Guardando..." : "Agendar cita"}
        </button>
      </div>
    </form>
  );
}
