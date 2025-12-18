import React, { useState } from "react";
import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import { getAgendaByFecha } from "../../api";
import "../../styles/list-pages.css";

export default function CalendarView() {
  const [events, setEvents] = useState([]);

  // Cargar citas según rango visible
  async function loadAgendaByRange(start, end) {
    try {
      const startDate = start.toISOString().slice(0, 10);
      const endDate = end.toISOString().slice(0, 10);

      const citas = [];

      // recorrer día por día porque tu backend es por fecha
      const current = new Date(startDate);
      const last = new Date(endDate);

      while (current <= last) {
        const fecha = current.toISOString().slice(0, 10);
        const res = await getAgendaByFecha(fecha);
        const data = Array.isArray(res) ? res : res?.data || [];
        citas.push(...data);
        current.setDate(current.getDate() + 1);
      }

      console.log("CITAS CALENDARIO:", citas);

      const mapped = citas.map((c) => {
        const startTime = `${c.fecha}T${c.hora}`;
        const duration = c.duracionEstimadaMin || 60;

        const endTime = new Date(startTime);
        endTime.setMinutes(endTime.getMinutes() + duration);

        return {
          id: c.id,
          title: `${c.clienteNombre} - ${c.mascotaNombre}`,
          start: startTime,
          end: endTime.toISOString(),
          extendedProps: {
            cliente: c.clienteNombre,
            mascota: c.mascotaNombre,
            estado: c.estadoNombre,
            observaciones: c.observaciones,
          },
        };
      });

      setEvents(mapped);
    } catch (error) {
      console.error("Error cargando agenda:", error);
    }
  }

  function handleDatesSet(arg) {
    loadAgendaByRange(arg.start, arg.end);
  }

  function handleEventClick(info) {
    const { cliente, mascota, estado } = info.event.extendedProps;

    alert(
      `Cita\n\n` +
        `Cliente: ${cliente}\n` +
        `Mascota: ${mascota}\n` +
        `Estado: ${estado}`
    );
  }

  return (
    <div className="mw-page">
      <h2 className="mw-page-title"> Calendario de Citas</h2>

      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        headerToolbar={{
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek,timeGridDay",
        }}
        events={events}
        datesSet={handleDatesSet}
        eventClick={handleEventClick}
        height="auto"
      />
    </div>
  );
}
