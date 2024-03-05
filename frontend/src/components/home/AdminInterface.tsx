"use client";
import React, {useEffect, useState} from "react";

import Table, {item} from "./Table";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import BaseDialog from "@/components/UI/BaseDialog";

import InfoIcon from '../../../public/icons/info.svg';

import {
    categories,
    officeCategories,
    officeColumns,
    tableColumns
} from "@/data/admin/ordersTableData";
import Image from "next/image";
import Button from "../UI/BaseButton";
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
let selectorData: selectorItem[] = [
    {
        title: 'Econt',
        code: 'econt',
    },
    {
        title: "Speedy",
        code: "speedy"
    },
    {
        title: "DHL",
        code: "dhl"
    }
];

const AdminInterface: React.FC = () => {
    const [session, setSession] = useState<null | {username: string; roles: string[]}>();
    const [data, setData] = useState<item[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    const [selectorItemData, setSelectorItemData] = useState<selectorItem[]>([]);

    const [selectedCompany, setSelectedCompany] = useState(selectorData[0]);
    const [showDialog, setShowDialog] = useState<boolean>(false)
    const [showCreateDialog, setShowCreateDialog] = useState<boolean>(false)



    const updateSelectedCompany = (data: selectorItem) => {
        console.log(data);
        setSelectedCompany(data);
    }


    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response)

                const dynamicClients = `localhost:8080/api/companies/${selectedCompany.title}/clients`;
                const dynamicEmployees = `localhost:8080/api/companies/${selectedCompany.title}/employees`;

                Promise.all([
                    fetch("http://localhost:8080/api/companies/1/clients", {
                        headers: {
                            "Authorization": response?.username,
                            "Content-Type": "application/json",
                            Accept: "*/*"
                        }
                    }),
                    fetch("http://localhost:8080/api/companies/1/employees", {
                        headers: {
                            "Authorization": response?.username,
                            "Content-Type": "application/json",
                            Accept: "*/*"
                        }
                    }),
                    fetch("http://localhost:8080/api/offices", {
                        headers: {
                            "Authorization": response?.username,
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

                            setData(combinedData);
                        })
                        .catch(error => setError(error));
                    })
    }, []);

    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response);

                fetch("http://localhost:8080/api/companies", {
                    headers: {
                        "Authorization": response?.username,
                        "Content-Type": "application/json",
                        Accept: "*/*"
                    }
                })
                    .then(response => response.json())
                    .then(companyData => {
                        const updatedSelectorData = companyData.map((company: any) => ({
                            title: company.name,
                            code: company.code
                        }));
                        setSelectorItemData(updatedSelectorData);
                    })
                    .catch(error => setError(error));
            });
    }, []);

    return <>
        <h3 className="flex justify-start w-full">
            Welcome {session?.username || "user"}! You&apos;re logged in as admin.
        </h3>

        <br/>

        <div className='flex justify-center items-center gap-x-10'>
            <DataSelectorWrapper
                hasInitialPlaceholderValue
                placeholderValue={selectedCompany.title}
                selectorData={selectorItemData}
                onResubForNewData={updateSelectedCompany}
            />

            <Image
                src={InfoIcon}
                alt='Message'
                className='cursor-pointer'
                onClick={() => setShowDialog(true)}
            />
            <Button fill
                    onClick={() => setShowCreateDialog(true)}
            >Create a company</Button>
        </div>
        {/* create company dialog  */}
        {showCreateDialog &&
            (<BaseDialog title="Create a company" tryClose={() => setShowCreateDialog(false)}>
                <div className="flex items-center justify-center gap-x-3 pb-3">
                    <label className="block  text-gray-500">Company name:</label>
                    <input type="text" id="company_name_add" className="input-info-dialog"
                           placeholder="Speedy "></input><br/>
                    <div className='flex gap-x-2 px-5 py-3 text-gray-500'>
                        <button
                            className="action_btn_green px-6 py-2"
                        >
                            Add
                        </button>
                    </div>
                </div>
            </BaseDialog>)
        }
        {/* company info dialog */}
        {showDialog &&
            (
                <BaseDialog title={selectedCompany.title} tryClose={() => setShowDialog(false)}>
                    <div className="flex flex-row items-center  gap-x-2 pt-4 pb-6">
                        <label className="block  text-gray-500">Company name:</label>
                        <input type="text" id="company_name_edit" className="input-info-dialog"
                               placeholder="Speedy "></input><br/>
                        <div className='flex py-3 text-gray-500'>
                            <button
                                className="action_btn_green px-6 py-2"
                            >
                                Edit
                            </button>
                        </div>
                    </div>
                    <div className="flex flex-row">

                        <div className="flex-column w-1/3">
                            <label className="block  text-gray-500">Employee's first name:</label>
                            <input type="text" id="employee_first_name" className="input-info-dialog"
                                   placeholder="John "></input>
                            <label className="block  text-gray-500">Employee's last name:</label>
                            <input type="text" id="employee_last_nname" className="input-info-dialog"
                                   placeholder="Doe"></input>
                            <div className='flex py-3 text-gray-500'>

                                <button
                                    className="action_btn_blue px-3 py-1.5"
                                >
                                    Add
                                </button>
                            </div>
                        </div>

                        <div className=" flex-column w-1/3">
                            <label className="block  text-gray-500">Client's first name:</label>
                            <input type="text" id="client_first_name" className="input-info-dialog"
                                   placeholder="Jane"></input>
                            <label className="block  text-gray-500">Client's last name:</label>
                            <input type="text" id="client_last_name" className="input-info-dialog"
                                   placeholder="Doe"></input>
                            <div className='flex py-3 text-gray-500'>
                                <button
                                    className="action_btn_blue px-3 py-1.5"
                                >
                                    Add
                                </button>
                            </div>
                        </div>
                        <div className=" flex-column w-1/3">
                            <label className="block  text-gray-500">Office location:</label>
                            <input type="text" id="office_location" className="input-info-dialog"
                                   placeholder="Sofia"></input>
                            <div className='flex py-3 text-gray-500'>
                                <button
                                    className="action_btn_blue px-3 py-1.5"
                                >
                                    Add
                                </button>
                            </div>
                        </div>

                    </div>


                    <button
                        className="action_btn_red px-6 py-1.5 fixed bottom-5 right-8"
                    >
                        DELETE COMPANY
                    </button>

                </BaseDialog>
            )
        }

        {/*/!* clients + employees *!/*/}

        {data &&
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
        {data &&
            <Table
                columns={officeColumns}
                categories={officeCategories}
                data={data
                    .filter(item => item?.address !== undefined )
                    .map((item) => {
                        const companyId = item.companyId as any;
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