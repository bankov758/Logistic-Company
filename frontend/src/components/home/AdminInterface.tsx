"use client";
import React, { useEffect, useState } from "react";
import Image from "next/image";

import Table, {item} from "./Table";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import BaseDialog from "@/components/UI/BaseDialog";
import Button from "@/components/UI/BaseButton";
import {useFormState} from "react-dom";
import {Company, deleteCompany, editCompany, createCompany, addOffice} from "@/lib/adminActions"
import InfoIcon from '../../../public/icons/info.svg';

import {
    categories,
    officeCategories,
    officeColumns,
    tableColumns
} from "@/data/admin/ordersTableData";

import {getSession} from "@/lib/auth";


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

async function getCompanies(session: { username: string; } | null) {
   const response = await fetch("http://localhost:8080/api/companies", {
        headers: {
            "Authorization": session?.username || "",
            "Content-Type": "application/json",
            Accept: "*/*"
        }
    })

   const data = await response.json()

   if( data ) {
       const updatedSelectorData = data.map((company: any) => ({
           title: company.name,
           code: company.name,
           id: company.id,
       }));

   }

   return data;
}

const AdminInterface: React.FC = () => {
    const [session, setSession] = useState<null | {username: string; roles: string[]}>(null);
    const [data, setData] = useState<item[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    const [selectorItemData, setSelectorItemData] = useState<selectorItem[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<selectorItem | null>(null);

    const [showDialog, setShowDialog] = useState<boolean>(false)
    const [showCreateDialog, setShowCreateDialog] = useState<boolean>(false)

    const [companyData, setCompanyData] = useState<Company[]>([]);
    const [updatedCompanyName, setUpdatedCompanyName] = useState('');
    const [officeLocation, setofficeLocation] =useState('');

    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response)
                setCompanyData(getCompanies(response));
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
                    ...clientsData.map((client: Client) => ({ ...client, category: "clients" })),
                    ...employeesData.map((employee: Employee) => ({ ...employee, category: "employees" })),
                    ...officesData.map((office: Office) => ({ ...office, category: "offices" }))
                ];
                console.log(combinedData)
                setData(combinedData);
            })
            .catch(error => setError(error));
    }
    const [createCompanyState, createCompanyAction] = useFormState(createCompany.bind(session), { message: "", errors: "" })

    // useEffect(() => {
    //     setShowDialog(false);
    //     setSelectedCompany(null);
    //     setCompanyData(getCompanies(session));
    // }, [createCompanyState]);

    return <>
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
                    onClick={() => setShowDialog(true)}
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
                <form
                    action={createCompanyAction}
                    className="flex items-center justify-center gap-x-3 pb-3"
                >
                    <label className="block text-gray-500" htmlFor='company_name'>Company name</label>
                    <input
                        type="text"
                        id="company_name"
                        className="input-info-dialog"
                        name='company_name'
                        placeholder="Speedy"
                    />
                    <br/>
                    <div className='flex gap-x-2 px-5 py-3 text-gray-500'>
                        <button
                            type='submit'
                            className="action_btn_green px-6 py-2"
                        >
                            Add
                        </button>
                    </div>
                </form>
            </BaseDialog>)
        }
        {/* company info dialog */}
        {showDialog && selectedCompany &&
            (
                <BaseDialog title={ "Edit company " + selectedCompany.title + "'s information"} tryClose={() => setShowDialog(false)}>
                    <div className="flex items-center  gap-x-2 pt-3 pb-4">
                        <label className="block  text-gray-500">Delete this company:</label>
                        <button
                            className="action_btn_red px-12 py-1.5 "
                            onClick={() => deleteCompany(selectedCompany.title,companyData, session )}
                        >
                            DELETE COMPANY
                        </button>
                    </div>
                    <div className="flex items-center  gap-x-2 pb-4">
                        <label className="block  text-gray-500">Change company name:</label>
                        <input type="text" id="company_name_edit" className="input-info-dialog"
                               onChange={(e) => setUpdatedCompanyName(e.target.value)}
                               placeholder="Speedy "></input><br/>
                        <div className='flex py-3 text-gray-500'>
                            <button
                                className="action_btn_green px-6 py-2"
                                onClick={() => editCompany(selectedCompany.title,updatedCompanyName, companyData, session )}

                            >
                                Edit
                            </button>
                        </div>
                    </div>
                    <div className="flex items-center gap-x-2 pb-4">
                        <label className="block  text-gray-500">Add a new office location:</label>
                        <input type="text" id="office_location" className="input-info-dialog"
                               onChange={(e) => setofficeLocation(e.target.value)}
                               placeholder="Sofia"></input>
                        <div className='flex py-3 text-gray-500'>
                            <button
                                className="action_btn_blue px-3 py-1.5"
                                onClick={() => addOffice(selectedCompany.title,officeLocation, companyData, session )}
                            >
                                Add
                            </button>
                        </div>
                    </div>


                </BaseDialog>
            )
        }

        {/*/!* clients + employees *!/*/}
        {data && selectedCompany && Object.keys(selectedCompany).length > 0 &&
            <Table
                columns={tableColumns}
                categories={categories}
                data={data.map((item) => {
                    if (item?.firstName !== undefined && item?.lastName !== undefined && item?.username === undefined) {
                        return {
                            ...item,
                            category: "clients",
                        } as item;
                    } else if (item?.username !== undefined && item?.address === undefined) {
                        return {
                            ...item,
                            category: "employees",
                        } as item;
                    }
                    return item as item;
                }) as item[]
                }

            />
        }

        {/*offices */}
        {data && selectedCompany && Object.keys(selectedCompany).length > 0 &&
            <Table
                columns={officeColumns}
                categories={officeCategories}
                data={data
                    .filter(item => item?.address !== undefined )
                    .map((item) => {
                        const companyId = item.companyId as any;end

                        if (companyId?.name === selectedCompany.title) {
                            return {
                                ...item,
                                category: "offices",
                                name: companyId?.name,
                            } as item;
                        }
                        return null;
                    })
                    .filter(Boolean) as item[]
                }
            />
        }
    </>;
}

export default AdminInterface;