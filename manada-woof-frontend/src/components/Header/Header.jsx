import React from "react";
import "./header.css";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

export default function Header() {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  return (
    <header className="mw-header">
      <div className="mw-header-left">
        <button className="mw-logo-btn" onClick={() => navigate("/dashboard")}>
          {/* si tienes logo en public, reemplaza por <img src="/logo.png" /> */}
          <svg width="36" height="36" viewBox="0 0 64 64" className="mw-logo-icon" aria-hidden>
            <g fill="#D9813B"><path d="M32 40c-9 0-16 7-16 16h32c0-9-7-16-16-16z"/><circle cx="20" cy="18" r="6"/><circle cx="44" cy="18" r="6"/></g>
          </svg>
          <span className="mw-brand-text"><strong>Manada</strong> <span>Woof</span></span>
        </button>
      </div>

      <nav className="mw-header-right">
        <span className="mw-welcome">Hola, {user?.nombre || user?.usuario || "Usuario"}</span>
        <button className="mw-logout" onClick={() => { logout(); navigate("/login"); }}>Cerrar sesión</button>
      </nav>
    </header>
  );
}
