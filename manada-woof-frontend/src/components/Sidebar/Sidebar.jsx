import React from "react";
import "./sidebar.css";
import { NavLink } from "react-router-dom";

export default function Sidebar() {
  return (
    <aside className="mw-sidebar">
      <div className="mw-sidebar-section">
        <button className="mw-sidebar-btn primary">Agenda</button>
        <nav className="mw-nav">
          <NavLink to="/agendamiento" className={({isActive}) => "mw-nav-link" + (isActive ? " active":"")}>Agendar</NavLink>
          <NavLink to="/consultar-citas" className="mw-nav-link">Consultar citas</NavLink>
        </nav>
      </div>

      <div className="mw-sidebar-section">
        <button className="mw-sidebar-btn">Gestión</button>
        <nav className="mw-nav">
          <NavLink to="/clientes" className="mw-nav-link">Clientes</NavLink>
          <NavLink to="/mascotas" className="mw-nav-link">Mascotas</NavLink>
          <NavLink to="/servicios" className="mw-nav-link">Servicios</NavLink>
          <NavLink to="/personal" className="mw-nav-link">Veterinarios</NavLink>
        </nav>
      </div>
    </aside>
  );
}
