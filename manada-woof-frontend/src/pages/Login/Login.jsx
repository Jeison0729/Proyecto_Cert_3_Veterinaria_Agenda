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
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);
    const res = await login({
      usuario: form.usuario,
      password: form.password,
    });

    if (res.success) {
      navigate("/dashboard");
    } else {
      setError(res.message || "Usuario o contraseña incorrectos");
    }
  }

  return (
    <div className="mw-login-page">
      <div className="mw-login-card card">
        <div className="row no-gutters h-100">
          {/* PANEL IZQUIERDO */}
          <div className="col-md-6 d-none d-md-flex mw-login-left">
            <div className="text-center">
              <div className="mw-logo-large">🐾</div>
              <h2>Manada Woof</h2>
              <p className="mw-subtitle">Sistema de gestión veterinaria</p>
            </div>
          </div>

          {/* FORMULARIO */}
          <div className="col-md-6 d-flex align-items-center">
            <form className="mw-login-form w-100" onSubmit={handleSubmit}>
              <h4>Bienvenido</h4>
              <p className="text-muted mb-3">Ingrese sus credenciales</p>

              {error && <div className="mw-login-error">{error}</div>}

              <div className="form-group">
                <label className="small text-muted">Usuario</label>
                <input
                  className="form-control"
                  name="usuario"
                  value={form.usuario}
                  onChange={handleChange}
                  placeholder="Usuario"
                />
              </div>

              <div className="form-group">
                <label className="small text-muted">Contraseña</label>
                <input
                  type="password"
                  className="form-control"
                  name="password"
                  value={form.password}
                  onChange={handleChange}
                  placeholder="Contraseña"
                />
              </div>

              <button
                type="submit"
                className="btn mw-btn-primary mw-btn-center"
                disabled={loading}
              >
                {loading ? "Ingresando..." : "Entrar"}
              </button>

              <div className="text-center mt-3">
                <small className="text-muted">© Manada Woof</small>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
