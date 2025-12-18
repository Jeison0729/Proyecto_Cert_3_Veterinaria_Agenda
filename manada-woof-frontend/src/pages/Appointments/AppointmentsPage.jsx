import React, { useEffect, useState } from "react";
import AppointmentForm from "./AppointmentForm";
import {
  getClientes,
  getServicios,
  getPersonal,
  getAgendaByFecha,
} from "../../api";

export default function AppointmentsPage() {
  const [clientes, setClientes] = useState([]);
  const [servicios, setServicios] = useState([]);
  const [personal, setPersonal] = useState([]);
  const [agendaDia, setAgendaDia] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function load() {
      setLoading(true);
      try {
        const c = await getClientes();
        setClientes(c);
      } catch (e) {
        console.error("clientes", e);
      }
      try {
        const s = await getServicios();
        setServicios(Array.isArray(s) ? s : s.data || s);
      } catch (e) {
        console.error("servicios", e);
      }
      try {
        const p = await getPersonal();
        setPersonal(p);
      } catch (e) {
        console.error("personal", e);
      }

      const hoy = new Date().toISOString().slice(0, 10);
      try {
        const a = await getAgendaByFecha(hoy);
        setAgendaDia(a || []);
      } catch (e) {
        console.error("agenda", e);
      }
      setLoading(false);
    }
    load();
  }, []);

  const recargarAgenda = async () => {
    const hoy = new Date().toISOString().slice(0, 10);
    try {
      const a = await getAgendaByFecha(hoy);
      setAgendaDia(a || []);
    } catch (e) {
      console.error("agenda", e);
    }
  };

  if (loading) {
    return (
      <div className="container-fluid py-4">
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
          <p className="mt-3 text-muted">Cargando datos...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="container-fluid py-4">
      <div className="mb-4">
        <h2 className="fw-bold text-dark">Agendar Nueva Cita</h2>
        <p className="text-muted">
          Complete los datos para registrar una nueva cita
        </p>
      </div>

      <div className="row g-4">
        <div className="col-lg-8">
          <AppointmentForm
            clientes={clientes}
            servicios={servicios}
            personal={personal}
            onSaved={recargarAgenda}
          />
        </div>

        <div className="col-lg-4">
          <div className="card border-0 shadow-sm">
            <div className="card-header bg-light">
              <h6 className="mb-0 fw-bold">Agenda del Día</h6>
            </div>
            <div className="card-body">
              {agendaDia.length === 0 ? (
                <div className="text-center py-4 text-muted">
                  <p className="mb-0">No hay citas para hoy</p>
                </div>
              ) : (
                <div className="list-group list-group-flush">
                  {agendaDia.map((it) => (
                    <div
                      key={it.id}
                      className="list-group-item px-0 py-2 border-0 border-bottom"
                    >
                      <div className="d-flex justify-content-between align-items-start">
                        <div className="flex-grow-1">
                          <div className="fw-bold text-primary">{it.hora}</div>
                          <div className="small text-muted">
                            {it.clienteNombre || it.cliente}
                          </div>
                          <div className="small">
                            {it.mascotaNombre || it.mascota}
                          </div>
                        </div>
                        <span
                          className={`badge ${
                            it.estadoNombre === "PENDIENTE"
                              ? "bg-warning"
                              : it.estadoNombre === "COMPLETADA"
                                ? "bg-success"
                                : it.estadoNombre === "CANCELADA"
                                  ? "bg-danger"
                                  : "bg-secondary"
                          }`}
                        >
                          {it.estadoNombre || "Pendiente"}
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>

          <div className="card border-0 shadow-sm mt-3">
            <div className="card-body">
              <h6 className="fw-bold mb-3">Resumen</h6>
              <div className="d-flex justify-content-between mb-2">
                <span className="text-muted">Total citas:</span>
                <span className="fw-bold">{agendaDia.length}</span>
              </div>
              <div className="d-flex justify-content-between">
                <span className="text-muted">Fecha:</span>
                <span className="fw-bold">
                  {new Date().toLocaleDateString("es-PE", {
                    day: "2-digit",
                    month: "short",
                    year: "numeric",
                  })}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
