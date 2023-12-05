import React, { Fragment } from "react";
import { Outlet } from "react-router-dom";

//layout components
import TheHeader from "./layout/TheHeader";
import Footer from "./layout/Footer";

const RootLayout:React.FC = () => {

    return (
        <Fragment>
            <header>
                <TheHeader />
            </header>
            <main>
                {/* the place where the children routes should be rendered */}
                <Outlet />
            </main>
            <footer>
                <Footer />
            </footer>
        </Fragment>
    )

}

export default RootLayout;