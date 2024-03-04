"use client";
import React, {Fragment, useEffect, useState} from "react";
import {getSession} from "@/lib/auth";

import ClientInterface from "@/components/home/ClientInterface";
import EmployeeInterface from "@/components/home/EmployeeInterface/EmployeeInterface";
import AdminInterface from "@/components/home/AdminInterface";
import BaseDialog from "@/components/UI/BaseDialog";
import {redirect} from "next/navigation";


const Home: React.FC = () => {
	const [session, setSession] = useState<null | {username: string; roles: string[]}>();
    const [selectedInterface, setSelectedInterface] = useState<string | null>(null);
    const [showDialog, setShowDialog] = useState<boolean>(true);
    const [isResolved, setIsResolved] = useState(false)

    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response)
                setIsResolved(true);
                if (response && response.roles.includes("USER") && !response.roles.includes("EMPLOYEE")) {
                    setSelectedInterface("user");
                    setShowDialog(false);
                }
            })
    }, []);

    // useEffect(() => {
    //     if( !session && isResolved) {
    //         redirect("/login")
    //     }
    // }, [session, isResolved]);

    const selectInterface = (selectedInterface: string) => {
        setSelectedInterface(selectedInterface)
        setShowDialog(false);
    }

    return (
        <Fragment>
            {/* {	session && showDialog &&
                (
					session.roles.includes("EMPLOYEE") && !session.roles.includes("ADMIN") ?
						<BaseDialog title="Choose an administrative role">
                            <td className="flex gap-x-3 items-center">
                                <button className="base-btn-blue" onClick={() => selectInterface('user')}>User</button>
                                <button className="base-btn-blue" onClick={() => selectInterface('employee')}>Employee</button>
                            </td>
						</BaseDialog> :
					session.roles.includes("ADMIN") ?
                        <BaseDialog title="Choose an administrative role">
                            <td className="flex gap-x-3 items-center">
                                <button className="base-btn-blue" onClick={() => selectInterface('user')}>User</button>
                                <button className="base-btn-blue" onClick={() => selectInterface('employee')}>Employee</button>
                                <button className="base-btn-blue" onClick={() => selectInterface("admin")}>Admin</button>
                            </td>
                        </BaseDialog> :
                        null
                )
            } */}
            <BaseDialog title="Choose an administrative role" >
                <td className="flex gap-x-3 items-center">
                    <button className="base-btn-blue" onClick={() => selectInterface('user')}>User</button>
                    <button className="base-btn-blue" onClick={() => selectInterface('employee')}>Employee</button>
                    <button className="base-btn-blue" onClick={() => selectInterface("admin")}>Admin</button>
                </td>
            </BaseDialog> 
            
            {
                selectedInterface === "user" ?
                    <ClientInterface /> :
                selectedInterface === "employee" ?
                    <EmployeeInterface /> :
                selectedInterface === "admin" ?
                    <AdminInterface /> :
                    null
            }
        </Fragment>
    );
}

export default Home;