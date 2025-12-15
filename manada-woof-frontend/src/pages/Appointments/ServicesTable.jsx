// src/pages/Appointments/ServicesTable.jsx
import React, { useEffect, useState } from "react";

export default function ServicesTable({ servicios = [], personal = [], detalleServicios, setDetalleServicios }) {
  const [row, setRow] = useState({
    id_servicio: "",
    valor_servicio: 0,
    cantidad: 1,
    duracion_min: 0,
    id_personal: "",
    adicionales: ""
  });

  const serviciosArray = Array.isArray(servicios) ? servicios : (servicios?.data ?? []);

  useEffect(() => {
    const s = serviciosArray.find(x => String(x.id) === String(row.id_servicio));
    if (s) {
      setRow(r => ({ ...r, valor_servicio: s.precio ?? r.valor_servicio, duracion_min: s.duracionMinutos ?? s.duracion_minutos ?? r.duracion_min }));
    }
  }, [row.id_servicio, serviciosArray]);

  function handleChange(e) {
    const { name, value } = e.target;
    if (name === "valor_servicio" || name === "cantidad" || name === "duracion_min") {
      setRow(prev => ({ ...prev, [name]: value === "" ? "" : Number(value) }));
    } else {
      setRow(prev => ({ ...prev, [name]: value }));
    }
  }

  function addItem() {
    if (!row.id_servicio) { alert("Seleccione servicio"); return; }
    if (!row.id_personal) { alert("Asigne responsable"); return; }
    const servicioObj = serviciosArray.find(s => String(s.id) === String(row.id_servicio));
    const nombre = servicioObj?.nombre ?? servicioObj?.name ?? "";
    const serviceObj = {
      id: Date.now() + Math.floor(Math.random()*999),
      id_servicio: Number(row.id_servicio),
      nombre_servicio: nombre,
      valor_servicio: Number(row.valor_servicio) || 0,
      cantidad: Number(row.cantidad) || 1,
      duracion_min: Number(row.duracion_min) || 0,
      id_personal: Number(row.id_personal),
      adicionales: row.adicionales || ""
    };
    setDetalleServicios(prev => [...prev, serviceObj]);
    setRow({ id_servicio:"", valor_servicio:0, cantidad:1, duracion_min:0, id_personal:"", adicionales:"" });
  }

  function removeItem(id) { setDetalleServicios(prev => prev.filter(p=>p.id!==id)); }

  function formatMoney(v) { return (Number(v)||0).toFixed(2); }

  return (
    <div>
      <div className="d-flex gap-2 align-items-center mb-2">
        <select className="form-select" name="id_servicio" value={row.id_servicio} onChange={handleChange}>
          <option value="">Seleccione un servicio</option>
          {serviciosArray.map(s => <option key={s.id} value={s.id}>{s.nombre ?? s.name}</option>)}
        </select>
        <input className="form-control" name="valor_servicio" value={row.valor_servicio} onChange={handleChange} placeholder="Valor S/." type="number" step="0.01" />
        <input className="form-control" name="cantidad" value={row.cantidad} onChange={handleChange} type="number" min="1" style={{width:80}} />
        <input className="form-control" name="duracion_min" value={row.duracion_min} onChange={handleChange} type="number" min="0" style={{width:110}} />
        <select className="form-select" name="id_personal" value={row.id_personal} onChange={handleChange}>
          <option value="">Seleccionar responsable</option>
          {Array.isArray(personal) && personal.map(p => <option key={p.id} value={p.id}>{p.nombres} {p.apellidos}</option>)}
        </select>
        <input className="form-control" name="adicionales" value={row.adicionales} onChange={handleChange} placeholder="Adicionales..." />
        <button className="btn btn-success" type="button" onClick={addItem}>Agregar</button>
      </div>

      <table className="table table-sm">
        <thead><tr><th>Servicio</th><th>Resp.</th><th>Cant</th><th>Duración</th><th>Valor</th><th>Subtotal</th><th>Acción</th></tr></thead>
        <tbody>
          {detalleServicios.length === 0 && <tr><td colSpan="7">No hay servicios agregados</td></tr>}
          {detalleServicios.map(it => {
            const resp = Array.isArray(personal) ? personal.find(p=>Number(p.id)===Number(it.id_personal)) : null;
            const subtotal = (Number(it.valor_servicio)||0) * (Number(it.cantidad)||1);
            return (
              <tr key={it.id}>
                <td>{it.nombre_servicio}</td>
                <td>{resp ? `${resp.nombres} ${resp.apellidos}` : "-"}</td>
                <td>{it.cantidad}</td>
                <td>{it.duracion_min}</td>
                <td>{formatMoney(it.valor_servicio)}</td>
                <td>{formatMoney(subtotal)}</td>
                <td><button className="btn btn-sm btn-danger" onClick={()=>removeItem(it.id)}>X</button></td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
