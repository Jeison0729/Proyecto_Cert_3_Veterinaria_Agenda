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
    fecha: "",
    hora: "10:00",
    abonoInicial: "0",
    observaciones: "",
  });

  const [mascotas, setMascotas] = useState([]);
  const [detalleServicios, setDetalleServicios] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!form.clienteId) {
      setMascotas([]);
      return;
    }
    (async () => {
      try {
        const res = await getMascotasByCliente(form.clienteId);
        setMascotas(Array.isArray(res) ? res : []);
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
      alert("Agregue al menos un servicio");
      return;
    }
    if (!form.fecha || !form.hora) {
      alert("Seleccione fecha y hora");
      return;
    }

    setLoading(true);

    const payload = {
      idCliente: Number(form.clienteId),
      idMascota: Number(form.mascotaId),
      fecha: form.fecha,
      hora: form.hora.length === 5 ? form.hora + ":00" : form.hora,
      idEstado: 1,
      idMedioSolicitud: 1,
      observaciones: form.observaciones || "",
      abonoInicial: Number(form.abonoInicial || 0),
      idUsuarioRegistro: Number(user?.id || 1),
      servicios: detalleServicios.map((s) => ({
        idServicio: Number(s.id_servicio),
        idPersonal: Number(s.id_personal),
        cantidad: Number(s.cantidad || 1),
        valorServicio: Number(s.valor_servicio || 0),
        adicionales: s.adicionales || "",
      })),
    };

    if (payload.abonoInicial > 0) {
      payload.idMedioPago = 1;
    }

    if (form.fecha && form.hora) {
      payload.recordatorioTipo = "PRE_CITA";
      payload.recordatorioFecha = `${form.fecha}T${form.hora.length === 5 ? form.hora + ":00" : form.hora}`;
    }

    try {
      console.log("📤 PAYLOAD:", JSON.stringify(payload, null, 2));
      const response = await createAgenda(payload);
      console.log("✅ RESPUESTA:", response);

      alert("Cita agendada exitosamente");

      setDetalleServicios([]);
      setForm({
        clienteId: "",
        mascotaId: "",
        fecha: "",
        hora: "10:00",
        abonoInicial: "0",
        observaciones: "",
      });

      onSaved?.();
    } catch (err) {
      console.error("❌ ERROR:", err);
      console.error("❌ RESPONSE:", err.response);
      const msg =
        err.response?.data?.message || err.message || "Error desconocido";
      alert("Error al agendar: " + msg);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="card border-0 shadow-sm">
      <div className="card-header bg-primary text-white">
        <h5 className="mb-0 fw-bold">Datos de la Cita</h5>
      </div>

      <div className="card-body">
        <div className="row g-3">
          <div className="col-md-6">
            <label className="form-label fw-semibold">Cliente</label>
            <select
              className="form-select"
              name="clienteId"
              value={form.clienteId}
              onChange={handleChange}
              required
            >
              <option value="">Seleccionar cliente</option>
              {Array.isArray(clientes) &&
                clientes.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.nombres} {c.apellidos}
                  </option>
                ))}
            </select>
          </div>

          <div className="col-md-6">
            <label className="form-label fw-semibold">Mascota</label>
            <select
              className="form-select"
              name="mascotaId"
              value={form.mascotaId}
              onChange={handleChange}
              disabled={!form.clienteId}
              required
            >
              <option value="">Seleccionar mascota</option>
              {mascotas.map((m) => (
                <option key={m.id} value={m.id}>
                  {m.nombre} - {m.especie}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-4">
            <label className="form-label fw-semibold">Fecha</label>
            <input
              type="date"
              className="form-control"
              name="fecha"
              value={form.fecha}
              onChange={handleChange}
              min={new Date().toISOString().split("T")[0]}
              required
            />
          </div>

          <div className="col-md-4">
            <label className="form-label fw-semibold">Hora</label>
            <input
              type="time"
              className="form-control"
              name="hora"
              value={form.hora}
              onChange={handleChange}
              step="900"
              required
            />
          </div>

          <div className="col-md-4">
            <label className="form-label fw-semibold">
              Abono Inicial (S/.)
            </label>
            <input
              type="number"
              className="form-control"
              name="abonoInicial"
              value={form.abonoInicial}
              onChange={handleChange}
              min="0"
              step="0.01"
            />
          </div>

          <div className="col-12">
            <label className="form-label fw-semibold">Observaciones</label>
            <textarea
              className="form-control"
              name="observaciones"
              value={form.observaciones}
              onChange={handleChange}
              rows="2"
              placeholder="Notas adicionales..."
            />
          </div>
        </div>

        <hr className="my-4" />

        <h6 className="fw-bold mb-3">Servicios</h6>
        <ServicesTable
          servicios={servicios}
          personal={personal}
          detalleServicios={detalleServicios}
          setDetalleServicios={setDetalleServicios}
        />

        <div className="alert alert-light border mt-3 d-flex justify-content-between align-items-center">
          <div>
            <strong>Duración Total:</strong> {totalDuracion} min
          </div>
          <div>
            <strong>Costo Total:</strong> S/ {totalCosto.toFixed(2)}
          </div>
        </div>
      </div>

      <div className="card-footer bg-light d-flex gap-2 justify-content-end">
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => {
            setDetalleServicios([]);
            setForm({
              clienteId: "",
              mascotaId: "",
              fecha: "",
              hora: "10:00",
              abonoInicial: "0",
              observaciones: "",
            });
          }}
        >
          Cancelar
        </button>
        <button
          type="button"
          className="btn btn-primary"
          disabled={loading}
          onClick={handleSave}
        >
          {loading ? (
            <>
              <span className="spinner-border spinner-border-sm me-2"></span>
              Guardando...
            </>
          ) : (
            "Agendar Cita"
          )}
        </button>
      </div>
    </div>
  );
}
