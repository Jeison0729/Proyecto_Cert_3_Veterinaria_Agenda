import React from "react";
import { useNavigate } from "react-router-dom";
import "./dashboard.css";

export default function Dashboard() {
  const navigate = useNavigate();

  return (
    <div className="container mt-4">
      {/* HERO */}
      <div className="card mb-4 mw-hero-bs">
        <div className="card-body text-center">
          <h4 className="font-weight-bold text-black mb-1">Panel principal</h4>
          <p className="text-black-50 mb-3">
            Accesos rápidos a las principales funciones
          </p>

          <div className="d-flex flex-wrap justify-content-center">
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/mascotas")}
            >
              Mascotas
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/agendamiento")}
            >
              Agendar Cita
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/personal")}
            >
              Veterinarios
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/disponibilidad")}
            >
              Calendario Personal
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/calendario")}
            >
              Ver calendario
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/servicios")}
            >
              Servicios
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/clientes")}
            >
              Clientes
            </button>
            <button
              className="btn mw-btn mx-2 mb-2"
              onClick={() => navigate("/informes")}
            >
              Informes
            </button>
          </div>
        </div>
      </div>

      {/* RESUMEN */}
      <div className="d-flex align-items-center mb-2">
        <h6 className="mb-0 font-weight-bold text-muted">Resumen general</h6>
        <hr className="flex-grow-1 ml-3" />
      </div>

      <div className="row">
        <DashboardCard title="Total Mascotas" value="10" />
        <DashboardCard title="Veterinarios Activos" value="6" />
        <DashboardCard title="Citas Hoy" value="8" />
        <DashboardCard title="Servicios Realizados" value="42" />
      </div>
    </div>
  );
}

function DashboardCard({ title, value }) {
  return (
    <div className="col-md-3 col-sm-6 mb-3">
      <div className="card mw-stat-card">
        <div className="card-body">
          <small className="text-muted">{title}</small>
          <h4 className="font-weight-bold mw-text mb-0">{value}</h4>
        </div>
      </div>
    </div>
  );
}
