// src/routes/PrivateRoute.jsx
import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import Header from "../components/Header/Header";
import Sidebar from "../components/Sidebar/Sidebar";
import "./privateLayout.css";

export default function PrivateRoute({ children }) {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) return <Navigate to="/login" replace />;

  return (
    <div className="mw-layout">
      <Header />

      <div className="mw-layout-body">
        <Sidebar />

        <main className="mw-layout-content">
          {children}
        </main>
      </div>
    </div>
  );
}
