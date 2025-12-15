import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import api from "../../api";
import "../../styles/list-pages.css";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js';
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

export default function ReportsPage() {
  const [chartData, setChartData] = useState(null);

  useEffect(() => {
    (async ()=>{
      try {
        // ejemplo: pedir reporte diario al backend (ajusta endpoint)
        const res = await api.get("/agenda/reporte/dia?fecha=" + new Date().toISOString().slice(0,10)).then(r=>r.data||r);
        // Si tu endpoint devuelve estructura compleja, adapta la extracción:
        // Aquí simulamos datos si el backend no devuelve lo esperado
        const sample = {
          labels: ["09:00","10:00","11:00","12:00"],
          datasets: [{ label:"Citas", data:[2,3,1,4] }]
        };
        setChartData(sample);
      } catch (err) {
        console.error(err);
        setChartData({
          labels: ["09:00","10:00","11:00"],
          datasets: [{ label:"Citas", data:[0,0,0] }]
        });
      }
    })();
  }, []);

  if (!chartData) return <div className="mw-page"><p>Cargando reportes...</p></div>;

  return (
    <div className="mw-page">
      <h2 className="mw-page-title">Reportes de Citas</h2>
      <div style={{maxWidth:900}}>
        <Bar data={chartData} />
      </div>
    </div>
  );
}
