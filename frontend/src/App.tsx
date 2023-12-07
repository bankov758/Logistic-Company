import React from "react";
import { RouterProvider } from "react-router-dom";

//router
import router from "./util/routes";

const App: React.FC = () => {
 
    return <RouterProvider router={router} /> 
}

export default App;
