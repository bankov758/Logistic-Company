import React from "react";

import Button from "../UI/BaseButton";

const EmployeeInterface: React.FC = () => {

    return (
        <section className="flex flex-col justify-start items-start w-full">
            <h1>Welcome, Antoan</h1>
            <div className="flex gap-x-4">
                <Button fill>Create an order</Button>
                <Button secondary fill>Close an order</Button>
            </div>
        </section>
    )
};

export default EmployeeInterface;