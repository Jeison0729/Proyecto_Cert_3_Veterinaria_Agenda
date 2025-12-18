import React from "react";
import { NavLink } from "react-router-dom";
import "./sidebar.css";

export default function Sidebar() {
  return (
    <aside className="mw-sidebar">
      {/* HEADER */}
      <div className="mw-sidebar-header">
        <span className="mw-sidebar-title">Menú</span>
      </div>

      {/* AGENDA */}
      <div className="mw-sidebar-section">
        <div className="mw-sidebar-btn primary">Agenda</div>
        <nav className="mw-nav">
          <NavLink to="/agendamiento" className="mw-nav-link">
            Agendar
          </NavLink>

          <NavLink to="/calendario" className="mw-nav-link">
            Calendario
          </NavLink>
        </nav>
      </div>

      {/* GESTIÓN */}
      <div className="mw-sidebar-section">
        <div className="mw-sidebar-btn primary">Gestión</div>
        <nav className="mw-nav">
          <NavLink to="/clientes" className="mw-nav-link">
            Clientes
          </NavLink>
          <NavLink to="/mascotas" className="mw-nav-link">
            Mascotas
          </NavLink>
          <NavLink to="/servicios" className="mw-nav-link">
            Servicios
          </NavLink>
          <NavLink to="/personal" className="mw-nav-link">
            Veterinarios
          </NavLink>
          <NavLink to="/disponibilidad" className="mw-nav-link">
            Disponibilidad
          </NavLink>
        </nav>
      </div>

      {/* FOOTER */}
      <div className="mw-sidebar-footer">
        <small>Manada Woof</small>
      </div>
    </aside>
  );
}
