import React from "react";
import ReactDOM from "react-dom/client";

import App from "./App.jsx";

import Providers from "./app/providers.jsx";

import { ToastContainer } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";

import "./assets/styles/global.css";


ReactDOM.createRoot(
    document.getElementById("root")
).render(

    <React.StrictMode>

        <Providers>

            <App />

            <ToastContainer

                position="top-right"

                autoClose={3000}

                hideProgressBar={false}

                newestOnTop

                closeOnClick

                pauseOnHover

                draggable

                theme="light"

            />

        </Providers>

    </React.StrictMode>

);