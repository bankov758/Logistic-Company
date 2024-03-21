"use client";
import React, { useEffect, useState } from "react";
import Image from "next/image";
import {AxiosError} from "axios";
import axios from "@/lib/axios";

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
import {getCompanies} from "@/lib/actions";

const AdminInterface: React.FC = () => {
    const [session, setSession] = useState<null | Session>(null);
    const [data, setData] = useState<item[][] | null>(null);
    const [error, setError] = useState<AxiosError | null | string>(null);
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
                    const companies = await getCompanies();

                    if( companies ) {
                        setCompanies(companies);
                    } else {
                        setError("Something went wrong!")
                    }
                } catch (err) {
                    if( err instanceof AxiosError ) {
                        setError(err)
                    } 
                }
            });
    }, []);

    const handleSelectCompany = (data: selectorItem) => {
        setSelectedCompany(data);

        Promise.all([
            //clients
            axios.get(`/companies/${data.id}/clients`),
            //employees
            axios.get(`/companies/${data.id}/employees`),
            //couriers
            axios.get(`/couriers`),
            //offices
            axios.get("/offices")
        ])
            .then(([clientsData, employeesData, courierData, officesData]) => {
                const combinedData = [
                    clientsData.data,
                    employeesData.data,
                    courierData.data,
                    officesData.data
                ];
                setData(combinedData);
            })
            .catch(error => setError(error));
    }

    return <>
        {error &&
            <Notification status='error'>
                <div className='flex flex-col justify-center items-center w-full'>
                    <p>{error instanceof AxiosError ? error.message : error}</p>
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
                    <ShowCompanyInfo session={session} companyData={companies} selectedCompany={selectedCompany.title}/>
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
                                            } else if( index === 2 ) {//couriers
                                                //const courierId: { id: number; companyName: string; } = item?.;
                                                if (selectedCompany.title === item?.companyName) {
                                                    return {
                                                        ...item,
                                                        category: "couriers",
                                                    };
                                                }
                                            } else if( index === 3 ) {//offices
                                                const companyId: { id: number; name: string; } = item?.companyId;
                                                if (selectedCompany.title === companyId.name) {
                                                    return {
                                                        ...item,
                                                        name: companyId.name,
                                                        category: "offices",
                                                    };
                                                }
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