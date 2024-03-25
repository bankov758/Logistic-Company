import React from "react";
import {useEffect, useState} from "react";
import {Session, getSession} from "@/lib/auth";
import axios from "@/lib/axios";

import {categories, tableColumns} from "@/data/client/ordersTableData";

import Table, {item} from "../Table";
import Notification from "@/components/UI/Notification";
import BaseDialog from "@/components/UI/BaseDialog";
import Button from "@/components/UI/BaseButton";
import SelfDeleteUserForm from "@/components/home/ClientInterface/SelfDeleteUserForm";

const ClientInterface: React.FC = () => {
    const [session, setSession] = useState<null | Session>(null);

    const [data, setData] = useState<item[] | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [tryAgain, setTryAgain] = useState<boolean>(false);
    const [showSelfDeleteDialog, setShowSelfDeleteDialog] = useState<boolean>(false);

    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response)
                setError(null);

                axios.get("/shipments/logged-user")
                    .then(response => setData(response.data))
                    .catch(error => setError(error));
            })
    }, [tryAgain]);

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
            <h3 className="flex justify-start w-full">Welcome {session?.username || "Anonymous"}! This is your home
                page.</h3>
            <br></br>
            {data &&
                <Table
                    columns={tableColumns}
                    categories={categories}
                    session={session}
                    data={
                        data
                            .filter((item) => item.sender === session?.username || item.receiver === session?.username)
                            .map((item) => {
                                if (session?.username === item?.sender) {
                                    return {
                                        ...item,//spread operator
                                        category: "sent"
                                    }
                                }

                                return {
                                    ...item,//spread operator
                                    category: "received"
                                }
                            })
                    }
                />
            }
            {showSelfDeleteDialog &&
                ( <BaseDialog title="Self deletion" tryClose={() => setShowSelfDeleteDialog(false) }>
                    <SelfDeleteUserForm 
                        session={session}
                        onClick={(setAction) => {setShowSelfDeleteDialog(setAction)}}
                    />
                </BaseDialog>)
            }
            <div className="flex gap-x-5 items-center">
                <h3>Delete your account here ={" >"} </h3>
                <Button className="bg-red-600 text-white" fill={true} onClick={() => setShowSelfDeleteDialog(true)}>Delete User</Button>
            </div>


        </>
    );
}

export default ClientInterface;