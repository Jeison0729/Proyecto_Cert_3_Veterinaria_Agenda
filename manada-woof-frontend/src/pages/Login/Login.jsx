// src/pages/Login/Login.jsx
import React, { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "./login.css";

export default function Login() {
  const { login, loading } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({ usuario: "", password: "" });
  const [error, setError] = useState(null);

  function handleChange(e) {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);
    const res = await login({ usuario: form.usuario, password: form.password });
    if (res.success) {
      navigate("/dashboard");
    } else {
      setError(res.message || "Usuario o contraseña incorrectos");
    }
  }

  return (
    <div className="mw-login-page">
      <div className="mw-login-card">
        <div className="mw-login-left">
          <div className="mw-logo-large">🐾</div>
          <h2>Manada Woof</h2>
        </div>

        <form className="mw-login-form" onSubmit={handleSubmit}>
          <h3>Bienvenido!</h3>
          {error && <div className="mw-login-error">{error}</div>}
          <input name="usuario" placeholder="Usuario" value={form.usuario} onChange={handleChange} />
          <input name="password" placeholder="Contraseña" type="password" value={form.password} onChange={handleChange} />
          <button type="submit" disabled={loading}>{loading ? "Ingresando..." : "Entrar"}</button>
        </form>
      </div>
    </div>
  );
}
