import React, { useEffect, useState } from "react";
import api, { getPersonal, getDisponibilidadPersonal } from "../../api";
import "../../styles/list-pages.css";

export default function AvailabilityConfig() {
  const [personal, setPersonal] = useState([]);
  const [selected, setSelected] = useState(null);
  const [disponibilidades, setDisponibilidades] = useState([]);
  const [inicio, setInicio] = useState("");
  const [fin, setFin] = useState("");

  useEffect(() => {
    (async ()=>{
      try {
        const res = await getPersonal();
        setPersonal(Array.isArray(res) ? res : (res?.data||[]));
      } catch (e) { console.error(e); }
    })();
  }, []);

  useEffect(() => {
    if (!selected) return setDisponibilidades([]);
    (async ()=>{
      try {
        const hoy = new Date().toISOString().slice(0,10);
        const res = await getDisponibilidadPersonal(selected, inicio || hoy, fin || inicio || hoy);
        setDisponibilidades(Array.isArray(res) ? res : (res?.data||[]));
      } catch (e) { console.error(e); setDisponibilidades([]); }
    })();
  }, [selected, inicio, fin]);

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Configuración de Disponibilidad</h2>

      <div style={{display:"flex", gap:16, alignItems:"center", marginBottom:12}}>
        <select onChange={e=>setSelected(e.target.value)} value={selected||""}>
          <option value="">Seleccionar personal</option>
          {personal.map(p => <option key={p.id} value={p.id}>{p.nombres} {p.apellidos}</option>)}
        </select>

        <input type="date" value={inicio} onChange={e=>setInicio(e.target.value)} />
        <input type="date" value={fin} onChange={e=>setFin(e.target.value)} />
      </div>

      <h4>Disponibilidades</h4>
      <table className="mw-table">
        <thead><tr><th>Fecha</th><th>Hora inicio</th><th>Hora fin</th><th>Activo</th></tr></thead>
        <tbody>
          {disponibilidades.map(d => (
            <tr key={d.id}>
              <td>{d.fecha}</td>
              <td>{d.hora_inicio}</td>
              <td>{d.hora_fin}</td>
              <td>{d.activo ? "Sí" : "No"}</td>
            </tr>
          ))}
          {disponibilidades.length===0 && <tr><td colSpan="4">No hay disponibilidades</td></tr>}
        </tbody>
      </table>
    </div>
  );
}
