"use client";

import React, {Fragment, useEffect, useState} from "react";
import {Session, getSession} from "@/lib/auth";

import {categories, tableColumns} from "@/data/client/ordersTableData";

import Table, {item} from "../Table";
import Notification from "@/components/UI/Notification";
import BaseDialog from "@/components/UI/BaseDialog";
import Button from "@/components/UI/BaseButton";
import SelfDeleteUserForm from "@/components/home/ClientInterface/SelfDeleteUserForm";
import useHttp from "@/hooks/useHttp";
import SkeletonLoadingAnimation from "@/components/UI/SkeletonLoadingAnimation";

const ClientInterface: React.FC = () => {
    const [session, setSession] = useState<null | Session>(null);

    const [data, setData] = useState<item[] | null>(null);
    const [tryAgain, setTryAgain] = useState<boolean>(false);
    const [showSelfDeleteDialog, setShowSelfDeleteDialog] = useState<boolean>(false);

    const {
        isLoading,
        error,
        sendRequest
    } = useHttp();

    useEffect(() => {
        getSession()
            .then(async (response) => {
                setSession(response)

                const responseData = await sendRequest({
                    url: "/shipments/logged-user"
                });

                if( responseData && responseData.data ) setData(responseData.data);

            })
    }, [sendRequest, tryAgain]);

    return (
        <>
            {error ?
                <Notification status='error'>
                    <div className='flex flex-col justify-center items-center w-full'>
                        <p>{error?.message}</p>
                        <button className='base-btn-blue' onClick={() => setTryAgain(!tryAgain)}>Try again</button>
                    </div>
                </Notification> :
                isLoading ?
                    <SkeletonLoadingAnimation header="tabs" layoutItems={5} /> :
                    <Fragment>
                        {data ?
                            <Fragment>
                                <h3 className="flex justify-start px-3 py-3 w-full mb-12">Welcome {session?.username || "Anonymous"}!
                                    This is your home page.</h3>
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
                            </Fragment> :
                            <p>No Data available!</p>
                        }
                    </Fragment>
            }
            {showSelfDeleteDialog &&
                (
                    <BaseDialog title="Self deletion" tryClose={() => setShowSelfDeleteDialog(false)}>
                        <SelfDeleteUserForm
                            session={session}
                            onClick={(setAction) => {
                                setShowSelfDeleteDialog(setAction)
                            }}
                        />
                    </BaseDialog>
                )
            }
            <div className="p-4 bottom-0 left-0 right-0 flex justify-center items-center">
                <h3 className="mr-2">Delete your account here:</h3>
                <Button
                    onClick={() => setShowSelfDeleteDialog(true)}
                    className="bg-red-600 text-white font-bold py-2 px-4"
                    fill={true}
                >
                    Delete User
                </Button>
            </div>
        </>
    );
}

export default ClientInterface;