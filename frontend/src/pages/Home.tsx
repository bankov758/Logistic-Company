import React, { Fragment } from "react";
import { Link } from "react-router-dom";

const Home: React.FC = () => {

    return (
        <Fragment>
            <h1>home page</h1>
            <Link to='/auth'>Auth page</Link>
        </Fragment>
    )
}

export default Home;