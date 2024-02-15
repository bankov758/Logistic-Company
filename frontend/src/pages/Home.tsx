import React, { Fragment, useState } from "react";

const Home: React.FC = () => {
    // let a = 5;
    let [a, setA] = useState(5);//initial value

    console.log('render')
    // setTimeout(() => console.log(a), 5000)

    return (
        <Fragment>
            <h1>home page</h1>
            <button onClick={() => setA((prevState) => prevState+1)}>Increment</button>
            {a}
        </Fragment>
    )
}

export default Home;