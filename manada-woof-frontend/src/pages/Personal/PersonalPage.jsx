import React, { useEffect, useState } from "react";
import { getPersonal } from "../../api";
import "../../styles/list-pages.css";

export default function PersonalPage() {
  const [personal, setPersonal] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const res = await getPersonal();
        setPersonal(Array.isArray(res) ? res : res?.data || []);
      } catch (err) {
        console.error(err);
        setPersonal([]);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Personal / Veterinarios</h2>
      {loading ? (
        <p>Cargando personal...</p>
      ) : (
        <table className="mw-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombres</th>
              <th>Apellidos</th>
              <th>Especialidad</th>
            </tr>
          </thead>
          <tbody>
            {personal.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.nombres}</td>
                <td>{p.apellidos}</td>
                <td>{p.especialidad}</td>
              </tr>
            ))}
            {personal.length === 0 && (
              <tr>
                <td colSpan="5">No hay personal registrado</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
