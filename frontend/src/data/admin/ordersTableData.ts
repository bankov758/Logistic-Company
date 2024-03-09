import { category, column } from "@/components/home/Table";

//office columns
export const officeColumns: column[] = [
    {
        title: "Address",
        code: "address",
        id: 1,
    },
    {
        title: "Company Name",
        code: "name",
        id: 2,
    },
    {
        title: "Actions",
        code: "adminInterfaceActions",
        hide: true,
        id: 3,
    },
]

//TODO: modify
export const adminColumns: column[][] = [
    [],//client column
    [],//employee column
    [],//admin column
]

export const officeCategories: category[] = [
    {
        title: 'Offices',
        code: 'offices',
        id: 1
    }
];

//client + employee columns
export const tableColumns: column[] = [
    {
        title: "First name",
        code: "firstName",
        id: 1,
    },
    {
        title: "Last name",
        code: "lastName",
        id: 2,
    },
    {
        title: "Actions",
        code: "adminInterfaceActions",
        hide: true,
        id: 6,
    },
    
];

//client + employee category
export const categories: category[] = [
    {
        title: "Clients",
        code: "clients",
        id: 1,
    },
    {
        title: "Employees",
        code: "employees",
        id: 2,
    },
];