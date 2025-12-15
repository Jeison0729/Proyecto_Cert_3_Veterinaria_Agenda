import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import { getAgendaByFecha } from "../../api";
import "../../styles/list-pages.css";

export default function CalendarView() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Carga inicial: rango del mes actual -> tu backend provee periodo endpoint
    (async () => {
      try {
        const today = new Date().toISOString().slice(0,10);
        const res = await getAgendaByFecha(today);
        // espera array de citas: convertir a eventos de FullCalendar
        const citas = Array.isArray(res) ? res : (res?.data || []);
        const ev = citas.map(c => ({
          id: c.id,
          title: `${c.cliente ?? c.clienteNombre ?? ""} - ${c.mascota ?? c.mascotaNombre ?? ""}`,
          start: `${c.fecha}T${(c.hora||"09:00")}`,
          end: (() => {
            // intenta sumar duracion_estimada_min o duracion_min por servicios
            const dur = c.duracion_estimada_min || c.duracion_min || 60;
            const start = new Date(`${c.fecha}T${c.hora||"09:00"}`);
            start.setMinutes(start.getMinutes() + Number(dur));
            return start.toISOString();
          })()
        }));
        setEvents(ev);
      } catch (err) {
        console.error("Error cargando agenda:", err);
        setEvents([]);
      }
    })();
  }, []);

  function handleDateClick(arg) {
    // ejemplo: al click abrir modal para crear cita con fecha prellenada
    alert("Click en fecha: " + arg.dateStr);
  }

  function handleEventClick(info) {
    // ver detalle de la cita
    alert("Cita: " + info.event.title);
  }

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Calendario</h2>
      <FullCalendar
        plugins={[ dayGridPlugin, timeGridPlugin, interactionPlugin ]}
        initialView="timeGridWeek"
        headerToolbar={{
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        }}
        events={events}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        height="auto"
      />
    </div>
  );
}
