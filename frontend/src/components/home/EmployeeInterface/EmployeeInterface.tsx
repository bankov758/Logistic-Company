"use client";
import React, { useEffect, useState } from "react";
import { categories, tableColumns } from "@/data/employee/ordersTableData";
import {getSession, Session} from "@/lib/auth";
import axios from "@/lib/axios";

import FilterOrders from "./FilterOrders";
import Table, { item } from "../Table";
import Button from "../../UI/BaseButton";
import BaseDialog from "../../UI/BaseDialog";
import Notification from "@/components/UI/Notification";
import CreateAnOrderForm from "@/components/home/EmployeeInterface/CreateAnOrderForm";

const EmployeeInterface: React.FC = () => {
    const [showCreateOrderDialog, setShowCreateOrderDialog] = useState<boolean>(false)
    
    const [session, setSession] = useState<null | Session>(null);
    const [data, setData] = useState<item[] | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [tryAgain, setTryAgain] = useState<boolean>(false);

    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response)
                setError(null);

                axios.get("/shipments/logged-user")
                .then(response => setData(response.data))
                .catch(error => setError(error));
            })
    }, []);

    return (
        <>
            {error &&
                <Notification status='error'>
                    <div className='flex flex-col justify-center items-center w-full'>
                        <p>{error?.message}</p>
                        <button className='base-btn-blue' onClick={() => setTryAgain(!tryAgain)}>Try again</button>
                    </div>
                </Notification>
            }
            <div className='flex justify-start items-center gap-x-4'>
                <h3>Welcome, {session?.username || "user"}! You&apos;re logged in as employee.</h3>
                <br/>
                <Button fill onClick={() => setShowCreateOrderDialog(true)}>Create an order</Button>
            </div>
            {showCreateOrderDialog &&
                <BaseDialog title="NEW ORDER" tryClose={() => setShowCreateOrderDialog(false)}>
                    <CreateAnOrderForm />
                </BaseDialog>
            }
            <FilterOrders />
            {data &&
                <Table
                    columns={tableColumns}
                    categories={categories}
                    session={session}
                    data={data.map((item) => ({
                           ...item,
                           category: "registered"
                    }))}
                />
            }
        </>
    )
};

export default EmployeeInterface;