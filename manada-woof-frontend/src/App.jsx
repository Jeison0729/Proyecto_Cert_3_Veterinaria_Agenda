// src/App.jsx
import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login/Login";
import Dashboard from "./pages/Dashboard/Dashboard";
import AppointmentsPage from "./pages/Appointments/AppointmentsPage";

import ClientesPage from "./pages/Clientes/ClientesPage";
import MascotasPage from "./pages/Mascotas/MascotasPage";
import ServiciosPage from "./pages/Servicios/ServiciosPage";
import PersonalPage from "./pages/Personal/PersonalPage";

import CalendarView from "./pages/Calendar/CalendarView";
import AvailabilityConfig from "./pages/Availability/AvailabilityConfig";
import ReportsPage from "./pages/Reports/ReportsPage";

import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./routes/PrivateRoute";
import PublicRoute from "./routes/PublicRoute";

export default function App() {
  return (
    <AuthProvider>
      <Routes>

        {/* PÚBLICA */}
        <Route path="/login" element={
          <PublicRoute>
            <Login />
          </PublicRoute>
        } />

        {/* PRIVADAS */}
        <Route path="/dashboard" element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        } />

        <Route path="/agendamiento" element={
          <PrivateRoute>
            <AppointmentsPage />
          </PrivateRoute>
        } />

        <Route path="/clientes" element={
          <PrivateRoute>
            <ClientesPage />
          </PrivateRoute>
        } />

        <Route path="/mascotas" element={
          <PrivateRoute>
            <MascotasPage />
          </PrivateRoute>
        }
          />

        <Route path="/servicios" element={
          <PrivateRoute>
            <ServiciosPage />
          </PrivateRoute>
        } />

        <Route path="/personal" element={
          <PrivateRoute>
            <PersonalPage />
          </PrivateRoute>
        } />

        <Route path="/calendar" element={
          <PrivateRoute>
            <CalendarView />
          </PrivateRoute>
        } />

        <Route path="/config-disponibilidad" element={
          <PrivateRoute>
            <AvailabilityConfig />
          </PrivateRoute>
        } />

        <Route path="/informes" element={
          <PrivateRoute>
            <ReportsPage />
          </PrivateRoute>
        } />

        {/* DEFAULT */}
        <Route path="/" element={<Navigate to="/dashboard" replace />} />

      </Routes>
    </AuthProvider>
  );
}
