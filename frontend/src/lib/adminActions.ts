"use server"; //module level Server Actions defined by 'use server' React directive

import { z } from "zod";
import {useEffect, useState} from "react";
import {getSession} from "@/lib/auth";
import {selectorItem} from "@/components/UI/DataSelectorWrapper";

export interface Company {
    id: number;
    name: string;
    income: number;
}
export const handleAddCompany = async ( newCompanyName: any, session: { username: string; roles: string[]; } | null | undefined) => {
    try {
        if (!newCompanyName.trim()) {
            console.error('Company name cannot be empty');
            return;
        }
        const requestData = {
            name: newCompanyName.trim(),
        };

        const response = await fetch('http://localhost:8080/api/companies', {
            method: 'POST',
            body: JSON.stringify(requestData),
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });

        if (response.ok) {
            console.log('Company created successfully');
        } else {
            console.error('Failed to create company:', response.statusText);
        }
    } catch (error) {
        console.error('Error creating company:', error);
    }
};

export const deleteCompany = async (companyName: string, companyData: Company[] ,session: { username: string; roles: string[]; } | null | undefined) => {

    const companyId = getCompanyId(companyName, companyData);
    try {
        const response = await fetch(`http://localhost:8080/api/companies/${companyId}`, {
            method: 'DELETE',
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to delete company")
        }else {
            console.log("Company successfully deleted!")

        }
    } catch (error) {
        console.error("Failed to delete the company", error);
    }
}
export const getCompanyId = (companyName: string, companyData: Company[]) => {
    const foundCompany = companyData.find(company => company.name === companyName);
    if (foundCompany) {
        return foundCompany.id;
    }

    return null;
}
export const editCompany = async (companyName: string, updatedCompanyName: string, companyData: Company[] , session: { username: string; roles: string[]; } | null | undefined) => {

    const companyId = getCompanyId(companyName, companyData);
    const requestedData = {
        id: companyId,
        name: updatedCompanyName
    }
    console.log(companyId);
    console.log(updatedCompanyName);
    console.log(requestedData);


    try {
        const response = await fetch(`http://localhost:8080/api/companies`, {
            method: 'PUT',
            body: JSON.stringify(requestedData),
            headers: {
                Authorization: session?.username || '',

                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to update company")
        }else {
            console.log("Company successfully updated!")

        }
    } catch (error) {
        console.error("Failed to update the company", error);
    }
}
