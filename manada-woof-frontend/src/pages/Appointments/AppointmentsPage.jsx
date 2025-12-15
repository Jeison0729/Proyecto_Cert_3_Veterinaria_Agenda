// src/pages/Appointments/AppointmentsPage.jsx
import React, { useEffect, useState } from "react";
import AppointmentForm from "./AppointmentForm";
import { getClientes, getServicios, getPersonal, getAgendaByFecha } from "../../api";

export default function AppointmentsPage() {
  const [clientes, setClientes] = useState([]);
  const [servicios, setServicios] = useState([]);
  const [personal, setPersonal] = useState([]);
  const [agendaDia, setAgendaDia] = useState([]);

  useEffect(() => {
    async function load() {
      try {
        const c = await getClientes(); setClientes(c);
      } catch (e) { console.error("clientes", e); }
      try {
        const s = await getServicios(); setServicios(Array.isArray(s) ? s : (s.data||s));
      } catch (e) { console.error("servicios", e); }
      try {
        const p = await getPersonal(); setPersonal(p);
      } catch (e) { console.error("personal", e); }

      // cargar agenda de hoy
      const hoy = new Date().toISOString().slice(0,10);
      try {
        const a = await getAgendaByFecha(hoy);
        setAgendaDia(a || []);
      } catch (e) { console.error("agenda", e); }
    }
    load();
  }, []);

  return (
    <div style={{padding:20}}>
      <h2>Agendar nueva cita</h2>
      <div style={{display:"grid", gridTemplateColumns:"1fr 380px", gap:18}}>
        <AppointmentForm clientes={clientes} servicios={servicios} personal={personal} onSaved={() => { /* recarga si quieres */ }} />
        <div style={{background:"#fff", padding:12, borderRadius:8}}>
          <h5>Agenda del día</h5>
          {agendaDia.length === 0 ? <div>No hay citas para esta fecha.</div> : (
            <ul>
              {agendaDia.map(it => <li key={it.id}>{it.hora} - {it.cliente} / {it.mascota}</li>)}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
}
