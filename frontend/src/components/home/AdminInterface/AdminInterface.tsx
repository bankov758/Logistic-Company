"use client";
import React, {useCallback, useEffect, useState} from "react";
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
import {
    searchCategories,
    searchColumns
} from "@/data/admin/searchUsersTableData";

import {getSession, Session} from "@/lib/auth";
import ShowCompanyInfo from "@/components/home/AdminInterface/ShowCompanyInfo";
import CreateCompanyForm from "@/components/home/AdminInterface/CreateCompanyForm";

import {getCompanies} from "@/lib/actions";
import Notification from "@/components/UI/Notification";

import SkeletonLoadingAnimation from "@/components/UI/SkeletonLoadingAnimation";

const AdminInterface: React.FC = () => {
    const [session, setSession] = useState<null | Session>(null);
    const [data, setData] = useState<item[][] | null>(null);

    const [actionSuccessMessage, setActionSuccessMessage] = useState<{  message: string; errors: string; }>({ message: "", errors: ""})
    const [error, setError] = useState<AxiosError | Error | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [tryAgain, setTryAgain] = useState<boolean>(false);

    const [companies, setCompanies] = useState<selectorItem[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<selectorItem | null>(null);

    const [searchData, setSearchData ] = useState<item[] | null>(null);
    const [searchedUsername, setSearchedUsername] = useState("");

    const [showCompanyInfoDialog, setShowCompanyInfoDialog] = useState<boolean>(false)
    const [showCreateCompanyDialog, setShowCreateCompanyDialog] = useState<boolean>(false)

    // whenever the selectedCompany state change, the entire AdminInterface React component is re-executed
    const fetchData = useCallback(() => {
        if( selectedCompany ) {
            setIsLoading(true);
            Promise.all([
                //clients
                axios.get(`/companies/${selectedCompany.id}/clients`),
                //employees
                axios.get(`/companies/${selectedCompany.id}/employees`),
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
                    setIsLoading(false);
                })
                .catch(error => {
                    setError(error);
                    setIsLoading(false);
                });
        }
    }, [selectedCompany]);

    const fetchUserData = useCallback( () => {
        setIsLoading(true);
        axios.get(`/users?search=${searchedUsername}`)
            .then(response => {
                setSearchData(response.data)
                setIsLoading(false);
                })
            .catch(error => {
                setError(error);
                setIsLoading(false);});
        }, [searchedUsername])


    useEffect(() => {
        getSession()
            .then( async (response) => {
                setSession(response)
                await getAndSetCompanies();
            });
    }, []);

    useEffect(() => {
        fetchData();
    }, [fetchData, tryAgain]);

    const getAndSetCompanies = async () => {
        try {
            const companies = await getCompanies();

            if( companies ) {
                setCompanies(companies);
            } else {
                setError(new Error("Something went wrong!"))
            }
        } catch (error) {
            if( error instanceof Error || error instanceof AxiosError ) setError(error)
        }
    }

    const handleSelectCompany = (data: selectorItem) => {
        setSelectedCompany(data);
    }

    // company info dialog actions START

    const onSuccessCreateCompany = async () => {
        setShowCreateCompanyDialog(false);
        await getAndSetCompanies()
    }

    const onSuccessCompanyOrOfficeAction = async () => {
        setShowCompanyInfoDialog(false);
        setSelectedCompany(null);
        await getAndSetCompanies();
        setData([]);
    }

    // company info dialog actions END

    // reset the data
    const onActionSuccess = async (data: {  message: string; errors: string; }) => {
        setSelectedCompany(null);
        await getAndSetCompanies();
        setData([]);
        setSearchData([]);
        setIsLoading(false);
        setActionSuccessMessage(data)
    }

    return <>
        {/*Notification error message*/}
        {error &&
            <Notification status='error'>
                <div className='flex flex-col justify-center items-center w-full'>
                    <p>{error.message}</p>
                    <button className='base-btn-blue' onClick={() => setTryAgain(!tryAgain)}>Try again</button>
                </div>
            </Notification>
        }
        {/*Notification message*/}
        { actionSuccessMessage && (actionSuccessMessage.message || actionSuccessMessage.errors) &&
            <Notification status={actionSuccessMessage.message ? "success" : "error"} timeout={5000} >
                <p>{actionSuccessMessage.message || actionSuccessMessage.errors }</p>
            </Notification>
        }

        <h3 className="flex justify-start w-full px-2 py-2 mb-12">
            Welcome {session?.username || "user"}! You&apos;re logged in as admin.
        </h3>

        {/* search for user by username BEGIN */}
        <div className="flex justify-center gap-x-5 items-center" >
                <label htmlFor="clientName" className="" >Search for client with name: </label>
                <input type="text" id="clientName" name="clientName" className="input-info-dialog" placeholder="username" value={searchedUsername}
                       onChange={(e) => setSearchedUsername(e.target.value)}/>
                <Button fill={true} onClick={() => {fetchUserData(); setSelectedCompany(null); }}>Search</Button>
        </div>
        {/* search for user by username END */}

        {/* select a company dropdown */}
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
                onClick={() => setShowCreateCompanyDialog(true)}
            >
                Create a company
            </Button>
        </div>
        {/* create company dialog  */}
        {showCreateCompanyDialog &&
            <BaseDialog title="Create a company" tryClose={() => setShowCreateCompanyDialog(false)}>
                <CreateCompanyForm onSuccess={onSuccessCreateCompany} />
            </BaseDialog>
        }
        {/* company info dialog */}
        {showCompanyInfoDialog && selectedCompany &&
            (
                <BaseDialog
                    title={ "Edit company " + selectedCompany.title + "'s information"}
                    tryClose={() => setShowCompanyInfoDialog(false)}
                >
                    <ShowCompanyInfo
                        companyData={companies}
                        selectedCompany={selectedCompany}
                        onSuccessDelete={onSuccessCompanyOrOfficeAction}
                        onSuccessEdit={onSuccessCompanyOrOfficeAction}
                        onSuccessAddOffice={onSuccessCompanyOrOfficeAction}
                    />
                </BaseDialog>
            )
        }
        {/* clients + employees + currier + offices */}
        { isLoading ?
            <SkeletonLoadingAnimation header="tabs" layoutItems={5} /> :
            data && Object.keys(data).length > 0 && selectedCompany && Object.keys(selectedCompany).length > 0 ?

                data.map((individualData: item[], index: number) => {
                    if( !individualData.length ) return;

                    return (
                        <Table
                            key={index}
                            columns={adminColumns[index]}
                            categories={adminCategories[index]}
                            session={session}
                            onActionSuccess={onActionSuccess}
                            selectedCompany={selectedCompany}
                            data=
                                {
                                    individualData.map(item => {

                                        switch (index) {
                                            case 0: // clients
                                                return {
                                                    ...item,
                                                    category: "clients"
                                                }
                                            case 1: // employees
                                                return {
                                                    ...item,
                                                    category: "employees"
                                                }
                                            case 2: // couriers
                                                if (selectedCompany.title === item?.companyName) {
                                                    return {
                                                        ...item,
                                                        category: "couriers",
                                                    };
                                                }
                                                return {
                                                    ...item,
                                                    category: ""
                                                };
                                            case 3: // offices
                                                const companyId: { id: number; name: string; } = item?.companyId;
                                                if (selectedCompany.title === companyId.name) {
                                                    return {
                                                        ...item,
                                                        name: companyId.name,
                                                        category: "offices",
                                                    };
                                                }
                                                return {
                                                    ...item,
                                                    category: ""
                                                };
                                            default:
                                                return {
                                                    ...item,
                                                    category: ""
                                                };

                                        }
                                    })
                                }
                        />
                    )
                }) :
                selectedCompany && <p>No data available!</p>
        }
        {isLoading ?
            <SkeletonLoadingAnimation header="tabs" layoutItems={5}/> :
            searchData && Object.keys(searchData).length > 0 && !selectedCompany ?
                <Table
                    columns={searchColumns}
                    categories={searchCategories}
                    session={session}
                    onActionSuccess={onActionSuccess}
                    data={searchData.map((item) => ({
                        ...item,
                        category: "clients"
                    }))}/>
                : searchedUsername && searchData && !searchData?.length && !selectedCompany ?
                    <p>There is no found user with this username.</p>
                : null
        }
    </>;
}

export default AdminInterface;