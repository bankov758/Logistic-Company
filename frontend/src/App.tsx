import React from "react";
import { RouterProvider } from "react-router-dom";

//router
import router from "./util/routes";

const App: React.FC = () => {

 let b = 5;
 let a: number = 5;

    return <RouterProvider router={router} /> 
}

export default App;
