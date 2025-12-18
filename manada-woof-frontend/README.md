# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.

```
manada-woof-frontend
├─ .env
├─ eslint.config.js
├─ estructura.txt
├─ index.html
├─ package-lock.json
├─ package.json
├─ public
│  └─ vite.svg
├─ README.md
├─ src
│  ├─ api.js
│  ├─ App.css
│  ├─ App.jsx
│  ├─ assets
│  │  └─ react.svg
│  ├─ components
│  │  ├─ Clientes
│  │  │  ├─ ClienteForm.jsx
│  │  │  ├─ clientes.css
│  │  │  └─ ClientesPage.jsx
│  │  ├─ common
│  │  │  └─ ModalForm.jsx
│  │  ├─ Header
│  │  │  ├─ header.css
│  │  │  └─ Header.jsx
│  │  ├─ Mascotas
│  │  │  ├─ MascotaForm.jsx
│  │  │  └─ MascotasPage.jsx
│  │  ├─ Personal
│  │  │  ├─ PersonalForm.jsx
│  │  │  └─ PersonalPage.jsx
│  │  ├─ Servicios
│  │  │  ├─ ServicioForm.jsx
│  │  │  └─ ServiciosPage.jsx
│  │  └─ Sidebar
│  │     ├─ sidebar.css
│  │     └─ Sidebar.jsx
│  ├─ context
│  │  └─ AuthContext.jsx
│  ├─ index.css
│  ├─ main.jsx
│  ├─ pages
│  │  ├─ Appointments
│  │  │  ├─ AppointmentForm.jsx
│  │  │  ├─ appointments.css
│  │  │  ├─ AppointmentsPage.jsx
│  │  │  └─ ServicesTable.jsx
│  │  ├─ Availability
│  │  │  └─ AvailabilityConfig.jsx
│  │  ├─ Calendar
│  │  │  └─ CalendarView.jsx
│  │  ├─ Clientes
│  │  │  └─ ClientesPage.jsx
│  │  ├─ Dashboard
│  │  │  ├─ dashboard.css
│  │  │  └─ Dashboard.jsx
│  │  ├─ Login
│  │  │  ├─ login.css
│  │  │  ├─ Login.jsx
│  │  │  └─ logo-paw.PNG
│  │  ├─ Mascotas
│  │  │  └─ MascotasPage.jsx
│  │  ├─ Personal
│  │  │  └─ PersonalPage.jsx
│  │  ├─ Reports
│  │  │  └─ ReportsPage.jsx
│  │  └─ Servicios
│  │     └─ ServiciosPage.jsx
│  ├─ routes
│  │  ├─ privateLayout.css
│  │  ├─ PrivateRoute.jsx
│  │  └─ PublicRoute.jsx
│  └─ styles
│     └─ list-pages.css
└─ vite.config.js

```
```
manada-woof-frontend
├─ .env
├─ eslint.config.js
├─ estructura.txt
├─ index.html
├─ package-lock.json
├─ package.json
├─ public
│  └─ vite.svg
├─ README.md
├─ src
│  ├─ api.js
│  ├─ App.css
│  ├─ App.jsx
│  ├─ assets
│  │  └─ react.svg
│  ├─ components
│  │  ├─ Clientes
│  │  │  ├─ ClienteForm.jsx
│  │  │  ├─ clientes.css
│  │  │  └─ ClientesPage.jsx
│  │  ├─ common
│  │  │  └─ ModalForm.jsx
│  │  ├─ Header
│  │  │  ├─ header.css
│  │  │  └─ Header.jsx
│  │  ├─ Mascotas
│  │  │  ├─ MascotaForm.jsx
│  │  │  └─ MascotasPage.jsx
│  │  ├─ Personal
│  │  │  ├─ PersonalForm.jsx
│  │  │  └─ PersonalPage.jsx
│  │  ├─ Servicios
│  │  │  ├─ ServicioForm.jsx
│  │  │  └─ ServiciosPage.jsx
│  │  └─ Sidebar
│  │     ├─ sidebar.css
│  │     └─ Sidebar.jsx
│  ├─ context
│  │  └─ AuthContext.jsx
│  ├─ index.css
│  ├─ main.jsx
│  ├─ pages
│  │  ├─ Appointments
│  │  │  ├─ AppointmentForm.jsx
│  │  │  ├─ appointments.css
│  │  │  ├─ AppointmentsPage.jsx
│  │  │  └─ ServicesTable.jsx
│  │  ├─ Availability
│  │  │  └─ AvailabilityConfig.jsx
│  │  ├─ Calendar
│  │  │  └─ CalendarView.jsx
│  │  ├─ Clientes
│  │  │  └─ ClientesPage.jsx
│  │  ├─ Dashboard
│  │  │  ├─ dashboard.css
│  │  │  └─ Dashboard.jsx
│  │  ├─ Login
│  │  │  ├─ login.css
│  │  │  ├─ Login.jsx
│  │  │  └─ logo-paw.PNG
│  │  ├─ Mascotas
│  │  │  └─ MascotasPage.jsx
│  │  ├─ Personal
│  │  │  └─ PersonalPage.jsx
│  │  ├─ Reports
│  │  │  └─ ReportsPage.jsx
│  │  └─ Servicios
│  │     └─ ServiciosPage.jsx
│  ├─ routes
│  │  ├─ privateLayout.css
│  │  ├─ PrivateRoute.jsx
│  │  └─ PublicRoute.jsx
│  └─ styles
│     └─ list-pages.css
└─ vite.config.js

```