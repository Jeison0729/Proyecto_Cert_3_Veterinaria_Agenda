import React from "react";
import "./dashboard.css";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  return (
    <div className="mw-dashboard">
      <div className="mw-hero">
        <h1>¿Qué desea hacer?</h1>
        <div className="mw-actions">
          <button onClick={()=>navigate("/mascotas")} className="mw-action">🐶 Mascotas</button>
          <button onClick={()=>navigate("/agendamiento")} className="mw-action primary">📅 Agendar Cita</button>
          <button onClick={()=>navigate("/agenda")} className="mw-action">🔎 Consultar Agenda</button>
          <button onClick={()=>navigate("/personal")} className="mw-action">👩‍⚕️ Veterinarios</button>
          <button onClick={()=>navigate("/servicios")} className="mw-action">🛠 Servicios</button>
          <button onClick={()=>navigate("/clientes")} className="mw-action">👥 Clientes</button>
          <button onClick={()=>navigate("/informes")} className="mw-action">📊 Informes</button>
        </div>
      </div>

      <section className="mw-cards">
        <div className="mw-card-ind">
          <div className="mw-card-title">Total Mascotas</div>
          <div className="mw-card-value">128</div>
        </div>
        <div className="mw-card-ind">
          <div className="mw-card-title">Veterinarios Activos</div>
          <div className="mw-card-value">12</div>
        </div>
        <div className="mw-card-ind">
          <div className="mw-card-title">Citas Hoy</div>
          <div className="mw-card-value">8</div>
        </div>
        <div className="mw-card-ind">
          <div className="mw-card-title">Servicios Realizados</div>
          <div className="mw-card-value">42</div>
        </div>
      </section>
    </div>
  );
}
