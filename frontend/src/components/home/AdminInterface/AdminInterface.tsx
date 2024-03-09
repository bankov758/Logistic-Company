"use client";
import React, { useEffect, useState } from "react";
import {useFormState} from "react-dom";
import Image from "next/image";

import Table, {item} from "../Table";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import BaseDialog from "@/components/UI/BaseDialog";
import Button from "@/components/UI/BaseButton";

import InfoIcon from '../../../../public/icons/info.svg';

import {Company, deleteCompany, editCompany, createCompany, addOffice} from "@/lib/adminActions"

import {
    adminColumns,
    categories,
    officeCategories,
    officeColumns,
    tableColumns
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
   const response = await fetch("http://localhost:8080/api/companies", {
        headers: {
            "Authorization": session?.username || "",
            "Content-Type": "application/json",
            Accept: "*/*"
        }
    })

   const data = await response.json()

   if( data ) {

       return data.map((company: any) => ({
           title: company.name,
           code: company.name,
           id: company.id,
       }));
   }

   return null;
}

const AdminInterface: React.FC = () => {
    const [session, setSession] = useState<null | Session>(null);
    const [data, setData] = useState<item[][] | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [tryAgain, setTryAgain] = useState<boolean>(false);

    const [selectorItemData, setSelectorItemData] = useState<selectorItem[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<selectorItem | null>(null);

    const [showCompanyInfoDialog, setShowCompanyInfoDialog] = useState<boolean>(false)
    const [showCreateDialog, setShowCreateDialog] = useState<boolean>(false)

    const [companyData, setCompanyData] = useState<selectorItem[] | null>(null);
    const [updatedCompanyName, setUpdatedCompanyName] = useState('');
    const [officeLocation, setofficeLocation] =useState('');

    useEffect(() => {
        getSession()
            .then( async (response) => {
                setSession(response)
                const companies = await getCompanies(response);

                if( companies ) {
                    setSelectorItemData(companies);
                }
            });
    }, []);

    const updateSelectedCompany = (data: selectorItem) => {
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
                    clientsData.map((client: Client) => ({ ...client, category: "clients" })),
                    employeesData.map((employee: Employee) => ({ ...employee, category: "employees" })),
                    officesData.map((office: Office) => ({ ...office, category: "offices" }))
                ];
                console.log("combinedData >>> ", combinedData)
                setData(combinedData);
            })
            .catch(error => setError(error));
    }

    return <>
        {error &&
            <Notification status='error'>
                <div className='flex flex-col justify-center items-center w-full'>
                    <p>{error?.message}</p>
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
                selectorData={selectorItemData}
                onResubForNewData={updateSelectedCompany}
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
        {/*TODO: Split clients and employees into separate tables instead of mapping them twice */}
        {/*/!* clients + employees + offices *!/*/}
        {data && Object.keys(data).length > 0 && selectedCompany && Object.keys(selectedCompany).length > 0 &&
            data
                // .filter((individualData: item[]) => individualData.length > 0)
                .map((individualData: item[], index: number) => (
                <Table
                    key={index}
                    columns={adminColumns[index]}
                    categories={[{
                        title: "Data",
                        code: "data",
                        id: 1,
                    }]}
                    session={session}
                    data=
                        {
                            individualData
                                .map(item => {
                                    const companyId = item?.companyId as { id: number; name: string; };

                                    return {
                                        ...item,
                                        category: "data",
                                    };
                                })
                        }
                />
            ))
        }
    </>;
}

export default AdminInterface;