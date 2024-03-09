"use client";
import React, { useEffect, useState } from "react";
import Image from "next/image";

import Table, {item} from "../Table";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import BaseDialog from "@/components/UI/BaseDialog";
import Button from "@/components/UI/BaseButton";

import InfoIcon from '../../../../public/icons/info.svg';

import {
    adminCategories,
    adminColumns,
} from "@/data/admin/ordersTableData";

import {getSession, Session} from "@/lib/auth";
import Notification from "@/components/UI/Notification";
import ShowCompanyInfo from "@/components/home/AdminInterface/ShowCompanyInfo";
import CreateCompanyForm from "@/components/home/AdminInterface/CreateCompanyForm";

interface Client {
    id: number;
    name: string;
}

interface Employee {
    id: number;
    name: string;
}

interface Office {
    id: number;
    location: string;
}

async function getCompanies(session: { username: string; } | null): Promise<selectorItem[] | null> {
   try {
       const response = await fetch("http://localhost:8080/api/companies", {
           headers: {
               "Authorization": session?.username || "",
               "Content-Type": "application/json",
               Accept: "*/*"
           }
       })

       if( !response.ok ) {
           throw new Error("Something went wrong!");
       }

       const data = await response.json()

       if( data ) {

           return data.map((company: any) => ({
               title: company.name,
               code: company.name,
               id: company.id,
           }));
       }
       return null;
   } catch (err) {
       if( err instanceof Error ) {
           throw err;
       }
   }

   return null;
}

const AdminInterface: React.FC = () => {
    const [session, setSession] = useState<null | Session>(null);
    const [data, setData] = useState<item[][] | null>(null);
    const [error, setError] = useState<Error | null | string>(null);
    const [tryAgain, setTryAgain] = useState<boolean>(false);

    const [companies, setCompanies] = useState<selectorItem[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<selectorItem | null>(null);

    const [showCompanyInfoDialog, setShowCompanyInfoDialog] = useState<boolean>(false)
    const [showCreateDialog, setShowCreateDialog] = useState<boolean>(false)

    useEffect(() => {
        getSession()
            .then( async (response) => {
                setSession(response)
                
                try {
                    const companies = await getCompanies(response);

                    if( companies ) {
                        setCompanies(companies);
                    } else {
                        setError("Something went wrong!")
                    }
                } catch (err) {
                    if( err instanceof Error ) {
                        setError(err)
                    } 
                }
            });
    }, []);

    const handleSelectCompany = (data: selectorItem) => {
        setSelectedCompany(data);

        Promise.all([
            //clients
            fetch(`http://localhost:8080/api/companies/${data.id}/clients`, {
                headers: {
                    "Authorization": session?.username || "",
                    "Content-Type": "application/json",
                    Accept: "*/*"
                }
            }),
            //employees
            fetch(`http://localhost:8080/api/companies/${data.id}/employees`, {
                headers: {
                    "Authorization": session?.username || "",
                    "Content-Type": "application/json",
                    Accept: "*/*"
                }
            }),
            //offices
            fetch("http://localhost:8080/api/offices", {
                headers: {
                    "Authorization": session?.username || "",
                    "Content-Type": "application/json",
                    Accept: "*/*"
                }
            })
        ])
            .then(responses => Promise.all(responses.map(response => response.json())))
            .then(([clientsData, employeesData, officesData]) => {
                const combinedData = [
                    clientsData,
                    employeesData,
                    officesData
                ];
                setData(combinedData);
            })
            .catch(error => setError(error));
    }

    return <>
        {error &&
            <Notification status='error'>
                <div className='flex flex-col justify-center items-center w-full'>
                    <p>{error instanceof Error ? error.message : error}</p>
                    <button className='base-btn-blue' onClick={() => setTryAgain(!tryAgain)}>Try again</button>
                </div>
            </Notification>
        }
        <h3 className="flex justify-start w-full px-2 py-2">
            Welcome {session?.username || "user"}! You&apos;re logged in as admin.
        </h3>
        <br/>
        <div className='flex justify-center items-center gap-x-10'>
            <DataSelectorWrapper
                hasInitialPlaceholderValue
                placeholderValue={selectedCompany && Object.keys(selectedCompany).length > 0 ? selectedCompany.title : "Select a company"}
                selectorData={companies}
                onResubForNewData={handleSelectCompany}
            />
            { selectedCompany &&
                <Image
                    src={InfoIcon}
                    alt='Message'
                    className='cursor-pointer'
                    onClick={() => setShowCompanyInfoDialog(true)}
                />
            }
            <Button
                fill
                onClick={() => setShowCreateDialog(true)}
            >
                Create a company
            </Button>
        </div>
        {/* create company dialog  */}
        {showCreateDialog &&
            (<BaseDialog title="Create a company" tryClose={() => setShowCreateDialog(false)}>
                <CreateCompanyForm session={session} />
            </BaseDialog>)
        }
        {/* company info dialog */}
        {showCompanyInfoDialog && selectedCompany &&
            (
                <BaseDialog
                    title={ "Edit company " + selectedCompany.title + "'s information"}
                    tryClose={() => setShowCompanyInfoDialog(false)}
                >
                    <ShowCompanyInfo />
                </BaseDialog>
            )
        }
        {/* clients + employees + offices */}
        {data && Object.keys(data).length > 0 && selectedCompany && Object.keys(selectedCompany).length > 0 &&
            data
                .map((individualData: item[], index: number) => {
                    if( !individualData.length ) return;

                    return (
                        <Table
                            key={index}
                            columns={adminColumns[index]}
                            categories={adminCategories[index]}
                            session={session}
                            data=
                                {
                                    individualData
                                        .map(item => {

                                            if( index === 0 ) {//clients
                                                return {
                                                    ...item,
                                                    category: "clients"
                                                }
                                            } else if( index === 1 ) {//employees
                                                return {
                                                    ...item,
                                                    category: "employees"
                                                }
                                            } else if( index === 2 ) {//offices
                                                const companyId: { id: number; name: string; } = item?.companyId;

                                                return {
                                                    ...item,
                                                    name: companyId.name,
                                                    category: "offices",
                                                };
                                            }

                                            return {
                                                ...item,
                                                category: ""
                                            }
                                        })
                                }
                        />
                    )
                })
        }
    </>;
}

export default AdminInterface;